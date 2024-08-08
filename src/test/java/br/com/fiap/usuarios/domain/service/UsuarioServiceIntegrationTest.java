package br.com.fiap.usuarios.domain.service;

import br.com.fiap.usuarios.api.controller.UsuarioController;
import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.UsuarioDTODataFactory;
import br.com.fiap.usuarios.domain.model.Usuario;
import br.com.fiap.usuarios.domain.model.UsuarioDataFactory;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;

    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("db_autenticacao")
            .withUsername("postgres")
            .withPassword("teste123");
    static {
        postgresContainer.start();
        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
    }

    @Nested
    class ControllerToServiceAutenticar {
        @Nested
        class sucess{

            @Test
            void deveAutenticarUsuario() {
                AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();
                Usuario usuario = UsuarioDataFactory.criarUsuarioBuilder();
                usuario.setDataCadastro(LocalDateTime.parse("2024-08-08T15:11:27.091714"));
                var user = usuarioService.loadUserByUsername(autenticarDto.getUsername());
                Usuario u = (Usuario) user;
                assertNotNull(u.getDataCadastro());
                assertThat(user).isNotNull();
            }
        }
    }
}
