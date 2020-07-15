package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.utils.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-10
 * @Project :reportate
 * @Package :bo.com.reportate.model.dto.response
 * @Copyright :Reportate
 */
@Getter @Setter @NoArgsConstructor
public class MovilControlDiario implements Serializable {
    private Long id;
    private String fechaRegistro;
    private String recomendacion;
    private List<SintomaResponse> sintomas = new ArrayList<>();
    public MovilControlDiario(Long id, Date fechaRegistro, String recomendacion ){
        this.id = id;
        this.fechaRegistro = DateUtil.toString(DateUtil.FORMAT_DATE_MINUTE,fechaRegistro);
        this.recomendacion = recomendacion;
    }

    public MovilControlDiario(ControlDiario controlDiario){
        this.id = controlDiario.getId();
        this.fechaRegistro = DateUtil.toString(DateUtil.FORMAT_DATE_MINUTE,controlDiario.getCreatedDate());
        this.recomendacion = controlDiario.getRecomendacion();
    }
}
