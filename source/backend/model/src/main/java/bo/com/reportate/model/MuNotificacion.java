package bo.com.reportate.model;

import bo.com.reportate.model.enums.TipoAlarma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model
 * date:    14-06-19
 * author:  fmontero
 **/

@Entity
@Table(name = "MU_NOTIFICACION")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_NOTIFICACION_ID_GENERATOR", sequenceName = "SEQ_MU_NOTIFICACION_ID", allocationSize = 1)
public class MuNotificacion extends AbstractAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_NOTIFICACION_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ALARMA", nullable = false)
    private TipoAlarma tipoAlarma;

    @Column(name = "CC_LIST")
    private String ccList;

    @Column(name = "BCC_LIST")
    private String bccList;

    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuUsuario idUsuario;
}
