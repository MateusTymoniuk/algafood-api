package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.input.ItemPedidoInputDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Endereco, EnderecoDTO> enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoDTOTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

        modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
