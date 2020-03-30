package bo.com.reportate.service;

import bo.com.reportate.model.MuPermiso;

import java.util.List;

public interface PermisoService {

    List<MuPermiso> listarPermisos(Long idUsuario);
}
