package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @Column(name = "OBSERVACION", length = 4000)
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

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
    private Departamento departamento;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID")
    private Municipio municipio;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CENTRO_SALUD", referencedColumnName = "ID")
    private CentroSalud centroSalud;

    @JsonIgnore
    @OneToMany( mappedBy = "diagnostico", fetch = FetchType.LAZY)
    private List<DiagnosticoSintoma> sintomaList;


}
