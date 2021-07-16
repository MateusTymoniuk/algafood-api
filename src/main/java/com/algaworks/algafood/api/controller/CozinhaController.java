package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    private CozinhaInputDTODisassembler cozinhaInputDTODisassembler;

    @GetMapping
    public List<CozinhaDTO> listar() {
        return cozinhaDTOAssembler.toCollectionDTO(cozinhaRepository.findAll());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
        return cozinhaDTOAssembler.toDTO(cozinhaService.buscar(cozinhaId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO salvar(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinha = cozinhaInputDTODisassembler.toEntity(cozinhaInputDTO);
        return cozinhaDTOAssembler.toDTO(cozinhaService.salvar(cozinha));
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDTO atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
        Cozinha cozinhaEncontrada = cozinhaService.buscar(cozinhaId);

        cozinhaInputDTODisassembler.copyInputToEntity(cozinhaInputDTO, cozinhaEncontrada);

        return cozinhaDTOAssembler.toDTO(cozinhaService.salvar(cozinhaEncontrada));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }
}
