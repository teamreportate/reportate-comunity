package bo.com.reportate.service.impl;

import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;
import bo.com.reportate.service.EmailService;
import bo.com.reportate.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.service.impl
 * date:    14-06-19
 * author:  fmontero
 **/

@Service("notificacionService")
public class NotificacionServiceImpl implements NotificacionService {
    @Autowired
    private EmailService emailService;
    @Override
    @Async
    public void notificacionSospechoso(String to, String asunto, String mensaje) {
        emailService.sendSimpleMessage(to, asunto, mensaje);

    }
    @Async
    public void notificacionSospechosoSintomas(String paciente, String to, String asunto, String mensaje, List<String> sintomas){
        emailService.sentMessageEmail(paciente,asunto,to, sintomas, mensaje);
    }

    @Override
    @Async
    public void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas) {
        emailService.notidicacionMedico(subject, to,paciente,sintomas);
    }
    @Override
    @Async
    public void notidicacionMedico(String subject, List<String> to, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas) {
        emailService.notidicacionMedico(subject, to,paciente,sintomas);
    }

    @Override
    @Async
    public void notidicacionMedico(String subject, String to, List<String> cc, PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas) {
        emailService.notidicacionMedico(subject,to,cc,paciente,sintomas);
    }
}
