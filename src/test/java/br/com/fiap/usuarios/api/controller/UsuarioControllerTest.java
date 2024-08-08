package br.com.fiap.usuarios.api.controller;

import br.com.fiap.usuarios.api.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.TokenDto;
import br.com.fiap.usuarios.api.model.UsuarioDTODataFactory;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import br.com.fiap.usuarios.domain.service.JwtService;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class UsuarioControllerTest {
    private AutoCloseable closeable;

    @InjectMocks
    private UsuarioController usuarioController;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Nested
    class ControllerToServiceAutenticar {
        @Nested
        class sucess {

            @Test
            void deveAutenticarControllerSettersAndGetters() {
                // Arrange
                AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();
                Authentication authentication = new UsernamePasswordAuthenticationToken(autenticarDto.getUsername(), autenticarDto.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

                //mock
                when(authenticationManager.authenticate(any())).thenReturn(authentication);
                when(jwtService.generateToken(any(), any())).thenReturn("valid_token");

                // Act
                TokenDto usuarioAutenticado = usuarioController.autenticar(autenticarDto);
                assertNotNull(usuarioAutenticado);
            }

        }

        @Nested
        class exception {
            @Test
            void deveRetornarExceptionQuandoUsuarioNaoForAutenticado() {
                // Arrange
                AutenticarDto autenticarDto = UsuarioDTODataFactory.criarAutenticarDTOBuilder();
                Authentication authentication = new UsernamePasswordAuthenticationToken(autenticarDto.getUsername(), autenticarDto.getPassword());

                //mock
                when(authenticationManager.authenticate(any())).thenReturn(authentication);

                // Act & Assert
                assertThrows(UsuarioNaoAutorizadoException.class, () -> {
                    usuarioController.autenticar(autenticarDto);
                });
            }
        }
    }


    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

}
