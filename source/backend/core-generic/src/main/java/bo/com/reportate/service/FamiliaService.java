package bo.com.reportate.service;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.dto.response.FamiliaMovilResponseDto;
import bo.com.reportate.model.dto.response.FamiliaResponse;
import org.springframework.security.core.Authentication;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :MC4
 */
public interface FamiliaService {
    void save(Familia familia);
    FamiliaMovilResponseDto save(Authentication user, Long departamentoId, Long municipioId, String nombre, String telefono, String direccion, Double latitud, Double longitud, String zona, Long centroSaludId);
    FamiliaMovilResponseDto update(Long departamentoId, Long municipioId, Long familiaId, String nombre, String telefono, String direccion, String zona);
    void delete(Long familiaId);
    FamiliaResponse getInfo(Authentication authentication);
}
