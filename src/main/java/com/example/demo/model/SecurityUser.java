package com.example.demo.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.EntityUser;

public class SecurityUser implements UserDetails {

	private final EntityUser entityUser;
    
    public SecurityUser(EntityUser entityUser) {
        this.entityUser = entityUser;
    }
    
    
    @Override
    public String getUsername() {
        return this.entityUser.getUsername();
    }
    
    @Override
    public String getPassword() {
        return this.entityUser.getPassword();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.entityUser.getAuthority());
        //return Arrays.asList(() -> this.entityUser.getAuthority());
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
