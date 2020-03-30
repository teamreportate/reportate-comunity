package bo.com.reportate.repository;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.dto.GrupoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<MuGrupo, Long> {

    Optional<GrupoDto> findByNombre(String nombre);

    @Query("  SELECT a" +
            " FROM MuGrupo a " +
            " WHERE UPPER(a.nombre) = :nombre")
    Optional<MuGrupo> findByUpperCaseNombre(@Param("nombre") String nombre);

    @Query("  SELECT a" +
            " FROM MuGrupo a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND a.nombre = :nombre "
    )
    MuGrupo findByNombreOneForOne(@Param("nombre") String nombre);


    @Query("  SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            " FROM MuGrupo a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " ORDER BY a.createdDate DESC"
    )
    List<GrupoDto> listAll();

    @Query("  SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            " FROM MuGrupo a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND (" +
            "   TRIM(LOWER(a.nombre)) like TRIM(LOWER(:criterioBusqueda))" +
            "   OR TRIM(LOWER(a.descripcion)) like TRIM(LOWER(CONCAT('%',:criterioBusqueda,'%')))" +
            " )" +
            " ORDER BY a.id DESC"
    )
    List<GrupoDto> searchAll(@Param("criterioBusqueda") String criterioBusqueda);
    

    @Query("    SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            "   FROM MuGrupo a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id IN (" +
            "       select ug.idGrupo.id" +
            "       from MuUsuarioGrupo ug" +
            "       where ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND ug.idUsuario.id = :idUsuario" +
            "   )" +
            "   ORDER BY a.createdDate DESC"
    )
    List<GrupoDto> listByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("    SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            "   FROM MuGrupo a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id NOT IN (" +
            "       select ug.idGrupo.id" +
            "       from MuUsuarioGrupo ug" +
            "       where ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND ug.idUsuario.id = :usuarioId" +
            "   )" +
            "   ORDER BY a.createdDate DESC")
    List<GrupoDto> listNotAsignedByUsuario(@Param("usuarioId") Long usuarioId);

    @Query("    SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            "   FROM MuGrupo a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id IN (" +
            "       SELECT ug.idGrupo.id" +
            "       FROM MuGrupoRol ug" +
            "       WHERE ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND ug.idRol.id = :rolId" +
            "   )"
    )
    List<GrupoDto> listAsignedByRol(@Param("rolId") Long id);

    @Query("    SELECT new bo.com.reportate.model.dto.GrupoDto(a)" +
            "   FROM MuGrupo a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id NOT IN (" +
            "       SELECT ug.idGrupo.id" +
            "       FROM MuGrupoRol ug" +
            "       WHERE ug.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND ug.idRol.id = :rolId" +
            "   )"
    )
    List<GrupoDto> listNotAsignedByRol(@Param("rolId") Long id);
}
