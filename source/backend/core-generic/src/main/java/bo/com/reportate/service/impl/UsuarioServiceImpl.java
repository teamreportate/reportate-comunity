package bo.com.reportate.service.impl;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.MuUsuarioGrupo;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.dto.UsuarioDto;
import bo.com.reportate.model.enums.AuthTypeEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.UserStatusEnum;
import bo.com.reportate.repository.AlarmaReporsitory;
import bo.com.reportate.repository.GrupoRepository;
import bo.com.reportate.repository.UsuarioGrupoRepository;
import bo.com.reportate.repository.UsuarioRepository;
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


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private GrupoRepository grupoRepository;
    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AlarmaReporsitory alarmaReporsitory;


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
    public Long crear(String nombre, String username, String password, String passwordConfirm, List<GrupoDto> listaGrupos) {
        log.info("Iniciando validaciones para crear Usuarios");
        if (StringUtil.isEmptyOrNull(nombre)) {
            throw new OperationException(FormatUtil.requerido("Nombre"));
        }
        if (StringUtil.isEmptyOrNull(username)) {
            throw new OperationException(FormatUtil.requerido("Username"));
        }
        if (StringUtil.isEmptyOrNull(password)) {
            throw new OperationException(FormatUtil.requerido("Password"));
        }
        if (StringUtil.isEmptyOrNull(passwordConfirm)) {
            throw new OperationException(FormatUtil.requerido("Password Confirm"));
        }
//        List<MuUsuario> listaRegistrados = usuarioRepository.listByNombre(nombre);
//        if (!listaRegistrados.isEmpty()) {
//            throw new OperationException(FormatUtil.yaRegistrado("AuthUsuario", "Nombre", nombre));
//        }
//        listaRegistrados = usuarioRepository.listByUsername(username);
//        if (!listaRegistrados.isEmpty()) {
//            throw new OperationException(FormatUtil.yaRegistrado("AuthUsuario", "Username", username));
//        }
        if (!password.equals(passwordConfirm)) {
            throw new OperationException(FormatUtil.coincidencia("Contraseña", "Confirmar t stContraseña "));
        }
        log.info("Persistiendo Usuario");
        MuUsuario muUsuario = new MuUsuario();
        muUsuario.setNombre(StringUtil.trimUpperCase(nombre));
        muUsuario.setUsername(StringUtil.trimUpperCase(username));
        muUsuario.setPassword(passwordEncoder.encode(password));
        muUsuario.setAuthType(AuthTypeEnum.SISTEMA);
        muUsuario.setEstado(EstadoEnum.ACTIVO);
        usuarioRepository.save(muUsuario);

        listaGrupos.forEach(item -> {
            try {
                MuGrupo group = this.grupoRepository.findById(item.getId()).orElse(null);
                if (group != null) {
                    MuUsuarioGrupo muUsuarioGrupo = new MuUsuarioGrupo();
                    muUsuarioGrupo.setIdUsuario(muUsuario);
                    muUsuarioGrupo.setIdGrupo(group);
                    muUsuarioGrupo.setEstado(EstadoEnum.ACTIVO);
                    this.usuarioGrupoRepository.save(muUsuarioGrupo);
                }
            } catch (Exception e) {
                log.info("Error al agregar el Grupo [{}] al Usuario [{}]", item, muUsuario);
            }
        });

        return muUsuario.getId();
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
        log.info("Actualizando datos de Usuario");
        this.usuarioRepository.save(muUsuario);
        List<GrupoDto> grupos = this.listarGruposAsignados(id);
        this.removerGrupos(id, grupos);
        if (data.getGrupos() != null) {
            this.agregarGrupos(id, data.getGrupos());
        }
        return new UsuarioDto(muUsuario);
    }

    @Override
    public boolean eliminar(Long id) {
        log.info("Iniciando validaciones para eliminar Usuarios");
        MuUsuario muUsuarioRegistrado = usuarioRepository.getOne(id);
        if (muUsuarioRegistrado == null) {
            throw new OperationException(FormatUtil.noRegistrado("AuthUsuario", id));
        }
        log.info("Eliminando Usuarios");
        muUsuarioRegistrado.setEstado(EstadoEnum.ACTIVO);
        usuarioRepository.save(muUsuarioRegistrado);
        return true;
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
    public List<UsuarioDto> findUsuariosAsignados(Long alarmaId) {
        MuAlarma muAlarma = this.alarmaReporsitory.findById(alarmaId).orElseThrow(() -> new NotDataFoundException(FormatUtil.noRegistrado("SfeAlarma", alarmaId)));
        return this.usuarioRepository.findAllUsuariosAsignados(muAlarma);
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
        String contraseniaReq;
        if (usuario.getAuthType().equals(AuthTypeEnum.SISTEMA)) {
            contraseniaReq = this.passwordEncoder.encode(usuario.getPassword());
        } else {
            contraseniaReq = " ";
        }
        MuUsuario userToSave = MuUsuario.builder()
                .nombre(usuario.getNombre().trim())
                .email(usuario.getEmail().trim())
                .username(usuario.getUsername().trim())
                .password(contraseniaReq)
                .authType(usuario.getAuthType())
                .estadoUsuario(UserStatusEnum.ACTIVO)
                .build();
        this.usuarioRepository.save(userToSave);
        if (usuario.getGrupos() != null) {
            this.agregarGrupos(userToSave.getId(), usuario.getGrupos());
        }
        return new UsuarioDto(userToSave);
    }

    @Override
    public void agregarRoles(Long usuarioId, List<RolDto> listaRoles) {
        //FIXME: Falta implementar
    }

    @Override
    public void removerRoles(Long usuarioId, List<RolDto> listaRoles) {
        //FIXME: Falta implementar
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


    private String registroValidacion(MuUsuario muUsuario, String passConfirm) {
        if (StringUtil.isEmptyOrNull(muUsuario.getNombre())) return "Nombre";
        if (StringUtil.isEmptyOrNull(muUsuario.getUsername())) return "Nombre Usuario";
        if (StringUtil.isEmptyOrNull(muUsuario.getPassword())) return "Contraseña";
        if (StringUtil.isEmptyOrNull(passConfirm)) return "Confirmación Contraseña";
        if (StringUtil.isEmptyOrNull(muUsuario.getEmail())) return "Correo Electrónico";
        return null;
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
