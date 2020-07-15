package bo.com.reportate.repository;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.response.FamiliaResponse;
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
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    @Query(" SELECT f " +
            "FROM Familia f INNER JOIN FETCH f.departamento d INNER JOIN FETCH f.municipio m INNER JOIN FETCH f.pacientes p INNER JOIN FETCH p.controlDiarios cd" +
            " WHERE f.usuario=:user " +
            "AND f.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND d.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO  " +
            "AND m.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND cd.primerControl = true" )
    Optional<FamiliaResponse> getInfo(@Param("user")MuUsuario muUsuario);

    Optional<Familia> findFirstByUsuarioIdAndEstadoOrderByIdDesc(Long userId, EstadoEnum estadoEnum);
    @Query("SELECT distinct (f) FROM Paciente  p INNER JOIN p.familia f " +
            "WHERE p.id = :pacienteId " +
            "AND p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND f.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    Optional<Familia> getFamilia(@Param("pacienteId") Long pacienteId);
    @Modifying
    @Query(" UPDATE Familia f set f.estado= bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE f.id=:familiaId")
    void eliminarFamilia(@Param("familiaId") Long familiaId);

}
