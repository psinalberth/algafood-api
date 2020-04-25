package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante",
                              example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial de criação do pedido",
                              example = "2020-04-25T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final de criação do pedido",
                              example = "2020-04-26T23:59:59Z", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(
            VendaDiariaFilter filtro,
            @ApiParam(value = "Diferença em horas em relação ao UTC", defaultValue = "+00:00") String diferencaHora
    );


    ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, String diferencaHora);
}