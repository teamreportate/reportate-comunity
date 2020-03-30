package bo.com.reportate.repository;

import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.dto.DominioDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DominioRepository extends JpaRepository<MuDominio, Long> {
    List<DominioDto> findAllByCodigo(String codigo);
    Optional<MuDominio> findByCodigo(String codigo);
    List<DominioDto> findAllByOrderByCodigoAsc();
}
