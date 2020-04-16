package bo.com.reportate.service;

import bo.com.reportate.model.enums.NroReportes;
import bo.com.reportate.model.pojo.ArchivoPojo;

import java.util.List;


public interface ReportesService {

    ArchivoPojo generarReporte(List list, NroReportes nRep, Object objeto);
}


