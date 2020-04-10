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
    Date fechaLlegada;
    Date fechaSalida;
    Long controlPaisId;

    public PaisVisitadoDto(ControlDiarioPais controlDiarioPais){
        this.id=controlDiarioPais.getPais().getId();
        this.pais=controlDiarioPais.getPais().getNombre();
        this.fechaLlegada=controlDiarioPais.getFechaLlegada();
        this.fechaSalida = controlDiarioPais.getFechaSalida();
        this.ciudad=controlDiarioPais.getCiudades();
        this.controlPaisId = controlDiarioPais.getId();
    }
}
