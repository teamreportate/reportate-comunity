package bo.com.reportate.service;

import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;

import java.util.List;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service
 * date:    14-06-19
 * author:  fmontero
 **/
public interface NotificacionService {
    void notificacionSospechoso(String to, String asunto, String mensaje);
    void notificacionSospechosoSintomas(String paciente,String to, String asunto, String mensaje, List<String> sintomas);
    void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
    void notidicacionMedico(String subject, List<String> to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
    void notidicacionMedico(String subject, String to, List<String> cc, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
}
