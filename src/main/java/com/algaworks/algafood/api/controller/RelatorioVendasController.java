package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.service.report.JasperReportExporter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorio-vendas")
public class RelatorioVendasController {

    final VendaQueryService vendaService;
    final JasperReportExporter relatorioService;

    public RelatorioVendasController(VendaQueryService vendaService, JasperReportExporter relatorioService) {
        this.vendaService = vendaService;
        this.relatorioService = relatorioService;
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                             @RequestParam(required = false, defaultValue = "+00:00") String diferencaHora) {
        return vendaService.consultarVendasDiarias(filtro, diferencaHora);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarDiariasPdf(VendaDiariaFilter filtro,
                                  @RequestParam(required = false, defaultValue = "+00:00") String diferencaHora) {

        List<VendaDiaria> vendas = vendaService.consultarVendasDiarias(filtro, diferencaHora);
        String location = "vendas-diarias.jasper";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
        byte [] bytesRelatorio = relatorioService.emitirRelatorio(vendas, location, null);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(bytesRelatorio);
    }
}