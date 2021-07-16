package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.UsuarioAtualizacaoInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAtualizacaoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioAtualizacaoInputDTO usuarioAtualizacaoInputDTO) {
        return modelMapper.map(usuarioAtualizacaoInputDTO, Usuario.class);
    }

    public void copyInputToEntity(UsuarioAtualizacaoInputDTO usuarioAtualizacaoInputDTO, Usuario usuario) {
        modelMapper.map(usuarioAtualizacaoInputDTO, usuario);
    }
}
