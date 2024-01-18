package com.example.demo.filters;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Filter02 extends OncePerRequestFilter {

	@Override
    public void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = request;
        HttpServletResponse httpResponse = response;
        String requestId = request.getHeader("Custom-Header");
        if (requestId == null || requestId.equals("")) {
            System.out.println("REQUEST ID HEADER ES NULL :(((");
        } else {
            System.out.println("EL HEADER ID ES: "+requestId);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Current User: " + authentication.getName());
            Object principal = authentication.getPrincipal();
            String username = "";
            if (principal instanceof UserDetails) {
                System.out.println("INSTANCIAAAA");
                username = ((UserDetails) principal).getUsername();
                System.out.println(((UserDetails) principal).getPassword()); 
            } else {
                username = principal.toString();
            }
            System.out.println("NOMBREEE "+username);
            
        } else {
            System.out.println("No authenticated user");
        }
        
        
        System.out.println(request.getSession().getId());
        
        filterChain.doFilter(request, response);
        
    }
}
