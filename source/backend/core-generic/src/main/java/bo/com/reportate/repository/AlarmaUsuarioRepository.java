package bo.com.reportate.repository;

import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.MuAlarmaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.repository
 * date:    14-06-19
 * author:  fmontero
 **/
public interface AlarmaUsuarioRepository extends JpaRepository<MuAlarmaUsuario, Long> {

    @Query( "   SELECT ua " +
            "   FROM MuAlarmaUsuario ua " +
            "   WHERE ua.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "       AND ua.idMuUsuario.id = :userId")
    List<MuAlarmaUsuario> findAllByIdUsuario(@Param("userId") Long userId);

    @Query( "   SELECT ua " +
            "   FROM MuAlarmaUsuario ua " +
            "   WHERE ua.idMuAlarma = :alarmaId")
    List<MuAlarmaUsuario> findAllByAlarmaId(@Param("alarmaId") MuAlarma alarmaId);

    @Query("    SELECT ua " +
            "   FROM MuAlarmaUsuario ua " +
            "   WHERE ua.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "       AND ua.idMuAlarma.id = :alarmaId " +
            "       AND ua.principal = TRUE")
    MuAlarmaUsuario obtenerUsuarioPrincipal(@Param("alarmaId") Long id);



    @Query("    SELECT ua " +
            "   FROM MuAlarmaUsuario ua " +
            "   WHERE ua.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO  " +
            "       AND ua.idMuAlarma.id = :alarmaId " +
            "       AND ua.idMuUsuario.id = :usuarioId ")
    MuAlarmaUsuario obtenerPorUsuarioAlarma(@Param("alarmaId") Long alarmaId, @Param("usuarioId") Long usuarioId);
}
