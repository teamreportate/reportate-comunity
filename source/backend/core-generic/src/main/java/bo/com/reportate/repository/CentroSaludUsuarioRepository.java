package bo.com.reportate.repository;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.CentroSaludUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
public interface CentroSaludUsuarioRepository extends JpaRepository<CentroSaludUsuario, Long> {
    boolean existsByMuUsuarioAndCentroSaludAndEstado(MuUsuario muUsuario, CentroSalud centroSalud, EstadoEnum estadoEnum);
    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(d) " +
            "from CentroSalud d left join d.centroSaludUsuarios du where du.muUsuario.username =:username")
    List<CentroSaludUsuarioDto> listarCentrosSaludAsignados(@Param("username") String username);
}
