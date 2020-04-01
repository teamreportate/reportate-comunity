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
    @Query("SELECT new bo.com.reportate.model.dto.MunicipioUsuarioDto(mu.municipio) " +
            "from MunicipioUsuario mu left join mu.muUsuario u " +
            "where u.username =:username " +
            "AND mu.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<MunicipioUsuarioDto> listarMunicipiosAsignados(@Param("username") String username);

    @Query("SELECT new bo.com.reportate.model.dto.MunicipioUsuarioDto(mu) " +
            "from Municipio mu WHERE mu NOT IN (SELECT mud FROM MunicipioUsuario mud INNER JOIN mud.muUsuario u " +
            " WHERE u.username =:username " +
            " AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            " AND mud.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO) AND mu.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<MunicipioUsuarioDto> listarMunicipiosNoAsignados(@Param("username") String username);

    @Query(" UPDATE MunicipioUsuario du SET du.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE du.muUsuario =:usuario AND du.municipio.id NOT IN(:municipios)")
    void eliminaMunicipiosNoAsignados(@Param("usuario") MuUsuario muUsuario, @Param("municipios") List<Long> municipiosIds);
}
