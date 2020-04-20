package bo.com.reportate.repository;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.enums.EstadoEnum;
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
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    Optional<Familia> findFirstByUsuarioIdAndEstadoOrderByIdDesc(Long userId, EstadoEnum estadoEnum);
    Optional<Familia> findFirstByIdAndAndEstadoOrderByIdDesc(Long familiaId, EstadoEnum estadoEnum);
    boolean existsByNombreIgnoreCaseAndEstado(String nombre, EstadoEnum estadoEnum);
    @Query("SELECT distinct (f) FROM Paciente  p INNER JOIN p.familia f " +
            "WHERE p.id = :pacienteId " +
            "AND p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND f.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    Optional<Familia> getFamilia(@Param("pacienteId") Long pacienteId);

    @Modifying
    @Query(" UPDATE Familia f set f.estado= bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE f.id=:familiaId")
    void eliminarFamilia(@Param("familiaId") Long familiaId);

    @Query("SELECT  p "+
            "FROM Paciente p INNER join p.familia f "+
            "WHERE  p <>:paciente " +
            "AND f=:familia")
    List<PacienteDto> listarFamiliaByPaciente(@Param("paciente") Paciente paciente, @Param("familia") Familia familia);
}
