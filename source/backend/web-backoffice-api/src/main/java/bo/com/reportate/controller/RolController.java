package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.*;
import bo.com.reportate.service.RolService;
import bo.com.reportate.util.ConstantesRest;
import bo.com.reportate.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RolController {
    @Autowired private RolService rolService;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarRoles() {
        try {
            return ok(this.rolService.listar());
        } catch (OperationException e){
            log.error("Error al Listar Roles, Mensaje: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Listar Roles", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al Listar Roles", e);
            return CustomErrorType.serverError("Listar Roles", "Ocurrio un error interno");
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity nuevoRol(@RequestBody RolDto muRol) {
        try {
            return ok(this.rolService.crear(muRol));
        } catch (OperationException e){
            log.error("Error al Crear Rol: [{}], Mensaje: [{}]", muRol.getNombre(), e.getMessage());
            return CustomErrorType.badRequest("Crear Rol", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al Crear Rol", e);
            return CustomErrorType.serverError("Crear Rol", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity modificarRol(@PathVariable("rolId")Long rolId, @RequestBody RolDto muRol) {
        try {
            return ok(this.rolService.modificar(muRol.getDescripcion(), muRol.getNombre(), rolId));
        } catch (OperationException e){
            log.error("Error al Actualizar Rol: [{}], Mensaje: [{}]",muRol.getNombre(),  e.getMessage());
            return CustomErrorType.badRequest("Actualizar Rol", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al Actualizar Rol", e);
            return CustomErrorType.serverError("Actualizar Rol", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/cambiar-estado", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity cambiarEstado(@PathVariable("rolId")Long rolId) {
        try {
            return ok(this.rolService.cambiarEstado(rolId));
        } catch (OperationException e){
            log.error("Ocurrio un problema al Cambiar Estado Rol: [{}], Mensaje: [{}]",rolId, e.getMessage());
            return CustomErrorType.badRequest("Cambiar Estado Rol", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Actualizar Rol", e);
            return CustomErrorType.serverError("Cambiar Estado Rol", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/grupos-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarGruposAsignados(@PathVariable("rolId") Long rolId) {
        try {
            return ok(this.rolService.listarGruposAsignados(rolId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Listar Grupos Asignados al Rol: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Listar Grupos Asignados", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Grupos Asignados al Rol: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Listar Grupos Asignados", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/grupos-no-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarGruposNoAsignados(@PathVariable("rolId") Long rolId) {
        try {
            return ok(this.rolService.listarGruposNoAsignados(rolId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Listar Grupos NO Asignados al Rol: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Listar Grupos NO Asignados", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Grupos NO Asignados al Rol: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Listar Grupos NO Asignados", "Ocurrio un error interno");
        }
    }


    @RequestMapping(value = "/{rolId}/recursos-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarRecursosAsignados(@PathVariable("rolId") Long rolId) {
        try {
            return ok(this.rolService.listarRecursosAsignados(rolId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Listar Recursos Asignados al Rol: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Listar Recursos Asignados", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Recursos Asignados al Rol: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Listar Recursos Asignados", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/recursos-no-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarRecursosNoAsignados(@PathVariable("rolId") Long rolId) {
        try {
            return ok(this.rolService.listarRecursosNoAsignados(rolId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Listar Recursos NO Asignados al Rol: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Listar Recursos NO Asignados", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Listar Recursos NO Asignados al Rol: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Listar Recursos NO Asignados", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/asignar-grupos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarGrupos(@PathVariable("rolId") Long rolId, @RequestBody List<GrupoDto> listaGrupo) {
        try{
            this.rolService.asignarGrupos(rolId, listaGrupo);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Asignar Grupos al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Asignar Grupos", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Asignar Grupos al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Asignar Grupos", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/remover-grupos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerGrupos(@PathVariable("rolId") Long rolId, @RequestBody List<GrupoDto> listaRoles) {
        try{
            this.rolService.removerGrupos(rolId, listaRoles);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Ocurrio un problema al Remover Grupos al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Remover Grupos", e.getMessage());
        } catch (Exception e) {
            log.error("Se ha generado un error no controlado al Remover Grupos al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Remover Grupos", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/asignar-usuarios", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarUsuarios(@PathVariable("rolId") Long rolId, @RequestBody List<UsuarioDto> listaUsuarios) {
        try{
            this.rolService.asignarUsuarios(rolId, listaUsuarios);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Asignar Usuarios al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Asignar Usuarios", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Asignar Usuarios al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Asignar Usuarios", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/remover-usuarios", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerUsuarios(@PathVariable("rolId") Long rolId, @RequestBody List<UsuarioDto> listaUsuarios) {
        try{
            this.rolService.removerUsuarios(rolId, listaUsuarios);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Ocurrio un problema al Remover Usuarios al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Remover Usuarios", e.getMessage());
        } catch (Exception e) {
            log.error("Se ha generado un error no controlado al Remover Usuarios al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Remover Usuarios", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/asignar-recursos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarRecursos(@PathVariable("rolId") Long rolId, @RequestBody List<RecursoDto> listaRecursos) {
        try{
            this.rolService.asignarRecursos(rolId, listaRecursos);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Asignar Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Asignar Recursos", e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Asignar Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Asignar Recursos", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{rolId}/configurar-recursos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity configurarRecursos(@PathVariable("rolId") Long rolId,
                                             @RequestBody List<ConfiguracionDto> listaConfiguracion) {
        try{
            this.rolService.configurarRcursos(rolId, listaConfiguracion);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al Asignar Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest(ConstantesRest.TITULO_ROL_ASIGNAR_RECURSOS, e.getMessage());
        } catch (Exception e){
            log.error("Se ha generado un error no controlado al Asignar Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError(ConstantesRest.TITULO_ROL_ASIGNAR_RECURSOS, ConstantesRest.MENSAJE_GENERICO);
        }
    }

    @RequestMapping(value = "/{rolId}/remover-recursos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerRecursos(@PathVariable("rolId") Long rolId, @RequestBody List<RecursoDto> listaRecursos) {
        try{
            this.rolService.removerRecursos(rolId, listaRecursos);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Ocurrio un problema al Remover Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e.getMessage());
            return CustomErrorType.badRequest("Remover Recursos", e.getMessage());
        } catch (Exception e) {
            log.error("Se ha generado un error no controlado al Remover Recursos al Rol ID: [{}], Mensaje: [{}]", rolId, e);
            return CustomErrorType.serverError("Remover Recursos", "Ocurrio un error interno");
        }
    }
}
