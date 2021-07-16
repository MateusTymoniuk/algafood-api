package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        Restaurante restaurante = cadastroRestauranteService.buscar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(pedido.getFormaPagamento().getId());
        Cidade cidade = cadastroCidadeService.buscar(pedido.getEndereco().getCidade().getId());
        // Usuário fixo enquanto a autenticação não é implementada
        Usuario usuario = cadastroUsuarioService.buscar(1L);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

        // preencher os itens
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProdutoService.buscar(restaurante.getId(), item.getProduto().getId());
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.setPedido(pedido);
        });

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEndereco().setCidade(cidade);
        pedido.setCliente(usuario);
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }
}
