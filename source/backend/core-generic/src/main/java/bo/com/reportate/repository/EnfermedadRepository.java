package bo.com.reportate.repository;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.EnfermedadDto;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
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
public interface EnfermedadRepository extends JpaRepository<Enfermedad, Long> {
    Optional<Enfermedad> findByIdAndEstado(Long id, EstadoEnum activo);
    @Query("SELECT new bo.com.reportate.model.dto.response.EnfermedadResponse(e) From Enfermedad e WHERE e.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<EnfermedadResponse> listarEnfermedadesActivos();

    List<EnfermedadResponse> findByEnfermedadBaseFalseAndEstadoOrderByNombreAsc(EstadoEnum estadoEnum);
    List<EnfermedadResponse> findByEnfermedadBaseTrueAndEstadoOrderByNombreAsc(EstadoEnum estadoEnum);
    List<EnfermedadDto> findByEstadoOrderByNombreAsc(EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.response.EnfermedadResponse(e) " +
            "From Enfermedad e " +
            "WHERE e.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND e.enfermedadBase = true")
    List<EnfermedadResponse> listarEnfermedadesBaseActivos();

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndEstado(String nombre, EstadoEnum estadoEnum);
    boolean existsByIdNotAndNombreIgnoreCaseAndEstado(Long id, String nombre, EstadoEnum estadoEnum);

    Optional<Enfermedad> findByNombreAndEstado(String nombre, EstadoEnum estadoEnum);

    @Query(" SELECT e FROM Enfermedad e" +
            " order by e.id desc")
    List<Enfermedad> listAll();

    List<Enfermedad> findByEnfermedadBaseFalseAndEstado(EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE Enfermedad set estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE id=:enfermedadId")
    void eliminar(@Param("enfermedadId") Long id);
}
