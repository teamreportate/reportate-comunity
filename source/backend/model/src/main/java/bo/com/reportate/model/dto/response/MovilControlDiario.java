package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-10
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :MC4
 */
@Getter @Setter @NoArgsConstructor
public class MovilControlDiario implements Serializable {
    private Long id;
    private Date fechaRegistro;
    private String recomendacion;
    private List<SintomaResponse> sintomas = new ArrayList<>();
    public MovilControlDiario(Long id, Date fechaRegistro, String recomendacion ){
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.recomendacion = recomendacion;
    }

    public MovilControlDiario(ControlDiario controlDiario){
        this.id = controlDiario.getId();
        this.fechaRegistro = controlDiario.getCreatedDate();
        this.recomendacion = controlDiario.getRecomendacion();
    }
}
