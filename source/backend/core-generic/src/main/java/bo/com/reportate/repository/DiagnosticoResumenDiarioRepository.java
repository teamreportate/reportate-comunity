package bo.com.reportate.repository;

import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.response.ItemDto;
import bo.com.reportate.model.dto.response.ResumenDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DiagnosticoResumenDiarioRepository extends JpaRepository<DiagnosticosResumenDiario, Long> , PagingAndSortingRepository<DiagnosticosResumenDiario, Long>{
	@Query("SELECT d " +
            "FROM DiagnosticosResumenDiario d " +
            "WHERE CAST(d.createdDate AS date) = CAST(:fechaRegistro AS date) "
            + "AND d.departamento = :departamento "
            + "AND d.municipio = :municipio "
            + "AND d.centroSalud = :centroSalud "
            + "AND d.enfermedad = :enfermedad")
	DiagnosticosResumenDiario buscarPorFiltros(
            @Param("fechaRegistro") Date fechaRegistro,
            @Param("departamento") Departamento departamento,
            @Param("municipio") Municipio municipios,
            @Param("centroSalud") CentroSalud centrosSalud,
            @Param("enfermedad") Enfermedad enfermedad);
	
    @Query("SELECT new bo.com.reportate.model.dto.response.ResumenDto(cast(t.createdDate as date) as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.descartado) AS descartado, "
    		+ "SUM(t.positivo) AS positivo, "
    		+ "SUM(t.recuperado) AS recuperado, "
    		+ "SUM(t.deceso) AS deceso) "
            + "FROM DiagnosticosResumenDiario t WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
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
    		+ "SUM(t.descartado) AS descartado, "
    		+ "SUM(t.positivo) AS positivo, "
    		+ "SUM(t.recuperado) AS recuperado, "
    		+ "SUM(t.deceso) AS deceso) "
            + "FROM DiagnosticosResumenDiario t INNER JOIN t.departamento d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY positivo DESC")
    List<ItemDto> listarPorRangoFechasDepartamento(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
    @Query("SELECT new bo.com.reportate.model.dto.response.ItemDto(d.nombre as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.descartado) AS descartado, "
    		+ "SUM(t.positivo) AS positivo, "
    		+ "SUM(t.recuperado) AS recuperado, "
    		+ "SUM(t.deceso) AS deceso) "
            + "FROM DiagnosticosResumenDiario t INNER JOIN t.municipio d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY positivo DESC")
    List<ItemDto> listarPorRangoFechasMunicipio(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
    @Query("SELECT new bo.com.reportate.model.dto.response.ItemDto(d.nombre as nombreGrafico, "
    		+ "SUM(t.sospechoso) AS sospechoso, "
    		+ "SUM(t.descartado) AS descartado, "
    		+ "SUM(t.positivo) AS positivo, "
    		+ "SUM(t.recuperado) AS recuperado, "
    		+ "SUM(t.deceso) AS deceso) "
            + "FROM DiagnosticosResumenDiario t INNER JOIN t.centroSalud d WHERE t.createdDate BETWEEN :fechaInicio AND :fechaFin AND "
            + "t.departamento IN (:departamentos) AND "
            + "t.municipio in (:municipios) AND "
            + "t.centroSalud IN (:centrosSalud) AND "
            + "t.enfermedad IN (:enfermedades) "
            + "GROUP BY d.nombre "
            + "ORDER BY positivo DESC")
    List<ItemDto> listarPorRangoFechasCentroSalud(
            @Param("fechaInicio") Date from,
            @Param("fechaFin") Date to,
            @Param("departamentos") List<Departamento> departamentos,
            @Param("municipios") List<Municipio> municipios,
            @Param("centrosSalud") List<CentroSalud> centrosSalud,
            @Param("enfermedades") List<Enfermedad> enfermedades);
    
}
