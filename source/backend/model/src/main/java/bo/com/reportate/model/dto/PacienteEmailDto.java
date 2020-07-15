package bo.com.reportate.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-14
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto
 * @Copyright :Reportate
 */
@Getter @Setter @Builder
public class PacienteEmailDto implements Serializable {
    private Long id;
    private String nombre;
    private String telefono;
    private String sexo;
    private String enfermedad;
    private Integer valoracion;
    private Integer edad;
}
