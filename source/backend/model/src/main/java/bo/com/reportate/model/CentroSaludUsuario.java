package bo.com.reportate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
@Table(name = "CENTRO_SALUD_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "CENT_SALU_USUARIO_ID_GENERATOR", sequenceName = "SEQ_CENT_SALU_USUARIO_ID", allocationSize = 1)
public class CentroSaludUsuario extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CENT_SALU_USUARIO_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MuUsuario muUsuario;

    @JoinColumn(name = "ID_CENTRO_SALUD", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CentroSalud centroSalud;
}
