package com.algaworks.algafood.service;

import com.algaworks.algafood.notificador.NivelUrgencia;
import com.algaworks.algafood.notificador.TipoNotificador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.notificador.Notificador;

@Component
public class AtivacaoClienteService {

	@Autowired
	@TipoNotificador(NivelUrgencia.NORMAL)
	private Notificador notificador;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		notificador.notificar(cliente, "Seu cadastro no sistema está ativo!");
	}
}
