package bo.com.reportate.service;

import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.SintomaDto;

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
public interface SintomaService {
    List<SintomaDto> listAll();
    Sintoma save(SintomaDto sintomaDto);
    Sintoma findById(Long sintomaId);
    Sintoma update(Long id, SintomaDto sintomaDto);
    boolean eliminar(Long id);

}
