package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.GeneroEnum;
import lombok.AllArgsConstructor;
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
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PacienteResponse  implements Serializable {
    private Long id;
    private String nombre;
    private Integer edad;
    private GeneroEnum genero;
    private Boolean gestacion = false;
    private Integer tiempoGestacion;
    private Boolean controlInicial;
    private String ocupacion;
    public PacienteResponse(Paciente paciente){
        this.id = paciente.getId();
        this.nombre = paciente.getNombre();
        this.edad = paciente.getEdad();
        this.genero = paciente.getGenero();
        this.gestacion = paciente.getGestacion();
        this.tiempoGestacion = paciente.getTiempoGestacion();
        this.controlInicial = (paciente.getControlDiarios() != null && !paciente.getControlDiarios().isEmpty());
        this.ocupacion = paciente.getOcupacion();
    }
}
