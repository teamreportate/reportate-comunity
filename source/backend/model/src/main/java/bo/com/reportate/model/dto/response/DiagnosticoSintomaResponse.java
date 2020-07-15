package bo.com.reportate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :Reportate
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DiagnosticoSintomaResponse implements Serializable {
    private String sintoma;
    private BigDecimal valoracion;
}
