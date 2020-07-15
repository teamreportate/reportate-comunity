package bo.com.reportate.repository;

import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.dto.response.MapResponse;
import bo.com.reportate.model.dto.response.NivelValoracionDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.GeneroEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> , PagingAndSortingRepository<Diagnostico, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponseDto(d) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f  " +
            " WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin AND d.departamento IN (:departamentos) " +
            " AND d.municipio in (:municipios) AND d.centroSalud IN (:centrosSalud)" +
            " AND d.estadoDiagnostico IN (:diagnosticos) " +
            " AND enf IN (:enfermedades) " +
            " AND LOWER(p.nombre) LIKE %:nombre% ORDER BY d.id DESC")
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

    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponseDto(d) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f  " +
            " WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin AND d.departamento IN (:departamentos) " +
            " AND d.municipio in (:municipios) AND d.centroSalud IN (:centrosSalud)" +
            " AND d.estadoDiagnostico IN (:diagnosticos) " +
            " AND enf IN (:enfermedades) " +
            " AND p.id=:codigo ORDER BY d.id DESC")
    Page<DiagnosticoResponseDto> listarDiagnosticoPorCodigo(
            @Param("fechaInicio") Date date,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("diagnosticos") List<EstadoDiagnosticoEnum> diagnosticos,
            @Param("enfermedades") List<Enfermedad> enfermedades,
            @Param("codigo") Long codigo,
            Pageable pageable);

    @Query("SELECT new bo.com.reportate.model.dto.response.DiagnosticoResponseDto(d) " +
            "FROM Diagnostico d INNER JOIN d.enfermedad enf INNER JOIN d.controlDiario cd " +
            "INNER JOIN cd.paciente p INNER JOIN p.familia f  " +
            " WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin AND d.departamento IN (:departamentos) " +
            " AND d.municipio in (:municipios) AND d.centroSalud IN (:centrosSalud)" +
            " AND d.estadoDiagnostico IN (:diagnosticos) " +
            " AND enf IN (:enfermedades) " +
            " ORDER BY d.id DESC")
    Page<DiagnosticoResponseDto> listarDiagnostico(
            @Param("fechaInicio") Date date,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("diagnosticos") List<EstadoDiagnosticoEnum> diagnosticos,
            @Param("enfermedades") List<Enfermedad> enfermedades,
            Pageable pageable);


    @Query("SELECT  new bo.com.reportate.model.dto.DiagnosticoDto(d) "+
            "FROM Diagnostico d INNER  JOIN  d.controlDiario cd "+
            "WHERE cd.paciente =:paciente " +
            "ORDER BY cd.createdDate DESC" )
    List<DiagnosticoDto> listarDiagnosticoByPaciente(@Param("paciente") Paciente paciente, Pageable pageable);


    @Query("SELECT count(1) " +
            "FROM Paciente p INNER JOIN p.diagnostico d INNER JOIN d.enfermedad enf " +
            "WHERE "
            + "p.genero IN :generos AND "
            + "((:edadInicial IS NULL OR p.edad >= :edadInicial) AND (:edadFinal IS NULL OR p.edad <= :edadFinal)) AND "
            + "d.departamento IN (:departamentos) AND "
            + "d.municipio in (:municipios) AND "
            + "d.centroSalud IN (:centrosSalud) AND "
            + "d.estadoDiagnostico IN (:diagnosticos) AND "
            + "enf IN (:enfermedades)")
    Integer cantidadDiagnosticoPorFiltros(
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("diagnosticos") List<EstadoDiagnosticoEnum> diagnosticos,
            @Param("enfermedades") List<Enfermedad> enfermedades,
            @Param("generos")List<GeneroEnum> generos,
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

    Optional<Diagnostico> findByIdAndEstado(Long id, EstadoEnum estadoEnum);


    @Query("SELECT  new bo.com.reportate.model.dto.response.MapResponse(p)  FROM Paciente p INNER JOIN p.diagnostico d INNER JOIN p.familia f " +
            "WHERE d.createdDate BETWEEN :fechaInicio AND :fechaFin ANd d.departamento in (:departamentos) AND d.municipio IN (:municipios) " +
            "AND d.centroSalud IN (:centrosSalud) AND d.estadoDiagnostico IN (:diagnosticos) " +
            "AND d.enfermedad IN (:enfermedades) ")
    List<MapResponse> listarPacientesParaMapa(
            @Param("fechaInicio") Date date,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("diagnosticos") List<EstadoDiagnosticoEnum> diagnosticos,
            @Param("enfermedades") List<Enfermedad> enfermedades);
}
