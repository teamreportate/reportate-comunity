package bo.com.reportate;

import bo.com.reportate.model.*;
import bo.com.reportate.model.enums.*;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.DominioService;
import bo.com.reportate.service.ParamService;
import bo.com.reportate.sign.crypto.Crypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class WebSecurityDataInitializer implements CommandLineRunner {
    @Autowired
    private ParamService paramService;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RecursoRepository recursoRepository;
    @Autowired
    PermisoRepository permisoRepository;
    @Autowired
    GrupoRepository grupoRepository;
    @Autowired
    UsuarioGrupoRepository usuarioGrupoRepository;
    @Autowired
    GrupoRolRepository grupoRolRepository;

    @Autowired
    GrupoParametroRepository grupoParametroRepository;
    @Autowired
    DominioRepository dominioRepository;
    @Autowired
    ValorDominioRepository valorDominioRepository;
    @Autowired
    DominioService dominioService;

    @Override
    public void run(String... args) throws Exception {
        addUsers();
        addParams();
        addDomain();
    }
    private void addUsers(){
        log.info("************************* Cargando usuarios por defecto *************************");
        MuUsuario usuario = usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, "admin").orElse(null);
        if (usuario == null) {
            log.debug("Inicializando datos de roles ...");
            MuRol rolAdmin = MuRol.builder()
                    .nombre("Administrador")
                    .descripcion("Rol administrador de sistema")
                    .estadoRol(EstadoRol.ACTIVO)
                    .build();
            this.rolRepository.save(rolAdmin);

            MuUsuario userAdmin = MuUsuario.builder()
                    .nombre("Administrador de sistema")
                    .username("admin")
                    .password(this.passwordEncoder.encode("admin"))
                    .estadoUsuario(UserStatusEnum.ACTIVO)
                    .passwordGenerado(false)
                    .authType(AuthTypeEnum.SISTEMA)
                    .tipoUsuario(TipoUsuarioEnum.ADMINISTRATIVO)
                    .build();
            userAdmin = this.usuarioRepository.save(userAdmin);

            MuGrupo grupoAdmin = new MuGrupo();
            grupoAdmin.setEstado(EstadoEnum.ACTIVO);
            grupoAdmin.setNombre("Administrador");
            grupoAdmin.setDescripcion("Grupo de Administradores");
            grupoAdmin.setEstadoGrupo(EstadoGrupo.ACTIVO);
            grupoRepository.save(grupoAdmin);

            MuUsuarioGrupo ugAdminAdmin = new MuUsuarioGrupo();
            ugAdminAdmin.setIdGrupo(grupoAdmin);
            ugAdminAdmin.setIdUsuario(userAdmin);
            ugAdminAdmin.setEstado(EstadoEnum.ACTIVO);
            usuarioGrupoRepository.save(ugAdminAdmin);

            MuGrupoRol grAdminAdmin = new MuGrupoRol();
            grAdminAdmin.setIdGrupo(grupoAdmin);
            grAdminAdmin.setIdRol(rolAdmin);
            grAdminAdmin.setEstado(EstadoEnum.ACTIVO);
            grupoRolRepository.save(grAdminAdmin);

            addPermisos();
        }
        log.info("************************* Fin de cargar usuarios por defecto *************************");
    }
    private void addPermisos(){
        log.info("************************* Cargando permisos por defecto *************************");
        MuRol rolAdmin = rolRepository.findByName("Administrador");
        recPadre("Accesos", "Interfaces para gestión del módulo de usuario ", "accesos", 1, "menu", rolAdmin);
        recHijo("Recursos", "accesos", "Interfaz para administración de Recursos del sistema", "resources", 1, "insert_link", rolAdmin);
        recHijo("Roles", "accesos", "Interfaz para administración de Roles", "roles", 2, "border_color", rolAdmin);
        recHijo("Grupos", "accesos", "Interfaz para administración de Grupos", "groups", 3, "group_work", rolAdmin);
        recHijo("Usuarios", "accesos", "Interfaz para administración de usuarios.", "users", 5, "group_add", rolAdmin);

        recPadre("Monitoreo", "Interfaces para monitoreo del sistema", "monitoreo", 1, "menu", rolAdmin);
        recHijo("Bitácora", "monitoreo", "Interfaz para monitoreo de logs", "bitacora", 1, "flag", rolAdmin);

        recPadre("Configuración", "Interfaces para configuración de parámetros y dominios", "configuracion", 1, "menu", rolAdmin);
        recHijo("Parámetros", "configuracion", "Interfaz para administración de parámetros", "parameters", 1, "code", rolAdmin);
        recHijo("Dominios", "configuracion", "Interfaz para administración de dominios", "dominios", 2, "code", rolAdmin);
        recHijo("Alarmas", "configuracion", "Interfaz para administración de Alarmas", "alarmas", 3, "code", rolAdmin);

        log.info("************************* Fin de cargar permisos por defecto *************************");

    }

    /**
     * Método en tiempo de desarrollo para agregar parametros
     */
    private void addParams() {
        log.info("************************* Cargando parametros *************************");
        MuParametro parametro;


        MuGrupoParametro mailParams = this.grupoParametroRepository.findByGrupo("Parámetros del servidor de correo").orElse(MuGrupoParametro.builder()
                .grupo("Parámetros del servidor de correo")
                .descripcion("Parámetros de configuración del servidor de correo.")
                .build());
        this.grupoParametroRepository.save(mailParams);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_SMTP_AUTH, "Bandera que indica si la cuenta desde la que se envia el correo necesita autentificarse.", null, true, null, null, ParamTipoDato.BOOLEANO, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_SMTP_STARTTLS_ENABLE, "Bandera que indica si el servidor de correos tiene habilitado TLS.", null, true, null, null, ParamTipoDato.BOOLEANO, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_HOST, "Host del servidor de correo electronico.", "smtp.gmail.com", null, null, null, ParamTipoDato.CADENA, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_PORT, "Puerto del servidor de correo electronico.", null, null, new BigDecimal(587), null, ParamTipoDato.NUMERICO, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_PROTOCOL, "Protocolo del servidor de correo electronico.", "smtp", null, null, null, ParamTipoDato.CADENA, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_USERNAME, "Usuario de la cuenta de correo electronico.", "reportate@gmail.com", null, null, null, ParamTipoDato.CADENA, false,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_PASS, "Contraseña de la cuenta de correo electronico.", Crypt.getInstance().crypt("Password"), null, null, null, ParamTipoDato.CADENA, true,null, mailParams);
        this.paramService.saveParametro(parametro);
        parametro = new MuParametro(null, Constants.Parameters.MAIL_FROM, "Dirección de correo electronico desde la que se va a enviar los correos.", "no-reply@mc4.com.bo", null, null, null, ParamTipoDato.CADENA, false,null, mailParams);
        this.paramService.saveParametro(parametro);

        log.info("************************* Fin Cargando parametros *************************");
    }

    /**
     * Método en tiempo de desarrollo para agregar dominios
     */
    private void addDomain() {
        log.info("************************* Cargando dominios *************************");
        MuValorDominio valorDominio;

//        Los parámetros estan clasificados por Grupos, cuando se agregue un parámetro se debe inidicar a que grupo pertenece
//        MuDominio dominio = this.dominioRepository.findByCodigo(Constants.Domain.DEPARTAMENTOS).orElse(MuDominio.builder()
//                .codigo(Constants.Domain.DEPARTAMENTOS)
//                .descripcion("Departamentos de Bolivia")
//                .build());
//        this.dominioRepository.save(dominio);
//
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("LA PAZ").descripcion("Departamento de La Paz").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("SANTA CRUZ").descripcion("Departamento de Santa Cruz").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("COCHABAMBA").descripcion("Departamento de Cochabamba").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("TARIJA").descripcion("Departamento de Tarija").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("BENI").descripcion("Departamento de Beni").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("PANDO").descripcion("Departamento de Pando").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("ORURO").descripcion("Departamento de Oruro").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("POTOSI").descripcion("Departamento de Potosi").build();
//        this.dominioService.saveValorDominio(valorDominio);
//        valorDominio = MuValorDominio.builder().dominio(dominio).valor("CHUQUISACA").descripcion("Departamento de Chuquisaca").build();
//        this.dominioService.saveValorDominio(valorDominio);
        
        log.info("************************* Fin Cargando dominios *************************");
    }


    private void recPadre(String nombre, String descripcion, String url, int ordenMenu, String icono, MuRol rolAdmin) {
        recPadre(nombre, descripcion, url, ordenMenu, icono, rolAdmin, EstadoEnum.ACTIVO);
    }

    private void recPadre(String nombre, String descripcion, String url, int ordenMenu, String icono, MuRol rolAdmin, EstadoEnum estado) {
        MuRecurso recPadre = recursoRepository.findByUrl(url);
        if (recPadre == null) {
           MuRecurso  recPadrex = MuRecurso.builder()
                    .idRecursoPadre(null)
                    .nombre(nombre)
                        .descripcion(descripcion)
                    .url(url)
                    .icon(icono)
                    .ordenMenu(ordenMenu)
                    .build();
            recPadrex.setEstado(estado);
            recursoRepository.save(recPadrex);

            MuPermiso permiso = MuPermiso.builder()
                    .lectura(true)
                    .escritura(true)
                    .eliminacion(true)
                    .idRol(rolAdmin)
                    .idRecurso(recPadrex)
                    .build();
            permiso.setEstado(EstadoEnum.ACTIVO);
            permisoRepository.save(permiso);
            return;
        }
        recPadre.setIdRecursoPadre(null);
        recPadre.setNombre(nombre);
        recPadre.setIcon(icono);
        recPadre.setOrdenMenu(ordenMenu);
        recPadre.setEstado(estado);
        recursoRepository.save(recPadre);
    }

    private void recHijo(String nombre, String urlPadre, String descripcion, String url, int ordenMenu, String icono, MuRol muRol) {
        recHijo(nombre, urlPadre, descripcion, url, ordenMenu, icono, muRol, EstadoEnum.ACTIVO);
    }

    private void recHijo(String nombre, String urlPadre, String descripcion, String url, int ordenMenu, String icono, MuRol muRol, EstadoEnum estado) {
        MuRecurso recursoPadre = recursoRepository.findByUrl(urlPadre);
        MuRecurso recHijox = recursoRepository.findByUrl(url);
        if (recHijox == null) {
            recHijox = new MuRecurso(nombre, descripcion, url, ordenMenu, icono, estado);
        }
        recHijox.setIdRecursoPadre(recursoPadre);
        recHijox.setNombre(nombre);
        recHijox.setIcon(icono);
        recHijox.setOrdenMenu(ordenMenu);
        recHijox.setEstado(estado);
        recursoRepository.save(recHijox);
        MuPermiso permiso = permisoRepository.findByIdRolAndIdRecurso(muRol, recHijox);
        if (permiso == null) {
            permiso = MuPermiso.builder()
                    .lectura(true)
                    .escritura(true)
                    .eliminacion(true)
                    .idRol(muRol)
                    .idRecurso(recHijox)
                    .build();
            permisoRepository.save(permiso);
        }
    }
}
