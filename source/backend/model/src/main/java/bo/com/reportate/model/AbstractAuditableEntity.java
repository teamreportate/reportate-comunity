package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class AbstractAuditableEntity implements Serializable, Cloneable {

    @Version
    //@Column(name="VERSION",columnDefinition = "NUMBER(19) default 0")
    @Column(name="VERSION")
    private Long version;

    @CreatedDate
    //@Column(name = "FECHA_REGISTRO",columnDefinition = "TIMESTAMP (6) default TO_TIMESTAMP('2020-01-01 01:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')")
    @Column(name = "FECHA_REGISTRO")
    Date createdDate;

    @LastModifiedDate
    //@Column(name = "FECHA_ACTUALIZACION",columnDefinition = "TIMESTAMP (6) default TO_TIMESTAMP('2020-01-01 01:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')")
    @Column(name = "FECHA_ACTUALIZACION")
    Date lastModifiedDate;

    @CreatedBy
    //@Column(name = "USUARIO_REGISTRO",columnDefinition = "VARCHAR2(255) default 'SISTEMA'")
    @Column(name = "USUARIO_REGISTRO")
    String createdBy;

    @LastModifiedBy
    //@Column(name = "USUARIO_ACTUALIZACION",columnDefinition = "VARCHAR2(255) default 'SISTEMA'")
    @Column(name = "USUARIO_ACTUALIZACION")
    String lastModifiedBy;


    @Column(name = "ESTADO", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    EstadoEnum estado = EstadoEnum.ACTIVO; // 1: habilitado, 0: deshabilitado, 2: eliminado

    public Object clone()throws CloneNotSupportedException{
        return (AbstractAuditableEntity)super.clone();
    }

}
