package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.MuParametroDto;
import bo.com.reportate.service.ParamService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/parametros")
@Slf4j
public class ParametroController {

    @Autowired
    private ParamService paramService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listaGruposParametro() {
        try {
            return ok(this.paramService.listaGruposParametro());
        } catch (OperationException e){
            log.error("Ocurrio un problema al Listar Grupos de Parámetros, Mensaje: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Ocurrió un problema al Listar Grupos de Parámetros.", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Grupos de Parámetros.", e);
            return CustomErrorType.serverError("Se ha generado un error no controlado al Listar Grupos de Parámetros", e.getMessage());
        }
    }

    @RequestMapping(value = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listaParametros(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(this.paramService.listar(grupoId));
        } catch (OperationException e) {
            log.error("Ocurrio un problema al Listar Parámetros, Mensaje: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Ocurrio un problema al Listar Parámetros.", e.getMessage());
        } catch (Exception e) {
            log.error("Se ha generado un error no controlado al Listar Parámetros", e);
            return CustomErrorType.serverError("Se ha generado un error no controlado al Listar Parámetros.", e.getMessage());
        }
    }
    /**
     * Retorna un parametro segun su id
     * @param codigo
     * @return
     */
    @GetMapping("/{codigo}")
    public ResponseEntity obtener(@PathVariable("codigo") String codigo) {
        try{
            MuParametroDto muParametro = paramService.getParametro(codigo);
            return ok(muParametro);
        }
        catch (OperationException e){
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        }
        catch (Exception e){
            log.error(FormatUtil.MSG_TITLE_ERROR,e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @RequestMapping(value = "/{parametroId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity actualizarParametro(@PathVariable("parametroId") Long parametroId, @RequestBody MuParametroDto parametroDto) {
        try{
            this.paramService.actualizarParametro(parametroId, parametroDto);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Actualizar Parámetros, Mensaje: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Ocurrio un problema al Actualizar Parámetros.", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Parámetros", e);

            return CustomErrorType.serverError("Se ha generado un error no controlado al Listar Parámetros.", e.getMessage());
        }
    }

}
