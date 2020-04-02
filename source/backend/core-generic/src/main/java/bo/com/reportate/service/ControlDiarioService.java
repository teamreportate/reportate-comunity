package bo.com.reportate.service;

import bo.com.reportate.model.dto.response.ControlDiarioFullResponse;
import bo.com.reportate.model.dto.response.SintomaResponse;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service
 * @Copyright :MC4
 */
public interface ControlDiarioService {
    ControlDiarioFullResponse getEncuentaFull();
    List<SintomaResponse> getEncuesta();
}
