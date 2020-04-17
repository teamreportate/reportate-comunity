package bo.com.reportate.service;

import bo.com.reportate.model.MuBitacora;
import bo.com.reportate.model.dto.ProcessDto;
import bo.com.reportate.model.enums.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :13-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : MC4
 */

public interface LogService {
    void info(Process procesoSfeEnum, String mensaje, Object... var2);
    void warning(Process procesoSfeEnum, String mensaje);
    void warning(Process procesoSfeEnum, String mensaje,Object... var2);
    void error(Process procesoSfeEnum, String mensaje,Object... var2);

    /**
     * Obtiene al objeto segun su id
     * @param id
     * @return
     */
    MuBitacora obtener(Long id);

    Page<MuBitacora> listPageableByDatesAndType(Date from, Date to, String mensaje, String proceso, Pageable pageable);

    List<Process> listProcesos();
}
