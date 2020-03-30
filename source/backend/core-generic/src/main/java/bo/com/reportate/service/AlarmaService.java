package bo.com.reportate.service;

import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.MuAlarmaUsuario;
import bo.com.reportate.model.dto.AlarmaDto;

import java.util.List;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service
 * date:    14-06-19
 * author:  fmontero
 **/
public interface AlarmaService {
    List<MuAlarmaUsuario> findAllByUsuario(Long userId);

    List<MuAlarmaUsuario> findAllByAlarmaId(Long alarmaId);

    List<AlarmaDto> findAll();

    MuAlarma actualizarDatos(Long alarmaId, MuAlarma muAlarma);

    Boolean asignacion(Long alarmaId, List<MuAlarmaUsuario> alarmaUsuarioList);

    void save(MuAlarma muAlarma);

    Boolean asignarUsuarioPrincipal(Long alarmaId, Long usuarioId);
}
