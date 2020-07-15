package bo.com.reportate.service;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.dto.ConfiguracionDto;
import bo.com.reportate.model.dto.GrupoDto;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.dto.UsuarioDto;

import java.util.List;

/**
 * Created by :Reportate
 * Autor      :vtorrez
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : Reportate
 */

public interface GrupoService {

    /**
     * Realiza el listado del objeto
     * @return
     */
    List<GrupoDto> listar();

    /**
     * Realiza el listado del objeto segun el criterio de busqueda
     * @param criterioBusqueda
     * @return
     */
    List<GrupoDto> listarBusqueda(String criterioBusqueda);

    /**
     * Obtiene al objeto segun su id
     * @param id
     * @return
     */
    MuGrupo obtener(Long id);

    /**
     * Lista a los usuarios del grupo
     * @param grupoId
     * @return
     */
    List<UsuarioDto> listarUsuariosAsignados(Long grupoId);

    List<UsuarioDto> listarUsuariosNoAsignados(Long grupoId);

    /**
     * Realiza la creacion del objeto
     * @param authGrupo
     * @return
     */
    GrupoDto nuevoGrupo(GrupoDto authGrupo);

    /**
     * Realiza la modificacion del objeto
     * @param grupo
     * @param id
     * @return
     */
    GrupoDto actualizarGrupo(GrupoDto grupo, Long id);

    /**
     * Permite cambiar de estado al objeto seleccionado
     * @param id
     * @return
     */
    GrupoDto cambiarEstado(Long id);

    void agregarRoles(Long grupoId, List<RolDto> listaRoles);

    void removerRoles(Long grupoId, List<RolDto> listaRoles);

    void agregarUsuarios(Long grupoId, List<UsuarioDto> listaUsuarios);

    void configurarUsuarios(Long grupoId, List<ConfiguracionDto> listaConfiguracion);

    void removerUsuarios(Long grupoId, List<UsuarioDto> listaUsuarios);
}
