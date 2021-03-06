package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toEntity(FormaPagamentoInputDTO formaPagamentoInputDTO) {
        return modelMapper.map(formaPagamentoInputDTO, FormaPagamento.class);
    }

    public void copyInputToEntity(FormaPagamentoInputDTO formaPagamentoInputDTO, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInputDTO, formaPagamento);
    }
}
