package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Diagnostico;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
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
            "INNER JOIN cd.paciente p INNER JOIN p.familia f INNER JOIN f.departamento dep " +
            " WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin AND dep=:departamento AND d.estadoDiagnostico=:diagnostico AND enf=:enfermedad ORDER BY d.id DESC")
    Page<DiagnosticoResponseDto> listarDiagnostico(
            @Param("fechaInicio") Date date,
            @Param("fechaFin") Date to,
            @Param("departamento")Departamento departamento,
            @Param("diagnostico")EstadoDiagnosticoEnum diagnostico,
            @Param("enfermedad")Enfermedad enfermedad, Pageable pageable);
    //selecciona por resultado valoracion, sexo, rango de edad, departamento y municipio
    @Query("SELECT count(1) " +
    		"FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f INNER JOIN f.departamento dep INNER JOIN f.municipio mun " +
            "WHERE d.resultadoValoracion >= :valoracionInicio AND d.resultadoValoracion <= :valoracionFin AND "
            + "(:genero IS NULL OR p.genero = :genero) AND "
            + "((:edadInicial IS NULL OR p.edad >= :edadInicial) AND (:edadFinal IS NULL OR p.edad <= :edadFinal)) AND "
            + "((:departamento IS NULL OR :departamento = dep) AND (:municipio IS NULL OR :municipio = mun))")
    Integer countDiagnosticoByResultadoValoracion(
            @Param("valoracionInicio") BigDecimal valoracionInicio,
            @Param("valoracionFin") BigDecimal valoracionFin,
            @Param("departamento")Departamento departamento,
            @Param("municipio")Municipio municipio,
            @Param("genero")GeneroEnum genero,
            @Param("edadInicial")Integer edadInicial,
            @Param("edadFinal")Integer edadFinal);
}
