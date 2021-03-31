package com.algaworks.algafood.config;

import com.algaworks.algafood.notificador.NotificadorEmailMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.notificador.NotificadorEmail;
import org.springframework.context.annotation.Profile;

@Configuration
public class NotificadorConfig {

	@Autowired
	private NotificadorEmailProperties properties;

	@Bean
	@Profile("prod")
	public NotificadorEmail notificadorEmail() {
		NotificadorEmail notificadorEmail = new NotificadorEmail(getHostServidorEmail());
		notificadorEmail.setCaixaAlta(false);
		
		return notificadorEmail;
	}

	@Bean
	@Profile("dev")
	public NotificadorEmailMock notificadorEmailMock() {
		NotificadorEmailMock notificadorEmailMock = new NotificadorEmailMock(getHostServidorEmail());
		notificadorEmailMock.setCaixaAlta(false);

		return notificadorEmailMock;
	}

	private String getHostServidorEmail() {
		StringBuilder builder = new StringBuilder();
		builder.append(properties.getHostServidor());
		builder.append(":");
		builder.append(properties.getPortaServidor());
		return builder.toString();
	}
}
