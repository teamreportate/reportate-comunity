package bo.com.reportate.web;

import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
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
public class ControlDiarioRequest implements Serializable {
    private List<PaisRequest> paises;
    private List<EnfermedadRequest> enfermedadesBase;
    private List<SintomaRequest> sintomas;
}
