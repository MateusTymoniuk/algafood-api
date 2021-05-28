package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.exception.EntidadeNaoEncontradaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CadastroRestauranteService service;

    @GetMapping
    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long restauranteId) {
        Optional<Restaurante> restaurante = repository.findById(restauranteId);

        return restaurante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante novoRestaurante = service.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                       @RequestBody Restaurante restaurante) {
        Optional<Restaurante> optionalRestaurante = repository.findById(restauranteId);

        if(optionalRestaurante.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restauranteEncontrado = optionalRestaurante.get();

        BeanUtils.copyProperties(restaurante, restauranteEncontrado,
                "id", "formasPagamento", "endereco", "dataCriacao", "produtos");

        try {
            restauranteEncontrado = service.salvar(restauranteEncontrado);
            return ResponseEntity.ok(restauranteEncontrado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                       @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> optionalRestaurante = repository.findById(restauranteId);

        if(optionalRestaurante.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restaurante = optionalRestaurante.get();

        merge(campos, restaurante);

        return atualizar(restauranteId, restaurante);
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante atualizacoesRestaurante = objectMapper.convertValue(campos, Restaurante.class);

        campos.forEach((chave, valor) -> {
            Field campo = ReflectionUtils.findField(Restaurante.class, chave);
            campo.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(campo, atualizacoesRestaurante);

            ReflectionUtils.setField(campo, restauranteDestino, novoValor);
        });
    }
}
