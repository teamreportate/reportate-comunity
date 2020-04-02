package bo.com.reportate.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

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
public class SintomaRequest implements Serializable {
    private Long id;
    private Boolean respuesta;
    private String observacion;
}
