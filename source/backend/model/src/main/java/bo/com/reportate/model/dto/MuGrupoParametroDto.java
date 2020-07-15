package bo.com.reportate.model.dto;
import bo.com.reportate.model.MuGrupoParametro;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :18/12/2019
 * Project       :reportate
 * Package       :bo.com.Reportate.reportate.model.dto
 **/
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MuGrupoParametroDto implements Serializable {
    private Long id;
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private Long createdBy;
    private Long lastModifiedBy;
    private Boolean estado;

    private String grupo;
    private String descripcion;

    public MuGrupoParametroDto(MuGrupoParametro idMuGrupoParametro) {
        this.id = idMuGrupoParametro.getId();
        this.version = idMuGrupoParametro.getVersion();
        this.createdDate = idMuGrupoParametro.getCreatedDate();
        this.lastModifiedDate = idMuGrupoParametro.getLastModifiedDate();
//        this.createdBy = idMuGrupoParametro.getCreatedBy();
//        this.lastModifiedBy = idMuGrupoParametro.getLastModifiedBy();
//        this.estado = idMuGrupoParametro.getEstado();

        this.grupo = idMuGrupoParametro.getGrupo();
        this.descripcion = idMuGrupoParametro.getDescripcion();
    }
}

