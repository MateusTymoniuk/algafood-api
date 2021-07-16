package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ItemPedidoInputDTO {

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private Long produtoId;

    private String observacao;
}
