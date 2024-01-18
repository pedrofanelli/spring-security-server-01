package com.example.demo.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Filter01 extends OncePerRequestFilter {

	@Override
    public void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = request;
        HttpServletResponse httpResponse = response;
        String requestId = request.getHeader("Custom-Header");
        if (requestId == null || requestId.equals("")) {
            //httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //return;
            response.setHeader("Custom-Header", "161616");
            System.out.println("REQUEST ID HEADER ES NULL!");
        }
        
        System.out.println(response.getHeader("Custom-Header"));
        filterChain.doFilter(request, response);
        
    }
}
