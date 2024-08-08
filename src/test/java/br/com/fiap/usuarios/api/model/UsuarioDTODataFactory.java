package br.com.fiap.usuarios.api.model;

import br.com.fiap.usuarios.domain.enums.Role;

public class UsuarioDTODataFactory {

    public static AutenticarDto criarAutenticarDTOBuilder() {
        return AutenticarDto.builder()
                .username("adj2")
                .password("adj@1234")
                .build();
    }

    public static TokenDto criarTokenDTOBuilder() {
        return TokenDto.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIl0sInN1YiI6ImFkajIiLCJpYXQiOjE3MjMwMDA1MDksImV4cCI6MTcyMzAwNjUwOX0.55_0GVq5sHAB8kp3UVBZx5Y5WVJJ0PABiBAU12mhJ2o")
                .build();
    }

    public static UsuarioDto criarUsuarioDtoBuilder() {
        return UsuarioDto.builder()
                .id(1L)
                .email("adj2@fiap.com.br")
                .nome("adj2")
                .username("adj2")
                .password("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK")
                .role(Role.ROLE_ADMIN)
                .build();
    }

}
