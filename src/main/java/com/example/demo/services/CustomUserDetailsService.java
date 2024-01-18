package com.example.demo.services;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException("Problem during authentication!");

        //EntityUser u = userRepository.findUserByUsername(username).orElseThrow(s);

        EntityUser u = userRepository.findUserByUsername(username); //usuario JPA
        if (u == null) {
            throw s.get();
        }
        
        return new SecurityUser(u); //usuario SpringSecurity
    }
    
    
    /*
    private final List<UserDetails> users;
    
    public CustomUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return users.stream().filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
    }
    */
	
}
