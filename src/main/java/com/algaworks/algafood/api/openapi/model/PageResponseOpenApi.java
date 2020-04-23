package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por página")
    private int pageSize;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private int totalElements;

    @ApiModelProperty(example = "0", value = "Número da página (inicia em 0)")
    private int pageNumber;

    @ApiModelProperty(example = "5", value = "Total de páginas")
    private int totalPages;
}