package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.model.SecurityUser;

@Service
public class CustomAuthenticatorProvider implements AuthenticationProvider {

	//@Autowired
    //private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private CustomUserDetailsManager userDetailsManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public Authentication authenticate (Authentication authentication) throws AuthenticationException {
        
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //SecurityUser user = userDetailsService.loadUserByUsername(username);
        
        SecurityUser user = userDetailsManager.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Bad credentials");
        }
        
        return checkPassword(user, password, bCryptPasswordEncoder);

    }
    
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
    
    private Authentication checkPassword(SecurityUser user, String rawPassword, PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
	
}
