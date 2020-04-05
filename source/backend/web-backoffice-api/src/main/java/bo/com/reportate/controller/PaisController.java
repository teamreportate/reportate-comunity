package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisDto;
import bo.com.reportate.service.PaisService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @RequestMapping(value = "/{paisId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pais> getById(@PathVariable("paisId") Long paisId) {
        try {
            return ok(this.paisService.findById(paisId));
        }catch (NotDataFoundException e){
            log.error("Se genero un error al obtener el país con ID: {}. Causa. {}",paisId,e.getMessage());
            return CustomErrorType.badRequest("Obtener país", "Ocurrió un error al obtener el país con ID: "+paisId);
        }catch (Exception e){
            log.error("Se genero un error al obtener el pais con ID: {}",paisId,e);
            return CustomErrorType.serverError("Obtener país", "Ocurrió un error al obtener el país con ID: "+paisId);
        }
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pais> saveMunicipio(@RequestBody PaisDto paisDto) {
        try {
            return ok(this.paisService.save(paisDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el pais: {}. Causa. {}",paisDto.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar país", "Ocurrió un error al guardar el país: "+paisDto.getNombre());
        }catch (Exception e){
            log.error("Se genero un error al guardar el pais : {}",paisDto.getNombre(),e);
            return CustomErrorType.serverError("Guardar país", "Ocurrió un error al guardar el país: "+paisDto.getNombre());
        }
    }

    @RequestMapping(value = "/{paisId}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pais> updateMunicipio(@PathVariable("paisId")Long paisId, @RequestBody PaisDto paisDto) {
        try {
            return ok(this.paisService.update(paisId, paisDto));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar el país: {}. Causa. {}",paisId,e.getMessage());
            return CustomErrorType.badRequest("Modificar país", "Ocurrió un error al modificar el país: "+paisId);
        }catch (Exception e){
            log.error("Se genero un error al modificar el país : {}",paisId,e);
            return CustomErrorType.serverError("Modificar país", "Ocurrió un error al modificar el país: "+paisId);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaisDto>> listar() {
        try {
            return ok(this.paisService.listAll());
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener los países. Causa {}",e.getMessage());
            return CustomErrorType.badRequest("Obtener país", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener los países.",e.getMessage());
            return CustomErrorType.serverError("Obtener país", "Ocurrió un error al obtener los países");
        }
    }

    @DeleteMapping("/{id}/cambiar-estado")
    public ResponseEntity cambiarEstado(@PathVariable("id") Long id) {
        try{
            boolean estado = paisService.cambiarEstado(id);
            if(estado){
                return ok().build();
            }
            else {
                return noContent().build();
            }
        }
        catch (OperationException e){
            log.error("Error al cambiar de estado el País: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al cambiar de estado el País.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

}
