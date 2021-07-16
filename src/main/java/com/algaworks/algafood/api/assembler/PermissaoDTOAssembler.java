package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao Permissao) {
        return modelMapper.map(Permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
