package br.com.fiap.usuarios.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutenticarDto {

    private String username;

    private String password;
}
