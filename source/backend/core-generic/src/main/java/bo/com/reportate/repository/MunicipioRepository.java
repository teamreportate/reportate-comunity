package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.Municipio;
import bo.com.reportate.model.dto.MunicipioDto;
import bo.com.reportate.model.dto.MunicipioUsuarioDto;
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
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    boolean existsByNombreIgnoreCaseAndDepartamento(String nombre, Departamento departamento);

    List<MunicipioDto> findByEstadoInAndDepartamentoIdOrderByNombreAsc(List<EstadoEnum> estadoEnums, Long id);
    List<Municipio> findByDepartamentoInAndEstado(List<Departamento> departamento, EstadoEnum estadoEnum);
    Optional<Municipio> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    boolean existsByIdIsNotAndNombreIgnoreCase(Long municipioId, String nombre);
    @Modifying
    @Query("UPDATE Municipio d SET d.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO where d.id =:id")
    void eliminar(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Municipio d SET d.estado = bo.com.reportate.model.enums.EstadoEnum.INACTIVO where d.id =:id")
    void inactivar(@Param("id") Long id);

    @Query("SELECT new bo.com.reportate.model.dto.MunicipioUsuarioDto(m.id, m.nombre, false, m.departamento.id ) from  Municipio m WHERE m.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<MunicipioUsuarioDto> listaMunicipios();
}
