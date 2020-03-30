package bo.com.reportate.repository;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.MuGrupoRol;
import bo.com.reportate.model.MuRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrupoRolRepository extends JpaRepository<MuGrupoRol, Long> {

    @Query("    SELECT a" +
            "   FROM MuGrupoRol a" +
            "   WHERE " +
            "   a.idGrupo.id = :idGrupo" +
            "   AND a.idRol.id = :idRol" +
            "   AND a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   ORDER BY a.id DESC"
    )
    MuGrupoRol findByIdGrupoAndIdRol(@Param("idGrupo") Long idGrupo, @Param("idRol") Long idRol);


//    @Query("    UPDATE a" +
//            "   FROM MuGrupoRol a" +
//            "   WHERE " +
//            "   a.id = :id" +
//            "   AND a.estado = EstadoEnum.activo" +
//            "   ORDER BY a.id DESC"
//    )
//    MuGrupoRol eliminar(@Param("id") Long id);

    @Query("    SELECT a" +
            "   FROM MuGrupoRol a" +
            "   WHERE a.id = :id" +
            "   AND a.idGrupo.id = :idGrupo" +
            "   AND a.idRol.id = :idRol" +
            "   ORDER BY a.id DESC"
    )
    MuGrupoRol findByIdGrupoAndIdRol(@Param("id") Long id,@Param("idGrupo") Long idGrupo, @Param("idRol") Long idRol);


    List<MuGrupoRol> findAllByIdGrupo(MuGrupo MUGrupo);

    @Modifying
    @Query(" UPDATE MuGrupoRol gr set gr.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO  where gr.idGrupo=:grupo")
    void deleteLogic(@Param("grupo") MuGrupo muGrupo);

    @Modifying
    @Query(" UPDATE MuGrupoRol gr set gr.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO  " +
            "where gr.idGrupo=:grupo AND gr.idRol=:rol")
    void deleteLogic(@Param("grupo") MuGrupo muGrupo, @Param("rol") MuRol muRol);

    void deleteByIdGrupo(MuGrupo muGrupo);
    void deleteByIdGrupoAndIdRol(MuGrupo muGrupo, MuRol muRol);


}
