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
	public Token(int id, String token, String lexema, int linha, int coluna) {
		this.id = id;
		this.token = token;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
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

	public int getColuna() {
		return coluna;
	}

	public void setLinhaColuna(int linha, int coluna) {
		this.coluna = coluna;
		this.linha = linha
	}

	public Token() {
		super();
	}
}