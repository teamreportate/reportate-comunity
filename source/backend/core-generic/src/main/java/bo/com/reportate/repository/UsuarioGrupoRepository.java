package bo.com.reportate.repository;

import bo.com.reportate.model.MuGrupo;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.MuUsuarioGrupo;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioGrupoRepository extends JpaRepository<MuUsuarioGrupo, Long> {
    boolean existsByIdUsuarioAndIdGrupoAndEstado(MuUsuario muUsuario, MuGrupo muGrupo, EstadoEnum estadoEnum);
    void deleteByIdGrupoAndIdUsuario(MuGrupo muGrupo, MuUsuario muUsuario);

    @Query("    SELECT a" +
            "   FROM MuUsuarioGrupo a" +
            "   WHERE a.idUsuario.id = :idUsuario" +
            "   AND a.idGrupo.id = :idGrupo"
    )
    MuUsuarioGrupo findByIdUsuarioAndIdGrupo(@Param("idUsuario") Long idUsuario,@Param("idGrupo") Long idGrupo);


}
