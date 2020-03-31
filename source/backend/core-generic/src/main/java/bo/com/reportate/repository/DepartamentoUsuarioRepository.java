package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.DepartamentoUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
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
public interface DepartamentoUsuarioRepository extends JpaRepository<DepartamentoUsuario, Long> {
    boolean existsByMuUsuarioAndDepartamentoAndEstado(MuUsuario muUsuario, Departamento departamento, EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(d) " +
            "from Departamento d left join d.departamentoUsuarios du where du.muUsuario.username =:username")
    List<DepartamentoUsuarioDto> listarDepartamentoAsignados(@Param("username") String username);
}
