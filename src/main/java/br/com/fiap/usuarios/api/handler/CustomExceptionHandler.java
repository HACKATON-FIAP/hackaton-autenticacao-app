package br.com.fiap.usuarios.api.handler;

import br.com.fiap.usuarios.api.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.usuarios.domain.exception.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    private final ErrorMessage errorMessage = new ErrorMessage();

    @ExceptionHandler(UsuarioNaoAutorizadoException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> usuarioNaoAutorizado(UsuarioNaoAutorizadoException e, HttpServletRequest request){
        var status = HttpStatus.FORBIDDEN;

        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(e.getMessage());
        errorMessage.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.errorMessage);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> clienteNaoEncontrado(UsuarioNaoEncontradoException e, HttpServletRequest request){
        var status = HttpStatus.NOT_FOUND;

        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setStatus(status.value());
        errorMessage.setMessage(e.getMessage());
        errorMessage.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(this.errorMessage);
    }

}
