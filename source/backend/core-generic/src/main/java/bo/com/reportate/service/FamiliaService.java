package bo.com.reportate.service;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.dto.FamiliaMovilResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

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
    FamiliaMovilResponseDto save(UserDetails user, Long departamentoId, Long municipioId, String nombre, String telefono, String direccion, Double latitud, Double longitud, String ciudad, String zona);
}
