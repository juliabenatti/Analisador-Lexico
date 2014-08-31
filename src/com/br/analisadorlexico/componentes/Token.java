package com.br.analisadorlexico.componentes;

public class Token {
	private int id;
	private String valor;
	private String nomeToken;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getNomeToken() {
		return nomeToken;
	}
	public void setNomeToken(String nomeToken) {
		this.nomeToken = nomeToken;
	}

	
}
