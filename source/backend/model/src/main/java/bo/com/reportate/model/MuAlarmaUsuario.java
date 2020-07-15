package bo.com.reportate.model;

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
@Table(name = "MU_ALARMA_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_ALARMA_USUARIO_GENERATOR", sequenceName = "SEQ_MU_ALARMA_USUARIO_ID", allocationSize = 1)
public class MuAlarmaUsuario extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_ALARMA_USUARIO_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRINCIPAL")
    private Boolean principal;
    @JoinColumn(name = "ID_ALARMA", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuAlarma idMuAlarma;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuUsuario idMuUsuario;
}
