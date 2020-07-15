package bo.com.reportate.repository;

import bo.com.reportate.model.DiagnosticosResumenDiario;
import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.response.PacienteResponse;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndNombreIgnoreCaseAndEstado(Familia familia, String nombre, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(Familia familia,Long id, String nombre, EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.PacienteDto(p) " +
            "FROM Paciente  p INNER JOIN p.familia f " +
            "WHERE f =:familia AND p.id<>:pacienteId AND p.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<PacienteDto> listarPacienteByFamilia(@Param("familia") Familia familia, @Param("pacienteId") Long pacienteId);
    
    
    @Query("SELECT new bo.com.reportate.model.DiagnosticosResumenDiario( "
    		+ "d.enfermedad, d.departamento, d.municipio, d.centroSalud, "
            + "(select count(d1) from Paciente t1 INNER JOIN t1.diagnostico d1 where d1.createdDate BETWEEN :fechaInicio AND :fechaFin AND d1.estadoDiagnostico = :sospechoso AND d.enfermedad = d1.enfermedad AND d.departamento = d1.departamento AND d.municipio = d1.municipio AND d.centroSalud = d1.centroSalud) AS sospechoso, "
            + "(select count(d2) from Paciente t2 INNER JOIN t2.diagnostico d2 where d2.createdDate BETWEEN :fechaInicio AND :fechaFin AND d2.estadoDiagnostico = :descartado AND d.enfermedad = d2.enfermedad AND d.departamento = d2.departamento AND d.municipio = d2.municipio AND d.centroSalud = d2.centroSalud) AS descartado, "
            + "(select count(d3) from Paciente t3 INNER JOIN t3.diagnostico d3 where d3.createdDate BETWEEN :fechaInicio AND :fechaFin AND d3.estadoDiagnostico = :positivo AND d.enfermedad = d3.enfermedad AND d.departamento = d3.departamento AND d.municipio = d3.municipio AND d.centroSalud = d3.centroSalud) AS positivo, "
            + "(select count(d4) from Paciente t4 INNER JOIN t4.diagnostico d4 where d4.createdDate BETWEEN :fechaInicio AND :fechaFin AND d4.estadoDiagnostico = :recuperado AND d.enfermedad = d4.enfermedad AND d.departamento = d4.departamento AND d.municipio = d4.municipio AND d.centroSalud = d4.centroSalud) AS recuperado, "
            + "(select count(d5) from Paciente t5 INNER JOIN t5.diagnostico d5 where d5.createdDate BETWEEN :fechaInicio AND :fechaFin AND d5.estadoDiagnostico = :deceso AND d.enfermedad = d5.enfermedad AND d.departamento = d5.departamento AND d.municipio = d5.municipio AND d.centroSalud = d5.centroSalud) AS deceso) "
            + "FROM Paciente t INNER JOIN t.diagnostico d WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin "
            + "GROUP BY d.enfermedad, d.departamento, d.municipio, d.centroSalud "
            + "ORDER BY d.enfermedad, d.departamento, d.municipio, d.centroSalud")
    
    List<DiagnosticosResumenDiario> resumenPorPacienteEstadoDiagnostico(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("sospechoso") EstadoDiagnosticoEnum sospechoso,
            @Param("descartado") EstadoDiagnosticoEnum descartado,
            @Param("positivo") EstadoDiagnosticoEnum positivo,
            @Param("recuperado") EstadoDiagnosticoEnum recuperado,
            @Param("deceso") EstadoDiagnosticoEnum deceso);

    @Modifying
    @Query(" UPDATE Paciente p SET p.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE p.id =:contactoId")
    void eliminarContacto(@Param("contactoId") Long contactoId);



}
