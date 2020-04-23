package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponse {

    @ApiModelProperty(example = "Avenida A")
    private String logradouro;

    @ApiModelProperty(example = "5B", position = 5)
    private String numero;

    @ApiModelProperty(example = "Ao lado do Posto Magnólia", position = 10)
    private String complemento;

    @ApiModelProperty(example = "65477-468", position = 15)
    private String cep;

    @ApiModelProperty(example = "Cohafuma", position = 20)
    private String bairro;

    @ApiModelProperty(position = 25)
    private CidadeResponse cidade;
}