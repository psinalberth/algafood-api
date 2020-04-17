package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    public SandboxEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
                                    Configuration freemarkerConfig, FreeMarkerProperties freeMarkerProperties) {
        super(mailSender, emailProperties, freemarkerConfig, freeMarkerProperties);
    }

    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {

        MimeMessage message = super.criarMimeMessage(mensagem);

        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getRecipient());

        return message;
    }
}