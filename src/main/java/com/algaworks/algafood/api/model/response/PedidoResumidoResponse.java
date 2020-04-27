package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumidoResponse extends RepresentationModel<PedidoResumidoResponse> {

    @ApiModelProperty(example = "05d2b496-9751-4d4d-9875-71444271fc25")
    private String codigo;

    @ApiModelProperty(position = 5)
    private RestauranteResumidoResponse restaurante;

    @ApiModelProperty(position = 10)
    private UsuarioResponse cliente;

    @ApiModelProperty(example = "CRIADO", position = 15)
    private String status;

    @ApiModelProperty(example = "116.00", position = 20)
    private BigDecimal subtotal;

    @ApiModelProperty(example = "7.50", position = 25)
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "123.50", position = 30)
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "2020-04-11T00:03:25Z", position = 35)
    private OffsetDateTime dataCriacao;
}