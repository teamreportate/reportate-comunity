package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.DominioDto;
import bo.com.reportate.model.dto.ValorDominioDto;
import bo.com.reportate.service.DominioService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/dominios")
@Slf4j
@Tag(name = "dominio", description = "API de dominios")
public class DominioController {

    @Autowired
    private DominioService dominioService;

    /**
     * Lista a los valores dominio habilitados.
     * @return
     */
    @GetMapping()
    @Operation(summary = "Lista todos los dominios", description = "Lista todos los dominios del sistema.", tags = { "dominio" })
    public ResponseEntity<List<DominioDto>> listarDominios() {
        try {
            return ok(dominioService.findAll());
        } catch (OperationException e){
            log.error("Se genero un error al listar dominios: Causa. {}", e.getMessage());
            return CustomErrorType.badRequest("Listar Dominios", e.getMessage());
        } catch (Exception e){
            log.error("Se genero un error al listar los dominios.",e);
            return CustomErrorType.badRequest("Listar Dominios", FormatUtil.defaultError());
        }
    }

    @RequestMapping(value = "/{dominioId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listaValores(@PathVariable("dominioId") Long dominioId) {
        try {
            return ok(this.dominioService.listaValores(dominioId));
        } catch (OperationException e){
            log.error("Ocurrio un problema al Listar Valores del Dominio, Mensaje: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Listar Valores del Dominio", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Valores del Dominio.", e);
            return CustomErrorType.serverError("Listar Valores del Domini","Ocurrió un error interno");
        }
    }

    /**
     * Retorna un valor dominio segun el criterio de busqueda
     * @param codigo
     * @return
     */
    @GetMapping("/{codigo}/valores")
    @Operation(summary = "Lista los valores de un dominio", description = "Lista llos valores de un dominio", tags = { "dominio" })
    public ResponseEntity<List<ValorDominioDto>> obtener(@Parameter(description = "codigo de dominio",required = true) @PathVariable("codigo") String codigo) {
        try {

            return ok(dominioService.findAllByCodigo(codigo));
        }
        catch (OperationException e){
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error(FormatUtil.MSG_TITLE_ERROR,e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Retorna un valor dominio segun el criterio de busqueda
     * @param codigo
     * @return
     */
    @PostMapping("/{codigo}/agregar-valor")
    @Operation(summary = "Permite agregar un valor al dominio", description = "Permite agregar un valor al dominio con codigo", tags = { "dominio" })
    public ResponseEntity<ValorDominioDto> agregarValorDominio(
            @Parameter(description = "codigo de dominio",required = true) @PathVariable("codigo") String codigo,
            @Parameter(description = "Valor dominio", required = true) @RequestBody ValorDominioDto valordominio ) {
        try {
            return ok(dominioService.saveValorDominio(codigo, valordominio));
        } catch (OperationException e){
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error(FormatUtil.MSG_TITLE_ERROR,e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Modifica los atributos de un valor dominio
     * @param id
     * @param sfeValorDominio
     * @return
     */
    @PostMapping("/valor/{id}")
    @Operation(summary = "Permite modificar el Objeto ValorDominioDto", description = "Método que permite modificar el valor o descripción de un valor de dominio.", tags = { "dominio" })
    public ResponseEntity modificarValorDominio(@Parameter(description = "identificador del valor dominio",required = true) @PathVariable("id") Long id, @RequestBody ValorDominioDto sfeValorDominio) {
        try {
            dominioService.modificar(sfeValorDominio, id);
            return noContent().build();
        }
        catch (OperationException e){
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error(FormatUtil.MSG_TITLE_ERROR,e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Modifica los atributos de un valor dominio
     * @param id
     * @return
     */
    @PutMapping("/valor/{id}/eliminar")
    @Operation(summary = "Permite eliminar un valor de dominio.", description = "Método que permite eliminar un valor del dominio", tags = { "dominio" })
    public ResponseEntity eliminarValorDominio(@Parameter(description = "identificador del valor dominio",required = true) @PathVariable("id") Long id) {
        try {
            dominioService.deleteValorDominio( id);
            return noContent().build();
        }
        catch (OperationException e){
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error(FormatUtil.MSG_TITLE_ERROR,e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

}
