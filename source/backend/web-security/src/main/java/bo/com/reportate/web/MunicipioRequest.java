package bo.com.reportate.web;

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
@Getter
@Setter
@NoArgsConstructor
public class MunicipioRequest implements Serializable {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;

}
