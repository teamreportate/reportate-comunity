package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.jwt.JwtTokenProvider;
import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.TokenDto;
import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.service.RecursoService;
import bo.com.reportate.service.TokenService;
import bo.com.reportate.service.UsuarioService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.DateUtil;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@Slf4j
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RecursoService recursoService;

    /**
     * Lista a todos los usuarios habilitados
     *
     * @return
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<UsuarioDto>> listar() {
        try {
            return ok(this.usuarioService.listar());
        } catch (OperationException e) {
            log.error("Error al listar usuarios habilitados");
            return CustomErrorType.badRequest("Listar Usuarios", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al listar Usuarios habilitados", e);
            return CustomErrorType.badRequest("Listar Usuarios", "Ocurrió un error interno al listar los usuarios.");
        }
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<UsuarioDto> nuevoUsuario(@RequestBody UsuarioDto authUsuario, @RequestHeader("passwordConfirm") String passwordConfirm) {
        try {
            return ok(usuarioService.nuevoUsuario(authUsuario, passwordConfirm));
        } catch (OperationException e) {
            log.error("Error al guardar un nuevo usuario: {}. Causa: [{}]", authUsuario.getUsername(), e.getMessage());
            return CustomErrorType.badRequest("Registro Usuario", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al guardar un nuevo usuario: {}",authUsuario.getUsername(), e);
            return CustomErrorType.serverError("Registro Usuario", "Ocurrió un error interno al guardar un nuevo usuario.");
        }
    }

    /**
     * Permite actualizarGrupo los atributos de un usuario
     *
     * @param id
     * @param usuario
     * @return
     */
    @RequestMapping(value = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> actualizarUsuario(@PathVariable("usuarioId") Long id, @RequestBody UsuarioDto usuario) {
        try {
            return ok(this.usuarioService.actualizarUsuario(usuario, id));
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al actualizar usuario: {}. Causa: [{}]",usuario.getUsername(),  e.getMessage());
            return CustomErrorType.badRequest("Actualizar Usuario", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al actualizar usuario", e);
            return CustomErrorType.serverError("Actualizar Usuario", "Ocurrió un error interno al actualizar el usuario.");
        }
    }

    /**
     * Permite cambiar el estado de un usuario
     *
     * @param usuarioId
     * @return
     */
    @RequestMapping(value = "/{usuarioId}/cambiar-estado", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<UsuarioDto> cambiarEstado(@PathVariable("usuarioId") Long usuarioId) {
        try {
            return ok(usuarioService.cambiarEstado(usuarioId));
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al inhabilitar usuario. Causa: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Inhabilitar Usuario", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al inhabilitar usuario.", e);
            return CustomErrorType.serverError("Inhabilitar Usuario", "Ocurrió un error interno al cambiar estado.");
        }
    }

    /**
     * Permite asignar grupos a un usuario
     *
     * @param usuarioId
     * @param listaGrupos
     * @return
     */
    @RequestMapping(value = "/{usuarioId}/asignar-grupos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity asignarGrupos(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<GrupoDto> listaGrupos) {
        try {
            this.usuarioService.agregarGrupos(usuarioId, listaGrupos);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al agregar grupos al usuario. Causa: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Agregar grupos", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al agregar grupos al usuario.", e);
            return CustomErrorType.serverError("Asignar Grupos", "Ocurrió un error interno al asignar grupos.");
        }
    }

    @RequestMapping(value = "/{usuarioId}/remover-grupos", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity removerGrupos(@PathVariable("usuarioId") Long usuarioId, @RequestBody List<GrupoDto> MuGrupoList) {
        try {
            this.usuarioService.removerGrupos(usuarioId, MuGrupoList);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al remover grupos de usuario. Causa: [{}]", e.getMessage());
            return CustomErrorType.badRequest("Remover grupos", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al remover grupos de usuario.", e);
            return CustomErrorType.serverError("Remover grupos", "Ocurrió un error interno al remover grupos.");
        }
    }

    @RequestMapping(value = "/{usuarioId}/grupos-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<GrupoDto>> listarGruposAsignados(@PathVariable("usuarioId") Long usuarioId) {
        try {
            return ok(this.usuarioService.listarGruposAsignados(usuarioId));
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al listar grupos asignados de Usuario con ID: [{}],Mensaje: [{}]", usuarioId, e.getMessage());
            return CustomErrorType.badRequest("Listar grupos de usuario", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al listar grupos asignados de usuario.", e);
            return CustomErrorType.serverError("Listar grupos de usuarios", "Ocurrió un error interno al listar grupos asignados.");
        }
    }

    @RequestMapping(value = "/{usuarioId}/grupos-no-asignados", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<GrupoDto>> listarGruposNoAsignados(@PathVariable("usuarioId") Long usuarioId) {
        try {
            return ok(this.usuarioService.listarGruposNoAsignados(usuarioId));
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al listar grupos no asignados del usuario con ID: [{}]. Causa: [{}]", usuarioId, e.getMessage());
            return CustomErrorType.badRequest("Listar grupos no asignados", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al listar grupos no asignados de usuario con ID: [{}].", usuarioId, e);
            return CustomErrorType.serverError("Listar grupos No asignados", "Ocurrió un error interno al listar grupos no asignados.");
        }
    }

    @RequestMapping(value = "/{usuarioId}/cambiar-password", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity cambiarPassword(@PathVariable("usuarioId") Long usuarioId, @RequestHeader("old") String oldPassword, @RequestHeader("new") String newPassword) {
        try {
            this.usuarioService.cambiarContrasenia(usuarioId, oldPassword, newPassword);
            return ok().build();
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al cambiar contrasenia de usuario con ID: [{}]. Causa: [{}]", usuarioId, e.getMessage());
            return CustomErrorType.badRequest("Cambiar Contraseña", e.getMessage());
        } catch (Exception e) {
            log.error("Error no controlado al cambiar contrasenia al usuario con ID: [{}].", usuarioId, e);
            return CustomErrorType.serverError("Cambiar Contraseña", "Ocurrio un error interno al cambiar contraseña.");
        }
    }

    @RequestMapping(value = "/{usuarioId}/token", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<TokenDto> generarToken(@PathVariable("usuarioId") Long usuarioId, @RequestBody TokenDto tokenDto) {
        try {
            MuUsuario muUsuario = this.usuarioService.obtenerUsuario(usuarioId);
            String campoInvalido = tokenDto.registroValidacion();
            if (!StringUtil.isEmptyOrNull(campoInvalido))
                throw new OperationException(FormatUtil.requerido(campoInvalido));
            Date fechaActual = new Date();
            if (tokenDto.getIndefinido() != null && tokenDto.getIndefinido()) {
                tokenDto.setFechaExpiracion(DateUtil.adicionarAnios(new Date(), 50));
            } else tokenDto.setIndefinido(false);
            String token = this.jwtTokenProvider.createTokenApi(muUsuario.getUsername(), fechaActual, tokenDto.getFechaExpiracion());

            MuToken muToken = MuToken.builder()
                    .fechaInicio(fechaActual)
                    .idUsuario(muUsuario)
                    .token(token)
                    .fechaExpiracion(tokenDto.getFechaExpiracion())
                    .indefinido(tokenDto.getIndefinido())
                    .build();

            return ok(this.tokenService.registroToken(muToken));
        } catch (OperationException | NotDataFoundException e) {
            log.error("Error al generar token de usuario con Id: [{}]. Causa [{}]", usuarioId, e.getMessage());
            return CustomErrorType.badRequest("Generar Token", e.getMessage());
        } catch (Exception e) {
            log.error("Error al generar token de usuario con Id: [{}]", usuarioId, e);
            return CustomErrorType.serverError("Generar Token", "Ocurrió un error interno al generar token.");
        }
    }


    @RequestMapping(value = "/{usuarioId}/lista-token", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<TokenDto>> listaTokenUsuario(@PathVariable("usuarioId") Long usuarioId) {
        try {
            return ok(this.tokenService.obtenerListadoTokenUsuario(usuarioId));
        } catch (NotDataFoundException e) {
            log.error("Error al recuperar lista de token usuario con Id: [{}]. Causa [{}]", usuarioId, e.getMessage());
            return CustomErrorType.badRequest("Lista de Token", e.getMessage());
        } catch (Exception e) {
            log.error("Error al recuperar lista de token usuario con Id: [{}].", usuarioId, e);
            return CustomErrorType.serverError("Lista de Token", "Ocurrió un error interno al listar token.");
        }
    }
    @RequestMapping(value = "/token/{tokenId}/inhabilitar", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<List<TokenDto>> inhabilitarToken(@PathVariable("tokenId") Long tokenId) {
        try {
            this.tokenService.inahbilitarToken(tokenId);
            return ok().build();
        } catch (NotDataFoundException e) {
            log.error("Error al inhabilitar el token con Id: [{}]. Causa [{}]", tokenId, e.getMessage());
            return CustomErrorType.badRequest("Inhabilitar Token", e.getMessage());
        } catch (Exception e) {
            log.error("Error al inhabilitar el token con Id: [{}].", tokenId, e);
            return CustomErrorType.serverError("Inhabilitar  Token", "Ocurrió un error interno al Inhabilitar token.");
        }
    }

    @RequestMapping(value = "/obtener-menu/{username}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity listarMenu(@PathVariable("username") String username, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ok(recursoService.listMenu(userDetails.getUsername()));
        } catch (NotDataFoundException e) {
            log.error("Ocurrio un problema al recuperar lista de menu para usuario: [{}]", username);
            return CustomErrorType.badRequest("Actualizar Menu", e.getMessage());
        } catch (Exception e) {
            log.error("Ocurrio un error al recuperar lista de menu para usuario: [{}]", username, e);
            return CustomErrorType.serverError("Obtener menú", "Ocurrió un error interno");
        }
    }

}
