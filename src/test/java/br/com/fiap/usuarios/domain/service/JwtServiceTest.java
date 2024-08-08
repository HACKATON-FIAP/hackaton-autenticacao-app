package br.com.fiap.usuarios.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

class JwtServiceTest {
    private final JwtService jwtService = new JwtService();

    @Test
    void deveExtrairUsuarioCorretoDoToken() {
        String username = "john.doe";
        Collection<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        String token = jwtService.generateToken(username, roles);
        String extractedUsername = jwtService.extractUsername(token);
        Assertions.assertEquals(username, extractedUsername);
    }
    @Test
    void deveExtrairExpiracaoDoToken()  {
        String username = "john.doe";
        Collection<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        String token = jwtService.generateToken(username, roles);
        Date extractedUsername = jwtService.extractExpiration(token);
        Assertions.assertNotNull(extractedUsername);
    }
    @Test
    void deveValidarToken() {
        UserDetails userDetails = User.builder()
            .username("testuser")
            .password("password") // Não precisa ser uma senha válida para testes
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
            .build();

        String token = jwtService.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        String tokenValidator = String.valueOf(jwtService.validateToken(token, userDetails));

        Assertions.assertNotNull(tokenValidator);
        Assertions.assertEquals("true", tokenValidator);
    }
}
