package bo.com.reportate.model.dto;

import bo.com.reportate.model.ControlDiarioPais;

import java.util.Date;

public class PaisVisitadoDto {
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
