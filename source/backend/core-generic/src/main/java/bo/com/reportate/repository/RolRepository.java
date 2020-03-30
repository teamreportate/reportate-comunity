package bo.com.reportate.repository;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.MuRol;
import bo.com.reportate.model.dto.RolDto;
import bo.com.reportate.model.enums.EstadoRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<MuRol, Long> {

    @Query("  SELECT a" +
            " FROM MuRol a " +
            " WHERE UPPER(a.nombre) = :nombre" +
            "       AND a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    Optional<MuRol> findByUpperCaseNombre(@Param("nombre") String nombre);

    @Query("  SELECT new bo.com.reportate.model.dto.RolDto(a)" +
            " FROM MuRol a" +
            " WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " ORDER BY a.nombre ASC"
    )
    List<RolDto> listAll();

    @Query("    SELECT u " +
            "   FROM MuRol u " +
            "   WHERE u.estado <> bo.com.reportate.model.enums.EstadoEnum.ELIMINADO " +
            "       AND u.nombre = :nombre")
    MuRol findByName(@Param("nombre") String nombre);



    @Query("    SELECT new bo.com.reportate.model.dto.RolDto(a)" +
            "   FROM MuRol a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id IN (" +
            "       SELECT gr.idRol.id" +
            "       FROM MuGrupoRol gr" +
            "       WHERE gr.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND gr.idGrupo.id = :grupoId)"
    )
    List<RolDto> listAsignedByGrupo(@Param("grupoId") Long grupoId);

    @Query("    SELECT new bo.com.reportate.model.dto.RolDto(a) " +
            "   FROM MuRol a" +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   AND a.id NOT IN (" +
            "       SELECT gr.idRol.id" +
            "       FROM MuGrupoRol gr" +
            "       WHERE gr.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "       AND gr.idGrupo = :grupoId)"
    )
    List<RolDto> listNotAsignedByGrupo(@Param("grupoId") MuGrupo grupoId);



    @Modifying
    @Query("  UPDATE MuRol r " +
            " SET r.nombre =:nombre" +
            " , r.descripcion =:descripcion" +
            " WHERE r.id =:id")
    void modify(@Param("nombre") String nombre
                ,@Param("descripcion") String descripcion
                ,@Param("id") Long id);

    @Modifying
     @Query("UPDATE MuRol r SET r.estadoRol = :estadoRol WHERE r.id =:id ")
    void cambiarEstado(@Param("estadoRol") EstadoRol estadoRol,
           @Param("id") Long id);

    boolean existsByNombreAndIdNot(String nombre, Long id);

    @Query("SELECT r FROM MuRol r " +
            "WHERE r.estadoRol = bo.com.reportate.model.enums.EstadoRol.ACTIVO " +
            "AND r.id = :rolId")
    Optional<MuRol> findActiveById(@Param("rolId") Long rolId);

}
