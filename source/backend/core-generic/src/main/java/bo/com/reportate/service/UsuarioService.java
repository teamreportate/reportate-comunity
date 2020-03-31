package bo.com.reportate.service;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.*;

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
     * Realiza la modificacion del objeto
     * @param authUsuario
     * @param id
     * @return
     */
    UsuarioDto actualizarUsuario(UsuarioDto authUsuario, Long id);

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
    void agregarGrupos(MuUsuario muUsuario, List<GrupoDto> gruposList);

    List<UsuarioDto> findUsuariosNoAsignados(Long alarmaId);

    UsuarioDto nuevoUsuario(UsuarioDto authUsuario, String passwordConfirm);

    void cambiarContrasenia(Long usuarioId, String newPassword, String confirmPassword);

    MuUsuario obtenerUsuario(Long usuarioId);
    void agregarDepartamento(Long usuarioId, List<DepartamentoDto> departamentos);
    void agregarDepartamento(MuUsuario muUsuario, List<DepartamentoDto> departamentos);
    void agregarCentroSalud(Long usuarioId, List<CentroSaludDto> centroSaluds);
    void agregarCentroSalud(MuUsuario muUsuario, List<CentroSaludDto> centroSaluds);
    void agregarMunicipio(MuUsuario muUsuario, List<MunicipioDto> municipios);
    void agregarMunicipio(Long usuarioId, List<MunicipioDto> municipios);
}
