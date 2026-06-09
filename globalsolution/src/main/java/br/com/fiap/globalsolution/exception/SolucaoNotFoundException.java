package br.com.fiap.globalsolution.exception;

public class SolucaoNotFoundException extends RuntimeException {

    public SolucaoNotFoundException(Long id) {
        super("Solução não encontrada com o ID: " + id);
    }

    public SolucaoNotFoundException(String mensagem) {
        super(mensagem);
    }
}
