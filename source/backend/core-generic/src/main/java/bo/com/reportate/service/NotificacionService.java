package bo.com.reportate.service;

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
}
