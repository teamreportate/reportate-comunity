package bo.com.reportate.service;

import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisDto;

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
public interface PaisService {
    List<PaisDto> listAll();
    Pais save(PaisDto paisDto);
    Pais findById(Long paisId);
    Pais update(Long id, PaisDto paisDto);
    boolean eliminar(Long id);
}
