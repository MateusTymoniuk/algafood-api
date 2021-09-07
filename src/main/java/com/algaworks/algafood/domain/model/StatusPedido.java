package com.algaworks.algafood.domain.model;

import java.util.List;

import static java.util.Arrays.asList;

public enum StatusPedido {
    CRIADO("criado"),
    CONFIRMADO("confirmado", CRIADO),
    ENTREGUE("entregue", CONFIRMADO),
    CANCELADO("cancelado", CRIADO);

    private String descricao;
    private List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = asList(statusAnteriores);
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }
}
