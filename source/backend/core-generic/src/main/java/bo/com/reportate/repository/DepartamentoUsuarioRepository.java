package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.DepartamentoUsuario;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.DepartamentoUsuarioDto;
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
public interface DepartamentoUsuarioRepository extends JpaRepository<DepartamentoUsuario, Long> {
    boolean existsByMuUsuarioAndDepartamentoAndEstado(MuUsuario muUsuario, Departamento departamento, EstadoEnum estadoEnum);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(du.departamento) " +
            "FROM DepartamentoUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u.username =:username " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<DepartamentoUsuarioDto> listarDepartamentoAsignados(@Param("username") String username);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(du.departamento.id, du.departamento.nombre, true) " +
            "FROM DepartamentoUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u.id =:userId " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<DepartamentoUsuarioDto> listarDepartamentoAsignados(@Param("userId") Long userId);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(du.departamento) " +
            "FROM DepartamentoUsuario du INNER JOIN du.muUsuario u " +
            "WHERE u=:usuario " +
            "AND du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "AND u.estado=bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<DepartamentoUsuarioDto> listarAsignados(@Param("usuario") MuUsuario username);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(d) " +
            "from Departamento d " +
            "WHERE d NOT IN" +
            "   (SELECT du.departamento FROM DepartamentoUsuario du INNER JOIN du.muUsuario u " +
            "   WHERE u.username=:username AND du.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   ) " +
            "AND d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<DepartamentoUsuarioDto> listarDepartamentoNoAsignados(@Param("username") String username);

    @Query("SELECT new bo.com.reportate.model.dto.DepartamentoUsuarioDto(d.id, d.nombre, false ) " +
            "from Departamento d " +
            "WHERE d NOT IN" +
            "   (SELECT du.departamento FROM DepartamentoUsuario du INNER JOIN du.muUsuario u " +
            "   WHERE u.id=:userId AND du.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND u.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            "   ) " +
            "AND d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<DepartamentoUsuarioDto> listarDepartamentoNoAsignados(@Param("userId") Long userId);

    @Modifying
    @Query(" UPDATE DepartamentoUsuario du SET du.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE du.muUsuario =:usuario AND du.departamento.id NOT IN(:departamentos)")
    void eliminaDepartamentosNoAsignados(@Param("usuario") MuUsuario muUsuario, @Param("departamentos") List<Long> departamentoIds);

    @Query(" SELECT d FROM DepartamentoUsuario du INNER JOIN du.departamento d" +
            " WHERE du.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
            " AND du.muUsuario =:usuario")
    List<Departamento> listarDepartamentoAsignados(@Param("usuario") MuUsuario muUsuario);
}
