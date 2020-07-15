package bo.com.reportate.service;

import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;

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
public interface MunicipioService {
    List<Municipio> findAll();
    List<MunicipioDto> findByDepartamento(Long idDepartamento);
    Municipio save(Municipio municipio);
    Municipio save(Long idDepartamento, String nombre, Double latitud, Double longitud, String telefono);
    List<MunicipioUsuarioDto> listarMuniciposAsignados(String username);

    Municipio findById(Long municipioId);

    Municipio update(Long municipioId, String nombre, Double latitud, Double longitud, String telefono);

    void eliminar(Long municipioId);
    void inactivar(Long municipioId);
}
