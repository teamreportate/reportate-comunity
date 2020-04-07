package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.UserService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MuUsuario findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", id)));
    }

    @Override
    public void update(MuUsuario muUsuario) {
        userRepository.save(muUsuario);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(MuUsuario muUsuario) {
        userRepository.save(muUsuario);
    }

    @Override
    public MuUsuario findByUsername(String username) throws NotDataFoundException {
        return this.userRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username).orElseThrow(() -> new NotDataFoundException("No existe ningún usuario con el username '" + username + "'"));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(String username, String newPassword, String confirmPassword) {


        ValidationUtil.throwExceptionIfInvalidText("Nueva contraseña", newPassword, false, 8, 50);
        ValidationUtil.throwExceptionIfInvalidText("Confirmación de contraseña", confirmPassword, false, 8, 50);
        if (!newPassword.equals(confirmPassword)) {
            throw new OperationException("No coincide la nueva contraseña y la confirmación");
        }


        MuUsuario muUsuario = userRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username).orElseThrow(() -> new OperationException("No existe ningún usuario con el username '" + username + "'"));
        if (muUsuario.getEstado() == EstadoEnum.ELIMINADO) {
            log.warn("El usuario: {} está eliminado", username);
            throw new OperationException("Credenciales incorrectos.");
        }
        if (muUsuario.getEstadoUsuario().equals(UserStatusEnum.BLOQUEADO)) {
            log.warn("El Usuario: {} se encuentra bloqueado", muUsuario.getUsername());
            throw new OperationException("El Usuario '" + username + "' se encuentra bloqueado.");
        }

        this.userRepository.modifyPassword(username, this.passwordEncoder.encode(newPassword));
    }

    @Override
    public MuUsuario validarDatosCliente(String username, String password) {
        MuUsuario muUsuario = userRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username).orElseThrow(()->new NotDataFoundException("No se ha encontrado ninguna usuario con username:"+username));
        if (muUsuario.getEstadoUsuario() == UserStatusEnum.BLOQUEADO) {
            log.warn("[{}] Usuario bloqueado", username);
            throw new OperationException("Cuenta bloqueada. Por favor, comuníquese con el administrador del sistema");
        }
        if (muUsuario.getAuthType() != AuthTypeEnum.SISTEMA) {
            log.error("Los clientes externos no pueden autentificarse por AD(LDAP)");
            throw new OperationException("Credenciales  incorrectos");
        }
        return muUsuario;
    }

}
