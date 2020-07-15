package bo.com.reportate.model;

import bo.com.reportate.model.enums.GeneroEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
@Table(name = "PACIENTE")
@Setter
@Getter
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
    @Column(name = "OCUPACION", length = 50)
    private String ocupacion;
    @Column(name = "ci", length = 20)
    private String ci;
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;
    @Column(name = "CODIGO_SEGURO", length = 30)
    private String codigoSeguro;
    @Column(name = "SEGURDO", length = 50)
    private String seguro;
    @Column(name = "CANTIDAD_CONTROLES")
    private Integer cantidadControles;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DIAGNOSTICO", referencedColumnName = "ID")
    private Diagnostico diagnostico;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAMILIA", referencedColumnName = "ID")
    private Familia familia;

    @JsonIgnore
    @OneToMany( mappedBy = "paciente", fetch = FetchType.LAZY)
    private Set<ControlDiario> controlDiarios;

}
