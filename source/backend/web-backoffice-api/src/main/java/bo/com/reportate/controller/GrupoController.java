package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.ConfiguracionDto;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.service.GrupoService;
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

@RestController
@RequestMapping("/api/grupos")
@Slf4j
public class GrupoController {
    @Autowired private GrupoService grupoService;
    @Autowired private RolService rolService;

    @GetMapping()
    public ResponseEntity listar() {
        try {
            return ok(this.grupoService.listar());
        } catch (OperationException e){
            log.error("Error al Listar grupos, Mensaje: [{}]", e.getMessage(), e);
            return CustomErrorType.badRequest("Listar Grupos", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al listar Grupos.",  e);
            return CustomErrorType.serverError("Listar Grupos", "Ocurrió un error interno");
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity nuevoGrupo(@RequestBody GrupoDto grupoDto) {
        try {
            return ok(this.grupoService.nuevoGrupo(grupoDto));
        } catch (OperationException e){
            log.error("Error al persistir los datos de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Registro Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al persistir los datos de Grupo.",  e);
            return CustomErrorType.serverError("Registro Grupo", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity actualizarGrupo(@PathVariable("grupoId") Long grupoId, @RequestBody GrupoDto grupo) {
        try {
            return ok(grupoService.actualizarGrupo(grupo, grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al actualizar los datos de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Actualizar Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al actualizar los datos de Grupo.",  e);
            return CustomErrorType.serverError("Actualizar Usuario", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/cambiar-estado", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity cambiarEstado(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(grupoService.cambiarEstado(grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al cambiar estado de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Cambio Estado Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al cambiar estado de Grupo.",  e);
            return CustomErrorType.serverError("Cambio Estado Grupo", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/usuarios-asignados", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
    public ResponseEntity listarUsuariosAsignados(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(this.grupoService.listarUsuariosAsignados(grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al listar Usuarios Asignados por Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Listar usuarios de Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al listar Usuarios Asignados por Grupo.",  e);
            return CustomErrorType.serverError("Listar usuarios de Grupo", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/usuarios-no-asignados", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
    public ResponseEntity listarUsuariosNoAsignados(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(this.grupoService.listarUsuariosNoAsignados(grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al listar Usuarios No Asignados por Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Listar usuarios NO asignados", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al listar Usuarios No Asignados por Grupo.",  e);
            return CustomErrorType.serverError("Listar usuarios NO asignados", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/roles-asignados", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
    public ResponseEntity listarRolesAsignados(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(this.rolService.listarRolesPorGrupo(grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al listar Roles Asignados de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Listar Roles de Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al listar Roles Asignados de Grupo.",  e);
            return CustomErrorType.serverError("Listar Roles de Grupo", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/roles-no-asignados", produces = MediaType.APPLICATION_JSON_VALUE ,method = RequestMethod.GET)
    public ResponseEntity listarRolesNoAsignados(@PathVariable("grupoId") Long grupoId) {
        try {
            return ok(this.rolService.listarRolesNoAsignadosPorGrupo(grupoId));
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al listar Roles No Asignados de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Listar roles no asignados", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al listar Roles No Asignados de Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.serverError("Listar roles no asignados", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/asignar-roles", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarRoles(@PathVariable("grupoId") Long grupoId, @RequestBody List<RolDto> listaRoles) {
        try{
            this.grupoService.agregarRoles(grupoId, listaRoles);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al agregar Roles a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Agregar roles", e.getMessage());
        } catch (Exception e){
            log.error("Error no controlado al agregar Roles a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.serverError("Agregar roles", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/remover-roles", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerRoles(@PathVariable("grupoId") Long grupoId, @RequestBody List<RolDto> listaRoles) {
        try{
            this.grupoService.removerRoles(grupoId, listaRoles);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al Remover Roles de un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Remover roles", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al Remover Roles de un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.serverError("Remover roles", "Ocurrio un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/configurar-usuarios", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity configurarUsuarios(@PathVariable("grupoId") Long grupoId,
                                             @RequestBody List<ConfiguracionDto> listaConfiguracion) {
        try {
            this.grupoService.configurarUsuarios(grupoId, listaConfiguracion);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Ocurrio un problema al agregar usuarios al grupo ID: [{}], Mensaje: [{}]", grupoId, e.getMessage());
            return CustomErrorType.badRequest(ConstantesRest.TITULO_GRUPO_ASIGNAR_USUARIOS, e.getMessage());
        } catch (Exception e) {
            log.error("Se ha generado un error no controlado al agregar usuarios al grupo ID: [{}]", grupoId, e);
            return CustomErrorType.serverError(ConstantesRest.TITULO_GRUPO_ASIGNAR_USUARIOS, ConstantesRest.MENSAJE_GENERICO);
        }
    }

    @RequestMapping(value = "/{grupoId}/asignar-usuarios", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarUsuarios(@PathVariable("grupoId") Long grupoId, @RequestBody List<UsuarioDto> listaUsuarios) {
        try{
            this.grupoService.agregarUsuarios(grupoId, listaUsuarios);
            return ok().build();
        } catch (OperationException | NotDataFoundException e){
            log.error("Error al Agregar Usuarios a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Agregar Usuarios a un Grupo", e.getMessage());
        } catch (Exception e){
            log.error("Error No Controlado al Agregar Usuarios a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.serverError("Agregar Usuarios", "Ocurrió un error interno");
        }
    }

    @RequestMapping(value = "/{grupoId}/remover-usuarios", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerUsuarios(@PathVariable("grupoId") Long grupoId, @RequestBody List<UsuarioDto> listaUsuarios) {
        try{
            this.grupoService.removerUsuarios(grupoId, listaUsuarios);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al Remover Usuarios a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.badRequest("Remover Usuarios a  un Grupo", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al Remover Usuarios a un Grupo, Mensaje: [{}]",  e.getMessage());
            return CustomErrorType.serverError("Remover Usuarios a un Grupo", "Ocurrió un error interno");
        }
    }
}
