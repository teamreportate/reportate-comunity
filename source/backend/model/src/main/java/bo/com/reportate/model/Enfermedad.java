package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Entity
@Table(name = "ENFERMEDAD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "ENFERMEDAD_ID_GENERATOR", sequenceName = "SEQ_ENFERMEDAD_ID", allocationSize = 1)
public class Enfermedad extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENFERMEDAD_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    @Column(name = "ENFERMEDAD_BASE",nullable = false)
    private Boolean enfermedadBase;
    @Column(name = "MENSAJE_DIAGNOSTICO", length = 4000)
    private String mensajeDiagnostico;
}
