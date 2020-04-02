package bo.com.reportate.repository;

import bo.com.reportate.model.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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
    @Query(" SELECT s FROM Sintoma s" +
            " order by s.id desc")
    List<Sintoma> listAll();

}
