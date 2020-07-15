package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
@Table(name = "CONTROL_DIARIO_PAIS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "CTRL_DIAR_PAIS_ID_GENERATOR", sequenceName = "SEQ_CTRL_DIAR_PAIS_ID", allocationSize = 1)
public class ControlDiarioPais extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CTRL_DIAR_PAIS_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FECHA_LLEGADA")
    private Date fechaLlegada;

    @Column(name = "FECHA_SALIDA")
    private Date fechaSalida;

    @Column(name = "CIUDAD", length = 500)
    private String ciudades;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID")
    private Pais pais;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTROL_DIARIO", referencedColumnName = "ID")
    private ControlDiario controlDiario;


}
