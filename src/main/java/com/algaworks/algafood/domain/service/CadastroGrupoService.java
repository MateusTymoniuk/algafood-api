package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido pois está em uso.";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    public Grupo buscar(Long grupoId) {
        return grupoRepository.findById(grupoId).orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void excluir(Long grupoId) throws EntidadeEmUsoException, EntidadeNaoEncontradaException {
        try {
            grupoRepository.deleteById(grupoId);
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscar(permissaoId);

        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscar(grupoId);
        Permissao permissao = cadastroPermissaoService.buscar(permissaoId);

        grupo.desassociarPermissao(permissao);
    }
}
