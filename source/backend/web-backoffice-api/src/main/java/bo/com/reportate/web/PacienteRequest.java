package bo.com.reportate.web;

import bo.com.reportate.model.enums.GeneroEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.web
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class PacienteRequest implements Serializable {
    private Long id;
    private String nombre;
    private Integer edad;
    private GeneroEnum genero;
    private Boolean gestacion = false;
    private Integer tiempoGestacion;// los tiempos se toma en cuenta en semanas.
    private String ocupacion;
    private String ci;
    private String fechaNacimiento;
    private String seguro;
    private String codigoSeguro;
}
