package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.response.FamiliaResponse;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.ControlDiarioRequest;
import bo.com.reportate.web.PacienteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
@RequestMapping("/api/pacientes")
@Slf4j
@Tag(name = "paciente", description = "API de pacientes")
public class PacienteController {
    @Autowired private PacienteService pacienteService;
    @Autowired private LogService logService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de paciente a una familia", description = "Guarda un registro de paciente que pertenece a la familia del usuario autentificado", tags = { "paciente" })
    public ResponseEntity<PacienteDto> saveFamilia(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Objeto paciente para registrar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.save(userDetails,pacienteRequest.getNombre(), pacienteRequest.getEdad(),pacienteRequest.getGenero(), pacienteRequest.getGestacion(), pacienteRequest.getTiempoGestacion());
            log.info("Se registro de manera correcta al paciente: {}",pacienteRequest.getNombre());
            logService.info(Process.REGISTRO_FAMILIA,"Se registro de manera correcta el paciente: {}",pacienteRequest.getNombre());
            return ok(responseDto);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el paciente: {}. Causa. {}",pacienteRequest.getNombre(),e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar el paciente: {}. Causa. {}",pacienteRequest.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar Paciente", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar el paciente : {}",pacienteRequest.getNombre(),e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar el paciente : {}",pacienteRequest.getNombre());
            return CustomErrorType.serverError("Guardar Paciente", "Ocurrió un error al guardar el paciente: "+pacienteRequest.getNombre());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un registro de paciente", description = "Actualiza el registro de paciente para el usuario autentificado", tags = { "paciente" })
    public ResponseEntity<PacienteDto> updateFamilia(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Objeto paciente para actualizar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.update(userDetails, pacienteRequest.getId(),
                    pacienteRequest.getNombre(), pacienteRequest.getEdad(), pacienteRequest.getGenero(), pacienteRequest.getGestacion(), pacienteRequest.getTiempoGestacion());
            log.info("Se actualizo de manera correcta el registro del paciente: {}",pacienteRequest.getNombre());
            logService.info(Process.REGISTRO_FAMILIA,"Se actualizo de manera correcta el registro del paciente: {}",pacienteRequest.getNombre());
            return ok(responseDto);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error actualizar el registro del paciente: {}. Causa. {}",pacienteRequest.getNombre(),e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error actualizar el registro del paciente: {}. Causa. {}",pacienteRequest.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Actualizar Paciente", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar la familia : {}",pacienteRequest.getNombre(),e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al actualizar el registro del paciete : {}",pacienteRequest.getNombre());
            return CustomErrorType.serverError("Actualizar Paciente", "Se genero un error al actualizar el registro del paciete: "+pacienteRequest.getNombre());
        }
    }

    @RequestMapping(value = "{pacienteId}/control-diario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de control diario de un paciente", description = "Guarda un registro de control diario para un paciente", tags = { "paciente" })
    public ResponseEntity<String> controlDiario(
            @Parameter(description = "Identificador del paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Objeto de control diario", required = true)
            @RequestBody ControlDiarioRequest controlDiario) {
        try {
            String mensajeDiagnostico = this.pacienteService.controlDiario(pacienteId,controlDiario.getEnfermedadesBase(),controlDiario.getPaises(),controlDiario.getSintomas());
            return ok(mensajeDiagnostico);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error registrar el control diario del paciente: {}. Causa. {}",pacienteId,e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error registrar el control diario del paciente: {}. Causa. {}",pacienteId,e.getMessage());
            return CustomErrorType.badRequest("Control Diario", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al registrar el control diario del paciente : {}",pacienteId,e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al registrar el control diario del paciente : {}",pacienteId);
            return CustomErrorType.serverError("Control Diario", "Se genero un error al registrar el control diario del paciente: " + pacienteId);
        }
    }

    @RequestMapping(value = "/ficha-epidemiologica/{pacienteId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene información  la ficha Epidemiologica del paciente", description = "Obtiene información  la ficha Epidemiologica del paciente segun pacienteId", tags = { "paciente" })
    public ResponseEntity<FichaEpidemiologicaResponse> getFichaEpidemiologica(@PathVariable("pacienteId") Long pacienteId) {
        try {
            return ok(this.pacienteService.getFichaEpidemiologica(pacienteId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener la ficha Epidemiologica. Causa. {} ",e.getMessage());
            //logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al obtener la ficha Epidemiologica . Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Obtener Ficha Epidemiologica", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener la familia del usuario",e);
            //logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al obtener la ficha Epidemiologica");
            return CustomErrorType.serverError("Obtener Ficha Epidemiologica", "Se genero un error al obtener la ficha Epidemiologica");
        }
    }
}
