package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        return formaPagamentoDTOAssembler.toCollectionDTO(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO buscarPorId(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscar(formaPagamentoId);
        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO salvar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamento = formaPagamentoInputDTODisassembler.toEntity(formaPagamentoInputDTO);
            return formaPagamentoDTOAssembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
                                       @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
        try {
            FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscar(formaPagamentoId);

            formaPagamentoInputDTODisassembler.copyInputToEntity(formaPagamentoInputDTO, formaPagamentoAtual);

            return formaPagamentoDTOAssembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.excluir(formaPagamentoId);
    }
}
