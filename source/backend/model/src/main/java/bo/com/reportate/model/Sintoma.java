package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :Reportate
 */
@Entity
@Table(name = "SINTOMA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "SINTOMA_ID_GENERATOR", sequenceName = "SEQ_SINTOMA_ID", allocationSize = 1)
public class Sintoma extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SINTOMA_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    @Column(name = "PREGUNTA", length = 500, nullable = true)
    private String pregunta;
    @Column(name = "CONTROL_DIARIO", nullable = false)
    private Boolean controlDiario;
    @Column(name = "AYUDA", length = 500)
    private String ayuda;
}
