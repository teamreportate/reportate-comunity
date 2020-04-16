package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.enums.NroReportes;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.service.ReportesService;
import bo.com.reportate.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import bo.com.reportate.model.pojo.ArchivoPojo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate
 * date:    11-04-2020
 * author:  Merlo
 **/

@Slf4j
@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    @Autowired
    private ReportesService reportesService;
    @Autowired private PacienteService pacienteService;


    @PostMapping("/ficha-epidemiologica")
    public ResponseEntity fichaEpidemiologica(@PathVariable Long pacienteId) {
        try {
            List list =new ArrayList();
            FichaEpidemiologicaResponse obj = this.pacienteService.getFichaEpidemiologica(pacienteId);
            if(list.size() !=0) {
                ArchivoPojo pdf = generarReporte(list, NroReportes.RP01, obj);
                return ok(pdf);
            }
            return ok().build();
        }catch (OperationException e){
            log.error("Error al generar el reporte de Venta Neta.", e.getMessage());
            return CustomErrorType.badRequest("Generar reporte Venta Neta", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al generar el reporte de Venta Neta.", e);
            return CustomErrorType.serverError("Generar reporte Venta Neta", "Ocurrió un error interno");
        }
    }

    public ArchivoPojo generarReporte(List list, NroReportes nroReportes, Object objeto) {
        ArchivoPojo archivoPojo = reportesService.generarReporte(list , nroReportes, objeto);
        return archivoPojo;
    }
}