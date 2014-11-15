package com.br.analisadorlexico.componentes;

import java.util.HashMap;
import java.util.Map;

public class TabSimbolos {
	private static TabSimbolos instancia = new TabSimbolos();
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
		if (tabelaSimbolos.containsKey(lex.toLowerCase())){
			token = tabelaSimbolos.get(lex);
			String tipo = token.getToken();
			tabelaSimbolos.remove(lex);
			token = new Token(2, tipo, lex, l, c);
			tabelaSimbolos.put(tipo.toLowerCase(), token);
			
			return token;
		}
		else{
			token = new Token(4,"ID", lex.trim(), l, c);
			tabelaSimbolos.put(lex.trim(), token);
			return token;
		}
	}
		
		public Token retornaNumero(String lex, long l, long c){
			if(lex.contains("."))
				return (new Token(2, "NUM_FLOAT", lex, l, c));
				
			
			else if (!lex.contains("."))
				return (new Token(1, "NUM_INT", lex, l, c));
			
			else
				return null;
		}
		
}
