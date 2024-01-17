package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectConfig {

	@Autowired
    //private AuthenticationProviderService authenticationProvider;
	
	@Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        
        http.httpBasic(Customizer.withDefaults());
        
        http.authenticationProvider(null);
        
        http.authorizeHttpRequests(c -> c.anyRequest().authenticated()); //.permitAll()
                
        return http.build();
    }
	
	@Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
}
