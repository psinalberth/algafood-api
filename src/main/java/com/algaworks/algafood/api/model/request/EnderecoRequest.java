package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoRequest {

    @ApiModelProperty(example = "Avenida A", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "5B", required = true, position = 5)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Ao lado do Posto Magnólia", position = 10)
    private String complemento;

    @ApiModelProperty(example = "65477-468", required = true, position = 15)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Cohafuma", required = true, position = 20)
    @NotBlank
    private String bairro;

    @ApiModelProperty(position = 25)
    @Valid
    @NotNull
    private CidadeIdRequest cidade;
}