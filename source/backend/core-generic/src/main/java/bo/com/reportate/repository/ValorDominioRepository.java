package bo.com.reportate.repository;

import bo.com.reportate.model.MuDominio;
import bo.com.reportate.model.MuValorDominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by :MC4
 * Autor      :Jonathan Valdivia
 * Email      :jvaldivia@mc4.com.bo
 * Date       :21-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.repository
 * Copyright  : MC4
 */

public interface ValorDominioRepository extends JpaRepository<MuValorDominio, Long> {

   <T>List<T> findByDescripcionContainingOrValorContainingAllIgnoreCase(String descripcion,String valor, Class<T> tClass);
   @Query("    SELECT vd " +
           "   FROM MuValorDominio vd " +
           "   WHERE vd.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
           "      AND vd.dominio.id = :dominioId " +
           "      AND vd.valor = :valor ")
   MuValorDominio findAllByDominioId(@Param("dominioId") Long id, @Param("valor") String valor);

   Optional<MuValorDominio> findByDominioAndValor(MuDominio dominio, String valor);

   @Query("SELECT d FROM MuValorDominio d INNER JOIN d.dominio dom where dom.codigo=:codigo")
   <T> List<T> findAllByCodigoDominio(@Param("codigo") String codigo, Class<T> tClass);

   @Query("    SELECT vd " +
           "   FROM MuValorDominio vd " +
           "   WHERE vd.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
           "   AND vd.dominio.id = :dominioId " +
           "   ORDER BY vd.id DESC ")
   List<MuValorDominio> listaValores(@Param("dominioId") Long dominioId);

   @Modifying
   @Query("UPDATE MuValorDominio vd set vd.descripcion=:descrip, vd.valor=:val WHERE  vd.id = :idValorDominio")
   void updateValorDominio(@Param("descrip") String descripcion,@Param("val") String valor, @Param("idValorDominio") Long idValorDominio);


}
