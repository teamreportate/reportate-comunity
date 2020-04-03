package bo.com.reportate.service;

import java.util.List;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service
 * date:    14-06-19
 * author:  fmontero
 **/
public interface NotificacionService {
    void notificacionSospechoso(String to, String asunto, String mensaje);
    void notificacionSospechosoSintomas(String paciente,String to, String asunto, String mensaje, List<String> sintomas);

}
