package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaDiariaRepository {

    List<VendaDiaria> buscarRelatorio(VendaDiariaFilter filtro, String offset);
}
