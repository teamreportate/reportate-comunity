package bo.com.reportate.repository;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.CentroSaludUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
public interface CentroSaludUsuarioRepository extends JpaRepository<CentroSaludUsuario, Long> {
    boolean existsByMuUsuarioAndCentroSaludAndEstado(MuUsuario muUsuario, CentroSalud centroSalud, EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(du.centroSalud) " +
            "FROM CentroSaludUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u.username =:username " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<CentroSaludUsuarioDto> listarCentrosSaludAsignados(@Param("username") String username);

    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(du.centroSalud.id,du.centroSalud.nombre, true, du.centroSalud.municipio.id) " +
            "FROM CentroSaludUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u =:usuario " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND du.centroSalud.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    List<CentroSaludUsuarioDto> listarCentrosSaludAsignados(@Param("usuario") MuUsuario muUsuario);

    @Query("SELECT du.centroSalud " +
            "FROM CentroSaludUsuario du INNER JOIN du.muUsuario u INNER JOIN du.centroSalud.municipio m " +
            "WHERE u =:usuario " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND m.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND m IN (:municipios)")
    List<CentroSalud> listarCentrosSaludAsignados(@Param("usuario") MuUsuario muUsuario, @Param("municipios")List<Municipio> municipios);

    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(du.centroSalud.id, du.centroSalud.nombre, true, du.centroSalud.municipio.id) " +
            "FROM CentroSaludUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u.id =:userId " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND du.centroSalud.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<CentroSaludUsuarioDto> listarCentrosSaludAsignados(@Param("userId") Long userId);


    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(d) " +
            "from CentroSalud d " +
            "WHERE d NOT IN" +
            "   (SELECT du.centroSalud FROM CentroSaludUsuario du INNER JOIN du.muUsuario u " +
            "   WHERE u.username=:username AND du.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   ) " +
            "AND d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<CentroSaludUsuarioDto> listarCentrosSaludNoAsignados(@Param("username") String username);

    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(d.id, d.nombre, false, d.municipio.id) " +
            "from CentroSalud d " +
            "WHERE d NOT IN" +
            "   (SELECT du.centroSalud FROM CentroSaludUsuario du INNER JOIN du.muUsuario u " +
            "   WHERE u.id=:userId AND du.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "    AND du.centroSalud.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   ) " +
            "AND d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<CentroSaludUsuarioDto> listarCentrosSaludNoAsignados(@Param("userId") Long userId);

    @Modifying
    @Query(" UPDATE CentroSaludUsuario du SET du.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE du.muUsuario =:usuario AND du.centroSalud.id NOT IN(:centros)")
    void eliminaCentrosNoAsignados(@Param("usuario") MuUsuario muUsuario, @Param("centros") List<Long> centrosIds);

    @Modifying
    @Query(" UPDATE CentroSaludUsuario du SET du.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO " +
            "WHERE du.muUsuario =:usuario")
    void eliminaCentrosAsignados(@Param("usuario") MuUsuario muUsuario);
}
