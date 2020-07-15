package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
@Table(name = "CONTROL_DIARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "CONTROL_DIARIO_ID_GENERATOR", sequenceName = "SEQ_CONTROL_DIARIO_ID", allocationSize = 1)
public class ControlDiario extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTROL_DIARIO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRIMER_CONTROL")
    private Boolean primerControl;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID")
    private Paciente paciente;

    @Column(name = "RECOMENDACION", length = 500)
    private String recomendacion;


}
