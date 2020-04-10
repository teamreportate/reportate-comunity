package bo.com.reportate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :MC4
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DiagnosticoSintomaResponse implements Serializable {
    private String sintoma;
    private BigDecimal valoracion;
}
