package br.com.fiap.usuarios.domain.model;

import br.com.fiap.usuarios.domain.enums.Role;

import java.time.LocalDateTime;

public class UsuarioDataFactory {
    public static Usuario criarUsuarioBuilder() {
        return Usuario.builder()
                .id(1L)
                .nome("adj2")
                .username("adj2")
                .dataCadastro(LocalDateTime.now())
                .email("adj2@fiap.com.br")
                .password("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK")
                .role(Role.ROLE_ADMIN)
                .build();
    }

    public static Usuario criarUsuarioSettersAndGetters() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("adj2");
        usuario.setUsername("adj2");
        usuario.setDataCadastro(LocalDateTime.now());
        usuario.setEmail("adj2@fiap.com.br");
        usuario.setPassword("$2a$10$p3VRS9KshxFJbc3/aWGSj.NTVvyVM.xVz5tixV.j2dZUJC59PYovK");
        usuario.setRole(Role.ROLE_ADMIN);
        return usuario;
    }
}
