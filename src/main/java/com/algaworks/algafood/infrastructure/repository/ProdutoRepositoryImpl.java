package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto fotoProduto) {
        return em.merge(fotoProduto);
    }

    @Override
    public void delete(FotoProduto fotoProduto) {
        em.remove(fotoProduto);
    }
}
