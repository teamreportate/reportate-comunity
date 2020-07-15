package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.*;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.EstadoRol;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.RolService;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.LongUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by :Reportate
 * Autor      :vtorrez
 * Date       :07-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service.impl
 * Copyright  : Reportate
 */
@Slf4j
@Service("rolService")
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RecursoRepository recursoRepository;
    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private GrupoRolRepository grupoRolRepository;

    @Override
    public List<RolDto> listar() {
        return rolRepository.listAll();
    }

    @Override
    public RolDto crear(RolDto rol) {
        String campoInvalido = this.registroValidacion(rol);
        if (campoInvalido != null) throw new OperationException(FormatUtil.requerido(campoInvalido));

        MuRol exist = this.rolRepository.findByUpperCaseNombre(rol.getNombre().toUpperCase().trim()).orElse(null);
        if (exist != null) throw new OperationException(FormatUtil.yaRegistrado("Rol", "Nombre", rol.getNombre()));

        MuRol toSave = MuRol.builder()
                .nombre(rol.getNombre().trim())
                .descripcion(rol.getDescripcion().trim())
                .estadoRol(EstadoRol.ACTIVO)

                .build();
        toSave.setEstado(EstadoEnum.ACTIVO);
        this.rolRepository.save(toSave);
        return new RolDto(toSave);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RolDto modificar(String descripcion, String nombre, Long id) {
        if (StringUtil.isEmptyOrNull(nombre))
            throw new OperationException(FormatUtil.requerido("Nombre"));
        if (StringUtil.isEmptyOrNull(descripcion)) throw new OperationException(FormatUtil.requerido("Descripcion"));
        ;

        if (this.rolRepository.existsByNombreAndIdNot(nombre.trim(), id)) {
            throw new OperationException(FormatUtil.yaRegistrado("Rol", "Nombre", nombre));
        }
        log.info(" Iniciando la persistencia en el servicio al modificar");
        this.rolRepository.modify(nombre, descripcion, id);

        return RolDto.builder().descripcion(descripcion).nombre(nombre).estadoRol(EstadoRol.ACTIVO).id(id).build();
    }

    @Override
    public RolDto cambiarEstado(Long idRol) {
        MuRol muRol = this.rolRepository.findById(idRol).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", idRol)));
        if (muRol.getEstadoRol() != null && muRol.getEstadoRol().equals(EstadoRol.ACTIVO))
            muRol.setEstadoRol(EstadoRol.BLOQUEADO);
        else muRol.setEstadoRol(EstadoRol.ACTIVO);
        log.info(" Iniciando la persistencia en el servicio al cambiar estado");
        this.rolRepository.save(muRol);
        return new RolDto(muRol);
    }

    @Override

    public void asignarRecursos(Long rolId, List<RecursoDto> listaRecursosDto) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        List<MuRecurso> listaValida = new ArrayList<>();

        for (RecursoDto muRecurso : listaRecursosDto) {
            if (LongUtil.isNullOrZero(muRecurso.getId()))
                throw new OperationException(FormatUtil.requerido("recursoId"));
            MuRecurso exist = this.recursoRepository.findById(muRecurso.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Recurso", muRecurso.getId())));
            listaValida.add(exist);
        }

        for (MuRecurso muRecurso : listaValida) {
            MuPermiso rolRecurso = this.permisoRepository.findByIdRolAndIdRecurso(muRol.getId(), muRecurso.getId());
            if (rolRecurso != null)
                throw new OperationException(String.format("El Recurso '%s' ya se encuentra asignado al Rol '%s'", muRecurso.getNombre(), muRol.getNombre()));

            MuPermiso muPermiso = MuPermiso.builder()
                    .idRol(muRol)
                    .idRecurso(muRecurso)
                    .lectura(true)
                    .autorizacion(false)
                    .eliminacion(false)
                    .escritura(false)
                    .solicitud(false)
                    .build();
            muPermiso.setEstado(EstadoEnum.ACTIVO);
            log.info(" Iniciando la persistencia en el servicio al asignar recursos");
            this.permisoRepository.save(muPermiso);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class, NotDataFoundException.class, OperationException.class})
    public void configurarRcursos(Long rolId, List<ConfiguracionDto> listaConfiguracion) {
        MuRol authRol = this.rolRepository.findActiveById(rolId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Rol", rolId)));
        for (ConfiguracionDto configuracionDto : listaConfiguracion) {
            MuRecurso authRecurso = this.recursoRepository.findActiveById(configuracionDto.getAsignacionId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Recurso", configuracionDto.getAsignacionId())));
            MuPermiso authPermiso = this.permisoRepository.findByIdRolAndIdRecurso(authRol.getId(), authRecurso.getId());
            if (configuracionDto.getNuevo()) {
                if (authPermiso == null) {
                    this.permisoRepository.save(MuPermiso.builder()
                            .idRol(authRol)
                            .idRecurso(authRecurso)
                            .lectura(true)
                            .autorizacion(false)
                            .eliminacion(false)
                            .escritura(false)
                            .solicitud(false)
                            .build());
                    MuPermiso authPermisoPadre = this.permisoRepository.findByIdRolAndIdRecurso(authRol.getId(), authRecurso.getIdRecursoPadre().getId());
                    if (authPermisoPadre == null)
                        this.permisoRepository.save(MuPermiso.builder()
                                .idRol(authRol)
                                .idRecurso(authRecurso.getIdRecursoPadre())
                                .lectura(true)
                                .autorizacion(false)
                                .eliminacion(false)
                                .escritura(false)
                                .solicitud(false)
                                .build());
                }
            } else if (authPermiso != null) {
                this.permisoRepository.delete(authPermiso);
                List<MuPermiso> permisoList = this.permisoRepository.listByIdRol(authRol);
                if (permisoList.size() == 1 && permisoList.get(0).getIdRecurso().getIdRecursoPadre() == null)
                    this.permisoRepository.delete(permisoList.get(0));
            }
        }
    }

    @Override
    public void removerRecursos(Long rolId, List<RecursoDto> listaRecursos) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        for (RecursoDto muRecurso : listaRecursos) {
            if (LongUtil.isNullOrZero(muRecurso.getId()))
                throw new OperationException(FormatUtil.requerido("Id Recurso"));
            MuRecurso exist = this.recursoRepository.findById(muRecurso.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Recurso", muRecurso.getId())));
            this.permisoRepository.deleteByIdRolAndIdRecurso(muRol, exist);
        }
    }


    @Override
    public List<RolDto> listarRolesPorGrupo(Long grupoId) {
        MuGrupo MUGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        return this.rolRepository.listAsignedByGrupo(MUGrupo.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDto> listarRolesNoAsignadosPorGrupo(Long grupoId) {
        MuGrupo muGrupo = this.grupoRepository.findById(grupoId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Grupo", grupoId)));
        return this.rolRepository.listNotAsignedByGrupo(muGrupo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrupoDto> listarGruposAsignados(Long rolId) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        return this.grupoRepository.listAsignedByRol(muRol.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrupoDto> listarGruposNoAsignados(Long rolId) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        return this.grupoRepository.listNotAsignedByRol(muRol.getId());
    }


    @Override
    @Transactional(readOnly = true)
    public List<RecursoDto> listarRecursosAsignados(Long rolId) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        return recursoRepository.listAsignedByRol(muRol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecursoDto> listarRecursosNoAsignados(Long rolId) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(()-> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        return recursoRepository.listNotAsignedByRol(muRol);
    }

    @Override
    public void asignarGrupos(Long rolId, List<GrupoDto> listaGrupo) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        for (GrupoDto MUGrupo : listaGrupo) {
            if (LongUtil.isNullOrZero(MUGrupo.getId())) throw new OperationException(FormatUtil.requerido("Id Grupo"));
            MuGrupo exist = this.grupoRepository.findById(MUGrupo.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Grupo", MUGrupo.getId())));
            MuGrupoRol muGrupoRol = MuGrupoRol.builder()
                    .idGrupo(exist)
                    .idRol(muRol)
                    .build();
            this.grupoRolRepository.save(muGrupoRol);
        }
    }

    @Override
    public void removerGrupos(Long rolId, List<GrupoDto> MuGrupoList) {
        MuRol muRol = this.rolRepository.findById(rolId).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Rol", rolId)));
        for (GrupoDto MUGrupo : MuGrupoList) {
            if (LongUtil.isNullOrZero(MUGrupo.getId())) throw new OperationException(FormatUtil.requerido("Id Grupo"));
            MuGrupo exist = this.grupoRepository.findById(MUGrupo.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Grupo", MUGrupo.getId())));
            grupoRolRepository.deleteByIdGrupoAndIdRol(exist, muRol);
        }
    }

    @Override
    public void asignarUsuarios(Long rolId, List<UsuarioDto> listaUsuarios) {
        //TODO: No estaba implementado en la version original, falta implementar.
    }

    @Override
    public void removerUsuarios(Long rolId, List<UsuarioDto> listaUsuarios) {
        //TODO: No estaba implementado en la version original, falta implementar.
    }

    private String registroValidacion(RolDto rol) {
        if (StringUtil.isEmptyOrNull(rol.getNombre())) return "Nombre";
        if (StringUtil.isEmptyOrNull(rol.getDescripcion())) return "Descripción";
        return null;
    }
}
