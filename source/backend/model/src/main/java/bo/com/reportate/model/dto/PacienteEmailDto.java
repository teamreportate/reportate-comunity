package bo.com.reportate.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-14
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto
 * @Copyright :MC4
 */
@Getter @Setter @Builder
public class PacienteEmailDto {
    private Long id;
    private String nombre;
    private String telefono;
    private String sexo;
    private String enfermedad;
    private Integer valoracion;
    private Integer edad;
}
