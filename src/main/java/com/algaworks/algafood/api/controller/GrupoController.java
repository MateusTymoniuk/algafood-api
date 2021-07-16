package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.disassembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @Autowired
    private GrupoInputDTODisassembler grupoInputDTODisassembler;

    @GetMapping
    public List<GrupoDTO> listar() {
        List<Grupo> grupos = grupoRepository.findAll();
        return grupoDTOAssembler.toCollectionDTO(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscarPorId(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscar(grupoId);
        return grupoDTOAssembler.toDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO salvar(@RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        Grupo grupo = grupoInputDTODisassembler.toEntity(grupoInputDTO);

        grupo = cadastroGrupoService.salvar(grupo);

        return grupoDTOAssembler.toDTO(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO salvar(@PathVariable Long grupoId,
                           @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
        Grupo grupoAtual = cadastroGrupoService.buscar(grupoId);

        grupoInputDTODisassembler.copyInputToEntity(grupoInputDTO, grupoAtual);

        cadastroGrupoService.salvar(grupoAtual);

        return grupoDTOAssembler.toDTO(grupoAtual);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId) {
        cadastroGrupoService.excluir(grupoId);
    }
}
