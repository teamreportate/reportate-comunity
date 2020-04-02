package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
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
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Entity
@Table(name = "DIAGNOSTICO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "DIAGNOSTICO_ID_GENERATOR", sequenceName = "SEQ_DIAGNOSTICO_ID", allocationSize = 1)
public class Diagnostico extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIAGNOSTICO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "OBSERVACION")
    @Lob
    private String observacion;

    @Column(name = "ESTADO_DIAGNOSTICO")
    @Enumerated(EnumType.STRING)
    private EstadoDiagnosticoEnum estadoDiagnostico;

    @Column(name = "RESULTADO_VALORACION", precision = 4, scale = 2)
    private BigDecimal resultadoValoracion;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTROL_DIARIO", referencedColumnName = "ID")
    private ControlDiario controlDiario;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MEDICO", referencedColumnName = "ID")
    private MuUsuario medico;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENFERMEDAD", referencedColumnName = "ID")
    private Enfermedad enfermedad;

}
