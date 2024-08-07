package br.com.fiap.usuarios.domain.exception;

public class SenhasNaoCombinamException extends RuntimeException {
    public SenhasNaoCombinamException(String mensagem) {
        super(mensagem);
    }
}