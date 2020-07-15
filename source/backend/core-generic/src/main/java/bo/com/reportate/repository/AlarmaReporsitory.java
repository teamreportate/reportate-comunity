package bo.com.reportate.repository;

import bo.com.reportate.model.MuAlarma;
import bo.com.reportate.model.dto.AlarmaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.repository
 * date:    14-06-19
 * author:  fmontero
 **/
public interface AlarmaReporsitory extends JpaRepository<MuAlarma, Long> {

    @Query("    SELECT new bo.com.reportate.model.dto.AlarmaDto(a) " +
            "   FROM MuAlarma a " +
            "   WHERE a.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<AlarmaDto> findAllActive();

}
