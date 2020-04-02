package bo.com.reportate.repository;

import bo.com.reportate.model.Pais;
import bo.com.reportate.model.dto.response.PaisResponse;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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
public interface PaisRepository extends JpaRepository<Pais, Long> {
    @Query(" SELECT p FROM Pais p" +
            " order by p.id desc")
    List<Pais> listAllActivos();

    Optional<Pais> findByIdAndEstado(Long id, EstadoEnum activo);
    @Query("SELECT new bo.com.reportate.model.dto.response.PaisResponse(p) FROM Pais p WHERE p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<PaisResponse> listarPaises();

    boolean existsByNombreIgnoreCase(String nombre);
}
