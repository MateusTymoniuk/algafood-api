package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @GetMapping
    public List<FormaPagamentoDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscar(restauranteId);
        return formaPagamentoDTOAssembler.toCollectionDTO(restaurante.getFormasPagamento());
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }
}
