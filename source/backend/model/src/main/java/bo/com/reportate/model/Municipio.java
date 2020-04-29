package bo.com.reportate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "MUNICIPIO")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MUNICIPIO_ID_GENERATOR", sequenceName = "SEQ_MUNICIPIO_ID", allocationSize = 1)
public class Municipio extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MUNICIPIO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    @Column(name = "LATITUD")
    private Double latitud;
    @Column(name = "LONGITUD")
    private Double longitud;
    @Column(name = "TELEFONO", length = 9)
    private String telefono;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
    private Departamento departamento;

    @JsonIgnore
    @OneToMany( mappedBy = "municipio", fetch = FetchType.LAZY)
    private List<CentroSalud> centroSaluds;

    @JsonIgnore
    @OneToMany( mappedBy = "municipio", fetch = FetchType.LAZY)
    private List<MunicipioUsuario> municipioUsuarios;

}
