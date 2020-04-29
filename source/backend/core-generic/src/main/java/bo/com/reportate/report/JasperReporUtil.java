package bo.com.reportate.report;

import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JasperReporUtil {


    public static final String ReporteFichaEpidemiologica = "reports/report3.jrxml";

    public static byte[] generarReporte(HashMap parametros, Collection<?> lista, String path) {
        byte[] byteArray = null;
        try {
            log.info("Obteniendo reporte de la ruta 2: {}", path);

//            File initialFile = new ClassPathResource(path).getFile(); // JBOSS
//            InputStream fonte = new DataInputStream(new FileInputStream(initialFile));

            InputStream fonte = new ClassPathResource(path).getInputStream(); // SPRING BOOT

            byteArray = generarReporte(parametros, null);
        } catch (Exception e) {
            log.error("Error al obtener archivo jasper:{}", path, e);
        }
        return byteArray;
    }

    private static byte[] generarReporte(@NotNull HashMap parametros, Collection<?> lista, InputStream inputStream) {
        byte[] byteArray = null;
        try {
            JasperReport report = JasperCompileManager.compileReport(inputStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros);
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

    public static byte[] generarReportePaciente(HashMap<String, Object> parametros, List<FichaEpidemiologicaResponse> listFichaEpidemiologicaResp){
        try{
            JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listFichaEpidemiologicaResp);
            String jasperFileName = "reports/report3.jasper";
            String jrxmlFileName = "reports/report3.jrxml";
//            JasperCompileManager.compileReportToFile(new ClassPathResource(jrxmlFileName).getPath(), new ClassPathResource(jasperFileName).getPath());
//            JasperCompileManager.compileReport(new ClassPathResource(jrxmlFileName).getInputStream());
            JasperReport report = JasperCompileManager.compileReport(new ClassPathResource(jrxmlFileName).getInputStream());
            log.info("Resultado de la ruta" + jasperFileName);
            JasperPrint jprint = JasperFillManager.fillReport(report, parametros, beanCollectionDataSource);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jprint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            return byteArrayOutputStream.toByteArray();
        }
        catch (JRException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] generarReporte(HashMap parametros, Collection<?> lista) {
        byte[] byteArray = null;
        try {


            log.info("PATH REPORT  [{}]", new ClassPathResource("reports/ficha.jasper").getPath());
            InputStream inputStream = new ClassPathResource("reports/ficha.jasper").getInputStream();
            JasperReport jasperReport =  (JasperReport) JRLoader.loadObject(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            exporter.exportReport();
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (JRException e) {
            log.error("Error al compilar el Jasper.", e);
        } catch (Exception e) {
            log.error("Error inesperado al compilar el Jasper", e);
        }
        return byteArray;
    }

    private static JasperReport getJasperReport(byte[] jasperBlob )
            throws JRException, IOException {
        InputStream is;
        is = new ByteArrayInputStream(jasperBlob);
        is.close();
        return (JasperReport) JRLoader.loadObject(is);
    }
}
