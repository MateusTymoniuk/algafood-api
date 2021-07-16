package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long cozinhaId) {
        super(String.format("Não existe cadastro de forma de pagamento de código %d.", cozinhaId));
    }
}
