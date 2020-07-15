package bo.com.reportate.service;

import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.MuValorDominio;
import bo.com.reportate.model.dto.DominioDto;
import bo.com.reportate.model.dto.ValorDominioDto;

import java.util.List;

/**
 * Date       :20-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  :Reportate
 *
 *
 */

public interface DominioService {

    List<DominioDto> findAll();

    List<MuValorDominio> listaValores(Long dominioId);

    List<DominioDto> findByCodigo(String codigo);
    /**
     * Realiza el listado del objeto segun el criterio de busqueda
     * @param criterioBusqueda
     * @return
     */
    List<ValorDominioDto> listarBusqueda(String criterioBusqueda);

    /**
     * Obtiene al objeto segun su id
     * @param id
     * @return
     */
    MuValorDominio obtener(Long id);

    /**
     * Realiza la creacion del objeto
     * @param muDominio
     * @param valor
     * @param descripcion
     * @return
     */
    Long crear(MuDominio muDominio, String valor, String descripcion);

    /**
     * Realiza la modificacion del objeto
     * @param sfeValorDominio
     * @param id
     * @return
     */
    Long modificar(ValorDominioDto sfeValorDominio, Long id);


    /**
     * Guarda un Dominio
     * @param muDominio
     */
    void saveDominio(MuDominio muDominio);

    /**
     * Lista los dominio de un codigo
     * @param codigo
     * @return
     */
    List<DominioDto> findDominioByCodigo(String codigo);

    MuValorDominio findByDominioIdAndValue(Long id, String valor);

    void saveValorDominio(MuValorDominio valorDominio);
    ValorDominioDto saveValorDominio(String codigo, ValorDominioDto valorDominio);

    /**
     * Lista los valores de un dominio
     * @param codigo
     * @return
     */
    List<ValorDominioDto> findAllByCodigo(String codigo);

    /**
     * Lista los valores de un dominio por criterio de busqueda
     * @param codigo
     * @param criterio
     * @return
     */
    List<ValorDominioDto> findByCodigoAndDescripcionOrValue(String codigo, String criterio);

    void deleteValorDominio(Long id);

}
