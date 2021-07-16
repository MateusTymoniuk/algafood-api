package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(Long permissaoId) {
        super(String.format("Não existe cadastro de permissao de código %d.", permissaoId));
    }
}
