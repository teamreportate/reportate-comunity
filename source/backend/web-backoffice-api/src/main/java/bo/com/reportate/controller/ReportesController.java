package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.enums.NroReportes;
import bo.com.reportate.model.pojo.ArchivoPojo;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.service.ReportesService;
import bo.com.reportate.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


/**
 * Reportate SRL
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


    @GetMapping("/{pacienteId}/ficha-epidemiologica")
    public ResponseEntity fichaEpidemiologica(
            @PathVariable("pacienteId") Long pacienteId) {
        try {
            List list =new ArrayList();
            FichaEpidemiologicaResponse obj = this.pacienteService.getFichaEpidemiologica(pacienteId);
            return ok(generarReporte(list, NroReportes.RP01, obj));
//            return ok().build();
        }catch (OperationException e){
            log.error("Error al generar la ficha epidemiológica. Causa {}", e.getMessage());
            return CustomErrorType.badRequest("Ficha Epidemiológica", e.getMessage());
        } catch (Exception e){
            log.error("Error al generar la ficha epidemiológica.", e);
            return CustomErrorType.serverError("Ficha Epidemiológica", "Ocurrió un error interno");
        }
    }

    private ArchivoPojo generarReporte(List list, NroReportes nroReportes, Object objeto) {
        return reportesService.generarReporte(list , nroReportes, objeto);
    }
}
