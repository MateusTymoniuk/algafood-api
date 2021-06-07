package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{
    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        super(String.format("Não existe cadastro de cozinha de código %d.", cozinhaId));
    }
}
