package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.ControlDiarioSintoma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface ControlDiarioSintomaRepository extends JpaRepository<ControlDiarioSintoma, Long> {
    List<ControlDiarioSintoma> findByControlDiario(ControlDiario controlDiario);
}
