package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    public enum EmailType {
        FAKE, SANDBOX, SMTP
    }

    private String sender;
    private EmailType type = EmailType.FAKE;
    private Sandbox sandbox = new Sandbox();

    @Getter
    @Setter
    public class Sandbox {
        private String recipient;
    }
}