package bo.com.reportate.service;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :MC4
 */
public interface CentroSaludService {
    List<CentroSalud> findAll();
    List<CentroSalud> findByMunicipio(Long idMunicipio);
    CentroSalud save(CentroSalud departamento);
    CentroSalud save(Long idMunicipio, String nombre, String direccion, String zona, String ciudad, Double latitud, Double longitud);

    List<CentroSaludUsuarioDto> listarCentroSaludAsignados(String username);
}
