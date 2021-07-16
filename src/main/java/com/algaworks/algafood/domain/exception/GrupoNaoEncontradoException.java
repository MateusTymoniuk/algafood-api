package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(Long grupoId) {
        super(String.format("Não existe cadastro de grupo de código %d.", grupoId));
    }
}
