package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiProblem {

    private OffsetDateTime timestamp;
    private Integer status;
    private String title;
    private String type;
    private String uri;
    private String userMessage;
    private List<ApiProblem.Detail> details;

    @Builder
    @Getter
    static class Detail {
        private String name;
        private String userMessage;
    }
}
