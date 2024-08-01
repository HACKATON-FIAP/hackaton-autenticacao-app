package br.com.fiap.usuarios.dados;

import br.com.fiap.usuarios.api.model.UsuarioDto;
import br.com.fiap.usuarios.domain.model.Usuario;

import java.time.LocalDate;

public class ClienteDados {

    public Usuario criarCliente1(){
        return Usuario.builder()
                .id(1L)
                .nome("Bruno Silveira")
                .email("bruno.silveira@gmail.com")
                .build();
    }

    public Usuario criarCliente2(){
        return Usuario.builder()
                .id(1L)
                .nome("Bruno Otávio")
                .build();
    }

    public UsuarioDto criarClienteDto1(){
        return UsuarioDto.builder()
                .id(1L)
                .nome("Bruno Silveira")
                .email("bruno.silveira@gmail.com")
                .build();
    }

    public UsuarioDto criarClienteDto2(){
        return UsuarioDto.builder()
                .id(1L)
                .nome("Bruno Otávio")
                .build();
    }
}
