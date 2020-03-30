package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MU_PERMISO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_PERMISO_ID_GENERATOR", sequenceName = "SEQ_MU_PERMISO_ID", allocationSize = 1)
public class MuPermiso extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_PERMISO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuRol idRol;

    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuRecurso idRecurso;

    @Column(name = "LECTURA", nullable = false)
    private Boolean lectura;

    @Column(name = "ESCRITURA", nullable = false)
    private Boolean escritura;

    @Column(name = "ELIMINACION", nullable = false)
    private Boolean eliminacion;

    @Column(name = "SOLICITUD")
    private Boolean solicitud = false;

    @Column(name = "AUTORIZACION")
    private Boolean autorizacion = false;
}
