package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

    final JavaMailSender mailSender;
    final EmailProperties emailProperties;
    final Configuration freeMarkerConfig;
    final FreeMarkerProperties freeMarkerProperties;

    public SmtpEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
                                 Configuration freemarkerConfig, FreeMarkerProperties freeMarkerProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.freeMarkerConfig = freemarkerConfig;
        this.freeMarkerProperties = freeMarkerProperties;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage message = criarMimeMessage(mensagem);
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail.", e);
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        String corpo = processarTemplate(mensagem);

        helper.setFrom(emailProperties.getSender());
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);

        return message;
    }

    protected String processarTemplate(Mensagem mensagem) {
        try {

            Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo() +
                    freeMarkerProperties.getSuffix());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getPropriedades());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar template do e-mail.", e);
        }
    }
}