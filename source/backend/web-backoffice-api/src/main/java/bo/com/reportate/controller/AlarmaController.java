package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.MuAlarmaUsuario;
import bo.com.reportate.model.dto.AlarmaDto;
import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.service.AlarmaService;
import bo.com.reportate.service.UsuarioService;
import bo.com.reportate.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.controller
 * date:    24-06-19
 * author:  fmontero
 **/

@Slf4j
@RestController
@RequestMapping("/api/alarmas")
public class AlarmaController {

    @Autowired
    private AlarmaService alarmaService;
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlarmaDto>> alarmas() {
        return ok(this.alarmaService.findAll());
    }

    @RequestMapping(value = "usuarios/{alarmaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userNotAsigned(@PathVariable("alarmaId") Long alarmaId) {
        List<MuAlarmaUsuario> asignados = this.alarmaService.findAllByAlarmaId(alarmaId);
        List<UsuarioDto> noAsignados = this.usuarioService.findUsuariosNoAsignados(alarmaId);
        Map<String, List<?> >listas = new HashMap<String, List<?>>();
        {{
            listas.put("asignados", asignados);
            listas.put("noAsignados", noAsignados);
        }}
        return ok(listas);
    }

    @RequestMapping(value = "actualizar/{alarmaId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity actualizarAlarma(@PathVariable("alarmaId") Long alarmaId, @RequestBody MuAlarma muAlarma) {
        try {
            MuAlarma updated = this.alarmaService.actualizarDatos(alarmaId, muAlarma);
            updated.setHtml(null);
            return ok(updated);
        } catch (NotDataFoundException e) {
            return CustomErrorType.badRequest("Error al actualizar", "No existe la alarma con id:" + alarmaId.toString());
        } catch (Exception e) {
            return CustomErrorType.serverError("Error al actualizar", "Ocurrio un error interno al actualizar");
        }
    }

    @RequestMapping(value = "asignacion/{alarmaId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignacion(@PathVariable("alarmaId") Long alarmaId, @RequestBody List<MuAlarmaUsuario> alarmaUsuarioList) {
        Boolean bool = this.alarmaService.asignacion(alarmaId, alarmaUsuarioList);
        return ok(bool);
    }

    @RequestMapping(value = "/{alarmaId}/principal/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignacion(@PathVariable("alarmaId") Long alarmaId, @PathVariable("usuarioId") Long usuarioId) {
        Boolean bool = this.alarmaService.asignarUsuarioPrincipal(alarmaId, usuarioId);
        return ok(bool);
    }
}
