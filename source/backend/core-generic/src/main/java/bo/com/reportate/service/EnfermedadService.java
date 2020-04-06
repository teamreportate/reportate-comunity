package bo.com.reportate.service;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;

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
public interface EnfermedadService {
    List<EnfermedadResponse> list();

    /**
     * Lista de enfermedades que no son Base, para filtro de enfermedades
     * @return
     */
    List<EnfermedadResponse> listNoBase();
    List<EnfermedadDto> listActivos();
    Enfermedad save(EnfermedadDto enfermedadDto);
    Enfermedad findById(Long enfermedadId);
    Enfermedad update(Long id, EnfermedadDto enfermedadDto);
    boolean cambiarEstado(Long id);
}
