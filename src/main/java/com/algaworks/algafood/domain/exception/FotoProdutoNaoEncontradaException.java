package com.algaworks.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
        super(String.format("Não existe foto cadastrada para o produto %s do restaurante de código %s", produtoId, restauranteId));
    }
}
