package bo.com.reportate.repository;

import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.response.DiagnosticoResponseDto;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

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

}
