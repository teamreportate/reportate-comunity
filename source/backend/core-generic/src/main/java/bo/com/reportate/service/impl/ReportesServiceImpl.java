package bo.com.reportate.service.impl;

import bo.com.reportate.model.enums.NroReportes;
import bo.com.reportate.model.pojo.ArchivoPojo;
import bo.com.reportate.report.JasperReporUtil;
import bo.com.reportate.report.JasperReportMime;
import bo.com.reportate.service.ReportesService;
import bo.com.reportate.sign.utils.FileUtil;
import bo.com.reportate.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Autor      :Alvaro Merlo
 * Date       :11-04-22
 * Project    :reportate
 * Package    :bo.com.reportate
 */
@Slf4j
@Service("reportesService")
public class ReportesServiceImpl implements ReportesService {
    @Override

    public ArchivoPojo generarReporte(List list, NroReportes nRep, Object objeto) {
        ArchivoPojo archivoPojo = new ArchivoPojo();
        byte[] byteArray = null;
        try{

            String tipoReporte ="";
            //Genera los parametros estandarizados para los reportes Representacion Grafica
            //Genera Parametros por Defecto
            // HashMap parametros = generateParamsStandar(escenario, planta, reporteTipo);
            HashMap parametros = new HashMap();
//            if (nRep == NroReportes.RP01) {
                tipoReporte = JasperReporUtil.ReporteFichaEpidemiologica;
//            }
            byteArray= JasperReporUtil.generarReporte(parametros, list, tipoReporte);

        }
        catch (Exception e) {
            byteArray = null;
            log.error("No se pudo generar el reporte:" + list, e);
        } finally {
            archivoPojo.setNombre(DateUtil.toString(DateUtil.FORMAT_FILE, new Date()) + " Reportate" + JasperReportMime.pdfType);
            archivoPojo.setLongitud(byteArray == null ? 0:byteArray.length);
            archivoPojo.setArchivoBase64(byteArray==null ? null: FileUtil.byteToBase64(byteArray));
            archivoPojo.setTipo(JasperReportMime.pdfType);
            archivoPojo.setTipoMime(MediaType.APPLICATION_PDF.toString());
        }
        return archivoPojo;
    }
}
