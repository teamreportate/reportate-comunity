package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.jwt.JwtTokenProvider;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.TokenService;
import bo.com.reportate.service.UserService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.web.AuthenticationRequest;
import bo.com.reportate.web.ChangePasswordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;
    @Autowired private LogService logService;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {
        try {
            String token = validateAuthData(data);
            Map<Object, Object> model = new HashMap<>();
            model.put("username", data.getUsername());
            model.put("token", token);
            return ok(model);
        }catch (OperationException e){
            return CustomErrorType.badRequest("Login", e.getMessage());
        }catch (BadCredentialsException e){
                return CustomErrorType.badRequest("Login", e.getMessage());
        }catch (Exception e){
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @PostMapping("/movil-signin")
    public ResponseEntity movilSignin(@RequestBody AuthenticationRequest data) {
        try {
            return ok(validateAuthData(data));
        }catch (OperationException e){
            return CustomErrorType.badRequest("Login", e.getMessage());
        }catch (BadCredentialsException e){
            return CustomErrorType.badRequest("Login", e.getMessage());
        }catch (Exception e){
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity logout() {
        try {
            if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                MuUsuario authUsuario = (MuUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                tokenService.inhabilitarToken(authUsuario);
                log.info("Cerrando sesión del usuario: {}", authUsuario.getUsername());
                logService.info(Process.SESION, "Cerrando sesión del usuario: {}", authUsuario.getUsername());
            }
            return ok().build();
        }catch (OperationException | BadCredentialsException e){
            return CustomErrorType.badRequest("Inicio de Sesión", e.getMessage());
        }catch (Exception e){
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest data) {
        try {
            this.userService.changePassword(data.getUsername(),data.getNewPassword(),data.getConfirmPassword());
            return ok().build();
        }catch (OperationException e){
            return CustomErrorType.badRequest("Cambio contraseña", e.getMessage());
        }catch (Exception e){
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    private String validateAuthData(AuthenticationRequest data) {
        String username = data.getUsername();
        MuUsuario authUsuario = null;
        try {
            authUsuario = usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, data.getUsername()).orElseThrow(()->new BadCredentialsException("Username " + username + "no encontrado."));
        }catch (Exception e){
            log.error("No se encontro el usuario " + username + "Registrado en la Base de datos");
        }

        if(authUsuario == null) {
            throw new OperationException("Cuenta bloqueada. Por favor comuníquese con el aministrador del sistema.");
        }
        else{
            if( authUsuario.getEstadoUsuario() == UserStatusEnum.BLOQUEADO){
                log.warn("[{}] Usuario bloqueado",data.getUsername());
                throw new OperationException("Cuenta bloqueada. Por favor comuníquese con el aministrador del sistema.");
            }
        }
        if (authUsuario.getAuthType() == AuthTypeEnum.SISTEMA) {
            return this.dbLogin(data, authUsuario);
        }
        throw new OperationException("Tipo de autenticación no válida");
    }



    private String dbLogin(AuthenticationRequest data, MuUsuario authUsuario) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.createToken(data.getUsername(), authUsuario);
        } catch (AuthenticationException e) {
            log.warn("Las credenciales son incorrectas del usuario: {} ", authUsuario.getUsername());
            throw new BadCredentialsException("Las credenciales son incorrectas.");
        }catch (Exception e){
            log.error("Error de autentificacion: ",e);
            throw e;
        }
    }

}
