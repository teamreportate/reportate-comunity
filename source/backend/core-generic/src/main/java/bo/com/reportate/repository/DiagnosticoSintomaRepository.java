package bo.com.reportate.repository;

import bo.com.reportate.model.Diagnostico;
import bo.com.reportate.model.DiagnosticoSintoma;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-09
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface DiagnosticoSintomaRepository extends JpaRepository<DiagnosticoSintoma, Long> {

    @Query("SELECT  new bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse(s.nombre, ds.valoracion) " +
            "FROM DiagnosticoSintoma  ds INNER JOIN ds.diagnostico d INNER JOIN ds.sintoma s " +
            "WHERE d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND ds.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND s.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND d =:diagnostico")
    List<DiagnosticoSintomaResponse> listarSintomas(@Param("diagnostico")Diagnostico diagnostico);
}
