package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDTODisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroRestauranteService service;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteInputDTODisassembler restauranteInputDTODisassembler;

    @GetMapping
    public List<RestauranteDTO> listar() {
        List<Restaurante> restaurantes = repository.findAll();
        return restauranteDTOAssembler.toCollectionDTO(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscarPorId(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscar(restauranteId);
        return restauranteDTOAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO salvar(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {
            Restaurante restaurante = restauranteInputDTODisassembler.toEntity(restauranteInputDTO);
            return restauranteDTOAssembler.toDTO(service.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInputDTO restauranteInputDTO) {
        try {
//            Restaurante restaurante = restauranteInputDTODisassembler.toEntity(restauranteInputDTO);
            Restaurante restauranteEncontrado = service.buscar(restauranteId);

            restauranteInputDTODisassembler.copyInputToEntity(restauranteInputDTO, restauranteEncontrado);

//            BeanUtils.copyProperties(restaurante, restauranteEncontrado,
//                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteDTOAssembler.toDTO(service.salvar(restauranteEncontrado));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


}
