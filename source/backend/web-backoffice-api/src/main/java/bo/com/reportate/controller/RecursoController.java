package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.dto.RecursoDto;
import bo.com.reportate.service.RecursoService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    /**
     * Metodo que permite obtener todos los recursos habilitados
     * @return
     */
    @GetMapping()
    public ResponseEntity listar() {
        try{
            List<RecursoDto> lista=recursoService.listar();
            return ok(lista);
        }
        catch (OperationException e){
            log.error("Error al Listar Recursos: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al listar Recursos.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Metodo que permite obtener los recursos habiltiados segun un criterio de busqueda
     * @param criterioBusqueda
     * @return
     */
    @GetMapping("/listar-busqueda")
    @Operation(description = "Método para buscar recursos de acuerdo a un criterio", responses = @ApiResponse(description = "Retorna lista de Recursos de acuerdo a un criterio de busqueda." ))
    public ResponseEntity<List<RecursoDto>> listar(@RequestParam("criterio_busqueda") String criterioBusqueda) {
        try{
            List<RecursoDto> lista=recursoService.listarBusqueda(criterioBusqueda);
            return ok(lista);
        }
        catch (OperationException e){
            log.error("Error al Listar Busqueda Recursos: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al Listar Busqueda Recursos.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Metodo que retorna un recurso segun su id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity obtener(@PathVariable("id") Long id) {
        try{
            RecursoDto recurso=recursoService.obtener(id);
            return ok(recurso);
        }
        catch (OperationException e){
            log.error("Error al obtener Recurso: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al obtener Recurso.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }


    /**
     * Metodo que permite actualizarGrupo un recurso
     * @param id
     * @param recurso
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity modificar(@PathVariable("id") Long id, @RequestBody RecursoDto recurso) {
        try{
            RecursoDto mu = recursoService.modificar(recurso, id);
            return ok(mu);
        }
        catch (OperationException e){
            log.error("Error al modificar Recurso: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al modificar Recurso.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Metodo que permite cambiar de estado de un recurso
     * @param id
     * @return
     */
    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity cambiarEstado(@PathVariable("id") Long id) {
        try{
            boolean estado=recursoService.cambiarEstado(id);
            if(estado){
                return ok().build();
            }
            else {
                return noContent().build();
            }
        }
        catch (OperationException e){
            log.error("Error al cambiar de estado el Recurso: {}", e.getMessage());
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error("Error no controlado al cambiar de estado el Recurso.", e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

}
