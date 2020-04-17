package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FalsoEnvioEmailService extends SmtpEnvioEmailService {

    public FalsoEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties,
                                  Configuration freemarkerConfig, FreeMarkerProperties freeMarkerProperties) {
        super(mailSender, emailProperties, freemarkerConfig, freeMarkerProperties);
    }

    @Override
    public void enviar(Mensagem mensagem) {
        log.info("[FALSO E-MAIL] para {}\n{}", mensagem.getDestinatarios(), processarTemplate(mensagem));
    }
}