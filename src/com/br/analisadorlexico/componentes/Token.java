package com.br.analisadorlexico.componentes;

public class Token {
	private int id;
	private String token;
	private String lexema;
	private int linha;
	private int coluna;

	public Token(int id, String token, String lexema) {
		this.id = id;
		this.token = token;
		this.lexema = lexema;
	}

	public int getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public String getLexema() {
		return lexema;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public Token() {
		super();
	}
}