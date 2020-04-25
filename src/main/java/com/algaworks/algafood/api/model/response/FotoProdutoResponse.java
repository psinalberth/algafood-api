package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoResponse {

    @ApiModelProperty(example = "9f64bd0c-86c3-4776-b1df-2a68b63b4e73_1bbe774dcb7ef2e37afa524e20aece0b.jpg")
    private String nomeArquivo;

    @ApiModelProperty(example = "khai-phad-catalogo")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "100393")
    private Long tamanho;
}