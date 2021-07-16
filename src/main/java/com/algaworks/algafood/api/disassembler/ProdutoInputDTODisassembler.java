package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toEntity(ProdutoInputDTO produtoInputDTO) {
        return modelMapper.map(produtoInputDTO, Produto.class);
    }

    public void copyInputToEntity(ProdutoInputDTO produtoInputDTO, Produto produto) {
        modelMapper.map(produtoInputDTO, produto);
    }
}
