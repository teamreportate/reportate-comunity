package bo.com.reportate.model;

import bo.com.reportate.model.enums.LogLevel;
import bo.com.reportate.model.enums.Process;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "BITACORA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "MU_LOG_ID_GENERATOR", sequenceName = "SEQ_MU_LOG_ID", allocationSize = 1)
public class MuBitacora extends AbstractAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_LOG_ID_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "PROCESO", nullable = false)
    private Process proceso;
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_LOG", nullable = false)
    private LogLevel tipoLog;
    @Lob
    @Type(type = "text")
    @Column(name = "MENSAJE")
    private String mensaje;

    public MuBitacora(Process proceso, LogLevel tipoLog, String mensaje) {
        this.proceso = proceso;
        this.tipoLog = tipoLog;
        this.mensaje = mensaje;
    }
}
