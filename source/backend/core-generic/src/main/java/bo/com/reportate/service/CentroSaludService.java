package bo.com.reportate.service;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.dto.CentroSaludDto;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :Reportate
 */
public interface CentroSaludService {
    CentroSalud findById(Long centroSaludId);
    List<CentroSalud> findAll();
    List<CentroSaludDto> findByMunicipio(Long idMunicipio);
    CentroSalud save(CentroSalud departamento);
    CentroSalud save(Long idMunicipio, String nombre, String direccion, String zona, String ciudad, Double latitud, Double longitud);

    List<CentroSaludUsuarioDto> listarCentroSaludAsignados(String username);

    CentroSalud update(Long municipioId, String nombre, String direccion, String zona, String ciudad, Double latitud, Double longitud);

    void eliminar(Long centroSaludId);
}
