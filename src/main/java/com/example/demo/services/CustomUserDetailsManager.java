package com.example.demo.services;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.demo.entities.EntityUser;
import com.example.demo.model.SecurityUser;
import com.example.demo.repositories.UserRepository;

@Service
public class CustomUserDetailsManager implements UserDetailsManager {

	@Autowired
    private UserRepository userRepository;
    
    @Override
    public void createUser(UserDetails user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
}
