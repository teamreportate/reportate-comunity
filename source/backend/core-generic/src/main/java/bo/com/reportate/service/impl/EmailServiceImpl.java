package bo.com.reportate.service.impl;

import bo.com.reportate.cache.CacheService;
import bo.com.reportate.config.MailContentBuilder;
import bo.com.reportate.model.Constants;
import bo.com.reportate.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

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
    @Autowired private JavaMailSender emailSender;
    @Autowired private CacheService cacheService;
    @Autowired private MailContentBuilder mailContentBuilder;

    public void sendSimpleMessage(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setReplyTo(cacheService.getStringParam(Constants.Parameters.MAIL_FROM));
//        message.setText(text);
//        emailSender.send(message);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//            messageHelper.setFrom(cacheService.getStringParam(Constants.Parameters.MAIL_FROM));
            messageHelper.setTo(to);
            messageHelper.setFrom(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), cacheService.getStringParam(Constants.Parameters.MAIL_FROM)));
            messageHelper.setReplyTo(new InternetAddress(cacheService.getStringParam(Constants.Parameters.MAIL_FROM), false) );
            messageHelper.setSubject(subject);
            String content = mailContentBuilder.build(text);
            messageHelper.setText(content, true);
        };
        try {
            emailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
            log.error("Error al enviar correo.");
        }
    }
}
