package bo.com.reportate.service;

import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.MunicipioUsuario;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;

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
public interface MunicipioService {
    List<Municipio> findAll();
    List<MunicipioDto> findByDepartamento(Long idDepartamento);
    Municipio save(Municipio municipio);
    Municipio save(Long idDepartamento, String nombre, Double latitud, Double longitud);
    List<MunicipioUsuarioDto> listarMuniciposAsignados(String username);

    Municipio findById(Long municipioId);

    Municipio update(Long municipioId, String nombre, Double latitud, Double longitud);

    void eliminar(Long municipioId);
}
