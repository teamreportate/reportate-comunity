package bo.com.reportate.service.impl;

import bo.com.reportate.cache.CacheService;
import bo.com.reportate.config.MailContentBuilder;
import bo.com.reportate.model.Constants;
import bo.com.reportate.model.DiagnosticoSintoma;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Locale;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :29-04-19
 * Project    :reportate
 * Package    :bo.com.reportate.service.impl
 * Copyright  : MC4
 */
@Slf4j
@Service("emailService")
public class EmailServiceImpl implements EmailService {
    private static final String BANNER_PNG = "images/bannerreportate.png";
    @Autowired private JavaMailSender emailSender;
    @Autowired private CacheService cacheService;
    @Autowired private MailContentBuilder mailContentBuilder;
    @Autowired
    private ApplicationContext applicationContext;

    public void sendSimpleMessage(String to, String subject, String text) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setFrom(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), cacheService.getStringParam(Constants.Parameters.MAIL_FROM)));
            messageHelper.setReplyTo(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), false) );
            messageHelper.setSubject(subject);

            String content = mailContentBuilder.build(text);
            messageHelper.setText(content, true);
            log.info("Mensaje enviado correctamente.");
        };
        try {
            emailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            log.error("Error al enviar correo.");
        }
    }

    @Override
    public void sentMessageEmail(String nombrePaciente, String subject, String to, List<String> sintomas, String mensaje) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setFrom(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), cacheService.getStringParam(Constants.Parameters.MAIL_FROM)));
            messageHelper.setReplyTo(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), false));
            messageHelper.setSubject(subject);
            String message = mailContentBuilder.sendMailWithInline(nombrePaciente, "thymeleaf-banner", mensaje, sintomas);
            messageHelper.setText(message,true);
            messageHelper.addInline("bannerreportate", new ClassPathResource(BANNER_PNG) );
        };

        emailSender.send(messagePreparator);
    }

    @Override
    public void notidicacionMedico(String subject, String to, PacienteEmailDto paciente, List<DiagnosticoSintoma> sintomas) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setFrom(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), cacheService.getStringParam(Constants.Parameters.MAIL_FROM)));
            messageHelper.setReplyTo(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), false));
            messageHelper.setSubject(subject);
            String message = mailContentBuilder.notidicacionMedico(paciente, sintomas);
            messageHelper.setText(message,true);
//            messageHelper.addInline("bannerreportate", new ClassPathResource(BANNER_PNG) );
        };
        emailSender.send(messagePreparator);
    }
}
