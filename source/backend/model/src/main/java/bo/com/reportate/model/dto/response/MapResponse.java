package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-11
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MapResponse implements Serializable {
    private Long pacienteId;
    private Long diagnosticoId;
    private String nombrePaciente;
    private String nombreFamilia;
    private Double latitud;
    private Double longitud;
    private EstadoDiagnosticoEnum estadoDiagnostico;
    private String enfermedad;
    private BigDecimal valoracion;

    public MapResponse(Paciente p){
        this.pacienteId = p.getId();
        this.diagnosticoId = p.getDiagnostico().getId();
        this.nombrePaciente = p.getNombre();
        this.nombreFamilia = p.getFamilia().getNombre();
        this.latitud = p.getFamilia().getLatitud();
        this.longitud = p.getFamilia().getLongitud();
        this.estadoDiagnostico = p.getDiagnostico().getEstadoDiagnostico();
        this.enfermedad = p.getDiagnostico().getEnfermedad().getNombre();
        this.valoracion = p.getDiagnostico().getResultadoValoracion();
    }
}
