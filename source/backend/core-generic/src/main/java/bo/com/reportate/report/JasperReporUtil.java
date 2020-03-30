package bo.com.reportate.report;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
public class JasperReporUtil {
    public static byte[] generarReporte(HashMap parametros, Collection<?> lista, String path) {
        byte[] byteArray = null;
        try {
            log.info("Obteniendo reporte de la ruta 2: {}", path);

//            File initialFile = new ClassPathResource(path).getFile(); // JBOSS
//            InputStream fonte = new DataInputStream(new FileInputStream(initialFile));

            InputStream fonte = new ClassPathResource(path).getInputStream(); // SPRING BOOT

            byteArray = generarReporte(parametros, lista, fonte);
        } catch (Exception e) {
            log.error("Error al obtener archivo jasper:{}", path, e);
        }
        return byteArray;
    }

    private static byte[] generarReporte(@NotNull HashMap parametros, Collection<?> lista, InputStream inputStream) {
        byte[] byteArray = null;
        try {
            JasperReport report = JasperCompileManager.compileReport(inputStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jprint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (JRException e) {
            log.error("Error de JRException al generar el reporte", e);
        } catch (Exception e) {
            log.error("Error generico al generar reporte ", e);
        }
        return byteArray;
    }
}
