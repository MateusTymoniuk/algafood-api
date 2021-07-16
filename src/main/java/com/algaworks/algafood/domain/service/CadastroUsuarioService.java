package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    public Usuario buscar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioOptional.isPresent() && !usuarioOptional.get().equals(usuario)) {
            throw new NegocioException(format("Já existe um usuário cadastrado com o email %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    public void excluir(Long usuarioId) {
        Usuario usuario = buscar(usuarioId);
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public void atualizarSenha(Long usuarioId, String senha, String novaSenha) {
        Usuario usuario = buscar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senha)) {
            throw new NegocioException("Houve um erro, verifique os dados enviados");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscar(grupoId);

        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscar(usuarioId);
        Grupo grupo = cadastroGrupoService.buscar(grupoId);

        usuario.desassociarGrupo(grupo);
    }
}
