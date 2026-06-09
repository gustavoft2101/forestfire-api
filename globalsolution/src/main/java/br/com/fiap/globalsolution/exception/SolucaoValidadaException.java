package br.com.fiap.globalsolution.exception;

public class SolucaoValidadaException extends RuntimeException {

    public SolucaoValidadaException() {
        super("Não é permitido excluir uma solução com status VALIDADA.");
    }

    public SolucaoValidadaException(String mensagem) {
        super(mensagem);
    }
}
