package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.service.report.JasperReportExporter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.algaworks.algafood.api.AlgaLinks.linkToEstatisticasVendasDiarias;

@RestController
@RequestMapping(path = "/estatisticas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstatisticasController implements EstatisticasControllerOpenApi {

    final VendaQueryService vendaService;
    final JasperReportExporter reportExporter;

    public EstatisticasController(VendaQueryService vendaService, JasperReportExporter reportExporter) {
        this.vendaService = vendaService;
        this.reportExporter = reportExporter;
    }

    @GetMapping
    public EstatisticasModel estatisticas() {
        var estatisticas = new EstatisticasModel();

        estatisticas.add(linkToEstatisticasVendasDiarias("vendasDiarias"));

        return estatisticas;
    }

    @Override
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String diferencaHora) {
        return vendaService.consultarVendasDiarias(filtro, diferencaHora);
    }

    @Override
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
                                                      @RequestParam(required = false, defaultValue = "+00:00") String diferencaHora) {

        List<VendaDiaria> vendas = vendaService.consultarVendasDiarias(filtro, diferencaHora);
        String location = "vendas-diarias.jasper";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
        byte [] bytesRelatorio = reportExporter.emitirRelatorio(vendas, location, null);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(bytesRelatorio);
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {

    }
}