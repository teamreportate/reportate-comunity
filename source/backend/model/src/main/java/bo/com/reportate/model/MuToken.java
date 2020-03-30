package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model
 * date:    12-09-19
 * author:  fmontero
 **/

@Entity
@Table(name = "MU_TOKEN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_TOKEN_ID_GENERATOR", sequenceName = "SEQ_MU_TOKEN_ID", allocationSize = 1)
public class MuToken extends AbstractAuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_TOKEN_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Lob
    @Type(type = "text")
    private String token;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fecha_expiracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaExpiracion;

    @Column(name = "indefinido")
    private Boolean indefinido;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SFE_USUARIO", referencedColumnName = "ID")
    private MuUsuario idUsuario;
}
