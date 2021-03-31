package com.algaworks.algafood.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
@Getter
@Setter
public class NotificadorEmailProperties {

    private String hostServidor;

    private int portaServidor;
}
