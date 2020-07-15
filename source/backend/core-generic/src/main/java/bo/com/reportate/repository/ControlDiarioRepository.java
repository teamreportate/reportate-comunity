package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.domain.Pageable;
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
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface ControlDiarioRepository extends JpaRepository<ControlDiario, Long> {
    boolean existsByPrimerControlTrueAndPaciente(Paciente paciente);
    Optional<ControlDiario> findByPrimerControlTrueAndPacienteAndEstado(Paciente paciente, EstadoEnum estadoEnum);

    @Query(" SELECT cd  FROM ControlDiario cd INNER JOIN cd.paciente  p " +
            " WHERE cd.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND p=:paciente ORDER BY cd.id DESC ")
    List<ControlDiario> listarControlDiario(@Param("paciente") Paciente paciente, Pageable pageable);

    Integer countByPacienteAndCreatedDateBetween(Paciente paciente, Date from, Date to);

    @Modifying
    @Query(" UPDATE ControlDiario cd SET cd.recomendacion =:recomendacion WHERE cd=:controlDiario")
    void agregarRecomendacion(@Param("controlDiario") ControlDiario controlDiario, @Param("recomendacion") String recomendacion);
}
