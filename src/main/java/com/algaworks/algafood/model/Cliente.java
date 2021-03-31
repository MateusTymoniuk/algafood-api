package com.algaworks.algafood.model;

public class Cliente {

	private String nome;

	private String email;

	private String telefone;
	
	private boolean status = false;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void ativar() {
		this.status = true;
	}

}
