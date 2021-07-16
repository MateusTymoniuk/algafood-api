package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteInputDTO restauranteInputDTO) {
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

    public void copyInputToEntity(RestauranteInputDTO restauranteInputDTO, Restaurante restaurante) {
        /*
        Para evitar:
        Caused by: org.hibernate.HibernateException:
        identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered
         */
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteInputDTO, restaurante);
    }
}
