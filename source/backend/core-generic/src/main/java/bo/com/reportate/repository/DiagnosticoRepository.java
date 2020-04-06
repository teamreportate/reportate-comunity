package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Diagnostico;
import bo.com.reportate.model.Enfermedad;

import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.NivelValoracionDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.GeneroEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> , PagingAndSortingRepository<Diagnostico, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponseDto(d) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f  " +
            " WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin AND d.departamento IN (:departamentos) " +
            " AND d.municipio in (:municipios) AND d.centroSalud IN (:centrosSalud)" +
            " AND d.estadoDiagnostico IN (:diagnosticos) " +
            " AND enf IN (:enfermedades) " +
            " AND LOWER(p.nombre) LIKE :nombre ORDER BY d.id DESC")
    Page<DiagnosticoResponseDto> listarDiagnostico(
            @Param("fechaInicio") Date date,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("diagnosticos") List<EstadoDiagnosticoEnum> diagnosticos,
            @Param("enfermedades") List<Enfermedad> enfermedades,
            @Param("nombre") String nombre,
            Pageable pageable);

    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponseDto(d) "+
            "FROM Diagnostico d INNER  JOIN  d.controlDiario cd "+
            "WHERE cd.paciente=:paciente")
    List<DiagnosticoResponseDto> listarDiagnosticoByPaciente(@Param("paciente") Paciente paciente);



    @Query("SELECT count(1) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f INNER JOIN f.departamento dep INNER JOIN f.municipio mun " +
            "WHERE d.resultadoValoracion >= :valoracionInicio AND d.resultadoValoracion <= :valoracionFin AND "
            + "(:genero IS NULL OR p.genero = :genero) AND "
            + "((:edadInicial IS NULL OR p.edad >= :edadInicial) AND (:edadFinal IS NULL OR p.edad <= :edadFinal)) AND "
            + "((:departamento IS NULL OR :departamento = dep) AND (:municipio IS NULL OR :municipio = mun))")
    Integer cantidadDiagnosticoPorResultadoValoracion(
            @Param("valoracionInicio") BigDecimal valoracionInicio,
            @Param("valoracionFin") BigDecimal valoracionFin,
            @Param("departamento")Departamento departamento,
            @Param("municipio")Municipio municipio,
            @Param("genero")GeneroEnum genero,
            @Param("edadInicial")Integer edadInicial,
            @Param("edadFinal")Integer edadFinal);

    @Query("SELECT new bo.com.reportate.model.dto.response.NivelValoracionDto(cast(t.createdDate as date) as registrado,"
            + " (select count(d1) from Diagnostico d1 where d1.resultadoValoracion >= 6 AND d1.resultadoValoracion <= 12 and cast(d1.createdDate as date) = cast(t.createdDate as date)) as alto, "
            + " (select count(d2) from Diagnostico d2 where d2.resultadoValoracion >= 3 AND d2.resultadoValoracion <= 5 and cast(d2.createdDate as date) = cast(t.createdDate as date)) as medio, "
            + " (select count(d3) from Diagnostico d3 where d3.resultadoValoracion >= 0 AND d3.resultadoValoracion <= 2 and cast(d3.createdDate as date) = cast(t.createdDate as date)) as bajo) "
            + " FROM Diagnostico t WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin"
            + " GROUP BY t.createdDate"
            + " ORDER BY t.createdDate")
    List<NivelValoracionDto> listarPorNivelValoracion(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to);
}
