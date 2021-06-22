package warehouse.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
	 @Bean
	    public PasswordEncoder passwordEncoder() {    	
	        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    }
}
