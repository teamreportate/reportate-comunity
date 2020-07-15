package bo.com.reportate.service;

import bo.com.reportate.model.dto.response.ControlDiarioFullResponse;
import bo.com.reportate.model.dto.response.SintomaResponse;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :Reportate
 */
public interface ControlDiarioService {
    ControlDiarioFullResponse getEncuentaFull();
    List<SintomaResponse> getEncuesta();
}
