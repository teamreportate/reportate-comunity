package bo.com.reportate.repository;

import bo.com.reportate.model.MuBitacora;
import bo.com.reportate.model.enums.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface LogRepository extends JpaRepository<MuBitacora, Long> {

    @Query("  SELECT o" +
            " FROM MuBitacora o" +
            " WHERE o.createdDate between :fechaInicial and :fechaFinal AND o.proceso = :proceso " +
            " AND lower(o.mensaje) like :mensaje" +
            " ORDER BY o.createdDate asc")
    Page<MuBitacora> listByDate(@Param("fechaInicial") Date fechaInicial,
                                @Param("fechaFinal") Date fechaFinal,
                                @Param("proceso") Process proceso,
                                @Param("mensaje") String mensaje,
                                Pageable pageable);

    Page<MuBitacora> findAllByCreatedDateBetweenAndProcesoAndMensajeContainingOrderByCreatedDateDesc(Date from, Date to, Process proceso, String mensaje, Pageable pageable);
}
