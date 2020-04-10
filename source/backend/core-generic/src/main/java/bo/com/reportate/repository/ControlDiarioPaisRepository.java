package bo.com.reportate.repository;

import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface ControlDiarioPaisRepository extends JpaRepository<ControlDiarioPais, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.PaisVisitadoDto(cdp) " +
            "FROM ControlDiarioPais cdp INNER JOIN cdp.controlDiario cd "+
            "WHERE cd.paciente=:paciente AND cd.paciente.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" )
    List<PaisVisitadoDto> listarPaisesVisitados(@Param("paciente") Paciente paciente);
    boolean existsByControlDiarioAndPaisAndEstado(ControlDiario controlDiario, Pais pais, EstadoEnum estadoEnum);

    @Modifying
    @Query( " UPDATE ControlDiarioPais  cdp " +
            "SET cdp.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE cdp.pais =:pais AND cdp.controlDiario =:controlDiario ")
    void eliminarPais(@Param("pais") Pais pais, @Param("controlDiario") ControlDiario controlDiario);

    Optional<ControlDiarioPais> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
}
