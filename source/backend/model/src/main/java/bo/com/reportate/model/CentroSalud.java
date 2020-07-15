package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "CENTRO_SALUD")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "CENTRO_SALUD_ID_GENERATOR", sequenceName = "SEQ_CENTRO_SALUD_ID", allocationSize = 1)
public class CentroSalud extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CENTRO_SALUD_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    @Column(name = "LATITUD")
    private Double latitud;
    @Column(name = "LONGITUD")
    private Double longitud;
    @Column(name = "DIRECCION", length = 200, nullable = false)
    private String direccion;
    @Column(name = "ZONA", length = 100, nullable = false)
    private String zona;
    @Column(name = "TELEFONO", length = 9)
    private String telefono;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID")
    private Municipio municipio;

    @JsonIgnore
    @OneToMany( mappedBy = "centroSalud", fetch = FetchType.LAZY)
    private List<CentroSaludUsuario> centroSaludUsuarios;

}
