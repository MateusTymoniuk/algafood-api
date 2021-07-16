package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    @Autowired
    private ProdutoDTOAssembler produtoDTOAssembler;

    @Autowired
    private ProdutoInputDTODisassembler produtoInputDTODisassembler;

    @GetMapping
    public List<ProdutoDTO> buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscar(restauranteId);
        List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
        return produtoDTOAssembler.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);
        return produtoDTOAssembler.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
                                @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        Produto produto = produtoInputDTODisassembler.toEntity(produtoInputDTO);
        produto = cadastroProdutoService.salvar(produto, restauranteId);
        return produtoDTOAssembler.toDTO(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO alterar(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
        Produto produto = cadastroProdutoService.buscar(restauranteId, produtoId);
        produtoInputDTODisassembler.copyInputToEntity(produtoInputDTO, produto);
        produto = cadastroProdutoService.salvar(produto, restauranteId);
        return produtoDTOAssembler.toDTO(produto);
    }
}
