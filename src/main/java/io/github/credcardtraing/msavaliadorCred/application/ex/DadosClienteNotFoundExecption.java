package io.github.credcardtraing.msavaliadorCred.application.ex;

public class DadosClienteNotFoundExecption extends Exception{
    public DadosClienteNotFoundExecption() {
        super("Dados Cliente não encontrado para este CPF");
    }
}
