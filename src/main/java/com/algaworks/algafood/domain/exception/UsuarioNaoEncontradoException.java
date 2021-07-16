package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(Long usuarioId) {
        super(String.format("Não existe cadastro de usuário de código %d.", usuarioId));
    }
}
