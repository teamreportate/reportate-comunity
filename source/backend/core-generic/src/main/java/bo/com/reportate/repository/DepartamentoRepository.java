package bo.com.reportate.repository;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.dto.DepartamentoDto;
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
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByIdIsNotAndNombreIgnoreCase(Long departamentoID, String nombre);
    @Query(" SELECT new bo.com.reportate.model.dto.DepartamentoDto(d) FROM Departamento d WHERE d.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    List<DepartamentoDto> cargarConMunicipio();

    Optional<Departamento> findByIdAndEstado(Long id, EstadoEnum estadoEnum);
    @Modifying
    @Query("UPDATE Departamento d SET d.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO where d.id =:id")
    void eliminar(@Param("id") Long id);
}
