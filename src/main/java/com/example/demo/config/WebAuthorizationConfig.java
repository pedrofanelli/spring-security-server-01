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

import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
public class WebAuthorizationConfig {

	/**
     * Link para activar autorizacion (AUTHORIZATION ENDPOINT)
     * [GET /oauth2/authorization/google HTTP/1.1
     * va
     * Redirecting to https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=427856162424-gsh9jr9b5miiek5mb1ff2lo02h1imf63.apps.googleusercontent.com&scope=openid%20profile%20email&state=9VL2KQ3rSBBoZrpvov66GtQIEftkcbTdFPm0FFe8_q4%3D&redirect_uri=http://localhost:9090/login/oauth2/code/google&nonce=SYpWMxbXQrPWBEpyl96bMg4CtEkRTuZTym1anppEGcA
     * vuelve
     * (REDIRECTION ENDPOINT)
     * Securing GET /login/oauth2/code/google?state=9VL2KQ3rSBBoZrpvov66GtQIEftkcbTdFPm0FFe8_q4%3D&code=4%2F0AfJohXkq33FrftwK9EKEqG1NXc0mn9weiS2hGvvTy2meuUpdhMFGT7KeTUVULWtggqJVZw&scope=email+profile+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent
     * al finalizar redirige a /hello
     * 
     */
	
	@Autowired
    private CustomAuthenticatorProvider authenticationProvider;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    	RequestCache nullRequestCache = new NullRequestCache();
    	
        http
        	.oauth2Login(c -> c.defaultSuccessUrl("/hello", true))
            .formLogin(c -> c.defaultSuccessUrl("/hello", true))
            //.addFilterBefore(new Filter01(), BasicAuthenticationFilter.class)
            //.addFilterBefore(new Filter02(), BasicAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests(c -> 
            							//c.requestMatchers("/hello").hasRole("USER")
            		                    // .requestMatchers("/ciao").hasRole("ADMIN"));
            c.requestMatchers("/hello").authenticated()				
            .anyRequest().permitAll());
            
        	/*
        	.authorizeRequests(authorizeRequests ->
                //authorizeRequests.anyRequest().hasRole("ADMIN")
            
            
            authorizeRequests.mvcMatchers("/hello").hasRole("USER")
                                 .mvcMatchers("/ciao").hasRole("ADMIN")
                                 .anyRequest().permitAll()   */
            		                     
             /*
             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .requestCache(cache -> cache.requestCache(nullRequestCache));
         */
            
        
        return http.build();
    }
}
