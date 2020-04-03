package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Diagnostico;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.response.DiagnosticoResponse;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponse(d) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f INNER JOIN f.departamento dep " +
            " WHERE d.createdDate between :from AND :to AND dep =:departamento AND d.estadoDiagnostico=:diagnostico AND enf=:enfermedad")
    Page<DiagnosticoResponse> listarDiagnostico(
            @Param("from") Date date,
            @Param("to") Date to,
            @Param("departamento")Departamento departamento,
            @Param("diagnostico")EstadoDiagnosticoEnum estado,
            @Param("enfermedad")Enfermedad enfermedad, Pageable pageable);
}
