package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.repository.VendaDiariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private VendaDiariaRepository vendaDiariaRepository;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> buscar(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00")
                                    String offset) {
        return vendaDiariaRepository.buscarRelatorio(filtro, offset);
    }
}
