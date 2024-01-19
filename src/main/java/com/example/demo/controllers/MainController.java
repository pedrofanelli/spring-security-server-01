package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
    
    @GetMapping("/ciao")
    public String ciao() {
        return "<a href=\"/oauth2/authorization/google\">Google</a>";
    }
}
