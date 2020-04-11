package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Restaurante extends EntidadeBase {

    private static final long serialVersionUID = -6278033933237725258L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name = "id_cozinha")
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;
    private boolean ativo = true;
    private boolean aberto = false;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento;

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private Set<Usuario> responsaveis;

    // Ativo/Inativo

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    // Abertura/Fechamento

    public void abrir() {
        setAberto(true);
    }

    public void fechar() {
        setAberto(false);
    }

    // Forma de Pagamento

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        if (this.getFormasPagamento() == null)
            this.setFormasPagamento(new HashSet<>());

        if (aceitaFormaPagamento(formaPagamento))
            return true;

        return this.getFormasPagamento().add(formaPagamento);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        if (this.getFormasPagamento() == null)
            return true;

        return this.getFormasPagamento().remove(formaPagamento);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
        return this.getFormasPagamento().contains(formaPagamento);
    }

    // Responsáveis

    public boolean adicionarResponsavel(Usuario responsavel) {
        if (this.getResponsaveis() == null)
            this.setResponsaveis(new HashSet<>());

        if (this.getResponsaveis().contains(responsavel))
            return false;

        return this.getResponsaveis().add(responsavel);
    }

    public boolean removerResponsavel(Usuario responsavel) {
        if (this.getResponsaveis() == null)
            return true;

        return this.getResponsaveis().remove(responsavel);
    }
}