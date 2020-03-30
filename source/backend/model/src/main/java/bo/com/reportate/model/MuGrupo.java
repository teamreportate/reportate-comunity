package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoGrupo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "MU_GRUPO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_GRUPO_ID_GENERATOR", sequenceName = "SEQ_MU_GRUPO_ID", allocationSize = 1)
public class MuGrupo extends AbstractAuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_GRUPO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_GRUPO", nullable = false)
    private EstadoGrupo estadoGrupo;
    @JsonIgnore
    @OneToMany( mappedBy = "idGrupo", fetch = FetchType.LAZY)
    private List<MuUsuarioGrupo> muUsuarioGrupos;

}
