package br.com.fiap.globalsolution.exception;

public class SolucaoCanceladaException extends RuntimeException {

    public SolucaoCanceladaException() {
        super("Não é permitido alterar uma solução com status CANCELADA.");
    }

    public SolucaoCanceladaException(String mensagem) {
        super(mensagem);
    }
}
