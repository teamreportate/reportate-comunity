package bo.com.reportate.model;

import bo.com.reportate.model.enums.TipoAlarma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model
 * date:    14-06-19
 * author:  fmontero
 **/

@Entity
@Table(name = "MU_ALARMA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_ALARMA_ID_GENERATOR", sequenceName = "SEQ_MU_ALARMA_ID", allocationSize = 1)
public class MuAlarma extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_ALARMA_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "DESCRIPCION", nullable = false, length = 2048)
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ALARMA", nullable = false)
    private TipoAlarma tipoAlarma;
    @Lob
    @Type(type = "text")
    @Column(name = "HTML", nullable = false)
    private String html;
    @Column(name = "ASUNTO", nullable = false)
    private String asunto;
    @Column(name = "ATRIBUTOS")
    private String atributos;
    @Column(name = "ATRIBUTOS_SELECCIONADOS")
    private String atributosSeleccionados;
    @Column(name = "CONTENIDO", length = 4000)
    private String contenido;

}
