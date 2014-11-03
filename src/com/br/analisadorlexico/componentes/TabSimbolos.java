package com.br.analisadorlexico.componentes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabSimbolos {
	private static TabSimbolos instancia = new TabSimbolos();
	//private ArrayList<Token> listaToken = new ArrayList<Token>();
	//private ArrayList<Codigo> listaCodigo = new ArrayList<Codigo>();
	private Map <String, Token> tabelaSimbolos = new HashMap <String, Token>();
	public Map<String, Token> getListaToken(){
		return tabelaSimbolos;
	}
	private TabSimbolos() { // Iniciliza tokens
		
		tabelaSimbolos.put("true", new Token(12,"LOGIC_VAL", "true"));
		tabelaSimbolos.put("false", new Token(12,"LOGIC_VAL", "false"));
		tabelaSimbolos.put("not", new Token(13,"LOGIC_OP", "not"));
		tabelaSimbolos.put("or", new Token(13,"LOGIC_OP", "or"));
		tabelaSimbolos.put("and", new Token(13,"LOGIC_OP", "and"));
		tabelaSimbolos.put("bool", new Token(14,"TYPE", "bool"));
		tabelaSimbolos.put("text", new Token(14,"TYPE", "text"));
		tabelaSimbolos.put("int", new Token(14,"TYPE", "int"));
		tabelaSimbolos.put("float", new Token(14,"TYPE", "float"));
		tabelaSimbolos.put("program", new Token(15,"PROGRAM", "program"));
		tabelaSimbolos.put("end_prog", new Token(16,"END_PROG", "end_prog"));
		tabelaSimbolos.put("begin", new Token(17,"BEGIN", "begin"));
		tabelaSimbolos.put("end", new Token(18,"END", "end"));
		tabelaSimbolos.put("if", new Token(19,"IF", "if"));
		tabelaSimbolos.put("then", new Token(20,"THEM", "then"));
		tabelaSimbolos.put("else", new Token(21,"ELSE", "else"));
		tabelaSimbolos.put("for", new Token(22,"FOR", "for"));
		tabelaSimbolos.put("while", new Token(23,"WHILE", "while"));
		tabelaSimbolos.put("declare", new Token(24,"DECLARE", "declare"));
		tabelaSimbolos.put("to", new Token(25,"TO", "to"));
		
			}

	public static TabSimbolos getInstance() {
		return instancia;
	}

	public Token retornaOperadorRelacional(String lex, long l, long c){
		
		if (lex .equals("&<&"))		
			return new Token(5, "REL_OP", "&<&", l, c);
		
		else if (lex.equals("&<&"))
			return new Token(5, "REL_OP", "&>&", l, c);
	
		else if(lex.equals("&<=&"))
			return new Token(5, "REL_OP", "&<=&", l, c);
			
		else if(lex.equals("&>=&"))
			return new Token(5, "REL_OP", "&>=&", l, c);

		else if(lex.equals("&=&"))
			return new Token(5, "REL_OP", "&=&",l, c);
			
		else if(lex.equals("&<>&"))
			return new Token(5, "REL_OP", "&=&", l, c);
			
		else
			return null;
		
	}
	
	public Token retornaOperadorAddSub(String lex, long l, long c){
		
		if (lex.equals("+"))
			return new Token(6, "ADDSUB_OP", "+", l, c);

		else if (lex.equals("-"))
			return new Token(6, "ADDSUB_OP", "-");
	
		else
			return null;
	}
	
	public Token retornaOperadorMultDiv(String lex, long l, long c){
		
		if (lex.equals("*"))
			return new Token(7, "MULTDIV_OP", "*", l, c);
			
		else if (lex.equals("/"))
			return new Token(7, "MULTDIV_OP", "/", l, c);
			
		else
			return null;
	}
	
	public Token retornaOperadorAtr(String lex, long l, long c){
		
		if (lex.equals("<-"))
			return new Token(8, "ATTRIB_OP", "<-", l, c);
			
		else
			return null;
	}
	
	public Token retornaTerm(String lex, long l, long c){
		
		if (lex.equals(";"))
			return new Token(9, "TERM", ";", l, c);

		else
			return null;
	}
	
	public Token retornaParenteses(String lex, long l, long c){
		
		if (lex.equals("("))
			return new Token(10, "L_PAR", "(", l, c);
			
		else if(lex.equals(")"))
			return new Token(11, "R_PAR", ")", l, c);

		else
			return null;
	}
	
	public Token retornaLiteral(String lex, long l, long c){
		return new Token(3, "LITERAL", lex, l, c);
		
	}
	
	public Token retornaSalvaPalavra(String lex, long l, long c){
		Token token;
		
		if (tabelaSimbolos.containsKey(lex)){
			token = tabelaSimbolos.get(lex);
			token.setLinha(l);
			token.setColuna(c);
			return token;
		}
		else{
			token = new Token(4,"ID", lex.trim(), l, c);
			tabelaSimbolos.put(lex.trim(), token);
			return token;
		}
		}
		
		public Token retornaNumero(String lex, long l, long c){
			if(isInt(lex))
				return (new Token(1, "NUM_INT", lex, l, c));
			
			else if (isFloat(lex))
				return (new Token(2, "NUM_FLOAT", lex, l, c));
			
			else
				return null;
		}
	
//	public Token pesquisaPalavra(String lex, long l, long c) {
//		
////		if (lex.charAt(0) == '\''){
////			
////			listaToken.get(2).setLexema(lex);
////			listaToken.get(2).setLinha(l);
////			listaToken.get(2).setColuna(c);
////			return listaToken.get(2);
////		}
//		if (isInt(lex)){
//			 // Retorna NUM_INT primeiro
//			listaToken.get(0).setLexema(lex);
//			listaToken.get(0).setLinha(l);
//			listaToken.get(0).setColuna(c);
//			return listaToken.get(0);
//		}
////			// Retornar NUM_FLOAT primeiro vai dar errado
//		if (isFloat(lex)){
//			 // Retorna NUM_FLOAT se for float
//			listaToken.get(1).setLexema(lex);
//			listaToken.get(1).setLinha(l);
//			listaToken.get(1).setColuna(c);
//			return listaToken.get(1);
//		}
//		for (Token ltoken : listaToken)
//			// Pega token por token
//			if (ltoken.getLexema().equals(lex.trim())) { // Verifica se o token existe
//				ltoken.setLinha(l);
//				ltoken.setColuna(c);
//				return ltoken;
//			} // Se existe, grava linha/coluna e retorna o token
//		Token x = new Token(4, "ID", lex.trim(), l, c); // Se nao existe, considera que
//													// eh ID
//		listaToken.add(x);
//		return x;
//	}

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