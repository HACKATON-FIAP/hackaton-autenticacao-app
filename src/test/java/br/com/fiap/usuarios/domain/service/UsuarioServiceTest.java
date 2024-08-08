package br.com.fiap.usuarios.domain.service;

import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.UsuarioDTODataFactory;
import br.com.fiap.usuarios.domain.exception.UsuarioNaoEncontradoException;
import br.com.fiap.usuarios.domain.model.Usuario;
import br.com.fiap.usuarios.domain.model.UsuarioDataFactory;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    private AutoCloseable closeable;
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Nested
    class ServiceToRepositoryAutenticar {
        @Nested
        class sucess {
            @Test
            void deveEncontrarUsuario() {
                // Arrange
                AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();
                Usuario usuario = UsuarioDataFactory.criarUsuarioBuilder();

                //mock
                when(usuarioRepository.findByUsername(autenticarDto.getUsername())).thenReturn(usuario);

                // Act
                var user = usuarioService.loadUserByUsername(autenticarDto.getUsername());
                Usuario u = (Usuario) user;

                // Assert
                assertNotNull(u.getDataCadastro());
                Assertions.assertNotNull(user);
                Assertions.assertEquals(usuario, user);
                verify(usuarioRepository, times(1)).findByUsername(autenticarDto.getUsername());
            }
        }
        @Nested
        class exception {
            @Test
            void deveRetornarExceptionQuandoUsuarioNaoForEncontrado() {
                // Arrange
                AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();

                //mock
                when(usuarioRepository.findByUsername(autenticarDto.getUsername())).thenReturn(null);



                // Act & Assert
                assertThrows(UsuarioNaoEncontradoException.class, () -> {
                    usuarioService.loadUserByUsername(autenticarDto.getUsername());
                });
            }
        }
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }
}