package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuUsuarioGrupo;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioGrupoDto {

    private Long id;
    private UsuarioDto usuario;
    private GrupoDto grupo;

    // Campos heredados...
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private EstadoEnum estado = EstadoEnum.ACTIVO;

    public UsuarioGrupoDto(MuUsuarioGrupo usuarioGrupo){
        this.id = usuarioGrupo.getId();
        this.usuario = new UsuarioDto( usuarioGrupo.getIdUsuario());
        this.grupo = new GrupoDto(usuarioGrupo.getIdGrupo());

        this.version = usuarioGrupo.getVersion();
        this.createdDate = usuarioGrupo.getCreatedDate();
        this.lastModifiedDate = usuarioGrupo.getLastModifiedDate();
        this.createdBy = usuarioGrupo.getCreatedBy();
        this.lastModifiedBy = usuarioGrupo.getLastModifiedBy();
        this.estado = usuarioGrupo.getEstado();
    }

}
