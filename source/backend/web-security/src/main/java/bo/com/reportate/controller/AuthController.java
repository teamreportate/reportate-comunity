package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.jwt.JwtTokenProvider;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.enums.*;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.TokenService;
import bo.com.reportate.service.UserService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.web.AuthenticationRequest;
import bo.com.reportate.web.ChangePasswordRequest;
import bo.com.reportate.web.MovilAuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signin",consumes =  MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/movil-signin", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity movilSignin(@RequestBody MovilAuthenticationRequest data) {
        try {
            Map<Object, Object> model = new HashMap<>();
            model.put("username", data.getName());
            model.put("token", validateAuthDataUserMovil(data));
            return ok(model);
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
        ValidationUtil.throwExceptionIfInvalidText("Usuario",data.getUsername(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("Contraseña",data.getPassword(),true,30);
        MuUsuario authUsuario = usuarioRepository.findByUsernameIgnoreCaseAndAuthType(data.getUsername(), AuthTypeEnum.SISTEMA).orElseThrow(()->new BadCredentialsException("Credenciales inconrrectas"));
        if(authUsuario == null) {
            throw new OperationException("La cuenta no existe ó se encuetra bloqueada. Por favor comuníquese con el aministrador del sistema.");
        } else{
            if( authUsuario.getEstadoUsuario() == UserStatusEnum.BLOQUEADO){
                log.warn("[{}] Usuario bloqueado",data.getUsername());
                throw new OperationException("Cuenta bloqueada. Por favor comuníquese con el aministrador del sistema.");
            }
        }
        if (authUsuario.getAuthType().equals(AuthTypeEnum.SISTEMA)) {
            return this.dbLogin(data.getUsername(),data.getPassword(), authUsuario);
        }
        throw new OperationException("Tipo de autenticación no válida");
    }

    private String validateAuthDataUserMovil(MovilAuthenticationRequest data) {
        ValidationUtil.throwExceptionIfInvalidText("Username",data.getRemoteId(),true,500);
        ValidationUtil.throwExceptionIfInvalidText("Nombre",data.getName(),true,255);
        ValidationUtil.throwExceptionIfInvalidText("Email",data.getName(),false,255);
        String username = data.getRemoteId();
        MuUsuario authUsuario = null;
        try {
            authUsuario = usuarioRepository.findByUsernameIgnoreCaseAndTipoUsuarioAndAuthType(username, TipoUsuarioEnum.PACIENTE, data.getSource()).orElse(null);
        }catch (Exception e){
            log.error("No se encontro el usuario " + username + "Registrado en la Base de datos",e);
        }

        if(authUsuario == null) {
           authUsuario = MuUsuario.builder()
                   .authType(data.getSource())
                   .estadoUsuario(UserStatusEnum.ACTIVO)
                   .tipoUsuario(TipoUsuarioEnum.PACIENTE)
                   .nombre(data.getName())
                   .username(data.getRemoteId())
                   .password(this.passwordEncoder.encode(username))
                   .email(data.getEmail()).build();
           this.userService.save(authUsuario);
        } else{
            if( authUsuario.getEstadoUsuario() == UserStatusEnum.BLOQUEADO){
                log.warn("[{}] Usuario bloqueado",username);
                throw new OperationException("Cuenta bloqueada. Por favor comuníquese con el aministrador del sistema.");
            }
        }
        if (authUsuario.getAuthType().equals(AuthTypeEnum.GOOGLE) || authUsuario.getAuthType().equals(AuthTypeEnum.FACEBOOK)) {
            return this.dbLogin(data.getRemoteId(), data.getRemoteId(), authUsuario);
        }
        throw new OperationException("Tipo de autenticación no válida");
    }



    private String dbLogin(String username, String password, MuUsuario authUsuario) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.createToken(username, authUsuario);
        } catch (AuthenticationException e) {
            log.warn("Las credenciales son incorrectas del usuario: {} ", authUsuario.getUsername());
            throw new BadCredentialsException("Las credenciales son incorrectas.");
        }catch (Exception e){
            log.error("Error de autentificacion: ",e);
            throw e;
        }
    }

}
