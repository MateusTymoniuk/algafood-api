package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        super(String.format("Não existe cadastro de produto %s para o restaurante de código %s", produtoId, restauranteId));
    }
}
