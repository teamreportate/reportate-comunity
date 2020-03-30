package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "MU_DOMINIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_DOMINIO_USUARIO_GENERATOR", sequenceName = "SEQ_MU_DOMINIO_USUARIO_ID", allocationSize = 1)
public class MuDominio extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_DOMINIO_USUARIO_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "CODIGO", nullable = false, length = 20)
    private String codigo;
    @Column(name = "DESCRIPCION", nullable = false, length = 255)
    private String descripcion;
}
