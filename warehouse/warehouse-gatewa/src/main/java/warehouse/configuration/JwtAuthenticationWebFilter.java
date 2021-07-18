package warehouse.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.jwt.JWTConverter;

@Component
@Slf4j
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {
	private static final String BEARER = "Bearer ";
	private static final ReactiveAuthenticationManager emptyAuthManager = (Authentication auth) -> Mono.just(auth);
	@Autowired
    private JWTConverter jwtConverter;

	public JwtAuthenticationWebFilter() {
		super(emptyAuthManager);  
		setServerAuthenticationConverter(jwtAuthConverter());
	}

	private ServerAuthenticationConverter jwtAuthConverter() {
		return new ServerAuthenticationConverter() {
			@Override
			public Mono<Authentication> convert(ServerWebExchange exchange) {
				ServerHttpRequest request = exchange.getRequest();
				String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
				log.debug("AUTH: {}", authorization);
				if (!StringUtils.startsWithIgnoreCase(authorization, BEARER)) {
					log.debug("invalid AUTH header");
					return Mono.empty();
				}
				String token = authorization.substring(BEARER.length(), authorization.length());
				try {
					return Mono.just(jwtConverter.tokenToAuthentication(token));
				} catch (JwtException ex) {
					log.debug("Authentication failed {}", ex.getMessage());
					return Mono.error(new BadCredentialsException("Authentication failed"));
				}
			}

		};
	}
}
