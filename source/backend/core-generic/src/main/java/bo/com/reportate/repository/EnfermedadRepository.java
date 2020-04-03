package bo.com.reportate.repository;

import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Enfermedad> findByNombreAndEstado(String nombre, EstadoEnum estadoEnum);

    @Query(" SELECT e FROM Enfermedad e" +
            " order by e.id desc")
    List<Enfermedad> listAll();
}
