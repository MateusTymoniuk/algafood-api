package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

    @Autowired
    private ProcessarTemplateEmail processarTemplateEmail;

    @Override
    public void enviar(Mensagem mensagem) {
        String corpoEmail = processarTemplateEmail.processarTemplate(mensagem);

        log.info("Email Fake\n");
        log.info(String.format("Destinatários: %s", mensagem.getDestinatarios()));
        log.info(String.format("Corpo email: \n%s", corpoEmail));
    }
}
