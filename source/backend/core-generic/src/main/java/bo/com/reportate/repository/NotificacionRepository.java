package bo.com.reportate.repository;

import bo.com.reportate.model.MuNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.repository
 * date:    14-06-19
 * author:  fmontero
 **/
public interface NotificacionRepository extends JpaRepository<MuNotificacion, Long> {
}
