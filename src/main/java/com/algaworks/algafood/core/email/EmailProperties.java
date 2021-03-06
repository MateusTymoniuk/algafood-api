package com.algaworks.algafood.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("algafood.email")
public class EmailProperties {

    @NotBlank
    private String remetente;

    private TipoEnvioEmail tipo = TipoEnvioEmail.FAKE;

    private String sandbox;

    public enum TipoEnvioEmail {
        FAKE, SANDBOX, SMTP;
    }
}
