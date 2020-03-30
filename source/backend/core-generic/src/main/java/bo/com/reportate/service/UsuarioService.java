package bo.com.reportate.service;

import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.dto.UsuarioDto;

import java.util.List;

/**
 * Created by :MC4
 * Autor      :vtorrez
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : MC4
 */

public interface UsuarioService {

    /**
     * Realiza el listado del objeto
     * @return
     */
    List<UsuarioDto> listar();

    /**
     * Realiza el listado del objeto segun el criterio de busqueda
     * @param criterioBusqueda
     * @return
     */
    List<UsuarioDto> listarBusqueda(String criterioBusqueda);

    /**
     * Obtiene al objeto segun su id
     * @param id
     * @return
     */
    UsuarioDto obtener(Long id);

    /**
     * Realiza la creacion del usuario que se loguea desde la web backend
     * @param nombre
     * @param username
     * @param password
     * @param passwordConfirm
     * @param listaGrupos
     * @return
     */
    Long crear(String nombre, String username, String password, String passwordConfirm, List<GrupoDto> listaGrupos);

    /**
     * Realiza la modificacion del objeto
     * @param authUsuario
     * @param id
     * @return
     */
    UsuarioDto actualizarUsuario(UsuarioDto authUsuario, Long id);

    /**
     * Realiza la eliminacion logica del objeto
     * @param id
     * @return
     */
    boolean eliminar(Long id);

    /**
     * Permite cambiar de estado al objeto seleccionado
     * @param id
     * @return
     */
    UsuarioDto cambiarEstado(Long id);

    List<GrupoDto> listarGruposAsignados(Long idUsuario);

    List<GrupoDto> listarGruposNoAsignados(Long usuarioId);

    void removerGrupos(Long usuarioId, List<GrupoDto> MuGrupoList);

    /**
     * Actualizar lista de grupos asignados
     * @param usuarioId
     * @param gruposList
     * @return
     */
    void agregarGrupos(Long usuarioId, List<GrupoDto> gruposList);



    List<UsuarioDto> findUsuariosNoAsignados(Long alarmaId);

    List<UsuarioDto> findUsuariosAsignados(Long alarmaId);

    UsuarioDto nuevoUsuario(UsuarioDto authUsuario, String passwordConfirm);

    void agregarRoles(Long usuarioId, List<RolDto> listaRoles);

    void removerRoles(Long usuarioId, List<RolDto> listaRoles);

    void cambiarContrasenia(Long usuarioId, String newPassword, String confirmPassword);

    MuUsuario obtenerUsuario(Long usuarioId);
}
