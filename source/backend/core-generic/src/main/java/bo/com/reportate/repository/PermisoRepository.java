package bo.com.reportate.repository;

import bo.com.reportate.model.MuPermiso;
import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.MuRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermisoRepository extends JpaRepository<MuPermiso, Long> {

    @Deprecated
    @Query("    SELECT a" +
            "   FROM MuPermiso a" +
            "   WHERE " +
            "   a.lectura = true"
    )
    List<MuPermiso> listByUsuario(@Param("idUsuario") Long idUsuario);

    @Query("    SELECT p " +
            "   FROM MuPermiso p " +
            "   WHERE p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND p.idRecurso.id = :recursoId " +
            "   AND p.idRol.id = :rolId")
    MuPermiso findByIdRolAndIdRecurso(@Param("rolId") Long rolId, @Param("recursoId") Long recursoId);

    @Query("    SELECT p " +
            "   FROM MuPermiso p " +
            "   WHERE p.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND p.idRecurso = :recurso " +
            "   AND p.idRol = :rol")
    MuPermiso findByIdRolAndIdRecurso(@Param("rol") MuRol rolId, @Param("recurso") MuRecurso recursoId);

    void deleteByIdRolAndIdRecurso(MuRol muRol, MuRecurso muPermiso);

    @Query(
            "SELECT ap " +
                    "FROM MuPermiso ap " +
                    "WHERE ap.idRol = :idRol " +
                    "ORDER BY ap.id DESC"
    )
    List<MuPermiso> listByIdRol(@Param("idRol") MuRol idRol);
}
