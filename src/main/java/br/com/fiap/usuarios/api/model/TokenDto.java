package br.com.fiap.usuarios.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

    private String token;
}
