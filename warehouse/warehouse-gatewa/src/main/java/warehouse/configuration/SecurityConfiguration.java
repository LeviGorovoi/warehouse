package warehouse.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import warehouse.dto.security.UserData;

import static warehouse.dto.api.SecurityApi.*;
import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import java.util.*;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	@Value("${app-username-admin:admin}")
	String usernaneAdmin;
	@Value("${app-password-admin:****}")
	String passwordAdmin;

	@Autowired
	JwtAuthenticationWebFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	public Map<String, UserData> createUsersInMemory () {
		Map<String, UserData> map = new HashMap<>();
		UserData admin = UserData.builder().roles(new String[] {"ROLE_ADMIN"}).password("{noop}"+passwordAdmin)
				.username(usernaneAdmin).build();
		map.put(usernaneAdmin, admin);
		return map;
	}
	
	
	@Bean
	SecurityWebFilterChain securityFiltersChain(ServerHttpSecurity httpSecurity) {
		SecurityWebFilterChain securityFiltersChain = httpSecurity.csrf().disable().headers().frameOptions().disable()
				.and().httpBasic().disable()
				.authorizeExchange()			
				.pathMatchers(SECURITY+REGISTER,  
						SECURITY+USER_DATA, 
						WAREHOUSE_STATE_BACK+ROLE_CREATE,
						WAREHOUSE_STATE_BACK+OPERATOR_CREATE, 
						WAREHOUSE_STATE_BACK+SET_OPERATOR_TO_ROLE,
						WAREHOUSE_STATE_BACK+CHANGE_ROLE)
				.hasAnyRole("SECURITY_MANAGER", "ADMIN")
				.pathMatchers(WAREHOUSE_STATE_BACK+PRODUCT_CREATE,
						WAREHOUSE_STATE_BACK+CONTAINER_CREATE)
				.hasAnyAuthority("WAREHOUSE_MANAGER")
				.pathMatchers(SECURITY+LOGIN).permitAll()
				.pathMatchers(SECURITY+PASSWORD_CHANGE).authenticated()
				.anyExchange().hasRole("ADMIN")
				.and().addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
		return securityFiltersChain;
		
	}
}
