package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.ConfiguracionDto;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.EstadoGrupo;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.GrupoService;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.LongUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by :MC4
 * Autor      :vtorrez
 * Date       :07-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service.impl
 * Copyright  : MC4
 */
@Slf4j
@Service("grupoService")
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private GrupoRolRepository grupoRolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;


    @Override
    public List<GrupoDto> listar() {
        return grupoRepository.listAll();
    }

    @Override
    public List<GrupoDto> listarBusqueda(String criterioBusqueda) {
        if (StringUtil.isEmptyOrNull(criterioBusqueda)) {
            criterioBusqueda = "";
        }
        criterioBusqueda = "%" + criterioBusqueda + "%";
        return grupoRepository.searchAll(criterioBusqueda);
    }

    @Override
    public List<UsuarioDto> listarUsuariosAsignados(Long grupoId) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        return this.usuarioRepository.listUsersAssignedInGroups(muGrupo);
    }

    @Override
    public List<UsuarioDto> listarUsuariosNoAsignados(Long grupoId) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        return this.usuarioRepository.listUsersNotAssignedInGroups(muGrupo);
    }

    @Override
    public MuGrupo obtener(Long id) {
        MuGrupo grupo = grupoRepository.getOne(id);
        if (grupo == null) {
            throw new OperationException(FormatUtil.noRegistrado("AuthGrupo", id));
        }
        return grupo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, NotDataFoundException.class})
    public GrupoDto nuevoGrupo(GrupoDto grupoDto) {
        String campoInvalido = this.registroValidacion(grupoDto);
        if (campoInvalido != null) throw new OperationException(FormatUtil.requerido(campoInvalido));

        MuGrupo grupoValidacion = this.grupoRepository.findByUpperCaseNombre(grupoDto.getNombre().toUpperCase().trim()).orElse(null);
        if (grupoValidacion != null) {
            throw new OperationException(FormatUtil.yaRegistrado("Grupo", "Nombre", grupoDto.getNombre().trim()));
        }

        grupoValidacion = MuGrupo.builder()
                .nombre(grupoDto.getNombre().trim())
                .descripcion(grupoDto.getDescripcion().trim())
                .estadoGrupo(EstadoGrupo.ACTIVO)
                .build();
        grupoValidacion.setEstado(EstadoEnum.ACTIVO);
        log.info(Process.GRUPO + " Iniciando la persistencia en el servicio al agregar nuevo grupo");
        grupoRepository.save(grupoValidacion);

        if (grupoDto.getRoles() == null) {
            log.info("No existen Roles para asignar al grupo: {}", grupoValidacion.getNombre());
            return new GrupoDto(grupoValidacion);
        }

        for (RolDto rol : grupoDto.getRoles()) {
            if (LongUtil.isNullOrZero(rol.getId())) throw new OperationException(FormatUtil.requerido("Id Rol"));
            MuRol exist = this.rolRepository.findById(rol.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Rol", rol.getId())));
            MuGrupoRol muGrupoRol = MuGrupoRol.builder()
                    .idGrupo(grupoValidacion)
                    .idRol(exist)
                    .build();
            this.grupoRolRepository.save(muGrupoRol);
        }

        return new GrupoDto(grupoValidacion);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, NotDataFoundException.class})
    @Override
    public GrupoDto actualizarGrupo(GrupoDto grupoData, Long id) {
        MuGrupo muGrupo = this.grupoRepository.findById(id).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", id)));
        String campoInvalido = this.registroValidacion(grupoData);
        if (campoInvalido != null) throw new OperationException(FormatUtil.requerido(campoInvalido));

        MuGrupo grupoValidacion = this.grupoRepository.findByUpperCaseNombre(grupoData.getNombre().toUpperCase().trim()).orElse(null);
        if (grupoValidacion != null) {
            if (!muGrupo.getId().equals(grupoValidacion.getId())) {
                throw new OperationException(FormatUtil.yaRegistrado("Grupo", "Nombre", muGrupo.getNombre().trim()));
            }
        }

        muGrupo.setNombre(grupoData.getNombre().trim());
        muGrupo.setDescripcion(grupoData.getDescripcion().trim());
        this.grupoRolRepository.deleteByIdGrupo(muGrupo);
        if (grupoData.getRoles() != null) {
            for (RolDto rolDto:grupoData.getRoles()) {
                MuRol exist = this.rolRepository.findById(rolDto.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Rol", rolDto.getId())));
                MuGrupoRol muGrupoRol = MuGrupoRol.builder()
                        .idGrupo(grupoValidacion)
                        .idRol(exist)
                        .build();
                this.grupoRolRepository.save(muGrupoRol);
            }
        }
        return new GrupoDto(muGrupo);
    }

    @Override
    public GrupoDto cambiarEstado(Long id) {
        MuGrupo muGrupo = grupoRepository.findById(id).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", id)));
        if (muGrupo.getEstadoGrupo() != null && muGrupo.getEstadoGrupo().equals(EstadoGrupo.ACTIVO)) {
            muGrupo.setEstadoGrupo(EstadoGrupo.BLOQUEADO);
        }else {
            muGrupo.setEstadoGrupo(EstadoGrupo.ACTIVO);
        }
        log.info("Iniciando la persistencia en el servicio al cambiar estado");
        this.grupoRepository.save(muGrupo);
        return new GrupoDto(muGrupo);
    }

    @Override
    public void agregarRoles(Long grupoId, List<RolDto> listaRoles) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        for (RolDto rol : listaRoles) {
            if (LongUtil.isNullOrZero(rol.getId())) throw new OperationException(FormatUtil.requerido("Id Rol"));
            MuRol exist = this.rolRepository.findById(rol.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Rol", rol.getId())));
            MuGrupoRol muGrupoRol = MuGrupoRol.builder()
                    .idGrupo(muGrupo)
                    .idRol(exist)
                    .build();
            this.grupoRolRepository.save(muGrupoRol);
        }
    }

    @Override
    public void removerRoles(Long grupoId, List<RolDto> listaRoles) {
        MuGrupo authGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        for (RolDto rol : listaRoles) {
            if (LongUtil.isNullOrZero(rol.getId())) throw new OperationException(FormatUtil.requerido("idRol"));
            MuRol exist = this.rolRepository.findById(rol.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Rol", rol.getId())));
            this.grupoRolRepository.deleteByIdGrupoAndIdRol(authGrupo, exist);
        }
    }

    @Override
    public void agregarUsuarios(Long grupoId, List<UsuarioDto> listaUsuarios) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        for (UsuarioDto usuario : listaUsuarios) {
            if (LongUtil.isNullOrZero(usuario.getId()))
                throw new OperationException(FormatUtil.requerido("Id Usuario"));
            MuUsuario exist = this.usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuario.getId())));
            MuUsuarioGrupo muUsuarioGrupo = MuUsuarioGrupo.builder()
                    .idGrupo(muGrupo)
                    .idUsuario(exist)
                    .build();
            this.usuarioGrupoRepository.save(muUsuarioGrupo);
        }

    }

    @Override
    public void configurarUsuarios(Long grupoId, List<ConfiguracionDto> listaConfiguracion) {
        MuGrupo authGrupo = this.grupoRepository.findById(grupoId).orElseThrow(()-> new NotDataFoundException(FormatUtil.noRegistrado("Grupo", grupoId)));
        for (ConfiguracionDto configuracionDto : listaConfiguracion) {
            MuUsuario authUsuario = this.usuarioRepository.findActiveById(configuracionDto.getAsignacionId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", configuracionDto.getAsignacionId())));
            MuUsuarioGrupo authUsuarioGrupo = this.usuarioGrupoRepository.findByIdUsuarioAndIdGrupo(authUsuario.getId(), authGrupo.getId());
            if (configuracionDto.getNuevo()) {
                if (authUsuarioGrupo == null)
                    this.usuarioGrupoRepository.save(MuUsuarioGrupo.builder()
                            .idGrupo(authGrupo)
                            .idUsuario(authUsuario)
                            .build());
            } else if (authUsuarioGrupo != null) this.usuarioGrupoRepository.delete(authUsuarioGrupo);
        }
    }

    @Override
    public void removerUsuarios(Long grupoId, List<UsuarioDto> listaUsuarios) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        for (UsuarioDto usuario : listaUsuarios) {
            if (LongUtil.isNullOrZero(usuario.getId()))
                throw new OperationException(FormatUtil.requerido("Id Usuario"));
            MuUsuario exist = this.usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuario.getId())));
            this.usuarioGrupoRepository.deleteByIdGrupoAndIdUsuario(muGrupo, exist);
        }
    }

    String registroValidacion(GrupoDto authGrupo) {
        if (StringUtil.isEmptyOrNull(authGrupo.getNombre())) return "Nombre";
        if (StringUtil.isEmptyOrNull(authGrupo.getDescripcion())) return "Descripción";
        return null;
    }

}
