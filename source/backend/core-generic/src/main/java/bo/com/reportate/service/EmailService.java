package bo.com.reportate.service;

import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;

import java.util.List;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :29-04-19
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : Reportate
 */

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sentMessageEmail(String nombrePaciente, String subject, String to, List<String> sintomas,  String mensaje);
    void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
    void notidicacionMedico(String subject, List<String> to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
    void notidicacionMedico(String subject, String to, List<String> cc, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
}
