package bo.com.reportate.repository;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.MatrizDiagnostico;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-03
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface MatrizDiagnosticoRepository extends JpaRepository<MatrizDiagnostico, Long> {
    @Query("SELECT distinct (e) FROM MatrizDiagnostico m INNER JOIN m.enfermedad e " +
            "WHERE m.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND e.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    List<Enfermedad> listarEnfermedades();

    List<MatrizDiagnostico> findByEnfermedadAndEstado(Enfermedad enfermedad, EstadoEnum estadoEnum);
}
