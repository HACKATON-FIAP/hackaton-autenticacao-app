package br.com.fiap.usuarios.api.controller;

import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.TokenDto;
import br.com.fiap.usuarios.api.model.UsuarioDTODataFactory;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import br.com.fiap.usuarios.domain.service.JwtService;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioControllerIntegrationTest {

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
                TokenDto tokenReturn = usuarioController.autenticar(autenticarDto);
                assertThat(tokenReturn).isNotNull();
            }
        }
    }
}


