package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FalsoEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    final JavaMailSender mailSender;
    final EmailProperties emailProperties;
    final freemarker.template.Configuration freeMarkerConfig;
    final FreeMarkerProperties freeMarkerProperties;

    public EmailConfig(JavaMailSender mailSender, EmailProperties emailProperties,
                       freemarker.template.Configuration freeMarkerConfig, FreeMarkerProperties freeMarkerProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.freeMarkerConfig = freeMarkerConfig;
        this.freeMarkerProperties = freeMarkerProperties;
    }

    @Bean
    public EnvioEmailService emailService() {
        if (emailProperties.getType().equals(EmailProperties.EmailType.SANDBOX)) {
            return new SandboxEnvioEmailService(mailSender, emailProperties, freeMarkerConfig, freeMarkerProperties);

        } else if (emailProperties.getType().equals(EmailProperties.EmailType.SMTP)) {
            return new SmtpEnvioEmailService(mailSender, emailProperties, freeMarkerConfig, freeMarkerProperties);
        }

        return new FalsoEnvioEmailService(mailSender, emailProperties, freeMarkerConfig, freeMarkerProperties);
    }
}