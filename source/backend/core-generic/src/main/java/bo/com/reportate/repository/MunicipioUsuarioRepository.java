package bo.com.reportate.repository;

import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.MunicipioUsuario;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
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
public interface MunicipioUsuarioRepository extends JpaRepository<MunicipioUsuario, Long> {
    boolean existsByMuUsuarioAndMunicipioAndEstado(MuUsuario muUsuario, Municipio municipio, EstadoEnum estadoEnum);
    @Query("SELECT new bo.com.reportate.model.dto.MunicipioUsuarioDto(d) " +
            "from Municipio d left join d.municipioUsuarios du where du.muUsuario.username =:username")
    List<MunicipioUsuarioDto> listarMunicipiosAsignados(@Param("username") String username);
}
