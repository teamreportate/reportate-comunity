package bo.com.reportate.model.dto.response;

import bo.com.reportate.model.Sintoma;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Getter @Setter @AllArgsConstructor
public class SintomaResponse implements Serializable {
    private Long id;
    private String nombre;
    private String pregunta;
    private Boolean controlDiario;
    private String ayuda;

    public SintomaResponse(Sintoma sintoma) {
        this.id = sintoma.getId();
        this.nombre = sintoma.getNombre();
        this.pregunta = sintoma.getPregunta();
        this.controlDiario = sintoma.getControlDiario();
        this.ayuda = sintoma.getAyuda();
    }
}
