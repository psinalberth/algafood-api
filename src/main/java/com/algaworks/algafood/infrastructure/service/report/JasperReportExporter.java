package com.algaworks.algafood.infrastructure.service.report;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportExporter {

    @Value("${reports.jasper.path}")
    private Path diretorioRelatorios;

    public byte [] emitirRelatorio(List<?> dados, String arquivoRelatorio, Map<String, Object> parametros) {
        try {
            String caminhoRelatorio = Paths.get(diretorioRelatorios.toString(), arquivoRelatorio).toString()
                    .replace("\\", "/");
            var inputStream = this.getClass().getResourceAsStream(caminhoRelatorio);
            var dataSource = new JRBeanCollectionDataSource(dados);
            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RelatorioException("Não foi possível emitir o relatório.");
        }
    }
}