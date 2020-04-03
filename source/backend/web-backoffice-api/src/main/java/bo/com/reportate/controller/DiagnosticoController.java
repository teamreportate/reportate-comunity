package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.service.DiagnosticoService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :MC4
 */
@RestController
@RequestMapping("/api/diagnosticos")
@Slf4j
@Tag(name = "diagnostico", description = "API de diagnosticos")
public class DiagnosticoController {
    @Autowired private DiagnosticoService diagnosticoService;

    @RequestMapping(value = "/listar-filtro/{page}/{size}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar los diagnosticos", description = "Listar los diagnosticos", tags = { "diagnostico" })
    public ResponseEntity<Page<DiagnosticoResponseDto>> controlDiario(
            @Parameter(description = "Indice de página", required = true)
            @PathVariable("page") Integer page,
            @Parameter(description = "Tamaño de página", required = true)
            @PathVariable("size") Integer size,
            @Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
            @Parameter(description = "Identificador de Departamento", required = true)
            @RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Clasificacion de diagnostico", required = true)
            @RequestParam("clasificacion")EstadoDiagnosticoEnum clasificacion,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ok(this.diagnosticoService.listarDiagnostico(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), departamentoId, clasificacion, enfermedadId, pageable));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar Diagnostico", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al listar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar Diagnostico", "Se genero un error al listar los diagnosticos");
        }
    }
}
