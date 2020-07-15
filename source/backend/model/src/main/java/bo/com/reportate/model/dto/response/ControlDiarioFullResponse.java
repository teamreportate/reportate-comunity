package bo.com.reportate.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class ControlDiarioFullResponse implements Serializable {
    private List<PaisResponse> paises;
    private List<EnfermedadResponse> enfermedadesBase;
    private List<SintomaResponse> sintomas;
}
