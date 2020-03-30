package bo.com.reportate;

import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

@EnableJpaRepositories(basePackages = {"bo.com.reportate.repository"})
@EntityScan(basePackages = {"bo.com.reportate.model"})
@SpringBootApplication(scanBasePackages = {"bo.com.reportate.service", "bo.com.reportate","bo.com.reportate.jwt"})
public class WebBackofficeApplication  implements CommandLineRunner {
//public class WebBackofficeApplication  extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    EmailService emailService;
    public static void main(String[] args) {
        TimeZone.getTimeZone(ZoneId.of("America/La_Paz"));
        SpringApplication.run(WebBackofficeApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override
    public void run(String... strings) throws Exception {

    }


}

@Configuration
@EnableJpaAuditing
class DataJpaConfig {
    @Bean
    public AuditorAware<String> auditor() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("SISTEMA");
            }

            if(! (authentication.getPrincipal() instanceof MuUsuario)){
                return Optional.of("SISTEMA");
            }

            Optional<MuUsuario> principal = Optional.of((MuUsuario) authentication.getPrincipal());
            return Optional.ofNullable(principal.get().getUsername());
        };
    }
}


