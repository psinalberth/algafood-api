package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @ApiModelProperty(example = "Khai Phad Met Ma Muang", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Sauté de Frango com Castanhas de Caju", required = true, position = 5)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "58.00", required = true, position = 10)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true, position = 15)
    @NotNull
    private Boolean ativo;
}