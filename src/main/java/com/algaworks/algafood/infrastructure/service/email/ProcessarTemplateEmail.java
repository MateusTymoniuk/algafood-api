package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ProcessarTemplateEmail {

    @Autowired
    private Configuration freeMarkerConfig;

    public String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            Template template = freeMarkerConfig.getTemplate(mensagem.getTemplate());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getDados());
        } catch (Exception e) {
            throw new EmailException("Não foi possível processar o template do e-mail", e);
        }
    }
}
