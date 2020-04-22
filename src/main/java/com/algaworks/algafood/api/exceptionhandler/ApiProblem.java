package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@ApiModel
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiProblem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "2020-04-22T15:15:02.4579006Z", position = 5)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
    private String type;

    @ApiModelProperty(example = "Dados inválidos", position = 15)
    private String title;

    @ApiModelProperty(example = "/restaurantes", position = 20)
    private String uri;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
            position = 25)
    private String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
    private List<ApiProblem.Detail> details;

    @ApiModel("ApiProblemDetail")
    @Builder
    @Getter
    static class Detail {

        @ApiModelProperty(example = "nome")
        private String name;

        @ApiModelProperty(example = "Nome do restaurante é obrigatório.")
        private String userMessage;
    }
}
