package bo.com.reportate.model.dto;

import bo.com.reportate.model.Diagnostico;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor @Data
public class DiagnosticoDto implements Serializable {
    private Long id;

    private Date fechaRegistro;
    private String clasificacion;
    private String enfermedad;
    private BigDecimal valoracion;
    private String responsable;
    private String observacion;

    public DiagnosticoDto(Diagnostico diagnostico){
        this.id = diagnostico.getId();
        this.fechaRegistro = diagnostico.getCreatedDate();
        this.clasificacion = diagnostico.getEstadoDiagnostico().name();
        this.enfermedad=diagnostico.getEnfermedad().getNombre();
        this.valoracion=diagnostico.getResultadoValoracion();
        if(diagnostico.getMedico() != null){
            this.responsable=diagnostico.getMedico().getUsername();
        }else {
            this.responsable = "";
        }
        this.observacion = diagnostico.getObservacion();
    }
}
