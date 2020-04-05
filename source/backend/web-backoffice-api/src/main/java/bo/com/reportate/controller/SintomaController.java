package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.SintomaDto;
import bo.com.reportate.service.SintomaService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Sin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :02/04/2020
 * Project       :reportate
 * Package       :bo.com.reportate.controller
 **/
@Slf4j
@RestController
@RequestMapping("/api/sintoma")
public class SintomaController {
    @Autowired
    private SintomaService sintomaService;

    @RequestMapping(value = "/{sintomaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sintoma> getById(@PathVariable("sintomaId") Long sintomaId) {
        try {
            return ok(this.sintomaService.findById(sintomaId));
        }catch (NotDataFoundException e){
            log.error("Se genero un error al obtener el sintoma con ID: {}. Causa. {}",sintomaId,e.getMessage());
            return CustomErrorType.badRequest("Obtener síntoma", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener el sintoma con ID: {}",sintomaId,e);
            return CustomErrorType.serverError("Obtener síntoma", "Ocurrió un error al obtener el síntoma con ID: "+sintomaId);
        }
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sintoma> saveMunicipio(@RequestBody SintomaDto sintomaDto) {
        try {
            return ok(this.sintomaService.save(sintomaDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el sintoma: {}. Causa. {}",sintomaDto.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar síntoma", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar el pais : {}",sintomaDto.getNombre(),e);
            return CustomErrorType.serverError("Guardar síntoma", "Ocurrió un error al guardar el síntoma: "+sintomaDto.getNombre());
        }
    }

    @RequestMapping(value = "/{sintomaId}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Sintoma> updateMunicipio(@PathVariable("sintomaId")Long sintomaId, @RequestBody SintomaDto sintomaDto) {
        try {
            return ok(this.sintomaService.update(sintomaId, sintomaDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar el sintoma: {}. Causa. {}",sintomaId,e.getMessage());
            return CustomErrorType.badRequest("Modificar síntoma", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al modificar el sintoma : {}",sintomaId,e);
            return CustomErrorType.serverError("Modificar síntoma", "Ocurrió un error al modificar el síntoma: "+sintomaId);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SintomaDto>> listar() {
        try {
            return ok(this.sintomaService.listAll());
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener los sintomas. Causa {}",e.getMessage());
            return CustomErrorType.badRequest("Obtener síntomas", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener los sintomas.",e);
            return CustomErrorType.serverError("Obtener síntomas", "Ocurrió un error al obtener los síntomas");
        }
    }

    @DeleteMapping("/{id}/cambiar-estado")
    public ResponseEntity cambiarEstado(@PathVariable("id") Long id) {
        try{
            boolean estado = sintomaService.cambiarEstado(id);
            if(estado){
                return ok().build();
            }
            else {
                return noContent().build();
            }
        }
        catch (OperationException e){
            log.error("Error al cambiar de estado el síntoma: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al cambiar de estado el síntoma.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }
}
