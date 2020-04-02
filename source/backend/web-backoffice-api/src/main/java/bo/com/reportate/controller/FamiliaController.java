package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.FamiliaMovilResponseDto;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.service.FamiliaService;
import bo.com.reportate.service.LogService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.FamiliaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.validation.Valid;

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
@RequestMapping("/api/familias")
@Slf4j
@Tag(name = "familia", description = "API de familias")
public class FamiliaController {
    @Autowired private FamiliaService familiaService;
    @Autowired private LogService logService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crea un registro de familia", description = "Guarda un registro de familia para el usuario autentificado", tags = { "familia" })
    public ResponseEntity<FamiliaMovilResponseDto> saveFamilia(
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "Objeto familia para registrar", required = true) @Valid @RequestBody FamiliaRequest familiaRequest) {
        try {
            FamiliaMovilResponseDto responseDto = this.familiaService.save(userDetails,familiaRequest.getDepartamentoId(), familiaRequest.getMunicipioId(),familiaRequest.getNombre(),
                    familiaRequest.getTelefono(),familiaRequest.getDireccion(),familiaRequest.getLatitud(),
                    familiaRequest.getLongitud(),familiaRequest.getCiudad(),familiaRequest.getZona());
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
}
