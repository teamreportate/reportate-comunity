package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.ControlDiarioPais;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
public interface ControlDiarioPaisRepository extends JpaRepository<ControlDiarioPais, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.PaisVisitadoDto(cdp) " +
            "FROM ControlDiarioPais cdp INNER JOIN cdp.controlDiario cd "+
            "WHERE cd.paciente=:paciente " +
            "AND cd.paciente.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND cdp.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND cdp.pais.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" )
    List<PaisVisitadoDto> listarPaisesVisitados(@Param("paciente") Paciente paciente);

    boolean existsByControlDiarioAndPaisAndEstado(ControlDiario controlDiario, Pais pais, EstadoEnum estadoEnum);
    boolean existsByControlDiarioPacienteAndEstado(Paciente paciente, EstadoEnum estadoEnum);

    @Modifying
    @Query( " UPDATE ControlDiarioPais  cdp " +
            "SET cdp.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE cdp.pais =:pais AND cdp.controlDiario =:controlDiario ")
    void eliminarPais(@Param("pais") Pais pais, @Param("controlDiario") ControlDiario controlDiario);

    Optional<ControlDiarioPais> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
}
