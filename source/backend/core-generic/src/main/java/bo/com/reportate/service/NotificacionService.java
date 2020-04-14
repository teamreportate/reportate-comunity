package bo.com.reportate.service;

import bo.com.reportate.model.DiagnosticoSintoma;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteEmailDto;

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
    void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintoma> sintomas);

}
