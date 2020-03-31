package bo.com.reportate.service.impl;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.*;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.UsuarioService;
import bo.com.reportate.util.ValidacionServicios;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.LongUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAllByEstadoOrderByNombreAsc(EstadoEnum.ACTIVO);
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
        log.info("Iniciando validaciones para crear Usuarios");
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
        exist = this.usuarioRepository.findByEstadoAndEmail(EstadoEnum.ACTIVO, data.getEmail().trim()).orElse(null);
        if (exist != null) {
            if (!muUsuario.getId().equals(exist.getId())) {
                String correo = muUsuario.getEmail();
                if (muUsuario.getEmail().length() > 25)
                    correo = "xxxxxxxxx@xxx.xxxx";
                throw new OperationException("Ya se registró el correo electrónico " + correo);
            }
        }

        muUsuario.setEmail(data.getEmail().trim());
        muUsuario.setNombre(data.getNombre().trim());
        this.usuarioRepository.save(muUsuario);
        List<GrupoDto> grupos = this.listarGruposAsignados(id);
        this.removerGrupos(id, grupos);
        if (data.getGrupos() != null) {
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

        MuUsuario exist = this.usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, usuario.getUsername().toUpperCase().trim()).orElse(null);
        if (exist != null) throw new OperationException("Ya existe un Usuario: " + usuario.getUsername());

        if (!usuario.getEmail().equals(" ")) {
            exist = this.usuarioRepository.findByEstadoAndEmail(EstadoEnum.ACTIVO, usuario.getEmail()).orElse(null);
        }
        if (exist != null) {
            String correo = usuario.getEmail();
            if (usuario.getEmail().length() > 50)
                correo = "xxxxxxxxx@xxx.xxxx";
            throw new OperationException("Ya se registró el correo electrónico " + correo);
        }

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
    public void agregarDepartamento(Long usuarioId, List<DepartamentoDto> departamentos) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarDepartamento(muUsuario,departamentos);
    }

    @Override
    public void agregarCentroSalud(Long usuarioId, List<CentroSaludDto> centroSaluds) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarCentroSalud(muUsuario,centroSaluds);
    }


    @Override
    public void agregarDepartamento(MuUsuario muUsuario, List<DepartamentoDto> departamentos) {
        for (DepartamentoDto auxDep : departamentos) {
            Departamento departamento = this.departamentoRepository.findById(auxDep.getId()).orElseThrow(()->new NotDataFoundException("No se encontro un departamento con ID: "+auxDep.getId()));
            if(!departamentoUsuarioRepository.existsByMuUsuarioAndDepartamentoAndEstado(muUsuario,departamento,EstadoEnum.ACTIVO)){
                this.departamentoUsuarioRepository.save(DepartamentoUsuario.builder().departamento(departamento).muUsuario(muUsuario).build());
            }
        }
    }

    @Override
    public void agregarCentroSalud(MuUsuario muUsuario, List<CentroSaludDto> centroSaluds) {
        for (CentroSaludDto auxCentro:centroSaluds) {
            CentroSalud centroSalud = this.centroSaludRepository.findById(auxCentro.getId()).orElseThrow(()->new NotDataFoundException("No se encontro ningún centro de salud con ID: "+auxCentro.getId()));
            if(!centroSaludUsuarioRepository.existsByMuUsuarioAndCentroSaludAndEstado(muUsuario,centroSalud,EstadoEnum.ACTIVO)){
                this.centroSaludUsuarioRepository.save(CentroSaludUsuario.builder().muUsuario(muUsuario).centroSalud(centroSalud).build());
            }
        }

    }

    @Override
    public void agregarMunicipio(MuUsuario muUsuario, List<MunicipioDto> municipios) {
        for (MunicipioDto auxMunic : municipios) {
            Municipio municipio = this.municipioRepository.findById(auxMunic.getId()).orElseThrow(()->new NotDataFoundException("No se encontro ningún municipio con ID:"+auxMunic.getId()));
            if(!this.municipioUsuarioRepository.existsByMuUsuarioAndMunicipioAndEstado(muUsuario, municipio,EstadoEnum.ACTIVO)){
                this.municipioUsuarioRepository.save(MunicipioUsuario.builder().muUsuario(muUsuario).municipio(municipio).build());
            }
        }
    }

    @Override
    public void agregarMunicipio(Long usuarioId, List<MunicipioDto> municipios) {
        MuUsuario muUsuario = this.usuarioRepository.findById(usuarioId).orElseThrow(()->new NotDataFoundException("No se encontro nungún usuario con ID:"+usuarioId));
        agregarMunicipio(muUsuario,municipios);
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
