package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.CentroSaludDto;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.service.CentroSaludService;
import bo.com.reportate.service.MunicipioService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.MunicipioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :MC4
 */
@Slf4j
@RestController
@RequestMapping("/api/municipios")
@Tag(name = "municipio", description = "API de municipios")
public class MunicipioCotroller {
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private CentroSaludService centroSaludService;


    @RequestMapping(value = "/{municipioId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Municipio> getById(@PathVariable("municipioId") Long municipioId) {
        try {
            return ok(this.municipioService.findById(municipioId));
        }catch (NotDataFoundException e){
            log.error("Se genero un error al obtener el municipio con ID: {}. Causa. {}",municipioId,e.getMessage());
            return CustomErrorType.badRequest("Obtener Municipio", "Ocurrió un error al obtener el municipio con ID: "+municipioId);
        }catch (Exception e){
            log.error("Se genero un error al obtener el municipio con ID: {}",municipioId,e);
            return CustomErrorType.serverError("Obtener Municipio", "Ocurrió un error al obtener el municipio con ID: "+municipioId);
        }
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Guardar Municipio", description = "Metodo para guardar el municipio", tags = { "municipio" })
    public ResponseEntity<Municipio> saveMunicipio(
            @Parameter(description = "Objeto municipio a guardar", required = true)
            @RequestBody MunicipioRequest municipioDto) {
        try {
            return ok(this.municipioService.save(municipioDto.getDepartamentoId(), municipioDto.getNombre(), municipioDto.getLatitud(), municipioDto.getLongitud()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el municipio: {}. Causa. {}",municipioDto.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar Municipio", "Ocurrió un error al guardar el municipio: "+municipioDto.getNombre());
        }catch (Exception e){
            log.error("Se genero un error al guardar el municipio : {}",municipioDto.getNombre(),e);
            return CustomErrorType.serverError("Guardar Municipio", "Ocurrió un error al guardar el municipio: "+municipioDto.getNombre());
        }
    }

    @RequestMapping(value = "/{municipioId}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualizar Municipio", description = "Metodo para actualizar el municipio", tags = { "municipio" })
    public ResponseEntity<Municipio> updateMunicipio(
            @Parameter(description = "Identificador de municipio a modificar", required = true)
            @PathVariable("municipioId")Long municipioId,
            @Parameter(description = "Objeto municipio a guardar", required = true)
            @RequestBody MunicipioRequest municipioRequest) {
        try {
            return ok(this.municipioService.update(municipioId, municipioRequest.getNombre(), municipioRequest.getLatitud(), municipioRequest.getLongitud()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar el municipio: {}. Causa. {}",municipioId,e.getMessage());
            return CustomErrorType.badRequest("Modificar Municipio", "Ocurrió un error al modificar el municipio: "+municipioId);
        }catch (Exception e){
            log.error("Se genero un error al modificar el municipio : {}",municipioId,e);
            return CustomErrorType.serverError("Modificar Municipio", "Ocurrió un error al modificar el municipio: "+municipioId);
        }
    }

    @RequestMapping(value = "/{municipioId}/eliminar",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Eliminar Municipio", description = "Metodo para actualizar el municipio", tags = { "municipio" })
    public ResponseEntity eliminarMunicipio(@PathVariable("municipioId")Long municipioId) {
        try {
            this.municipioService.eliminar(municipioId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al elimianr el municipio: {}. Causa. {}",municipioId,e.getMessage());
            return CustomErrorType.badRequest("Eliminar Municipio", "Ocurrió un error al eliminar el municipio: "+municipioId);
        }catch (Exception e){
            log.error("Se genero un error al eliminar el municipio : {}",municipioId,e);
            return CustomErrorType.serverError("Eliminar Municipio", "Ocurrió un error al eliminar el municipio: "+municipioId);
        }
    }

    @RequestMapping(value = "/{municipioId}/centros-de-salud",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CentroSaludDto>> listarMunicipios(@PathVariable("municipioId")Long municipioId) {
        try {
            return ok(this.centroSaludService.findByMunicipio(municipioId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar los centros de salud del municipio: {}. Causa. {}",municipioId,e.getMessage());
            return CustomErrorType.badRequest("Listar Centros de Salud", "Ocurrió un error al listar los centros de salud del municipio: "+municipioId);
        }catch (Exception e){
            log.error("Se genero un error al listar centros de salud del municipio : {}",municipioId,e);
            return CustomErrorType.serverError("Listar Centros de Salud", "OOcurrió un error al listar los centros de salud del municipio "+municipioId);
        }
    }



}
