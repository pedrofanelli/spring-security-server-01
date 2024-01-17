package com.example.demo.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

	@Override
    public Authentication authenticate (Authentication authentication) throws AuthenticationException {
        
    }
    
    @Override
    public boolean supports(Class<?> authenticationType) {
        
    }
	
}
