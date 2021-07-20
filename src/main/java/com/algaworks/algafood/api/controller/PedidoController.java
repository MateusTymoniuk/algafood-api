package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.ConsultaPedidoService;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoDTOAssembler pedidoDTOAssembler;

    @Autowired
    private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @Autowired
    private PedidoInputDTODisassembler pedidoInputDTODisassembler;

    @Autowired
    private ConsultaPedidoService consultaPedidoService;

    @GetMapping
    public List<PedidoResumoDTO> pesquisar(PedidoFilter filtro) {
        List<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecification.filtrarPedidos(filtro));
        return pedidoResumoDTOAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido) {
        Pedido pedido = consultaPedidoService.buscar(codigoPedido);
        return pedidoDTOAssembler.toDTO(pedido);
    }

    @PostMapping
    public PedidoDTO  salvar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        Pedido pedido = pedidoInputDTODisassembler.toEntity(pedidoInputDTO);
        pedido = emissaoPedidoService.salvar(pedido);
        return pedidoDTOAssembler.toDTO(pedido);
    }
}
