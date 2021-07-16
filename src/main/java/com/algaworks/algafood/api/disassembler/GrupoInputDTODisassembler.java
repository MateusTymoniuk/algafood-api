package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toEntity(GrupoInputDTO grupoInputDTO) {
        return modelMapper.map(grupoInputDTO, Grupo.class);
    }

    public void copyInputToEntity(GrupoInputDTO grupoInputDTO, Grupo grupo) {
        modelMapper.map(grupoInputDTO, grupo);
    }

}
