package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoInputDTO {

    @Valid
    @NotNull
    RestauranteIdInputDTO restaurante;

    @Valid
    @NotNull
    FormaPagamentoIdInputDTO formaPagamento;

    @Valid
    @NotNull
    EnderecoInputDTO endereco;

    @Valid
    @NotNull
    @Size(min = 1)
    List<ItemPedidoInputDTO> itens;
}
