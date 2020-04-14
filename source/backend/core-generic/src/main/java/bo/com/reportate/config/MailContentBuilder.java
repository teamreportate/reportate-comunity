package bo.com.reportate.config;

import bo.com.reportate.model.DiagnosticoSintoma;
import bo.com.reportate.model.dto.PacienteEmailDto;
import bo.com.reportate.model.dto.response.DiagnosticoSintomaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;


/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-08
 * @Project :musers
 * @Package :bo.com.reportate.config
 * @Copyright :MC4
 */
@Service
public class MailContentBuilder {
    private static final String EMAIL_TEXT_TEMPLATE_NAME = "text/email-text";
    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-simple";
    private static final String EMAIL_WITHATTACHMENT_TEMPLATE_NAME = "html/email-withattachment";
    private static final String EMAIL_INLINEIMAGE_TEMPLATE_NAME = "html/email-inlineimage";
    private static final String EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES = "classpath:mail/editablehtml/email-editable.html";

    private static final String BACKGROUND_IMAGE = "mail/editablehtml/images/background.png";
    private static final String LOGO_BACKGROUND_IMAGE = "mail/editablehtml/images/logo-background.png";
    private static final String THYMELEAF_BANNER_IMAGE = "mail/editablehtml/images/thymeleaf-banner.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mail/editablehtml/images/thymeleaf-logo.png";

    private static final String PNG_MIME = "image/png";
    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }


    public String sendMailWithInline(String paciente,  String imageResourceName, String mensaje, List<String> sintomas) {
        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("paciente", paciente);
//        ctx.setVariable("sintomas", sintomas);
        ctx.setVariable("mensaje",mensaje);
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
        return this.templateEngine.process("mailTemplate2", ctx);
    }

    public String notidicacionMedico(PacienteEmailDto paciente, List<DiagnosticoSintomaResponse> sintomas) {
        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("paciente", paciente);
        ctx.setVariable("sintomas", sintomas);
//        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
        return this.templateEngine.process("mailTemplate3", ctx);
    }


    /*
     * Send HTML mail with inline image
     */
    public String sendEditableMail(
            final String recipientName, final String htmlContent, List<String> sintomas)
            throws MessagingException {
        // Prepare the evaluation context
        final Context ctx = new Context();
//        ctx.setVariable("name", recipientName);
//        ctx.setVariable("sintomas", sintomas);
        // Create the HTML body using Thymeleaf
        return this.templateEngine.process(htmlContent, ctx);
    }


}

