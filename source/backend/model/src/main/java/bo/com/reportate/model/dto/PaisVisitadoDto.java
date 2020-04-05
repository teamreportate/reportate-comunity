package bo.com.reportate.model.dto;

import bo.com.reportate.model.ControlDiarioPais;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class PaisVisitadoDto implements Serializable {
    Long id;
    String pais;
    String ciudad;
    Date fecha;

    public PaisVisitadoDto(ControlDiarioPais controlDiarioPais){
        this.id=controlDiarioPais.getPais().getId();
        this.pais=controlDiarioPais.getPais().getNombre();
        this.fecha=controlDiarioPais.getControlDiario().getCreatedDate();

    }
}
