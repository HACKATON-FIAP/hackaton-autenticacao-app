package br.com.fiap.usuarios.domain.repository;

import br.com.fiap.usuarios.api.controller.UsuarioController;
import br.com.fiap.usuarios.domain.model.Usuario;
import br.com.fiap.usuarios.domain.model.UsuarioDataFactory;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioRepositoryIntegrationTest {

    @Autowired
    private UsuarioController usuarioController;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;

    private final static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
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
    class repositoryFindByUsernameEntity {
        @Test
        void deveEncontrarUsuarioBuilder() {
            Usuario usuario = UsuarioDataFactory.criarUsuarioBuilder();
            Usuario usuarioSalvo = usuarioRepository.findByUsername(usuario.getUsername());
            assertThat(usuarioSalvo).isNotNull();
            assertEquals(usuario.getId(), usuarioSalvo.getId());
            assertEquals(usuario.getEmail(), usuarioSalvo.getEmail());
            assertEquals(usuario.getNome(), usuarioSalvo.getNome());
            assertEquals(usuario.getPassword(), usuarioSalvo.getPassword());
            assertEquals(usuario.getUsername(), usuarioSalvo.getUsername());
        }
    }

}
