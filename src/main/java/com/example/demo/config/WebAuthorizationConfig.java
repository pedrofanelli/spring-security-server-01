package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.filters.Filter01;
import com.example.demo.filters.Filter02;
import com.example.demo.services.CustomAuthenticatorProvider;

@Configuration
public class WebAuthorizationConfig {

	
	@Autowired
    private CustomAuthenticatorProvider authenticationProvider;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .formLogin(c -> c.defaultSuccessUrl("/hello", true))
            .addFilterBefore(new Filter01(), BasicAuthenticationFilter.class)
            .addFilterAfter(new Filter02(), BasicAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests(c -> c.requestMatchers("/hello").hasRole("USER")
            		                     .requestMatchers("/ciao").hasRole("ADMIN")
            
        	/*
        	.authorizeRequests(authorizeRequests ->
                //authorizeRequests.anyRequest().hasRole("ADMIN")
            
            
            authorizeRequests.mvcMatchers("/hello").hasRole("USER")
                                 .mvcMatchers("/ciao").hasRole("ADMIN")
                                 .anyRequest().permitAll()   */
            );
        
        return http.build();
    }
}
