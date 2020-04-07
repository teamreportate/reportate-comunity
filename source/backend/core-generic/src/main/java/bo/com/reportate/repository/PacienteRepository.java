package bo.com.reportate.repository;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndNombreIgnoreCaseAndEstado(Familia familia, String nombre, EstadoEnum estadoEnum);
    boolean existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(Familia familia,Long id, String nombre, EstadoEnum estadoEnum);

    List<PacienteDto> findByFamiliaAndIdNotAndEstado(Familia familia, Long pacienteId, EstadoEnum  estado);
    @Query("SELECT new bo.com.reportate.model.dto.PacienteDto(p) " +
            "FROM Paciente  p INNER JOIN p.familia f " +
            "WHERE f =:familia AND p.id<>:pacienteId AND p.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<PacienteDto> listarPacienteByFamilia(@Param("familia") Familia familia, @Param("pacienteId") Long pacienteId);
}