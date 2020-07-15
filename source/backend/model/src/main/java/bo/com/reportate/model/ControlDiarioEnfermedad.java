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
@Table(name = "CONTROL_DIARIO_ENFERMEDAD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "CTRL_DIAR_ENFER_ID_GENERATOR", sequenceName = "SEQ_CTRL_DIAR_ENFER_ID", allocationSize = 1)
public class ControlDiarioEnfermedad extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CTRL_DIAR_ENFER_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;


    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENFERMEDAD", referencedColumnName = "ID")
    private Enfermedad enfermedad;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTROL_DIARIO", referencedColumnName = "ID")
    private ControlDiario controlDiario;


}
