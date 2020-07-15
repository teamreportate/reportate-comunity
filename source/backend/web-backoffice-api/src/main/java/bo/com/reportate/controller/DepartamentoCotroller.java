package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.dto.response.ObjetoResponseDto;
import bo.com.reportate.service.DepartamentoService;
import bo.com.reportate.service.MunicipioService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.web.DepartamentoRequestDto;
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
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :Reportate
 */
@Slf4j
@RestController
@RequestMapping("/api/departamentos")
@Tag(name = "departamento", description = "API de departamentos")
public class DepartamentoCotroller {
    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private MunicipioService municipioService;

    /**
     * Método solo para la aplicación movil, y lista solo los activos
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar los departamentos con sus municipios", description = "Listar los departamentos con sus municipios", tags = { "departamento" })
    public ResponseEntity<List<DepartamentoDto>> listaDepartamentosActivos() {
        try {
            return ok(this.departamentoService.findAllConMunicipio());
        }catch (Exception e){
            log.error("Se genero un error al listar los departamentos",e);
            return CustomErrorType.serverError("Listar Departamentos", "Ocurrió un error al listar los departamentos");
        }
    }

    @RequestMapping(value = "/{departamentoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Departamento> getById(@PathVariable("departamentoId") Long departamentoId) {
        try {
            return ok(this.departamentoService.findById(departamentoId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener el departamento con ID: {}. Causa. {}",departamentoId,e.getMessage());
            return CustomErrorType.badRequest("Obtener Departamento", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener el departamento con ID: {}",departamentoId,e);
            return CustomErrorType.serverError("Obtener Departamento", "Ocurrió un error al obtener el departamento con ID: "+departamentoId);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Departamento> saveDepartamento(@RequestBody DepartamentoRequestDto departamentoDto) {
        try {
            return ok(this.departamentoService.save(departamentoDto.getNombre(), departamentoDto.getLatitud(), departamentoDto.getLongitud(), departamentoDto.getTelefono()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al guardar el departamento: {}. Causa. {}",departamentoDto.getNombre(),e.getMessage());
            return CustomErrorType.badRequest("Guardar Departamento", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al guardar el departamento : {}",departamentoDto.getNombre(),e);
            return CustomErrorType.serverError("Guardar Departamento", "Ocurrió un error al guardar el departamento: "+departamentoDto.getNombre());
        }
    }

    @RequestMapping(value = "/{departamentoId}",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Actualizar un departamento", description = "Método para actualizar un departamento", tags = { "departamento" })
    public ResponseEntity updateDepartamento(
            @Parameter(description = "Identificador de Departamento", required = true)
            @PathVariable("departamentoId")Long departamentoId,
            @Parameter(description = "Objeto departamento que se va a modificar", required = true)
            @RequestBody DepartamentoRequestDto departamentoDto) {
        try {
            return ok(this.departamentoService.update(departamentoId, departamentoDto.getNombre(), departamentoDto.getLatitud(), departamentoDto.getLongitud(), departamentoDto.getTelefono()));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al modificar el departamento: {}. Causa. {}",departamentoId,e.getMessage());
            return CustomErrorType.badRequest("Modificar Departamento", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al modificar el departamento : {}",departamentoId,e);
            return CustomErrorType.serverError("Modificar Departamento", "Ocurrió un error al modificar el departamento: "+departamentoId);
        }
    }

    @RequestMapping(value = "/{departamentoId}/eliminar",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity eliminarDepartamento(@PathVariable("departamentoId")Long departamentoId) {
        try {
            this.departamentoService.eliminar(departamentoId);
            return ok().build();
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al elimianr el departamento: {}. Causa. {}",departamentoId,e.getMessage());
            return CustomErrorType.badRequest("Eliminar Departamento", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al eliminar el departamento : {}",departamentoId,e);
            return CustomErrorType.serverError("Eliminar Departamento", "Ocurrió un error al eliminar el departamento: "+departamentoId);
        }
    }

    @RequestMapping(value = "/{departamentoId}/municipios",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MunicipioDto>> listarMunicipios(@PathVariable("departamentoId")Long departamentoId) {
        try {
            return ok(this.municipioService.findByDepartamento(departamentoId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar municipios: {}. Causa. {}",departamentoId,e.getMessage());
            return CustomErrorType.badRequest("Listar Municipios", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al listar municipios : {}",departamentoId,e);
            return CustomErrorType.serverError("Listar Municipios", "Ocurrió un error al listar municipios: "+departamentoId);
        }
    }

    @RequestMapping(value = "/asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoUsuarioDto>> listarDepartamentos(@AuthenticationPrincipal Authentication userDetails) {
        try {
            return ok(this.departamentoService.listarAsignados(userDetails));
        } catch (NotDataFoundException e) {
            log.error("Ocurrio un problema al recuperar lista de departamentos");
            return CustomErrorType.badRequest("Obtener Departamentos", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al recuperar lista de departamentos", e);
            return CustomErrorType.serverError("Obtener Departamentos", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/departamento-municipio-centro-salud", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<ObjetoResponseDto> listarDepartamentoMunicipioCentroSalud() {
        try {
            return ok(this.departamentoService.listarDepartamentoMunicipioCentroSalud());
        } catch (NotDataFoundException e) {
            log.error("Ocurrio un problema al recuperar lista de departamentos");
            return CustomErrorType.badRequest("Obtener Departamentos", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al recuperar lista de departamentos", e);
            return CustomErrorType.serverError("Obtener Departamentos", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/departamento-municipio-centro-salud-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<ObjetoResponseDto> listarDepartamentoMunicipioCentroSaludAsignados(@AuthenticationPrincipal Authentication userDetails) {
        try {
            return ok(this.departamentoService.listarDepartamentoMunicipioCentroSaludAsignados(userDetails));
        } catch (NotDataFoundException e) {
            log.error("Ocurrio un problema al recuperar lista de departamentos");
            return CustomErrorType.badRequest("Obtener Departamentos", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al recuperar lista de departamentos", e);
            return CustomErrorType.serverError("Obtener Departamentos", "Ocurrió un error interno");
        }
    }

}
