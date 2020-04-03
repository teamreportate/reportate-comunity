package bo.com.reportate.model.dto;

import bo.com.reportate.model.Pais;
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
public class PaisDto implements Serializable {
    private Long id;
    private String nombre;
    private EstadoEnum estado;

    public PaisDto(Pais pais){
        this.id = pais.getId();
        this.nombre = pais.getNombre();
        this.estado = pais.getEstado();
    }
}
