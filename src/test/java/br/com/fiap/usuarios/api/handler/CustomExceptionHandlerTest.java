package br.com.fiap.usuarios.api.handler;

import br.com.fiap.usuarios.api.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.usuarios.domain.exception.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class CustomExceptionHandlerTest {

    @Test
    void deveRetornarNotFoundParaUsuarioNaoEncontradoException() {

        CustomExceptionHandler handler = new CustomExceptionHandler();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/autenticacao");
        var message = "Usuário não encontrado";
        var status = HttpStatus.NOT_FOUND;

        UsuarioNaoEncontradoException exception = new UsuarioNaoEncontradoException(message);
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(LocalDateTime.parse("2024-08-08T17:08:44.158345200"));
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setPath(request.getRequestURI());
        ResponseEntity.status(status).body(errorMessage);

        ResponseEntity<ErrorMessage> response = handler.clienteNaoEncontrado(exception, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deveRetornarForbiddenParaUsuarioNaoEncontradoException() {
        CustomExceptionHandler handler = new CustomExceptionHandler();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/autenticacao");

        var message = "Usuário não Autenticado";
        var status = HttpStatus.FORBIDDEN;

        UsuarioNaoAutorizadoException exception = new UsuarioNaoAutorizadoException(message);

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(LocalDateTime.parse("2024-08-08T17:08:44.158345200"));
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setPath("teste usuario não encontrado");

        ResponseEntity.status(status).body(errorMessage);
        ResponseEntity<ErrorMessage> response = handler.usuarioNaoAutorizado(exception, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
