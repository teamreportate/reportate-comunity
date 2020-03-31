package bo.com.reportate.repository;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {
    List<CentroSalud> findByMunicipioIdOrderByIdDesc(Long idMunicipio);
    boolean existsByMunicipioAndNombreIgnoreCase(Municipio municipio, String nombre);
}
