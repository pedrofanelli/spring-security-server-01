package com.example.demo.repositories;

import org.springframework.stereotype.Service;

/**
 * En realidad sería una interfaz de JPA, buscando el objeto en la base
 */
@Service
public class UserRepository {

	public EntityUser findUserByUsername(String username) {
        // TESTEO
        if (username.equals("Peter")) {
            EntityUser user = new EntityUser();
            user.setAuthority("ROLE_USER");
            user.setPassword("$2a$10$p.gkjoexWwT.rom4bvfW9.lYXGIZo.XcA0ffdduIT6LOQxd1j527S");
            user.setUsername("Peter");
            return user;
        } else if (username.equals("Lara")) {
            EntityUser user = new EntityUser();
            user.setAuthority("ROLE_ADMIN");
            user.setPassword("$2a$10$p.gkjoexWwT.rom4bvfW9.lYXGIZo.XcA0ffdduIT6LOQxd1j527S");
            user.setUsername("Lara");
            return user;
        } else {
            return null;
        }
        
    }
	
}
