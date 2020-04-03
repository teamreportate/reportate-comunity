package bo.com.reportate.service.impl;

import bo.com.reportate.model.dto.response.ControlDiarioFullResponse;
import bo.com.reportate.model.dto.response.SintomaResponse;
import bo.com.reportate.repository.EnfermedadRepository;
import bo.com.reportate.repository.PaisRepository;
import bo.com.reportate.repository.SintomaRepository;
import bo.com.reportate.service.ControlDiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
public class ControlDiarioServiceImpl implements ControlDiarioService {
    @Autowired private PaisRepository paisRepository;
    @Autowired private EnfermedadRepository enfermedadRepository;
    @Autowired private SintomaRepository sintomaRepository;

    @Override
    public ControlDiarioFullResponse getEncuentaFull() {
        ControlDiarioFullResponse response = new ControlDiarioFullResponse();
        response.setSintomas(sintomaRepository.findByEstadoAndControlDiarioTrue());
        response.setPaises(paisRepository.listarPaises());
        response.setEnfermedadesBase(enfermedadRepository.listarEnfermedadesActivos());
        return response;
    }

    @Override
    public List<SintomaResponse> getEncuesta() {
        return  sintomaRepository.findByEstadoAndControlDiarioTrue();
    }
}
