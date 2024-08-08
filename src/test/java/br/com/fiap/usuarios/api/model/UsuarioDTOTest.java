package br.com.fiap.usuarios.api.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioDTOTest {
    @Nested
    class modelDtoCriacao {

        @Test
        void deveCriarModelPagamentoDTOSettersAndGetters() {
            AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();
            TokenDto tokenDto = UsuarioDTODataFactory.criarTokenDTOBuilder();
            UsuarioDto usuarioDto = UsuarioDTODataFactory.criarUsuarioDtoBuilder();

            //assert autenticar
            assertEquals("adj2", autenticarDto.getUsername());
            assertEquals("adj@1234", autenticarDto.getPassword());

            //assert tokenDto
            assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFkajIiLCJpYXQiOjE3MjMwMDA1MDksImV4cCI6MTcyMzAwNjUwOX0.55_0GVq5sHAB8kp3UVBZx5Y5WVJJ0PABiBAU12mhJ2o", tokenDto.getToken());

            //assert usuarioDto
            assertEquals(1L, usuarioDto.getId());
            assertEquals("adj2@fiap.com.br", usuarioDto.getEmail());
            assertEquals("adj2", usuarioDto.getNome());
            assertEquals("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK", usuarioDto.getPassword());
            assertEquals("adj2", usuarioDto.getUsername());

        }
    }
}
