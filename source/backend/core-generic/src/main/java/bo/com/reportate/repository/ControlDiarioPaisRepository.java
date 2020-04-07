package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.ControlDiarioPais;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
public interface ControlDiarioPaisRepository extends JpaRepository<ControlDiarioPais, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.PaisVisitadoDto(cdp) " +
            "FROM ControlDiarioPais cdp INNER JOIN cdp.controlDiario cd "+
            "WHERE cd.paciente=:paciente AND cd.paciente.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" )
    List<PaisVisitadoDto> listarPaisesVisitados(@Param("paciente") Paciente paciente);

    boolean existsByControlDiarioAndPaisAndEstado(ControlDiario controlDiario, Pais pais, EstadoEnum estadoEnum);
}
