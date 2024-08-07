package br.com.fiap.usuarios.domain.exception;

public class ErroConverterObjetoParaJsonException extends RuntimeException{

    public ErroConverterObjetoParaJsonException(String mensagem) {
        super(mensagem);
    }
}
