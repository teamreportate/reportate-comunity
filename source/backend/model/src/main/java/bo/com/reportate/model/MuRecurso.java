package bo.com.reportate.model;

import bo.com.reportate.model.enums.EstadoEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MU_RECURSO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SequenceGenerator(name = "MU_RECURSO_ID_GENERATOR", sequenceName = "SEQ_MU_RECURSO_ID", allocationSize = 1)
public class MuRecurso extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_RECURSO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "ID_REC_PADRE", referencedColumnName = "ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private MuRecurso idRecursoPadre;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "ICON", nullable = false)
    private String icon;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ORDEN_MENU", nullable = false)
    private Integer ordenMenu;

    @Column(name = "COMPONENTE_FRONT")
    private String componenteFront;

    @OneToMany( mappedBy = "idRecurso", fetch = FetchType.LAZY)
    private List<MuPermiso> permisos;

    @Transient
    private List<MuRecurso> listaRecursos;

    public MuRecurso(String nombre, String descripcion, String url, int ordenMenu, String icon, EstadoEnum estado){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.ordenMenu = ordenMenu;
        this.icon = icon;
        this.estado = estado;
    }

}
