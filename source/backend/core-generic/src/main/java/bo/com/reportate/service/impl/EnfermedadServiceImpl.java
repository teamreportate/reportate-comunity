package bo.com.reportate.service.impl;

import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.repository.EnfermedadRepository;
import bo.com.reportate.service.EnfermedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
public class EnfermedadServiceImpl implements EnfermedadService {
    @Autowired private EnfermedadRepository enfermedadRepository;
    @Override
    public List<EnfermedadResponse> list() {
        return enfermedadRepository.listarEnfermedadesActivos();
    }
}
