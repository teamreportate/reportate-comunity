package bo.com.reportate.model.dto;

import bo.com.reportate.model.enums.EstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDto {

    private Long id;
    private RolDto idRol;
    private RecursoDto idRecurso;
    private Boolean lectura;
    private Boolean escritura;
    private Boolean eliminacion;
    private Boolean solicitud = false;
    private Boolean autorizacion = false;

    // Campos heredados...
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private EstadoEnum estado;

}
