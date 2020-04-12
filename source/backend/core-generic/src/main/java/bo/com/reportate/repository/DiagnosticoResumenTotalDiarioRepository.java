package bo.com.reportate.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.DiagnosticosResumenDiario;
import bo.com.reportate.model.DiagnosticosResumenTotalDiario;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.response.ItemDto;
import bo.com.reportate.model.dto.response.ResumenDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;

public interface DiagnosticoResumenTotalDiarioRepository extends JpaRepository<DiagnosticosResumenTotalDiario, Long> , PagingAndSortingRepository<DiagnosticosResumenTotalDiario, Long>{
	@Query("SELECT d " +
            "FROM DiagnosticosResumenTotalDiario d " +
            "WHERE CAST(d.createdDate AS date) = CAST(:fechaRegistro AS date) "
            + "AND d.departamento = :departamento "
            + "AND d.municipio = :municipio "
            + "AND d.centroSalud = :centroSalud "
            + "AND d.enfermedad = :enfermedad")
	DiagnosticosResumenTotalDiario buscarPorFiltros(
            @Param("fechaRegistro") Date fechaRegistro,
            @Param("departamento") Departamento departamento,
            @Param("municipio") Municipio municipios,
            @Param("centroSalud") CentroSalud centrosSalud,
            @Param("enfermedad") Enfermedad enfermedad);
	
    @Query("SELECT new bo.com.reportate.model.dto.response.ResumenDto(cast(t.createdDate as date) as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.negativo) AS negativo, "
    		+ "SUM(t.confirmado) AS confirmado, "
    		+ "SUM(t.curado) AS curado, "
    		+ "SUM(t.fallecido) AS fallecido) "
            + "FROM DiagnosticosResumenTotalDiario t WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY t.createdDate "
            + "ORDER BY t.createdDate")
    List<ResumenDto> listarPorRangoFechas(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
    @Query("SELECT new bo.com.reportate.model.dto.response.ItemDto(d.nombre as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.negativo) AS negativo, "
    		+ "SUM(t.confirmado) AS confirmado, "
    		+ "SUM(t.curado) AS curado, "
    		+ "SUM(t.fallecido) AS fallecido) "
            + "FROM DiagnosticosResumenTotalDiario t INNER JOIN t.departamento d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY confirmado DESC")
    List<ItemDto> listarPorRangoFechasDepartamento(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
    @Query("SELECT new bo.com.reportate.model.dto.response.ItemDto(d.nombre as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.negativo) AS negativo, "
    		+ "SUM(t.confirmado) AS confirmado, "
    		+ "SUM(t.curado) AS curado, "
    		+ "SUM(t.fallecido) AS fallecido) "
            + "FROM DiagnosticosResumenTotalDiario t INNER JOIN t.municipio d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY confirmado DESC")
    List<ItemDto> listarPorRangoFechasMunicipio(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
    @Query("SELECT new bo.com.reportate.model.dto.response.ItemDto(d.nombre as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.negativo) AS negativo, "
    		+ "SUM(t.confirmado) AS confirmado, "
    		+ "SUM(t.curado) AS curado, "
    		+ "SUM(t.fallecido) AS fallecido) "
            + "FROM DiagnosticosResumenTotalDiario t INNER JOIN t.centroSalud d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY confirmado DESC")
    List<ItemDto> listarPorRangoFechasCentroSalud(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
}
