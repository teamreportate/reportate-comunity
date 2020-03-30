package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoRol;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "MU_ROL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_ROL_ID_GENERATOR", sequenceName = "SEQ_MU_ROL_ID", allocationSize = 1)
public class MuRol extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_ROL_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_ROL", nullable = false)
    private EstadoRol estadoRol;
    @JsonIgnore
    @OneToMany( mappedBy = "idRol", fetch = FetchType.LAZY)
    private List<MuGrupoRol> grupoRols;

    @JsonIgnore
    @OneToMany( mappedBy = "idRol", fetch = FetchType.LAZY)
    private List<MuPermiso> muPermisos;

}
