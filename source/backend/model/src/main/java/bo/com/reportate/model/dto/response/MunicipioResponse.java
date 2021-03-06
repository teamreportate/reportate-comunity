package bo.com.reportate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class MunicipioResponse implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
}
