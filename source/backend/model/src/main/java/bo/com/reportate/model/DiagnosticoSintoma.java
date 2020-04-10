package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Entity
@Table(name = "DIAGNOSTICO_SINTOMA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "DIAGN_SINTOMA_ID_GENERATOR", sequenceName = "SEQ_DIAGN_SINTOMA_ID", allocationSize = 1)
public class DiagnosticoSintoma extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIAGN_SINTOMA_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALORACION", precision = 4, scale = 2)
    private BigDecimal valoracion;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIAGNOSTICO", referencedColumnName = "ID")
    private Diagnostico diagnostico;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SINTOMA", referencedColumnName = "ID")
    private Sintoma sintoma;
}
