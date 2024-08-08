package br.com.fiap.usuarios.domain.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioTest {
    @Nested
    class modelEntityCriacao {
        @Test
        void deveCriarModelUsuarioBuilder() {
            Usuario usuario = UsuarioDataFactory.criarUsuarioBuilder();
            Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

            usuario.isAccountNonExpired();
            usuario.isAccountNonLocked();
            usuario.isCredentialsNonExpired();
            usuario.isEnabled();

            assertNotNull(usuario);
            assertEquals(1L, usuario.getId());
            assertEquals("adj2@fiap.com.br", usuario.getEmail());
            assertEquals("adj2", usuario.getNome());
            assertEquals("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK", usuario.getPassword());
            assertEquals("adj2", usuario.getUsername());
            assertEquals(1, authorities.size());
            assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
            assertEquals(true, usuario.isAccountNonExpired());
            assertEquals(true, usuario.isAccountNonLocked());
            assertEquals(true, usuario.isCredentialsNonExpired() );
            assertEquals(true, usuario.isEnabled() );

        }

        @Test
        void deveCriarModelUsuarioSettersAndGetters() {
            Usuario usuario = UsuarioDataFactory.criarUsuarioSettersAndGetters();
            Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

            usuario.isAccountNonExpired();
            usuario.isAccountNonLocked();
            usuario.isCredentialsNonExpired();
            usuario.isEnabled();

            assertNotNull(usuario);
            assertEquals(1L, usuario.getId());
            assertEquals("adj2@fiap.com.br", usuario.getEmail());
            assertEquals("adj2", usuario.getNome());
            assertEquals("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK", usuario.getPassword());
            assertEquals("adj2", usuario.getUsername());
            assertEquals(1, authorities.size());
            assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
            assertEquals(true, usuario.isAccountNonExpired());
            assertEquals(true, usuario.isAccountNonLocked());
            assertEquals(true, usuario.isCredentialsNonExpired() );
            assertEquals(true, usuario.isEnabled() );
        }
    }
}
