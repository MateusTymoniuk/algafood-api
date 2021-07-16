package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.inativar();
    }

    @Transactional
    public void ativarEmLote(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void inativarEmLote(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscar(restauranteId);
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);

        restaurante.desassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscar(restauranteId);
        restaurante.fechar();
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscar(usuarioId);

        restaurante.associarResponsavel(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscar(restauranteId);
        Usuario usuario = cadastroUsuarioService.buscar(usuarioId);

        restaurante.desassociarResponsavel(usuario);
    }
}
