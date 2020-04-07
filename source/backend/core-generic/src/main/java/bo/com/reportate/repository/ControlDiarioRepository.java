package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.service.EmailService;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface ControlDiarioRepository extends JpaRepository<ControlDiario, Long> {
    boolean existsByPrimerControlTrueAndPaciente(Paciente paciente);
    Optional<ControlDiario> findByPrimerControlTrueAndPacienteAndEstado(Paciente paciente, EstadoEnum estadoEnum);
}
