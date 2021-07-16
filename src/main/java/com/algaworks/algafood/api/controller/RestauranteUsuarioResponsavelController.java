package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @GetMapping
    public List<UsuarioDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscar(restauranteId);
        return usuarioDTOAssembler.toCollectionDTO(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
