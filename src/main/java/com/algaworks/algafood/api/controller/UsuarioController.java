package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioAtualizacaoInputDTODisassembler;
import com.algaworks.algafood.api.disassembler.UsuarioCadastroInputDTODisassembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioAtualizacaoInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioAtualizacaoSenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioCadastroInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    private UsuarioCadastroInputDTODisassembler usuarioCadastroInputDTODisassembler;

    @Autowired
    private UsuarioAtualizacaoInputDTODisassembler usuarioAtualizacaoInputDTODisassembler;

    @GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioDTOAssembler.toCollectionDTO(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscar(usuarioId);
        return usuarioDTOAssembler.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO salvar(@RequestBody @Valid UsuarioCadastroInputDTO usuarioCadastroInputDTO) {
        Usuario usuario = usuarioCadastroInputDTODisassembler.toEntity(usuarioCadastroInputDTO);
        cadastroUsuarioService.salvar(usuario);
        return usuarioDTOAssembler.toDTO(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(@PathVariable Long usuarioId,
                                @RequestBody @Valid UsuarioAtualizacaoInputDTO usuarioAtualizacaoInputDTO) {
        Usuario usuarioAtual = cadastroUsuarioService.buscar(usuarioId);
        usuarioAtualizacaoInputDTODisassembler.copyInputToEntity(usuarioAtualizacaoInputDTO, usuarioAtual);
        cadastroUsuarioService.salvar(usuarioAtual);
        return usuarioDTOAssembler.toDTO(usuarioAtual);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long usuarioId) {
        cadastroUsuarioService.excluir(usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId,
                             @RequestBody @Valid UsuarioAtualizacaoSenhaInputDTO usuarioAtualizacaoSenhaInputDTO) {
        cadastroUsuarioService.atualizarSenha(usuarioId,
                usuarioAtualizacaoSenhaInputDTO.getSenha(),
                usuarioAtualizacaoSenhaInputDTO.getNovaSenha());
    }
}
