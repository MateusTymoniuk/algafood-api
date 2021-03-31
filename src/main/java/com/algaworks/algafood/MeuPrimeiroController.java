package com.algaworks.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.algaworks.algafood.model.Cliente;
import com.algaworks.algafood.service.AtivacaoClienteService;

@Controller
public class MeuPrimeiroController {
	
	private AtivacaoClienteService ativacaoCliente;

	public MeuPrimeiroController(AtivacaoClienteService ativacaoCliente) {
		this.ativacaoCliente = ativacaoCliente;
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente cliente = new Cliente();
		cliente.setNome("João");
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("61 98765-4321");
		
		ativacaoCliente.ativar(cliente);
		
		return "alou";
	}
}
