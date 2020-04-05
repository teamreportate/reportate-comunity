package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.service.EnfermedadService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
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

    @RequestMapping(value = "/{enfermedadId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enfermedad> getById(@PathVariable("enfermedadId") Long enfermedadId) {
        try {
            return ok(this.enfermedadService.findById(enfermedadId));
        }catch (NotDataFoundException e){
            log.error("Se genero un error al obtener el sintoma con ID: {}. Causa. {}",enfermedadId,e.getMessage());
            return CustomErrorType.badRequest("Obtener síntoma", "Ocurrió un error al obtener la enfermdedad con ID: "+enfermedadId);
        }catch (Exception e){
            log.error("Se genero un error al obtener el sintoma con ID: {}",enfermedadId,e);
            return CustomErrorType.serverError("Obtener síntoma", "Ocurrió un error al obtener la enfermedad con ID: "+enfermedadId);
        }
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enfermedad> saveMunicipio(@RequestBody EnfermedadDto enfermedadDto) {
        try {
            return ok(this.enfermedadService.save(enfermedadDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar la enfermedad: {}. Causa. {}",enfermedadDto.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar enfermedad", "Ocurrió un error al guardar la enfermedad: "+enfermedadDto.getNombre());
        }catch (Exception e){
            log.error("Se genero un error al guardar la enfermedad : {}",enfermedadDto.getNombre(),e);
            return CustomErrorType.serverError("Guardar enfermedad", "Ocurrió un error al guardar la enfermedad: "+enfermedadDto.getNombre());
        }
    }

    @RequestMapping(value = "/{enfermedadId}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enfermedad> updateMunicipio(@PathVariable("enfermedadId")Long enfermedadId, @RequestBody EnfermedadDto enfermedadDto) {
        try {
            return ok(this.enfermedadService.update(enfermedadId, enfermedadDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar la enfermedad: {}. Causa. {}",enfermedadId,e.getMessage());
            return CustomErrorType.badRequest("Modificar síntoma", "Ocurrió un error al modificar la enfermedad: "+enfermedadId);
        }catch (Exception e){
            log.error("Se genero un error al modificar el sintoma : {}",enfermedadId,e);
            return CustomErrorType.serverError("Modificar enfermedad", "Ocurrió un error al modificar la enfermedad: "+enfermedadId);
        }
    }

    @RequestMapping(value ="/all" ,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EnfermedadDto>> listar() {
        try {
            return ok(this.enfermedadService.listActivos());
        }catch (NotDataFoundException e){
            log.error("Se genero un error al obtener la enfermedad.",e.getMessage());
            return CustomErrorType.badRequest("Obtener enfermedad", "Ocurrió un error al obtener las enfermedades");
        }catch (Exception e){
            log.error("Se genero un error al obtener las enfermedades.",e.getMessage());
            return CustomErrorType.serverError("Obtener enfermedades", "Ocurrió un error al obtener las enfermedades");
        }
    }

    @DeleteMapping("/{id}/cambiar-estado")
    public ResponseEntity cambiarEstado(@PathVariable("id") Long id) {
        try{
            boolean estado = enfermedadService.cambiarEstado(id);
            if(estado){
                return ok().build();
            }
            else {
                return noContent().build();
            }
        }
        catch (OperationException e){
            log.error("Error al cambiar de estado la enfermedad: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al cambiar de estado la enfermadad.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }
}
