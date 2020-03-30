package bo.com.reportate.cache;


import bo.com.reportate.model.Constants;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Slf4j
public class MailConfig {
    @Autowired
    private CacheService paramService;

    @Bean
    @Lazy
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        String mailHost = this.paramService.getStringParam(Constants.Parameters.MAIL_HOST);
        if(!StringUtil.isEmptyOrNull(mailHost)) {
            Properties mailProperties = new Properties();
            mailProperties.put("mail.smtp.auth", this.paramService.getBooleanParam(Constants.Parameters.MAIL_SMTP_AUTH));
            mailProperties.put("mail.smtp.starttls.enable", this.paramService.getBooleanParam(Constants.Parameters.MAIL_SMTP_STARTTLS_ENABLE));
            mailSender.setJavaMailProperties(mailProperties);
            mailSender.setHost(mailHost);
            mailSender.setPort(this.paramService.getIntegerParam(Constants.Parameters.MAIL_PORT));
            mailSender.setProtocol(this.paramService.getStringParam(Constants.Parameters.MAIL_PROTOCOL));
            mailSender.setUsername(this.paramService.getStringParam(Constants.Parameters.MAIL_USERNAME));
            mailSender.setPassword(this.paramService.getStringParam(Constants.Parameters.MAIL_PASS));
            mailSender.setDefaultEncoding("UTF-8");
        }
        return mailSender;
    }
}
