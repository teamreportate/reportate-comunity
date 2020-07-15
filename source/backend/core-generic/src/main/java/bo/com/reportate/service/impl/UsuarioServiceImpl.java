package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.*;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.TipoUsuarioEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.UsuarioService;
import bo.com.reportate.util.ValidacionServicios;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.LongUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private GrupoRepository grupoRepository;
    @Autowired private UsuarioGrupoRepository usuarioGrupoRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AlarmaReporsitory alarmaReporsitory;
    @Autowired private DepartamentoUsuarioRepository departamentoUsuarioRepository;
    @Autowired private MunicipioUsuarioRepository municipioUsuarioRepository;
    @Autowired private CentroSaludUsuarioRepository centroSaludUsuarioRepository;
    @Autowired private DepartamentoRepository departamentoRepository;
    @Autowired private MunicipioRepository municipioRepository;
    @Autowired private CentroSaludRepository centroSaludRepository;


    @Override
    public List<UsuarioDto> listar(Authentication authentication) {
        MuUsuario  usuario = (MuUsuario) authentication.getPrincipal();
        List<Departamento> departamentos = this.departamentoUsuarioRepository.listarDepartamentoAsignados(usuario);
        List<Municipio> municipios = this.municipioUsuarioRepository.listarMunicipiosAsignados(usuario,departamentos);
        List<CentroSalud> centroSaluds = this.centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuario,municipios);
        return usuarioRepository.findAllByEstadoAndTipoUsuarioNotOrderByNombreAsc(EstadoEnum.ACTIVO, TipoUsuarioEnum.PACIENTE);
    }

    @Override
    public Page<UsuarioDto> listarUsuarioPacientes(Pageable pageable) {
        return usuarioRepository.findByEstadoAndTipoUsuarioOrderByNombreAsc(EstadoEnum.ACTIVO,TipoUsuarioEnum.PACIENTE, pageable);
    }

    @Override
    public List<UsuarioDto> listarBusqueda(String criterioBusqueda) {
        if (StringUtil.isEmptyOrNull(criterioBusqueda)) {
            criterioBusqueda = "";
        }
        criterioBusqueda = "%" + criterioBusqueda + "%";
        return usuarioRepository.searchAll(EstadoEnum.ACTIVO, criterioBusqueda);
    }

    @Override
    public UsuarioDto obtener(Long id) {
        MuUsuario muUsuario = usuarioRepository.getOne(id);
        if (muUsuario == null) {
            throw new OperationException(FormatUtil.noRegistrado("AuthUsuario", id));
        }
        return new UsuarioDto(muUsuario);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, NotDataFoundException.class})
    public UsuarioDto actualizarUsuario(UsuarioDto data, Long id) {
        log.info("Iniciando validaciones para actualizar el usuario {}", data.getUsername());
        MuUsuario muUsuario = this.usuarioRepository.findById(id).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", id)));

        String campoIvalido = ValidacionServicios.validarUsuarioEdit(data);
        if (campoIvalido != null) throw new OperationException(FormatUtil.requerido(campoIvalido));

        String username = data.getUsername().toUpperCase().trim();
        MuUsuario exist = this.usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username.toUpperCase().trim()).orElse(null);
        if (exist != null) {
            if (!muUsuario.getId().equals(exist.getId())) {
                throw new OperationException("Ya existe un usuario: " + muUsuario.getUsername());
            }
        }

        if(this.usuarioRepository.existsByIdNotAndEstadoAndEmailAndTipoUsuarioNot(id,EstadoEnum.ACTIVO,data.getEmail(), TipoUsuarioEnum.PACIENTE)){
            throw new OperationException("Ya se registró el correo electrónico");
        }

        muUsuario.setEmail(data.getEmail().trim());
        muUsuario.setNombre(data.getNombre().trim());
        this.usuarioRepository.save(muUsuario);
        List<GrupoDto> grupos = this.listarGruposAsignados(id);
        grupos.forEach(grupoDto -> log.info("GRUPO: {}",grupoDto.getNombre()));
        this.removerGrupos(id, grupos);
        if (data.getGrupos() != null) {
            data.getGrupos().forEach(grupoDto -> log.info("GRUPO NUEVO: {}",grupoDto.getNombre()));
            this.agregarGrupos(id, data.getGrupos());
        }

        this.agregarDepartamento(muUsuario,data.getDepartamentos());
        this.agregarMunicipio(muUsuario,data.getMunicipios());
        this.agregarCentroSalud(muUsuario,data.getCentroSaluds());
        return new UsuarioDto(muUsuario);
    }


    @Override
    public UsuarioDto cambiarEstado(Long id) {
        log.info("Iniciando validaciones para cambiar de estado al Usuario");
        MuUsuario muUsuario = this.usuarioRepository.findById(id).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", id)));
        if (muUsuario.getEstadoUsuario() != null && muUsuario.getEstadoUsuario().equals(UserStatusEnum.ACTIVO))
            muUsuario.setEstadoUsuario(UserStatusEnum.BLOQUEADO);
        else muUsuario.setEstadoUsuario(UserStatusEnum.ACTIVO);
        this.usuarioRepository.save(muUsuario);
        return  new UsuarioDto( muUsuario);
    }

    @Override
    public List<GrupoDto> listarGruposAsignados(Long idUsuario) {
        log.info("Listando Grupos Asignados de Usuarios");
        MuUsuario muUsuario = this.usuarioRepository.findById(idUsuario).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", idUsuario)));
        return grupoRepository.listByIdUsuario(muUsuario.getId());
    }

    @Override
    public List<GrupoDto> listarGruposNoAsignados(Long usuarioId) {
        log.info("Listando Grupos No Asignados de Usuarios");
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        return this.grupoRepository.listNotAsignedByUsuario(muUsuario.getId());
    }

    @Override
    public void removerGrupos(Long usuarioId, List<GrupoDto> grupoDtos) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        log.info("Iniciando validaciones para remover grupos del usuario: {}", muUsuario.getUsername());
        for (GrupoDto grupoDto : grupoDtos) {
            if (LongUtil.isNullOrZero(grupoDto.getId())) throw new OperationException(FormatUtil.requerido("idGrupo"));
            MuGrupo exist = this.grupoRepository.findById(grupoDto.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Grupo", grupoDto.getId())));
            this.usuarioGrupoRepository.deleteByIdGrupoAndIdUsuario(exist, muUsuario);
        }
    }

    @Override
    public void agregarGrupos(Long usuarioId, List<GrupoDto> grouposList) {
        log.info("Iniciando validaciones para Agregar Grupos a un Usuario");
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        agregarGrupos(muUsuario,grouposList);
    }

    @Override
    public void agregarGrupos(MuUsuario muUsuario, List<GrupoDto> grouposList) {
        for (GrupoDto grupoDto : grouposList) {
            if (LongUtil.isNullOrZero(grupoDto.getId())) throw new OperationException(FormatUtil.requerido("grupoId"));
            MuGrupo muGrupo = this.grupoRepository.findById(grupoDto.getId()).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Grupo", grupoDto.getId())));
            if (this.usuarioGrupoRepository.existsByIdUsuarioAndIdGrupoAndEstado(muUsuario, muGrupo, EstadoEnum.ACTIVO)) {
                throw new OperationException(String.format("El grupo '%s' ya se encuentra asignado al usuario '%s'", grupoDto.getNombre(), muUsuario.getNombre()));
            }
            MuUsuarioGrupo muUsuarioGrupo = MuUsuarioGrupo.builder()
                    .idGrupo(muGrupo)
                    .idUsuario(muUsuario)
                    .build();
            muUsuarioGrupo.setEstado(EstadoEnum.ACTIVO);
            this.usuarioGrupoRepository.save(muUsuarioGrupo);
        }
    }

    @Override
    public List<UsuarioDto> findUsuariosNoAsignados(Long alarmaId) {
        MuAlarma muAlarma = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("SfeAlarma", alarmaId)));
        return this.usuarioRepository.findAllUsuariosNoAsignados(muAlarma);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, NotDataFoundException.class})
    public UsuarioDto nuevoUsuario(UsuarioDto usuario, String passwordConfirm) {
        String campoInvalido = this.registroValidacionCreacionUsuario(usuario, passwordConfirm);
        if (campoInvalido != null) throw new OperationException(FormatUtil.requerido(campoInvalido));
        if (usuario.getAuthType().equals(AuthTypeEnum.SISTEMA)) {
            if (!usuario.getPassword().equals(passwordConfirm))
                throw new OperationException("Las contraseñas no coinciden");
        }
        if (this.usuarioRepository.existsByEstadoAndUsername(EstadoEnum.ACTIVO,usuario.getUsername()))
            throw new OperationException("Ya existe un Usuario: " + usuario.getUsername());

        if(this.usuarioRepository.existsByEstadoAndEmailAndTipoUsuarioNot(EstadoEnum.ACTIVO,usuario.getEmail(), TipoUsuarioEnum.PACIENTE))
            throw new OperationException("Ya se existe otro usuario el correo electrónico");

        MuUsuario userToSave = MuUsuario.builder()
                .nombre(usuario.getNombre().trim())
                .email(usuario.getEmail().trim())
                .username(usuario.getUsername().trim())
                .password(this.passwordEncoder.encode(usuario.getPassword()))
                .authType(usuario.getAuthType())
                .estadoUsuario(UserStatusEnum.ACTIVO)
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
        this.usuarioRepository.save(userToSave);

        if (usuario.getGrupos() != null) {
            this.agregarGrupos(userToSave.getId(), usuario.getGrupos());
        }
        this.agregarDepartamento(userToSave,usuario.getDepartamentos());
        this.agregarMunicipio(userToSave,usuario.getMunicipios());
        this.agregarCentroSalud(userToSave,usuario.getCentroSaluds());
        return new UsuarioDto(userToSave);
    }

    @Override
    public void cambiarContrasenia(Long usuarioId, String newPassword, String confirmPassword) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("Usuario", usuarioId)));
        log.info("Iniciando validaciones para cambiar contrasenia del usuario: {}",muUsuario.getUsername());
        if (StringUtil.isEmptyOrNull(newPassword)) throw new OperationException(FormatUtil.requerido("Contraseña"));
        if (StringUtil.isEmptyOrNull(confirmPassword))
            throw new OperationException(FormatUtil.requerido("Confirmación Contraseñ"));
        if (!newPassword.equals(confirmPassword))
            throw new OperationException("La contraseña y confirmación de contraseña no coinciden");
        muUsuario.setPassword(this.passwordEncoder.encode(newPassword));
        this.usuarioRepository.save(muUsuario);
    }

    @Override
    public MuUsuario obtenerUsuario(Long usuarioId) {
        return  this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro ningún usuario con ID: "+usuarioId));
    }

    @Override
    public void agregarDepartamento(Long usuarioId, List<DepartamentoUsuarioDto> departamentos) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarDepartamento(muUsuario,departamentos);
    }

    @Override
    public void agregarCentroSalud(Long usuarioId, List<CentroSaludUsuarioDto> centroSaluds) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarCentroSalud(muUsuario,centroSaluds);
    }


    @Override
    public void agregarDepartamento(MuUsuario muUsuario, List<DepartamentoUsuarioDto> departamentos) {
        List<Long> depIds = new ArrayList<>();
        departamentos.forEach(departamentoDto -> depIds.add(departamentoDto.getId()));
        if(departamentos.isEmpty()){
            this.departamentoUsuarioRepository.eliminaDepartamentosAsignados(muUsuario);
        }else {
            this.departamentoUsuarioRepository.eliminaDepartamentosNoAsignados(muUsuario, depIds);
        }
        for (DepartamentoUsuarioDto auxDep : departamentos) {
            Departamento departamento = this.departamentoRepository.findById(auxDep.getId()).orElseThrow(()->new NotDataFoundException("No se encontro un departamento con ID: "+auxDep.getId()));
            if(!departamentoUsuarioRepository.existsByMuUsuarioAndDepartamentoAndEstado(muUsuario,departamento,EstadoEnum.ACTIVO)){
                this.departamentoUsuarioRepository.save(DepartamentoUsuario.builder().departamento(departamento).muUsuario(muUsuario).build());
            }
        }
    }

    @Override
    public void agregarCentroSalud(MuUsuario muUsuario, List<CentroSaludUsuarioDto> centroSaluds) {
        List<Long> ceIds = new ArrayList<>();
        centroSaluds.forEach(centroSaludDto -> ceIds.add(centroSaludDto.getId()));
        if(ceIds.isEmpty()){
            this.centroSaludUsuarioRepository.eliminaCentrosAsignados(muUsuario);
        }else{
            this.centroSaludUsuarioRepository.eliminaCentrosNoAsignados(muUsuario, ceIds);
        }
        for (CentroSaludUsuarioDto auxCentro:centroSaluds) {
            CentroSalud centroSalud = this.centroSaludRepository.findById(auxCentro.getId()).orElseThrow(()->new NotDataFoundException("No se encontro ningún centro de salud con ID: "+auxCentro.getId()));
            if(!centroSaludUsuarioRepository.existsByMuUsuarioAndCentroSaludAndEstado(muUsuario,centroSalud,EstadoEnum.ACTIVO)){
                this.centroSaludUsuarioRepository.save(CentroSaludUsuario.builder().muUsuario(muUsuario).centroSalud(centroSalud).build());
            }
        }

    }

    @Override
    public void agregarMunicipio(MuUsuario muUsuario, List<MunicipioUsuarioDto> municipios) {
        List<Long> muIds = new ArrayList<>();
        municipios.forEach(municipioDto -> muIds.add(municipioDto.getId()));
        if(muIds.isEmpty()){
            this.municipioUsuarioRepository.eliminaMunicipiosAsignados(muUsuario);
        }else {
            this.municipioUsuarioRepository.eliminaMunicipiosNoAsignados(muUsuario, muIds);
        }
        for (MunicipioUsuarioDto auxMunic : municipios) {
            Municipio municipio = this.municipioRepository.findById(auxMunic.getId()).orElseThrow(()->new NotDataFoundException("No se encontro ningún municipio con ID:"+auxMunic.getId()));
            if(!this.municipioUsuarioRepository.existsByMuUsuarioAndMunicipioAndEstado(muUsuario, municipio,EstadoEnum.ACTIVO)){
                this.municipioUsuarioRepository.save(MunicipioUsuario.builder().muUsuario(muUsuario).municipio(municipio).build());
            }
        }
    }

    @Override
    public void agregarMunicipio(Long usuarioId, List<MunicipioUsuarioDto> municipios) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarMunicipio(muUsuario,municipios);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto findById(Long usuarioId) {
        UsuarioDto usuarioDto = this.usuarioRepository.obtenerUsuarioPorId(usuarioId).orElseThrow(()-> new NotDataFoundException("No se encontro ningún usuario"));
        List<DepartamentoUsuarioDto> departamentoUsuarioDtos = departamentoUsuarioRepository.listarDepartamentoAsignados(usuarioId);
        departamentoUsuarioDtos.addAll(departamentoUsuarioRepository.listarDepartamentoNoAsignados(usuarioId));
        usuarioDto.setDepartamentos(departamentoUsuarioDtos);

        List<MunicipioUsuarioDto> usuarioDtoList = municipioUsuarioRepository.listarMunicipiosAsignados(usuarioId);
        usuarioDtoList.addAll(municipioUsuarioRepository.listarMunicipiosNoAsignados(usuarioId));
        usuarioDto.setMunicipios(usuarioDtoList);

        List<CentroSaludUsuarioDto> centroSaludUsuarioDtos = centroSaludUsuarioRepository.listarCentrosSaludAsignados(usuarioId);
        centroSaludUsuarioDtos.addAll(centroSaludUsuarioRepository.listarCentrosSaludNoAsignados(usuarioId));
        usuarioDto.setCentroSaluds(centroSaludUsuarioDtos);

        return usuarioDto;
    }

    private String registroValidacionCreacionUsuario(UsuarioDto authUsuario, String passConfirm) {
        if (StringUtil.isEmptyOrNull(authUsuario.getNombre())) return "Nombre";
        if (StringUtil.isEmptyOrNull(authUsuario.getUsername())) return "Nombre Usuario";
        if (authUsuario.getAuthType().equals(AuthTypeEnum.SISTEMA)) {
            if (StringUtil.isEmptyOrNull(authUsuario.getEmail())) return "Correo Electrónico";
        }
        if (authUsuario.getAuthType().equals(AuthTypeEnum.SISTEMA)) {
            if (StringUtil.isEmptyOrNull(authUsuario.getPassword())) return "Contraseña";
            if (StringUtil.isEmptyOrNull(passConfirm)) return "Confirmación Contraseña";
        }
        return null;
    }

}
