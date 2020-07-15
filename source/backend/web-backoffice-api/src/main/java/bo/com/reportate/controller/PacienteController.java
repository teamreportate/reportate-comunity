package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.DiagnosticoRequest;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.dto.response.MovilControlDiario;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.ControlDiarioRequest;
import bo.com.reportate.web.PacienteRequest;
import bo.com.reportate.web.PaisViajeRequest;
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

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :Reportate
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
    public ResponseEntity<PacienteDto> savePaciente(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Objeto paciente para registrar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.save(userDetails,pacienteRequest.getNombre(), pacienteRequest.getEdad(),pacienteRequest.getGenero(), pacienteRequest.getGestacion(), pacienteRequest.getTiempoGestacion(), pacienteRequest.getOcupacion());
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
    @Operation(summary = "Actualiza un registro de paciente desde FrontOffice", description = "Actualiza el registro de paciente para el usuario autentificado", tags = { "paciente" })
    public ResponseEntity<PacienteDto> updatePaciente(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Objeto paciente para actualizar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.update(
                    userDetails, pacienteRequest.getId(), pacienteRequest.getNombre(), pacienteRequest.getEdad(),
                    pacienteRequest.getGenero(), pacienteRequest.getGestacion(), pacienteRequest.getTiempoGestacion(),
                    pacienteRequest.getOcupacion(),pacienteRequest.getCi(),pacienteRequest.getFechaNacimiento(),
                    pacienteRequest.getSeguro(), pacienteRequest.getCodigoSeguro());

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

    @RequestMapping(value = "/{pacienteId}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un registro de paciente", description = "Actualiza el registro de paciente para el usuario autentificado", tags = { "paciente" })
    public ResponseEntity<PacienteDto> updatePaciente(
            @Parameter(description = "Identificador de Paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Objeto paciente para actualizar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.update(pacienteId, pacienteRequest.getNombre(), pacienteRequest.getEdad(),
                    pacienteRequest.getGenero(), pacienteRequest.getGestacion(), pacienteRequest.getTiempoGestacion(),
                    pacienteRequest.getOcupacion(),pacienteRequest.getCi(),pacienteRequest.getFechaNacimiento(),
                    pacienteRequest.getSeguro(), pacienteRequest.getCodigoSeguro());

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

    @RequestMapping(value = "/{pacienteId}/ficha-epidemiologica",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene información  la ficha Epidemiologica del paciente", description = "Obtiene información  la ficha Epidemiologica del paciente segun pacienteId", tags = { "paciente" })
    public ResponseEntity<FichaEpidemiologicaResponse> getFichaEpidemiologica(@PathVariable("pacienteId") Long pacienteId) {
        try {
            return ok(this.pacienteService.getFichaEpidemiologica(pacienteId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener la ficha Epidemiologica. Causa. {} ",e.getMessage());
            return CustomErrorType.badRequest("Obtener Ficha Epidemiologica", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener la ficha epidemiologica",e);
            return CustomErrorType.serverError("Obtener Ficha Epidemiologica", "Se genero un error al obtener la ficha Epidemiologica");
        }
    }

    @RequestMapping(value = "/{pacienteId}/{enfermedadId}/agregar-enfermedad-base",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agregar Enfermedad Base", description = "Agrega una enfermedad base para un paciente", tags = { "paciente" })
    public ResponseEntity<EnfermedadResponse> agregarEnfermedadBase(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Identificador de enfermedad", required = true)
            @PathVariable("enfermedadId") Long enfermedadId) {
        try {
            return ok(this.pacienteService.agregarEnfermedadBase(pacienteId, enfermedadId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al agregar enfermedad base. Causa. {} ",e.getMessage());
            return CustomErrorType.badRequest("Agregar Enfermedad Base", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agregar enfermedad base",e);
            return CustomErrorType.serverError("Agregar Enfermedad Base", "Se genero un error al agregar la enfermedad base");
        }
    }

    @RequestMapping(value = "/{pacienteId}/{enfermedadId}/eliminar-enfermedad-base",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminad Enfermedad Base", description = "Eliminar una enfermedad base para un paciente", tags = { "paciente" })
    public ResponseEntity<EnfermedadResponse> eliminarEnfermedadBase(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Identificador de enfermedad", required = true)
            @PathVariable("enfermedadId") Long enfermedadId) {
        try {
            this.pacienteService.eliminarEnfermedadBase(pacienteId, enfermedadId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al eliminar enfermedad base. Causa. {} ",e.getMessage());
            return CustomErrorType.badRequest("Eliminar Enfermedad Base", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al eliminar enfermedad base",e);
            return CustomErrorType.serverError("Eliminar Enfermedad Base", "Se genero un error al eliminar la enfermedad base");
        }
    }

    @RequestMapping(value = "/{pacienteId}/agregar-pais",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agregar País viajado", description = "Agrega un país al que viajo el paciente", tags = { "paciente" })
    public ResponseEntity<PaisVisitadoDto> agregarPais(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Identificador de País", required = true)
            @RequestBody PaisViajeRequest pais) {
        try {
            return ok(this.pacienteService.agregarPais(pacienteId, pais.getPaisId(), pais.getFechaLlegada(), pais.getFechaSalida(), pais.getCiudad()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al agregar un país. Causa. {} ", e.getMessage());
            return CustomErrorType.badRequest("Agregar País", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error el agregar país visitado.",e);
            return CustomErrorType.serverError("Agregar País", "Se genero un error al agregar país");
        }
    }

    @RequestMapping(value = "/{controlPaisId}/editar-pais",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualizar País viajado", description = "Actualizar un país al que viajo el paciente", tags = { "paciente" })
    public ResponseEntity<PaisVisitadoDto> editarPaises(
            @Parameter(description = "Identificador del Control Pais ", required = true)
            @PathVariable("controlPaisId") Long controlPaisId,
            @Parameter(description = "Objeto de País Viajado", required = true)
            @RequestBody PaisViajeRequest pais) {
        try {
            return ok(this.pacienteService.editarPaisesVisitados(controlPaisId, pais.getFechaLlegada(), pais.getFechaSalida(), pais.getCiudad()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al editar un país. Causa. {} ",e.getMessage());
            return CustomErrorType.badRequest("Actualizar País", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al editar el país visitado",e);
            return CustomErrorType.serverError("Actualizar País", "Se genero un error al editar el país visitado");
        }
    }

    @RequestMapping(value = "/{pacienteId}/{paisId}/eliminar-pais-visitado",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminad Enfermedad Base", description = "Eliminar una enfermedad base para un paciente", tags = { "paciente" })
    public ResponseEntity<EnfermedadResponse> eliminarPais(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Identificador de pais", required = true)
            @PathVariable("paisId") Long paisId) {
        try {
            this.pacienteService.eliminarPais(pacienteId, paisId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al eliminar pais. Causa. {} ",e.getMessage());
            return CustomErrorType.badRequest("Eliminar País", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al eliminar pais ",e);
            return CustomErrorType.serverError("Eliminar País", "Se genero un error al eliminar país");
        }
    }

    @RequestMapping(value = "/{pacienteId}/agregar-contacto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de paciente a una familia", description = "Guarda un registro de paciente que pertenece a la familia del usuario autentificado", tags = { "paciente" })
    public ResponseEntity<PacienteDto> agregarContacto(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Objeto paciente para registrar", required = true)
            @RequestBody PacienteRequest pacienteRequest) {
        try {
            PacienteDto responseDto = this.pacienteService.agregarContacto(pacienteId,pacienteRequest.getNombre(),
                    pacienteRequest.getEdad(),pacienteRequest.getGenero(), pacienteRequest.getGestacion(),
                    pacienteRequest.getTiempoGestacion(), pacienteRequest.getOcupacion(),pacienteRequest.getCi(),
                    pacienteRequest.getFechaNacimiento(),pacienteRequest.getSeguro(),pacienteRequest.getCodigoSeguro());

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

    @RequestMapping(value = "/{contactoId}/eliminar-contacto", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina un contacto", description = "Elimina contacto de una familia", tags = { "paciente" })
    public ResponseEntity<PacienteDto> eliminarContacto(
            @Parameter(description = "Identificador de Contacto", required = true)
            @PathVariable("contactoId") Long contactoId) {
        try {
            this.pacienteService.eliminarContacto(contactoId);
            log.info("Se elimina correctamente el contacto : {}",contactoId);
            logService.info(Process.REGISTRO_PACIENTE,"Se elimina correctamente el contacto : {}",contactoId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al eliminar el contacto: {}. Causa. {}",contactoId,e.getMessage());
            logService.error(Process.REGISTRO_PACIENTE,"Se genero un error al eliminar el contacto: {}. Causa. {}",contactoId,e.getMessage());
            return CustomErrorType.badRequest("Eliminar Contacto", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al eliminar el contcto : {}",contactoId,e);
            logService.error(Process.REGISTRO_PACIENTE,"Se genero un error al eliminar el contacto : {}",contactoId);
            return CustomErrorType.serverError("Eliminar Contacto", "Ocurrió un error al eliminar el contacto: " + contactoId);
        }
    }

    @RequestMapping(value = "/{pacienteId}/diagnosticos",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obteniene los ultimos diagnosticos", description = "Obteniene los ultimos diagnosticos del paciente. Este método es solo para la aplicación móvil", tags = { "familia" })
    public ResponseEntity<List<MovilControlDiario>> getDiagnostico(
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId) {
        try {
            return ok(this.pacienteService.getControlDiario(pacienteId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener la familia del usuario. Causa. {} ",e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al obtener la familia del usuario. Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Obtener Familia", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener la familia del usuario",e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al obtener la familia del usuario");
            return CustomErrorType.serverError("Obtener Familia", "Se genero un error al obtener la familia del usuario");
        }
    }


    @RequestMapping(value = "/{pacienteId}/diagnostico-medico", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de Diagnotico", description = "Guarda un registro de Diagnostico ingresado por un médico", tags = { "paciente" })
    public ResponseEntity<PacienteDto> diagnosticoMedico(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Identificador de paciente", required = true)
            @PathVariable("pacienteId") Long pacienteId,
            @Parameter(description = "Objeto sintomas para registrar", required = true)
            @RequestBody DiagnosticoRequest diagnosticoRequest) {
        try {
            this.pacienteService.agregarDiagnosticoMedico(userDetails,pacienteId,diagnosticoRequest);

            log.info("Se registro de manera correcta el diagnostico para el paciente: {}",pacienteId);

            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el diagnostico del paciente: {}. Causa. {}",pacienteId,e.getMessage());
            logService.error(Process.DIAGNOSTICO,"Se genero un error al guardar el diagnostico del paciente: {}. Causa. {}",pacienteId,e.getMessage());
            return CustomErrorType.badRequest("Diagnostico Medico", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar el diagnostico del paciente : {}",pacienteId,e);
            logService.error(Process.DIAGNOSTICO,"Se genero un error al guardar el diagnostico del paciente : {}",pacienteId);
            return CustomErrorType.serverError("Diagnostico Medico", "Ocurrió un error al guardar el diagnostico del paciente: "+pacienteId);
        }
    }

}
