package bo.com.reportate.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor
public class ControlDiarioFullResponse implements Serializable {
    private List<PaisResponse> paises;
    private List<EnfermedadResponse> enfermedadesBase;
    private List<SintomaResponse> sintomas;
}
