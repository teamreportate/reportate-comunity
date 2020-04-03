package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.service.CentroSaludService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.CentroSaludRequest;
import bo.com.reportate.web.MunicipioRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :MC4
 */
@Slf4j
@RestController
@RequestMapping("/api/centros-de-salud")
public class CentroSaludCotroller {
    @Autowired
    private CentroSaludService centroSaludService;


    @RequestMapping(value = "/{centroSaludId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroSalud> getById(@PathVariable("centroSaludId") Long centroSaludId) {
        try {
            return ok(this.centroSaludService.findById(centroSaludId));
        }catch (NotDataFoundException | OperationException e ){
            log.error("Se genero un error al obtener el centro de salud con ID: {}. Causa. {}",centroSaludId,e.getMessage());
            return CustomErrorType.badRequest("Obtener Centro de Salud", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener el municipio con ID: {}",centroSaludId,e);
            return CustomErrorType.serverError("Obtener Centro de Salud", "Ocurrió un error al obtener el centro de salud con ID: "+centroSaludId);
        }
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroSalud> saveCentroSalud(@RequestBody CentroSaludRequest centroSaludRequest) {
        try {
            return ok(this.centroSaludService.save(centroSaludRequest.getMunicipioId(), centroSaludRequest.getNombre(),
                    centroSaludRequest.getDireccion(), centroSaludRequest.getZona(),centroSaludRequest.getCiudad(),centroSaludRequest.getLatitud(), centroSaludRequest.getLongitud()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el centro de salud: {}. Causa. {}",centroSaludRequest.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar Centro de Salud", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar el centro de salud : {}",centroSaludRequest.getNombre(),e);
            return CustomErrorType.serverError("Guardar Centro de Salud", "Ocurrió un error al guardar el centro de salud: "+centroSaludRequest.getNombre());
        }
    }

    @RequestMapping(value = "/{centroSaludId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroSalud> updateCentroSalud(@PathVariable("centroSaludId")Long centroSaludId, @RequestBody CentroSaludRequest centroSaludRequest) {
        try {
            return ok(this.centroSaludService.update(centroSaludId, centroSaludRequest.getNombre(), centroSaludRequest.getDireccion(), centroSaludRequest.getZona(), centroSaludRequest.getCiudad(), centroSaludRequest.getLatitud(), centroSaludRequest.getLongitud()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar el centro de salud: {}. Causa. {}",centroSaludId,e.getMessage());
            return CustomErrorType.badRequest("Modificar Centro de Salud", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al modificar el municipio : {}",centroSaludId,e);
            return CustomErrorType.serverError("Modificar Centro de Salud", "Ocurrió un error al modificar el centro de salud: "+centroSaludId);
        }
    }

    @RequestMapping(value = "/{centroSaludId}/eliminar",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity eliminarCentroSalud(@PathVariable("centroSaludId")Long centroSaludId) {
        try {
            this.centroSaludService.eliminar(centroSaludId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al elimianr el centro de salud: {}. Causa. {}",centroSaludId,e.getMessage());
            return CustomErrorType.badRequest("Eliminar Municipio", "Ocurrió un error al eliminar el centro de salud: "+centroSaludId);
        }catch (Exception e){
            log.error("Se genero un error al eliminar el centro de salud : {}",centroSaludId,e);
            return CustomErrorType.serverError("Eliminar Municipio", "Ocurrió un error al eliminar el centro de salud: "+centroSaludId);
        }
    }


}
