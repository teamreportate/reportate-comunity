package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.*;

import java.util.Date;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model.dto
 * date:    12-09-19
 * author:  fmontero
 **/

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private Long id;
    private Long version;
    private Date createdDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private EstadoEnum estado;
    private String token;
    private Date fechaInicio;
    private Date fechaExpiracion;
    private UsuarioDto idUsuario;

    private Boolean indefinido;

    public TokenDto(MuToken muToken) {
        this.id = muToken.getId();
        this.version = muToken.getVersion();
        this.createdDate = muToken.getCreatedDate();
        this.lastModifiedDate = muToken.getLastModifiedDate();
        this.createdBy = muToken.getCreatedBy();
        this.lastModifiedBy = muToken.getLastModifiedBy();
        this.estado = muToken.getEstado();
        this.token = muToken.getToken();
        this.fechaInicio = muToken.getFechaInicio();
        this.fechaExpiracion = muToken.getFechaExpiracion();
        if(muToken.getIdUsuario()!= null) {
            this.idUsuario = new UsuarioDto(muToken.getIdUsuario());
        }
        this.indefinido = muToken.getIndefinido();
    }

    public String registroValidacion() {
        if (this.fechaExpiracion == null) return "Fecha de Expiracion";
        return null;
    }
}
