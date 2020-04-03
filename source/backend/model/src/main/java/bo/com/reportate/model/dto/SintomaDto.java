package bo.com.reportate.model.dto;

import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :02/04/2020
 * Project       :reportate
 * Package       :bo.com.reportate.model.dto
 **/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SintomaDto implements Serializable {
    private Long id;
    private String nombre;
    private String pregunta;
    private Boolean controlDiario;
    private String ayuda;
    private EstadoEnum estado;

    public SintomaDto(Sintoma sintoma) {
        this.id = sintoma.getId();
        this.nombre = sintoma.getNombre();
        this.pregunta = sintoma.getPregunta();
        this.controlDiario = sintoma.getControlDiario();
        this.ayuda = sintoma.getAyuda();
        this.estado = sintoma.getEstado();
    }
}
