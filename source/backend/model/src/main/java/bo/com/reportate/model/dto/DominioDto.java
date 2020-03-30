package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.Data;

import java.util.Date;

@Data
public class DominioDto {
    Long version;
    Date createdDate;
    Date lastModifiedDate;
    String createdBy;
    String lastModifiedBy;
    EstadoEnum estado;
    Long id;
    String codigo;
    String descripcion;

    public DominioDto(MuDominio dominio){
        this.version = dominio.getVersion();
        this.createdDate = dominio.getCreatedDate();
        this.lastModifiedDate = dominio.getLastModifiedDate();
        this.createdBy = dominio.getCreatedBy();
        this.lastModifiedBy = dominio.getLastModifiedBy();
        this.estado = dominio.getEstado();
        this.id = dominio.getId();
        this.codigo = dominio.getCodigo();
        this.descripcion = dominio.getDescripcion();
    }

    public DominioDto(Long version, Date createdDate, Date lastModifiedDate, String createdBy, String lastModifiedBy, EstadoEnum estado, Long id, String codigo, String descripcion) {
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.estado = estado;
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
}
