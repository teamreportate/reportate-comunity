package bo.com.reportate.repository;

import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.MuRol;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.RecursoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository extends JpaRepository<MuRecurso, Long> {

    @Query(" SELECT r FROM MuRecurso r where r.id=:idRec ")

    <T>Optional<T> obtenerPorId(@Param("idRec") Long id, Class<T> tClass );

    @Query("SELECT DISTINCT (rec) FROM MuRecurso rec " +
            "INNER JOIN  rec.permisos perm INNER JOIN perm.idRol rol " +
            "INNER JOIN  rol.grupoRols gr INNER JOIN gr.idGrupo  grupo " +
            "INNER JOIN  grupo.muUsuarioGrupos userGroup INNER JOIN userGroup.idUsuario user" +
            " WHERE  rec.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO AND rec.idRecursoPadre IS NULL AND user =:usuarioId ORDER BY rec.ordenMenu ASC")
    List<MuRecurso> listPadresByUsuario(@Param("usuarioId") MuUsuario usuarioId);

    @Query("SELECT DISTINCT (rec) FROM MuRecurso rec " +
            "INNER JOIN  rec.permisos perm INNER JOIN perm.idRol rol " +
            "INNER JOIN  rol.grupoRols gr INNER JOIN gr.idGrupo  grupo " +
            "INNER JOIN  grupo.muUsuarioGrupos userGroup INNER JOIN userGroup.idUsuario user" +
            " WHERE rec.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND rec.idRecursoPadre IS NOT NULL AND user =:usuarioId " +
            "ORDER BY rec.ordenMenu ASC")
    List<MuRecurso> listHijosByUsuario(@Param("usuarioId") MuUsuario usuarioId);

    @Query("  SELECT a" +
            " FROM MuRecurso a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " ORDER BY a.idRecursoPadre.ordenMenu ASC, a.ordenMenu ASC, a.id ASC"
    )
    List<RecursoDto> listAll();

    @Query("  SELECT new bo.com.reportate.model.dto.RecursoDto(a) " +
            " FROM MuRecurso a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND (" +
            "   TRIM(LOWER(a.nombre)) like TRIM(LOWER(:criterioBusqueda))" +
            "   OR TRIM(LOWER(a.descripcion)) like TRIM(LOWER(CONCAT('%',:criterioBusqueda,'%')))" +
            " )" +
            " ORDER BY a.idRecursoPadre.ordenMenu ASC, a.ordenMenu ASC, a.id ASC"
    )
    List<RecursoDto> searchAll(@Param("criterioBusqueda") String criterioBusqueda);

    @Query("  SELECT a" +
            " FROM MuRecurso a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND TRIM(LOWER(a.nombre)) = TRIM(LOWER(:nombre))" +
            " ORDER BY a.idRecursoPadre.ordenMenu ASC, a.ordenMenu ASC, a.id ASC"
    )
    List<RecursoDto> listByNombre(@Param("nombre") String nombre);


    @Query("    SELECT u " +
            "   FROM MuRecurso u " +
            "   WHERE u.estado <> bo.com.reportate.model.enums.EstadoEnum.ELIMINADO " +
            "       AND u.url = :url")
    MuRecurso findByUrl(@Param("url") String url);

    @Query("    SELECT new bo.com.reportate.model.dto.RecursoDto(u) " +
            "   FROM MuRecurso u " +
            "   WHERE u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.id IN (" +
            "       SELECT ug.idRecurso.id " +
            "       FROM MuPermiso ug " +
            "       WHERE ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "       AND ug.idRol = :rolId)"
    )
    List<RecursoDto> listAsignedByRol(@Param("rolId") MuRol id);


    @Query("    SELECT new bo.com.reportate.model.dto.RecursoDto(u) " +
            "   FROM MuRecurso u " +
            "   WHERE u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.id NOT IN (" +
            "       SELECT ug.idRecurso.id " +
            "       FROM MuPermiso ug " +
            "       WHERE ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "       AND ug.idRol = :rolId)"
    )
    List<RecursoDto> listNotAsignedByRol(@Param("rolId") MuRol id);


    @Modifying
    @Query(" UPDATE MuRecurso mr set mr.descripcion = :descripcion, mr.nombre = :nombre, mr.ordenMenu = :ordenMenu WHERE " +
            " mr.id = :id")
    void updateSave(@Param("id") Long id,
                   @Param("nombre") String nombre,
                   @Param("descripcion") String descripcion,
                   @Param("ordenMenu") Integer ordenMenu);

    @Query("SELECT r " +
            "FROM MuRecurso r " +
            "WHERE r.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND r.id = :recursoId")
    Optional<MuRecurso> findActiveById(@Param("recursoId") Long recursoId);


}
