package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.EstadoGrupo;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private EstadoGrupo estadoGrupo;
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private EstadoEnum estado;

    private List<RolDto> roles = new ArrayList<>();
    private List<UsuarioGrupoDto> muUsuarioGrupos = new ArrayList<>();

    public GrupoDto(MuGrupo grupo){
        this.id = grupo.getId();
        this.nombre = grupo.getNombre();
        this.descripcion = grupo.getDescripcion();
        this.estadoGrupo = grupo.getEstadoGrupo();
        this.version = grupo.getVersion();
        this.createdDate = grupo.getCreatedDate();
        this.lastModifiedDate = grupo.getLastModifiedDate();
        this.createdBy = grupo.getCreatedBy();
        this.lastModifiedBy = grupo.getLastModifiedBy();
        this.estado = grupo.getEstado();
    }
}
