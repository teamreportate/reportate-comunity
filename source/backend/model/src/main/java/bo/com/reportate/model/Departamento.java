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
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.model
 * @Copyright :MC4
 */
@Entity
@Table(name = "DEPARTAMENTO")
@Data
@Builder
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

    @JsonIgnore
    @OneToMany( mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<Municipio> municipios;

    @JsonIgnore
    @OneToMany( mappedBy = "departamento", fetch = FetchType.LAZY)
    private List<DepartamentoUsuario> departamentoUsuarios;

}
