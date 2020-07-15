package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :18/12/2019
 * Project       :reportate
 * Package       :bo.com.Reportate.reportate.model
 **/
@Entity
@Table(name = "MU_GRUPO_PARAMETRO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_GRUPO_PARAMETRO_ID_GENERATOR", sequenceName = "SEQ_MU_GRUPO_PARAMETRO_ID", allocationSize = 1)
public class MuGrupoParametro extends AbstractAuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_GRUPO_PARAMETRO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "GRUPO", nullable = false)
    private String grupo;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
}
