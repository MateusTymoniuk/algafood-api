package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.Groups.CozinhaId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    @ConvertGroup(to = CozinhaId.class)
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    private Boolean ativo = Boolean.TRUE;

    private Boolean aberto = Boolean.FALSE;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void associarFormaPagamento(FormaPagamento formaPagamento) {
        getFormasPagamento().add(formaPagamento);
    }

    public void desassociarFormaPagamento(FormaPagamento formaPagamento) {
        getFormasPagamento().remove(formaPagamento);
    }

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    public void associarResponsavel(Usuario usuario) {
        getResponsaveis().add(usuario);
    }

    public void desassociarResponsavel(Usuario usuario) {
        getResponsaveis().remove(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
        return !aceitaFormaPagamento(formaPagamento);
    }
}
