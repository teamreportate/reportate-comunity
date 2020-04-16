package bo.com.reportate.model.dto.request;

import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
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
 * @Package :bo.com.reportate.model.dto.request
 * @Copyright :MC4
 */
@Setter @Getter @NoArgsConstructor
public class DiagnosticoRequest implements Serializable {
    private Long enfermedadId;
    private EstadoDiagnosticoEnum clasificacion;
    private String recomendacion;
    private List<SintomaRequest> sintomas;
}
