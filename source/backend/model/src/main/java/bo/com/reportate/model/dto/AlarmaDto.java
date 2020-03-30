package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.TipoAlarma;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model
 * date:    14-06-19
 * author:  rlaredo
 **/

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AlarmaDto implements Serializable {
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private  String lastModifiedBy;
    private EstadoEnum estado;
    private Long id;
    private  String nombre;
    private  String descripcion;
    private TipoAlarma tipoAlarma;
    private  String html;
    private  String asunto;
    private  String atributos;
    private  String atributosSeleccionados;
    private  String contenido;

    public AlarmaDto(MuAlarma alarma) {
        this.version = alarma.getVersion();
        this.createdDate = alarma.getCreatedDate();
        this.lastModifiedDate = alarma.getLastModifiedDate();
        this.createdBy = alarma.getCreatedBy();
        this.lastModifiedBy = alarma.getLastModifiedBy();
        this.estado = alarma.getEstado();
        this.id = alarma.getId();
        this.nombre = alarma.getNombre();
        this.descripcion = alarma.getDescripcion();
        this.tipoAlarma = alarma.getTipoAlarma();
        this.html = alarma.getHtml();
        this.asunto = alarma.getAsunto();
        this.atributos = alarma.getAtributos();
        this.atributosSeleccionados = alarma.getAtributosSeleccionados();
        this.contenido = alarma.getContenido();
    }
}
