package bo.com.reportate.model.dto.request;

import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
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
 * @Package :bo.com.reportate.model.dto.request
 * @Copyright :Reportate
 */
@Setter @Getter @NoArgsConstructor
public class DiagnosticoRequest implements Serializable {
    private Long enfermedadId;
    private EstadoDiagnosticoEnum clasificacion;
    private String recomendacion;
    private List<SintomaRequest> sintomas;
}
