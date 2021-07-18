package warehouse.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.SecretKey;
@Component
public class JWTConverter {
	@Value("${jwt.expirationTimeSec:600}")
    private int expirationTimeSec;
    
    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    SecretKey key = Keys.secretKeyFor(algorithm); // random key of length >= 256 bytes

    /**
	 * Generates Java Web Token, extracting userName and roles from Authentication instance
	 * @return the jwtToken
	 */
    public String authenticationToToken(Authentication authentication){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeSec * 1000))
                .claim("roles", AuthorityUtils.authorityListToSet(authentication.getAuthorities()))                
                .signWith( key, algorithm)
                .compact();
    }

    /**
	 * Builds Authentication instance from Java Web Token
	 * @param jwtToken
	 * @return null if token parse failed
	 * @throws JwtException if token expired or has insufficient claims
	 */
    public Authentication tokenToAuthentication(String token)throws JwtException {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		String username = claims.getSubject();
		List<?> roleList = claims.get("roles", List.class);
		if (username == null || roleList == null) {
			throw new JwtException("Insufficient claims are specified");
		}

        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                AuthorityUtils.createAuthorityList(roleList.toArray(new String[0]))
        );
    }
}
