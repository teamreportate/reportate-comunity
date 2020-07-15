package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.response.FamiliaMovilResponseDto;
import bo.com.reportate.model.dto.response.FamiliaResponse;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.service.FamiliaService;
import bo.com.reportate.service.LogService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.FamiliaRequest;
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
@RequestMapping("/api/familias")
@Slf4j
@Tag(name = "familia", description = "API de familias")
public class FamiliaController {
    @Autowired private FamiliaService familiaService;
    @Autowired private LogService logService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de familia", description = "Guarda un registro de familia para el usuario autentificado", tags = { "familia" })
    public ResponseEntity<FamiliaMovilResponseDto> saveFamilia(
            @AuthenticationPrincipal Authentication userDetails,
            @Parameter(description = "Objeto familia para registrar", required = true)
            @RequestBody FamiliaRequest familiaRequest) {
        try {
            FamiliaMovilResponseDto responseDto = this.familiaService.save(userDetails,familiaRequest.getDepartamentoId(), familiaRequest.getMunicipioId(),familiaRequest.getNombre(),
                    familiaRequest.getTelefono(),familiaRequest.getDireccion(),familiaRequest.getLatitud(),
                    familiaRequest.getLongitud(),familiaRequest.getZona(), familiaRequest.getCentroSaludId());
            log.info("Se registro de manera correcta la familia: {}",familiaRequest.getNombre());
            logService.info(Process.REGISTRO_FAMILIA,"Se registro de manera correcta la familia: {}",familiaRequest.getNombre());
            return ok(responseDto);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar la familia: {}. Causa. {}",familiaRequest.getNombre(),e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar la familia: {}. Causa. {}",familiaRequest.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar Familia", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar la familia : {}",familiaRequest.getNombre(),e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar la familia : {}",familiaRequest.getNombre());
            return CustomErrorType.serverError("Guardar Familia", "Ocurrió un error al guardar la familia: "+familiaRequest.getNombre());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualiza un registro de familia", description = "Actualiza el registro de familia para el usuario autentificado", tags = { "familia" })
    public ResponseEntity<FamiliaMovilResponseDto> updateFamilia(
            @Parameter(description = "Objeto familia para actualizar", required = true)
            @RequestBody FamiliaRequest familiaRequest) {
        try {
            FamiliaMovilResponseDto responseDto = this.familiaService.update(familiaRequest.getDepartamentoId(), familiaRequest.getMunicipioId(), familiaRequest.getId(), familiaRequest.getNombre(),
                    familiaRequest.getTelefono(),familiaRequest.getDireccion(),familiaRequest.getZona());
            log.info("Se registro de manera correcta la familia: {}",familiaRequest.getNombre());
            logService.info(Process.REGISTRO_FAMILIA,"Se registro de manera correcta la familia: {}",familiaRequest.getNombre());
            return ok(responseDto);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar la familia: {}. Causa. {}",familiaRequest.getNombre(),e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar la familia: {}. Causa. {}",familiaRequest.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Actualizar Familia", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar la familia : {}",familiaRequest.getNombre(),e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al guardar la familia : {}",familiaRequest.getNombre());
            return CustomErrorType.serverError("Actualizar Familia", "Ocurrió un error al guardar la familia: "+familiaRequest.getNombre());
        }
    }

    @RequestMapping(value = "/{familiaId}/eliminar",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Elimina una familia", description = "Elimina una familia con el identificador recibido", tags = { "familia" })
    public ResponseEntity eliminarFamilia(
            @Parameter(description = "Identificador de Familia", required = true)
            @PathVariable("familiaId") Long familiaId) {
        try {
            this.familiaService.delete(familiaId);
            log.info("Se elimino correctamente la familia con id: {}",familiaId);
            logService.info(Process.REGISTRO_FAMILIA,"Se elimino correctamente la familia con id: {}",familiaId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al eliminar la familia con id: {}. Causa. {}",familiaId,e.getMessage());
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al eliminar la familia con id: {}. Causa. {}",familiaId,e.getMessage());
            return CustomErrorType.badRequest("Eliminar Familia", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al eliminar la familia con id: {}.",familiaId,e);
            logService.error(Process.REGISTRO_FAMILIA,"Se genero un error al eliminar la familia con id: {}.",familiaId);
            return CustomErrorType.serverError("Eliminar Familia", "Se genero un error al eliminar la familia con id: " + familiaId);
        }
    }

    @RequestMapping(value = "/informacion",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obteniene información de la familia", description = "Obtiene información de la familia del usuario autentificado", tags = { "familia" })
    public ResponseEntity<FamiliaResponse> getInfo(@AuthenticationPrincipal Authentication authentication) {
        try {
            return ok(this.familiaService.getInfo(authentication));
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
}
