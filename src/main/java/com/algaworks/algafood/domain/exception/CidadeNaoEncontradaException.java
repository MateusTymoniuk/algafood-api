package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(Long grupoId) {
        super(String.format("Não existe cadastro de cidade de código %d.", grupoId));
    }
}
