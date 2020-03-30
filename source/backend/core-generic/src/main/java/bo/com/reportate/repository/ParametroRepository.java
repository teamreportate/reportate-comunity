package bo.com.reportate.repository;

import bo.com.reportate.model.MuParametro;
import bo.com.reportate.model.dto.MuParametroDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :12-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.repository
 * Copyright  : MC4
 */

public interface ParametroRepository extends JpaRepository<MuParametro, Long> {

   Optional<MuParametro> findSfeParametroByCodigo(String codigo);

   @Query("    SELECT par " +
           "   FROM MuParametro par " +
           "   WHERE par.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
           "   AND TRIM(UPPER(par.codigo)) = TRIM(UPPER(:codigo)) ")
   MuParametro obtenerParametro(@Param("codigo") String codigo);

   @Query("    SELECT new  bo.com.reportate.model.dto.MuParametroDto(par)" +
           "   FROM MuParametro par" +
           "   WHERE par.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
           "   ORDER BY par.id DESC")
   List<MuParametroDto> list();

   @Query("    SELECT a" +
           "   FROM MuParametro a" +
           "   WHERE " +
           "   a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO" +
           "   AND (" +
           "      TRIM(LOWER(a.codigo)) like TRIM(LOWER(:criterioBusqueda))" +
           "   )" +
           "   ORDER BY a.id DESC")
   List<MuParametro> searchAll(@Param("criterioBusqueda") String criterioBusqueda);

   @Query("    SELECT new  bo.com.reportate.model.dto.MuParametroDto(par) " +
           "   FROM MuParametro par " +
           "   WHERE par.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
           "   AND par.idMuGrupoParametro.id = :grupoId" +
           "   ORDER BY par.codigo ASC")
   List<MuParametroDto> obtenerParametros(@Param("grupoId") Long grupoId);

        @Query("    SELECT a" +
           "   FROM MuParametro a" +
           "   WHERE a.codigo = :codigo" +
           " AND a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
   MuParametro getByCodigo(@Param("codigo") String codigo);
}
