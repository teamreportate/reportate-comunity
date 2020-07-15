package bo.com.reportate.repository;

import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.PaisDto;
import bo.com.reportate.model.dto.response.PaisResponse;
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
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface PaisRepository extends JpaRepository<Pais, Long> {

    List<PaisDto> findByEstadoOrderByNombreAsc(EstadoEnum estadoEnum);

    Optional<Pais> findByIdAndEstado(Long id, EstadoEnum activo);
    @Query("SELECT new bo.com.reportate.model.dto.response.PaisResponse(p) FROM Pais p WHERE p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<PaisResponse> listarPaises();

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCaseAndEstado(String nombre, EstadoEnum estadoEnum);
    boolean existsByIdNotAndNombreIgnoreCaseAndEstado(Long id, String nombre, EstadoEnum estadoEnum);

    @Modifying
    @Query("UPDATE Pais set estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE id=:paisId")
    void eliminar(@Param("paisId") Long id);
}
