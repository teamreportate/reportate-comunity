package bo.com.reportate.repository;

import bo.com.reportate.model.CentroSalud;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.CentroSaludDto;
import bo.com.reportate.model.dto.CentroSaludUsuarioDto;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :MC4
 */
public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {
    List<CentroSaludDto> findByMunicipioIdAndEstadoOrderByIdDesc(Long idMunicipio, EstadoEnum estadoEnum);
    boolean existsByMunicipioAndNombreIgnoreCase(Municipio municipio, String nombre);

    boolean existsByIdIsNotAndNombreIgnoreCase(Long municipioId, String nombre);

    @Modifying
    @Query("UPDATE CentroSalud d SET d.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO where d.id =:id")
    void eliminar(@Param("id") Long id);

    @Query("SELECT new bo.com.reportate.model.dto.CentroSaludUsuarioDto(c.id,c.nombre,false ,c.municipio.id) FROM CentroSalud  c WHERE c.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<CentroSaludUsuarioDto> listaCentrosSalud();

    Optional<CentroSalud> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    List<CentroSalud> findByMunicipioInAndEstado(List<Municipio> municipios, EstadoEnum estadoEnum);
}
