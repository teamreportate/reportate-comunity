package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Municipio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class MunicipioResponse implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
}
