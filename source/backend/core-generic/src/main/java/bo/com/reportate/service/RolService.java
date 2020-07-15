package bo.com.reportate.service;

import bo.com.reportate.model.dto.*;

import java.util.List;

/**
 * Created by :Reportate
 * Autor      :vtorrez
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : Reportate
 */

public interface RolService {

    /**
     * Realiza el listado del objeto
     * @return
     */
    List<RolDto> listar();

    /**
     * Realiza la creacion del objeto
     * @param rol
     * @return
     */
    RolDto crear(RolDto rol);

    /**
     * Realiza la modificacion del objeto
     * @param rol
     * @param id
     * @return
     */
    RolDto modificar(String descripcion, String nombre, Long id);

    /**
     * Permite cambiar de estado al objeto seleccionado
     * @param idRol
     * @param estadoRol
     * @return
     */
    RolDto cambiarEstado(Long idRol);


    /**
     * Asigna los recursos al rol
     * @param idRol
     * @param listaRecursos
     * @return
     */
    void asignarRecursos(Long idRol, List<RecursoDto> listaRecursos);

    void configurarRcursos(Long rolId, List<ConfiguracionDto> listaConfiguracion);

    void removerRecursos(Long idRol, List<RecursoDto> listaRecursos);

    List<RolDto> listarRolesPorGrupo(Long grupoId);

    List<RolDto> listarRolesNoAsignadosPorGrupo(Long grupoId);

    List<GrupoDto> listarGruposAsignados(Long rolId);

    List<GrupoDto> listarGruposNoAsignados(Long rolId);

    List<RecursoDto> listarRecursosAsignados(Long rolId);

    List<RecursoDto> listarRecursosNoAsignados(Long rolId);

    void asignarGrupos(Long rolId, List<GrupoDto> listaGrupo);

    void removerGrupos(Long rolId, List<GrupoDto> listaGrupo);

    void asignarUsuarios(Long rolId, List<UsuarioDto> listaUsuarios);

    void removerUsuarios(Long rolId, List<UsuarioDto> listaUsuarios);
}
