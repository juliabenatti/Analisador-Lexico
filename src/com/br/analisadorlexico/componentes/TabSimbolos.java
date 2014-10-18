package com.br.analisadorlexico.componentes;

import java.util.ArrayList;

public class TabSimbolos {
	private static TabSimbolos instancia = new TabSimbolos();
	private ArrayList<Token> listaToken = new ArrayList<Token>();
	private ArrayList<Codigo> listaCodigo = new ArrayList<Codigo>();

	public ArrayList<Token> getListaToken(){
		return listaToken;
	}
	private TabSimbolos() { // Iniciliza tokens
		listaToken.add(new Token(1, "NUM_INT", "inteiro"));
		listaToken.add(new Token(2, "NUM_FLOAT", "float"));
		listaToken.add(new Token(3, "LITERAL", "literal"));
		listaToken.add(new Token(4, "ID", "variavel"));
		listaToken.add(new Token(5, "REL_OP", "&<&"));
		listaToken.add(new Token(5, "REL_OP", "&>&"));
		listaToken.add(new Token(5, "REL_OP", "&<=&"));
		listaToken.add(new Token(5, "REL_OP", "&>=&"));
		listaToken.add(new Token(5, "REL_OP", "&=&"));
		listaToken.add(new Token(5, "REL_OP", "&<>&"));
		listaToken.add(new Token(6, "ADDSUB_OP", "-"));
		listaToken.add(new Token(6, "ADDSUB_OP", "+"));
		listaToken.add(new Token(7, "MULTDIV_OP", "*"));
		listaToken.add(new Token(7, "MULTDIV_OP", "/"));
		listaToken.add(new Token(8, "ATTRIB_OP", "<-"));
		listaToken.add(new Token(9, "TERM", ";"));
		listaToken.add(new Token(10, "L_PAR", "("));
		listaToken.add(new Token(11, "R_PAR", ")"));
		listaToken.add(new Token(12, "LOGIC_VAL", "true"));
		listaToken.add(new Token(12, "LOGIC_VAL", "false"));
		listaToken.add(new Token(13, "LOGIC_OP", "not"));
		listaToken.add(new Token(13, "LOGIC_OP", "and"));
		listaToken.add(new Token(13, "LOGIC_OP", "or"));
		listaToken.add(new Token(14, "TYPE", "bool"));
		listaToken.add(new Token(14, "TYPE", "text"));
		listaToken.add(new Token(14, "TYPE", "int"));
		listaToken.add(new Token(14, "TYPE", "float"));
		listaToken.add(new Token(15, "PROGRAM", "program"));
		listaToken.add(new Token(16, "END_PROG", "end_prog"));
		listaToken.add(new Token(17, "BEGIN", "begin"));
		listaToken.add(new Token(18, "END", "end"));
		listaToken.add(new Token(19, "IF", "if"));
		listaToken.add(new Token(20, "THEN", "then"));
		listaToken.add(new Token(21, "ELSE", "else"));
		listaToken.add(new Token(22, "FOR", "for"));
		listaToken.add(new Token(23, "WHILE", "while"));
		listaToken.add(new Token(24, "DECLARE", "declare"));
		listaToken.add(new Token(25, "TO", "to"));
		listaToken.add(new Token(26, "EOF", "eof"));
	}

	public static TabSimbolos getInstance() {
		return instancia;
	}

	public Token pesquisaPalavra(String lex, long l, long c) {
		if (lex.charAt(0) == '\''){
			listaToken.get(2).setLexema(lex);
			listaToken.get(2).setLinha(l);
			listaToken.get(2).setColuna(c);
			return listaToken.get(2);
		}
		if (isInt(lex)){
			 // Retorna NUM_INT primeiro
			listaToken.get(0).setLexema(lex);
			listaToken.get(0).setLinha(l);
			listaToken.get(0).setColuna(c);
			return listaToken.get(0);
		}
//			// Retornar NUM_FLOAT primeiro vai dar errado
		if (isFloat(lex)){
			 // Retorna NUM_FLOAT se for float
			listaToken.get(1).setLexema(lex);
			listaToken.get(1).setLinha(l);
			listaToken.get(1).setColuna(c);
			return listaToken.get(1);
		}
		for (Token ltoken : listaToken)
			// Pega token por token
			if (ltoken.getLexema().equals(lex.trim())) { // Verifica se o token existe
				ltoken.setLinha(l);
				ltoken.setColuna(c);
				return ltoken;
			} // Se existe, grava linha/coluna e retorna o token
		Token x = new Token(4, "ID", lex.trim(), l, c); // Se nao existe, considera que
													// eh ID
		listaToken.add(x);
		return x;
	}

	public static boolean isInt(String texto) { // Verifica se é INT
		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	public static boolean isFloat(String texto) { // Verifica se e FLOAT
		try {
			Float.parseFloat(texto);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
}