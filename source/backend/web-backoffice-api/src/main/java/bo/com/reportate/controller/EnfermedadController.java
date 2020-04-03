package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.service.EnfermedadService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/enfermedades")
@Slf4j
@Tag(name = "enfermedad", description = "API de enfermedades")
public class EnfermedadController {
    @Autowired private EnfermedadService enfermedadService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar enfermedades", description = "Listar enfermedades", tags = { "enfermedades" })
    public ResponseEntity<List<EnfermedadResponse>> controlDiario() {
        try {
            return ok(this.enfermedadService.list());
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar las enfermedades: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar Enfermedades", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al listar  enfermedades:",e);
            return CustomErrorType.serverError("Listar Enfermedades", "Se genero un error al listar enfermedades");
        }
    }
}
