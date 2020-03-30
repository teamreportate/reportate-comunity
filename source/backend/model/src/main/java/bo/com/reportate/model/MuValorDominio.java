package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "MU_VALOR_DOMINIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_VALOR_DOMINIO_ID_GENERATOR", sequenceName = "SEQ_MU_VALOR_DOMINIO_GRUPO_ID", allocationSize = 1)
public class MuValorDominio extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_VALOR_DOMINIO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "DOMINIO_ID", referencedColumnName = "CODIGO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuDominio dominio;
    @Column(name = "VALOR", nullable = false)
    private String valor;
    @Column(name = "DESCRIPCION")
    private String descripcion;
     
}
