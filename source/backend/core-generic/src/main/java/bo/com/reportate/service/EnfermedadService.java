package bo.com.reportate.service;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;

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
public interface EnfermedadService {
    List<EnfermedadResponse> list();

    /**
     * Lista de enfermedades que no son Base, para filtro de enfermedades
     * @return
     */
    List<EnfermedadResponse> listNoBase();
    List<EnfermedadResponse> listBase();
    List<EnfermedadDto> listActivos();
    Enfermedad save(EnfermedadDto enfermedadDto);
    Enfermedad findById(Long enfermedadId);
    Enfermedad update(Long id, EnfermedadDto enfermedadDto);
    boolean eliminar(Long id);
}
