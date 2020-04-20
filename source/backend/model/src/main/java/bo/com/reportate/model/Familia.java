package bo.com.reportate.model;

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
@Table(name = "FAMILIA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "FAMILIA_ID_GENERATOR", sequenceName = "SEQ_FAMILIA_ID", allocationSize = 1)
public class Familia extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAMILIA_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", length = 160, nullable = false)
    private String nombre;
    @Column(name = "TELEFONO", length = 8, nullable = false)
    private String telefono;
    @Column(name = "LATITUD")
    private Double latitud;
    @Column(name = "LONGITUD")
    private Double longitud;
    @Column(name = "DIRECCION", length = 200, nullable = false)
    private String direccion;
    @Column(name = "ZONA", length = 100, nullable = false)
    private String zona;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTAMENTO", referencedColumnName = "ID")
    private Departamento departamento;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MUNICIPIO", referencedColumnName = "ID")
    private Municipio municipio;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CENTRO_SALUD", referencedColumnName = "ID")
    private CentroSalud centroSalud;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    private MuUsuario usuario;

    @JsonIgnore
    @OneToMany( mappedBy = "familia", fetch = FetchType.LAZY)
    private List<Paciente> pacientes;

}
