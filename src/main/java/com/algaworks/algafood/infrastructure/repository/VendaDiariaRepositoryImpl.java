package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.repository.VendaDiariaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaDiariaRepositoryImpl implements VendaDiariaRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<VendaDiaria> buscarRelatorio(VendaDiariaFilter filtro) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = criteriaBuilder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);

        Expression<Date> dateFunction = criteriaBuilder.function("date", Date.class, root.get("dataCriacao"));

        Selection<? extends VendaDiaria> select = criteriaBuilder.construct(VendaDiaria.class,
                dateFunction,
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.sum(root.get("valorTotal")));

        query.select(select);
        query.groupBy(dateFunction);

        ArrayList<Predicate> predicates = new ArrayList<>();

        if(filtro.getRestauranteId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }

        if(filtro.getDataCriacaoInicio() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if(filtro.getDataCriacaoFim() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getResultList();
    }
}
