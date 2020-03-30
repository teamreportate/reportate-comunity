package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MU_USUARIO_GRUPO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_USUARIO_GRUPO_ID_GENERATOR", sequenceName = "SEQ_MU_USUARIO_GRUPO_ID", allocationSize = 1)
public class MuUsuarioGrupo extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_USUARIO_GRUPO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuUsuario idUsuario;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuGrupo idGrupo;


}
