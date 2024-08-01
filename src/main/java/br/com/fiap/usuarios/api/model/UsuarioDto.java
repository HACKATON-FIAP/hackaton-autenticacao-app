package br.com.fiap.usuarios.api.model;

import br.com.fiap.usuarios.domain.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "Nome precisa ser enviado")
    private String nome;

    private String username;

    @NotBlank(message = "Email precisa ser enviado")
    private String email;

    private String password;

    private String passwordConfirmation;

    private Role role;
}
