package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.UsuarioCadastroInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCadastroInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioCadastroInputDTO usuarioCadastroInputDTO) {
        return modelMapper.map(usuarioCadastroInputDTO, Usuario.class);
    }

    public void copyInputToEntity(UsuarioCadastroInputDTO usuarioCadastroInputDTO, Usuario usuario) {
        modelMapper.map(usuarioCadastroInputDTO, usuario);
    }
}
