package bo.com.reportate.model.dto;

import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.GeneroEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto
 * @Copyright :MC4
 */
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class PacienteDto implements Serializable {
    private Long id;
    private String nombre;
    private Integer edad;
    private GeneroEnum genero;
    private Boolean gestacion = false;
    private Integer tiempoGestacion;// los tiempos se toma en cuenta en semanas.
    private String ocupacion;
    private String ci;
    private Date fechaNacimiento;
    private String seguro;
    private String codigoSeguro;

    public  PacienteDto(Paciente paciente){
        this.id=paciente.getId();
        this.nombre=paciente.getNombre();
        this.edad=paciente.getEdad();
        this.genero=paciente.getGenero();
        this.ocupacion = paciente.getOcupacion();
        this.ci = paciente.getCi();
        this.fechaNacimiento = paciente.getFechaNacimiento();
        this.seguro = paciente.getSeguro();
        this.codigoSeguro = paciente.getCodigoSeguro();
    }
}
