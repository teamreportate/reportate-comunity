package bo.com.reportate.repository;

import bo.com.reportate.model.DiagnosticosResumenDiario;
import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndNombreIgnoreCaseAndEstado(Familia familia, String nombre, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(Familia familia,Long id, String nombre, EstadoEnum estadoEnum);

    List<PacienteDto> findByFamiliaAndIdNotAndEstado(Familia familia, Long pacienteId, EstadoEnum  estado);
    @Query("SELECT new bo.com.reportate.model.dto.PacienteDto(p) " +
            "FROM Paciente  p INNER JOIN p.familia f " +
            "WHERE f =:familia AND p.id<>:pacienteId AND p.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<PacienteDto> listarPacienteByFamilia(@Param("familia") Familia familia, @Param("pacienteId") Long pacienteId);
    
    
    @Query("SELECT new bo.com.reportate.model.DiagnosticosResumenDiario( "
    		+ "d.enfermedad, d.departamento, d.municipio, d.centroSalud, "
            + "(select count(d1) from Paciente t1 INNER JOIN t1.diagnostico d1 where d1.createdDate BETWEEN :fechaInicio AND :fechaFin AND d1.estadoDiagnostico = :sospechoso) AS sospechoso, "
            + "(select count(d2) from Paciente t2 INNER JOIN t2.diagnostico d2 where d2.createdDate BETWEEN :fechaInicio AND :fechaFin AND d2.estadoDiagnostico = :negativo) AS negativo, "
            + "(select count(d3) from Paciente t3 INNER JOIN t3.diagnostico d3 where d3.createdDate BETWEEN :fechaInicio AND :fechaFin AND  d3.estadoDiagnostico = :confirmado) AS confirmado, "
            + "(select count(d4) from Paciente t4 INNER JOIN t4.diagnostico d4 where d4.createdDate BETWEEN :fechaInicio AND :fechaFin AND d4.estadoDiagnostico = :curado) AS curado, "
            + "(select count(d5) from Paciente t5 INNER JOIN t5.diagnostico d5 where d5.createdDate BETWEEN :fechaInicio AND :fechaFin AND  d5.estadoDiagnostico = :fallecido) AS fallecido) "
            + "FROM Paciente t INNER JOIN t.diagnostico d WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin "
            + "GROUP BY d.enfermedad, d.departamento, d.municipio, d.centroSalud "
            + "ORDER BY d.enfermedad, d.departamento, d.municipio, d.centroSalud")
    
    List<DiagnosticosResumenDiario> resumenPorPacienteEstadoDiagnostico(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("sospechoso") EstadoDiagnosticoEnum sospechoso,
            @Param("negativo") EstadoDiagnosticoEnum negativo,
            @Param("confirmado") EstadoDiagnosticoEnum confirmado,
            @Param("curado") EstadoDiagnosticoEnum curado,
            @Param("fallecido") EstadoDiagnosticoEnum fallecido);
}