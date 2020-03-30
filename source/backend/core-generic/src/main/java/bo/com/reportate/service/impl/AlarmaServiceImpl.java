package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.MuAlarmaUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.AlarmaDto;
import bo.com.reportate.repository.AlarmaReporsitory;
import bo.com.reportate.repository.AlarmaUsuarioRepository;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.AlarmaService;
import bo.com.reportate.utils.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service.impl
 * date:    14-06-19
 * author:  fmontero
 **/

@Service("alarmaService")
public class AlarmaServiceImpl implements AlarmaService {
    @Autowired private AlarmaUsuarioRepository alarmaUsuarioRepository;
    @Autowired private AlarmaReporsitory alarmaReporsitory;
    @Autowired private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MuAlarmaUsuario> findAllByUsuario(Long userId) {
        MuUsuario muUsuario = this.usuarioRepository.findById(userId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", userId)));
        return this.alarmaUsuarioRepository.findAllByIdUsuario(muUsuario.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<MuAlarmaUsuario> findAllByAlarmaId(Long alarmaId) {
        MuAlarma muAlarma = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("SfeAlarma", alarmaId)));
        return this.alarmaUsuarioRepository.findAllByAlarmaId(muAlarma);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlarmaDto> findAll() {
        return this.alarmaReporsitory.findAllActive();
    }

    @Override
    public MuAlarma actualizarDatos(Long alarmaId, MuAlarma muAlarma) {
        MuAlarma older = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("SfeAlarma", alarmaId)));
        older.setAsunto(muAlarma.getAsunto());
        older.setDescripcion(muAlarma.getDescripcion());
        older.setAtributosSeleccionados(muAlarma.getAtributosSeleccionados());
        older.setContenido(muAlarma.getContenido());
        this.alarmaReporsitory.save(older);
        return older;
    }

    @Override
    public Boolean asignacion(Long alarmaId, List<MuAlarmaUsuario> alarmaUsuarioList) {
        MuAlarma muAlarma = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("SfeAlarma", alarmaId)));
        alarmaUsuarioList.forEach(muAlarmaUsuario -> {
            MuUsuario muUsuario = this.usuarioRepository.findById(muAlarmaUsuario.getIdMuUsuario().getId()).orElse(null);
            if (muUsuario != null) {
                MuAlarmaUsuario newAlarmaUsuario = MuAlarmaUsuario.builder()
                        .idMuUsuario(muUsuario)
                        .idMuAlarma(muAlarma)
                        .principal(false)
                        .build();
                this.alarmaUsuarioRepository.save(newAlarmaUsuario);
            }
        });
        return true;
    }

    @Override
    public void save(MuAlarma muAlarma) {
        this.alarmaReporsitory.save(muAlarma);
    }

    @Override
    public Boolean asignarUsuarioPrincipal(Long alarmaId, Long usuarioId) {
        MuAlarma muAlarma = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Alarma", alarmaId)));
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        MuAlarmaUsuario alarmaUsuario = this.alarmaUsuarioRepository.obtenerUsuarioPrincipal(muAlarma.getId());
        if (alarmaUsuario != null) {
            alarmaUsuario.setPrincipal(false);
            this.alarmaUsuarioRepository.save(alarmaUsuario);
        }

        MuAlarmaUsuario principal = this.alarmaUsuarioRepository.obtenerPorUsuarioAlarma(muAlarma.getId(), muUsuario.getId());
        if (principal != null) {
            principal.setPrincipal(true);
            this.alarmaUsuarioRepository.save(principal);
        }
        return true;
    }
}
