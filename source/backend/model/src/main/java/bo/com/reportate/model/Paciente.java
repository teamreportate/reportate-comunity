package bo.com.reportate.model;

import bo.com.reportate.model.enums.GeneroEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "PACIENTE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "PACIENTE_ID_GENERATOR", sequenceName = "SEQ_PACIENTE_ID", allocationSize = 1)
public class Paciente extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTE_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 160, nullable = false)
    private String nombre;
    @Column(name = "EDAD", nullable = false)
    private Integer edad;
    @Column(name = "GENERO", nullable = false)
    @Enumerated(EnumType.STRING)
    private GeneroEnum genero;
    @Column(name = "GESTACION", nullable = false)
    private Boolean gestacion = false;
    @Column(name = "TIEMPO_GESTACION")
    private Integer tiempoGestacion;// los tiempos se toma en cuenta en semanas.

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID")
    private Familia familia;

    @JsonIgnore
    @OneToMany( mappedBy = "paciente", fetch = FetchType.LAZY)
    private List<ControlDiario> controlDiarios;

}
