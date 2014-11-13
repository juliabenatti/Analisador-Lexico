package com.br.analisadorlexico.analisadores;

import java.util.List;
import java.util.ArrayList;
import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;

public class AnSintatico {

	public boolean analise(String caminho) {
		AnLexico analisadorLexico = new AnLexico(caminho);
		List<Token> listaTokens = new ArrayList<Token>();
		Token tk;
		int contador = 1;

		System.out.println("TOKENS ENCONTRADOS:");
		System.out.println("ORDEM | TOKEN | LEXEMA | POSICAO (lin, col)");
		try {
			do {
				tk = new Token();
				tk = analisadorLexico.nextToken();
				if (tk == null) {
					// NÃ£o exibe nada
				} else {
					if (tk.getToken() != "EOF") {
						listaTokens.add(tk);
						System.out.println(contador + " | " + tk.getToken()
								+ " | " + tk.getLexema() + " | "
								+ (tk.getLinha() + ", " + tk.getColuna()));
					}
				}
				contador++;
			} while (!"EOF".equals(tk.getToken()));
			System.out.println("");
		} catch (Exception e) {

		}
		
		if (analisadorLexico.retornarErros().size() == 0){
			System.out.println("Nenhum erro lexico foi encontrado.");
		}else{
			System.out.println("RELATORIO DE ERROS:");
			System.out.println("POSICAO (lin, col) | MENSAGEM ");
			for (String erro : analisadorLexico.retornarErros())
				System.out.println(erro);
		}

		System.out.println("");
		System.out.println("ESTADO DA TABELA DE SIMBOLOS:");
		System.out.println("ORDEM | TOKEN | LEXEMA | POSICAO FINAL (lin, col) ");
		
		TabSimbolos tS = TabSimbolos.getInstance();
		contador = 1;

		for (Token t : tS.getListaToken().values()) {
			System.out.println(contador + " | " + t.getToken() + " | "
					+ t.getLexema() + " | " + t.getLinha() + " | "
					+ t.getColuna());
			contador++;
		}

		if (analisadorLexico.retornarErros().size() == 0)
			return true;
		else {
			ErrorHandler eH = ErrorHandler.getInstance();
			eH.writeError();
			return false;
		}
	}

	public void bloco() {
	};

	public void cmds() {
	};

	public void ifflw() {
	};

	public void idflw() {
	};

	public void dcflw() {
	};

	public void cmd() {
	};

	public void decl() {
	};

	public void cond() {
	};

	public void cndb() {
	};

	public void atrib() {
	};

	public void exp() {
	};

	public void expl() {
	};

	public void logflw() {
	};

	public void genflw() {
	};

	public void genflw1() {
	};

	public void genflw2() {
	};

	public void genflw3() {
	};

	public void expr() {
	};

	public void expn() {
	};

	public void expn1() {
	};

	public void termon() {
	};

	public void termon1() {
	};

	public void valn() {
	};

	public void rep() {
	};

	public void repf() {
	};

	public void repw() {
	};
}
