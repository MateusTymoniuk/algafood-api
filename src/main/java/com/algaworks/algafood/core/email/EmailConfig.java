package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SandboxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "smtp")
    public SmtpEnvioEmailService smtpEnvioEmailService() {
        return new SmtpEnvioEmailService();
    }

    @Bean
    public EnvioEmailService EnvioEmailService() {
        switch (emailProperties.getTipo()) {
            case SANDBOX:
                return new SandboxEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case FAKE:
            default:
                return new FakeEnvioEmailService();
        }
    }
}
