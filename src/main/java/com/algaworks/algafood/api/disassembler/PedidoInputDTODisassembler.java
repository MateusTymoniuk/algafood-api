package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toEntity(PedidoInputDTO pedidoInputDTO) {
        return modelMapper.map(pedidoInputDTO, Pedido.class);
    }

    public void copyInputToEntity(PedidoInputDTO pedidoInputDTO, Pedido pedido) {
        modelMapper.map(pedidoInputDTO, pedido);
    }
}
