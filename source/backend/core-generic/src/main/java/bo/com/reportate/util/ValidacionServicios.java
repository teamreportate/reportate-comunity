package bo.com.reportate.util;

import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.utils.StringUtil;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.util
 * date:    08-08-19
 * author:  fmontero
 **/

public class ValidacionServicios {

    public static String validarUsuarioEdit(UsuarioDto authUsuario) {
        if (StringUtil.isEmptyOrNull(authUsuario.getNombre())) return "Nombre Usuario";
        if (StringUtil.isEmptyOrNull(authUsuario.getEmail())) return "Email Usuario";
        return null;
    }
}
