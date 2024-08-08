package br.com.fiap.usuarios.api.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {
    public UsuarioNaoAutorizadoException(String mensagem) {
        super(mensagem);
    }
}
