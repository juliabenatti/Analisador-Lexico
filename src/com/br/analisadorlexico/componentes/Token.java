package com.br.analisadorlexico.componentes;

public class Token {
	private int id;
	private String token;
	private String lexema;
	private long linha;
	private long coluna;

	public Token(int id, String token, String lexema) {
		this.id = id;
		this.token = token;
		this.lexema = lexema;
	}

	public Token(int id, String token, String lexema, long linha, long coluna) {
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

	public long getLinha() {
		return linha;
	}

	public long getColuna() {
		return coluna;
	}

	public void setLinha(long linha) {
		this.linha = linha;
	}

	public void setColuna(long coluna) {
		this.coluna = coluna;
	}

	public Token() {
		super();
	}
}