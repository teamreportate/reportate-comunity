package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Entity
@Table(name = "MATRIZ_DIAGNOSTICO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MTZ_DIAGNOSTICO_ID_GENERATOR", sequenceName = "SEQ_MTZ_DIAGNOSTICO_ID", allocationSize = 1)
public class MatrizDiagnostico extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MTZ_DIAGNOSTICO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PESO", precision = 4, scale = 2, nullable = false)
    private BigDecimal peso;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENFERMEDAD", referencedColumnName = "ID")
    private Enfermedad enfermedad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SINTOMA", referencedColumnName = "ID")
    private Sintoma sintoma;

}
