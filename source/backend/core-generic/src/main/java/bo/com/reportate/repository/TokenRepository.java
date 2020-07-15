package bo.com.reportate.repository;

import bo.com.reportate.model.MuToken;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.TokenDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.repository
 * date:    12-09-19
 * author:  fmontero
 **/
public interface TokenRepository extends JpaRepository<MuToken, Long> {

    @Query("    SELECT new bo.com.reportate.model.dto.TokenDto(t) " +
            "   FROM MuToken t " +
            "   WHERE  t.idUsuario = :usuarioId " +
            "   order by t.estado asc "
    )
    List<TokenDto> obtenerListadoTokenUsuario(@Param("usuarioId") MuUsuario usuarioId);

    @Modifying
    @Query(" UPDATE MuToken t set t.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE  t.idUsuario=:usuario")
    void inhabilitarToken(@Param("usuario")MuUsuario authUsuario);

    void deleteByIdUsuario(MuUsuario muUsuario);

    boolean existsByEstadoAndToken(EstadoEnum estadoEnum, String token);

    @Modifying
    @Query(" UPDATE MuToken t set t.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE t.id =:tokenId")
    void inhabilitarToken(@Param("tokenId")Long tokenId);
}

