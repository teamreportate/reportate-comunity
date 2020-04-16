package bo.com.reportate.repository;

import bo.com.reportate.model.Sintoma;
import bo.com.reportate.model.dto.SintomaDto;
import bo.com.reportate.model.dto.response.SintomaResponse;
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
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {
    List<SintomaDto> findByEstadoOrderByNombreDesc(EstadoEnum estadoEnum);
    Optional<Sintoma> findByIdAndEstado(Long id, EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.response.SintomaResponse(s) FROM Sintoma s where s.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO AND s.controlDiario = true")
    List<SintomaResponse> findByEstadoAndControlDiarioTrue();
    boolean existsByNombreIgnoreCaseAndEstado(String nombre, EstadoEnum estadoEnum);
    boolean existsByIdNotAndNombreIgnoreCaseAndEstado(Long id, String nombre, EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE Sintoma set estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE id=:sintomaId")
    void eliminar(@Param("sintomaId") Long id);
}
