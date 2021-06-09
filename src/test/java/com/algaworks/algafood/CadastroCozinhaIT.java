package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Test
    public void deveCadastrarUmaCozinhaComSucesso() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Nova cozinha");

        cozinha = cadastroCozinhaService.salvar(cozinha);

        assertNotNull(cozinha);
        assertNotNull(cozinha.getId());
    }

    @Test
    public void deveLancarExcecao_QuandoCadastrarCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                assertThrows(ConstraintViolationException.class, () -> {
                    cadastroCozinhaService.salvar(novaCozinha);
                });

        assertNotNull(erroEsperado);
    }

    @Test
    public void deveLancarExcecao_QuandoExcluirCozinhaInexistente() {
        assertThrows(CozinhaNaoEncontradaException.class, () ->
                cadastroCozinhaService.excluir(Long.MAX_VALUE)
        );
    }

    @Test
    public void deveLancarExcecao_QuandoExcluirCozinhaEmUso() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Nova cozinha");

        cozinha = cadastroCozinhaService.salvar(cozinha);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante");
        restaurante.setTaxaFrete(BigDecimal.ONE);
        restaurante.setCozinha(cozinha);

        cadastroRestauranteService.salvar(restaurante);

        Cozinha finalCozinha = cozinha;
        assertThrows(EntidadeEmUsoException.class, () ->
                cadastroCozinhaService.excluir(finalCozinha.getId())
        );
    }
}
