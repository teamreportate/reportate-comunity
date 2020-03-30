package bo.com.reportate.service;

import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.RecursoDto;

import java.util.List;

public interface RecursoService {

    List<MuRecurso> listHijosByUsuario(MuUsuario usuario);

    List<MuRecurso> listPadresByUsuario(MuUsuario usuario);

    List<RecursoDto> listMenu(String username);

    /**
     * Realiza el listado del objeto
     * @return
     */
    List<RecursoDto> listar();

    /**
     * Realiza el listado del objeto segun el criterio de busqueda
     * @param criterioBusqueda
     * @return
     */
    List<RecursoDto> listarBusqueda(String criterioBusqueda);


    /**
     * Obtiene al objeto segun su id
     * @param id
     * @return
     */
    RecursoDto obtener(Long id);

    /**
     * Realiza la creacion del objeto
     * @param recurso
     * @return
     */
    Long crear(MuRecurso recurso);

    /**
     * Realiza la modificacion del objeto
     * @param recurso
     * @param id
     * @return
     */
    RecursoDto modificar(RecursoDto recurso, Long id);

    /**
     * Realiza la eliminacion logica del objeto
     * @param id
     * @return
     */
//    boolean eliminar(Long id);

    /**
     * Permite cambiar de estado al objeto seleccionado
     * @param id
     * @return
     */
    boolean cambiarEstado(Long id);

}
