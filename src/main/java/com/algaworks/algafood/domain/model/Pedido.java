package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends EntidadeBase {

    private static final long serialVersionUID = -4741163426490670818L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne
    @JoinColumn
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    public void confirmar() {
        setStatus(StatusPedido.CONFIRMADO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar() {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar() {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    public void setStatus(StatusPedido status) {
        var mensagem = String.format("Status do pedido %s não pode ser alterado de %s para %s.",
                getCodigo(), getStatus().getDescricao(), status.getDescricao());

        if (!getStatus().isNovoStatusPermitido(status)) {
            throw new NegocioException(mensagem);
        }

        this.status = status;
    }

    public boolean podeSerConfirmado() {
        return getStatus().isNovoStatusPermitido(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerEntregue() {
        return getStatus().isNovoStatusPermitido(StatusPedido.ENTREGUE);
    }

    public boolean podeSerCancelado() {
        return getStatus().isNovoStatusPermitido(StatusPedido.CANCELADO);
    }

    public void calcularValorTotal() {
        this.getItens().forEach(ItemPedido::calcularPrecoTotal);
        this.subtotal = this.getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    @PrePersist
    public void gerarCodigo() {
        setCodigo(UUID.randomUUID().toString());
    }
}