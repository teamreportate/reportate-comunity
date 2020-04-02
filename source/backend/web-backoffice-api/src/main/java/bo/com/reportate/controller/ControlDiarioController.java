package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.ControlDiarioFullResponse;
import bo.com.reportate.model.dto.response.SintomaResponse;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.service.ControlDiarioService;
import bo.com.reportate.service.LogService;
import bo.com.reportate.util.CustomErrorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/control-diario")
@Slf4j
@Tag(name = "control_diario", description = "API para registro de controles diarios")
public class ControlDiarioController {
    @Autowired private ControlDiarioService controlDiarioService;
    @Autowired private LogService logService;

    @RequestMapping(value = "/encuesta-inicial", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtención de encuestas inicial", description = "Método para obtener la encuesta de control diario inicial", tags = { "control_diario" })
    public ResponseEntity<ControlDiarioFullResponse> getEncuestaFull() {
        try {
            return ok(controlDiarioService.getEncuentaFull());
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener las encuenta inicial. Causa. {}",e.getMessage());
            logService.error(Process.CONTROL_DIARIO,"Se genero un error al obtener las encuenta inicial. Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Obtener Encuesta", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener las encuenta inicial.",e);
            logService.error(Process.CONTROL_DIARIO,"Se genero un error al obtener las encuenta inicial");
            return CustomErrorType.serverError("Obtener Encuesta", "Se genero un error al obtener las encuenta inicial.");
        }
    }

    @RequestMapping(value = "/encuesta-diaria", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtención de encuestas diaria", description = "Método para obtener la encuesta de control diario", tags = { "control_diario" })
    public ResponseEntity<List<SintomaResponse>> getEncuesta() {
        try {
            return ok(controlDiarioService.getEncuesta());
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener las encuenta inicial. Causa. {}",e.getMessage());
            logService.error(Process.CONTROL_DIARIO,"Se genero un error al obtener las encuenta inicial. Causa. {}", e.getMessage());
            return CustomErrorType.badRequest("Obtener Encuesta", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener las encuenta inicial.",e);
            logService.error(Process.CONTROL_DIARIO,"Se genero un error al obtener las encuenta inicial");
            return CustomErrorType.serverError("Obtener Encuesta", "Se genero un error al obtener las encuenta inicial.");
        }
    }

}
