package bo.com.reportate.model.dto;

import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.MuValorDominio;
import bo.com.reportate.model.enums.EstadoEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class ValorDominioDto {
    Long version;
    Date createdDate;
    Date lastModifiedDate;
    String createdBy;
    String lastModifiedBy;
    EstadoEnum estado;
    Long id;
    DominioDto dominio;
    String valor;
    String descripcion;

    public ValorDominioDto(Long version, Date createdDate, Date lastModifiedDate, String createdBy, String lastModifiedBy, EstadoEnum estado, Long id, MuDominio dominio, String valor, String descripcion) {
        this.version = version;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.estado = estado;
        this.id = id;
        this.dominio = new DominioDto(dominio);
        this.valor = valor;
        this.descripcion = descripcion;
    }
    public ValorDominioDto(MuValorDominio valorDominio){
        this.version = valorDominio.getVersion();
        this.createdDate = valorDominio.getCreatedDate();
        this.lastModifiedDate = valorDominio.getLastModifiedDate();
        this.createdBy = valorDominio.getCreatedBy();
        this.lastModifiedBy = valorDominio.getLastModifiedBy();
        this.estado = valorDominio.getEstado();
        this.id = valorDominio.getId();
        this.dominio = new DominioDto(valorDominio.getDominio());
        this.valor = valorDominio.getValor();
        this.descripcion = valorDominio.getDescripcion();
    }


}
