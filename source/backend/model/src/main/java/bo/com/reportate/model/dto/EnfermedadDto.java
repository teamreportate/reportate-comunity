package bo.com.reportate.model.dto;

import bo.com.reportate.model.Enfermedad;
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
public class EnfermedadDto implements Serializable {
    private Long id;
    private String nombre;
    private Boolean enfermedadBase;
    private String mensajeDiagnostico;
    private EstadoEnum estado;

    public EnfermedadDto(Enfermedad enfermedad) {
        this.id = enfermedad.getId();
        this.nombre = enfermedad.getNombre();
        this.enfermedadBase = enfermedad.getEnfermedadBase();
        this.mensajeDiagnostico = enfermedad.getMensajeDiagnostico();
        this.estado = enfermedad.getEstado();
    }
}
