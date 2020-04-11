package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.ControlDiarioSintoma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface ControlDiarioSintomaRepository extends JpaRepository<ControlDiarioSintoma, Long> {
    List<ControlDiarioSintoma> findByControlDiario(ControlDiario controlDiario);
}
