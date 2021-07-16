package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static java.lang.String.format;

@Service
public class FluxoPedidoService {

    @Autowired
    private ConsultaPedidoService consultaPedidoService;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = alterarStatusPedido(codigoPedido, StatusPedido.CRIADO, StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = alterarStatusPedido(codigoPedido, StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE);
        pedido.setDataEntrega(OffsetDateTime.now());
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = alterarStatusPedido(codigoPedido, StatusPedido.CRIADO, StatusPedido.CANCELADO);
        pedido.setDataCancelamento(OffsetDateTime.now());
    }

    private Pedido alterarStatusPedido(String codigoPedido, StatusPedido statusAnteriorPermitido, StatusPedido statusNovo) {
        Pedido pedido = consultaPedidoService.buscar(codigoPedido);

        statusPedidoPermiteAlteracao(codigoPedido, statusAnteriorPermitido, statusNovo, pedido);

        pedido.setStatus(statusNovo);
        return pedido;
    }

    private void statusPedidoPermiteAlteracao(String codigoPedido, StatusPedido statusAnteriorPermitido, StatusPedido statusNovo, Pedido pedido) {
        if (!statusAnteriorPermitido.equals(pedido.getStatus())) {
            throw new NegocioException(format("O pedido %s de status %s não pode ser alterado para %s.",
                    codigoPedido, pedido.getStatus().getDescricao(), statusNovo.getDescricao()));
        }
    }
}
