package bo.com.reportate.service;

import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Created by :Reportate
 * Autor      :vtorrez
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : Reportate
 */

public interface UsuarioService {

    /**
     * Realiza el listado del objeto
     * @return
     */
    List<UsuarioDto> listar(Authentication authentication);
    Page<UsuarioDto> listarUsuarioPacientes(Pageable pageable);

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
    void agregarDepartamento(Long usuarioId, List<DepartamentoUsuarioDto> departamentos);
    void agregarDepartamento(MuUsuario muUsuario, List<DepartamentoUsuarioDto> departamentos);
    void agregarCentroSalud(Long usuarioId, List<CentroSaludUsuarioDto> centroSaluds);
    void agregarCentroSalud(MuUsuario muUsuario, List<CentroSaludUsuarioDto> centroSaluds);
    void agregarMunicipio(MuUsuario muUsuario, List<MunicipioUsuarioDto> municipios);
    void agregarMunicipio(Long usuarioId, List<MunicipioUsuarioDto> municipios);

    UsuarioDto findById(Long usuarioId);
}
