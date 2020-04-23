package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeIdRequest {

    @ApiModelProperty(example = "2111300", required = true)
    @NotNull
    private Long id;
}