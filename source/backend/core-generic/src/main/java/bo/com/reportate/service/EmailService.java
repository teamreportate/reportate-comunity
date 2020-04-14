package bo.com.reportate.service;

import bo.com.reportate.model.DiagnosticoSintoma;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;

import java.util.List;
import java.util.Locale;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :29-04-19
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : MC4
 */

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sentMessageEmail(String nombrePaciente, String subject, String to, List<String> sintomas,  String mensaje);
    void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas);
}
