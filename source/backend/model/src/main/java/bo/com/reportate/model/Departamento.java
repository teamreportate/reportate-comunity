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
@Table(name = "DEPARTAMENTO")
@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "DEPARTAMENTO_ID_GENERATOR", sequenceName = "SEQ_DEPARTAMENTO_ID", allocationSize = 1)
public class Departamento extends AbstractAuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_ID_GENERATOR")
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

    @JsonIgnore
    @OneToMany( mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Municipio> municipios;

    @JsonIgnore
    @OneToMany( mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<DepartamentoUsuario> departamentoUsuarios;

}
