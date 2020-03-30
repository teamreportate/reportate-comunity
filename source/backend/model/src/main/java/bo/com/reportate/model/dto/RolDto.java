package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuRol;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.EstadoRol;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolDto {
    private Long id;
    private String nombre;
   private String descripcion;
   private EstadoRol estadoRol;

    // Campos heredados...
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private EstadoEnum estado = EstadoEnum.ACTIVO;

    public RolDto(MuRol muRol) {
        this.id = muRol.getId();
        this.nombre = muRol.getNombre();
        this.descripcion = muRol.getDescripcion();
        this.estadoRol = muRol.getEstadoRol();

        //campos heredados
        this.version = muRol.getVersion();
        this.createdDate = muRol.getCreatedDate();
        this.lastModifiedDate = muRol.getLastModifiedDate();
        this.createdBy = muRol.getCreatedBy();
        this.lastModifiedBy = muRol.getLastModifiedBy();
        this.estado = muRol.getEstado();
    }
}
