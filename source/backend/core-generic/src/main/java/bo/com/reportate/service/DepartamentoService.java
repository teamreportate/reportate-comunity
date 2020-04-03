package bo.com.reportate.service;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.DepartamentoUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
import bo.com.reportate.model.dto.response.DepartamentoResponse;
import org.springframework.security.core.Authentication;

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
public interface DepartamentoService {
    Departamento findById(Long  departamentoId);
    List<DepartamentoDto> findAllConMunicipio();
    Departamento save(Departamento departamento);
    Departamento save(String nombre, Double latitud, Double longitud);

    Departamento update(Long departamentoId, String nombre, Double latitud, Double longitud);
    List<DepartamentoUsuarioDto> listarDepartamentosAsignados(String username);

    List<DepartamentoUsuarioDto> listarAsignados(Authentication userDetails);
}
