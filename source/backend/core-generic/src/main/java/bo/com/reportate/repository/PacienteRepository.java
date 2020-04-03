package bo.com.reportate.repository;

import bo.com.reportate.model.Familia;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
