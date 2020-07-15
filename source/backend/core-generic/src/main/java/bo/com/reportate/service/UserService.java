package bo.com.reportate.service;

import bo.com.reportate.model.MuUsuario;

/**
 * Created by    :Ricardo Laredo.
 * Date          :23/11/2018
 * Project       :reportate
 * Package       :bo.com.Reportate.core.service
 **/
public interface UserService {

    MuUsuario findById(Long id);

    void update(MuUsuario muUsuario);

    void save(MuUsuario muUsuario);

    MuUsuario findByUsername(String username);

    void changePassword(String username, String newPassword, String confirmPassword);

    MuUsuario validarDatosCliente(String username, String password);

}

