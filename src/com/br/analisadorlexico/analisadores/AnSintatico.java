package com.br.analisadorlexico.analisadores;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;

public class AnSintatico {
	private AnLexico analisadorLexico;
	ErrorHandler eH = ErrorHandler.getInstance();
	TabSimbolos tS = TabSimbolos.getInstance();

	public boolean analise(String caminho) {
		analisadorLexico = new AnLexico(caminho);

		procS();

		if (analisadorLexico.retornarErros().size() == 0) {
			System.out.println("Nenhum erro lexico foi encontrado.");
		} else {
			System.out.println("RELATÓRIO DE ERROS:");
			System.out.println("POSIÇÃO (lin, col) | MENSAGEM ");
			for (String erro : analisadorLexico.retornarErros())
				System.out.println(erro);
		}

		System.out.println("");
		System.out.println("ESTADO DA TABELA DE SÍMBOLOS:");
		System.out
				.println("ORDEM | TOKEN | LEXEMA | POSIÇÃO FINAL (lin, col) ");

		int contador = 1;
		for (Token t : tS.getListaToken().values()) {
			System.out.println(contador + " | " + t.getToken() + " | "
					+ t.getLexema() + " | " + t.getLinha() + " | "
					+ t.getColuna());
			contador++;
		}

		if (analisadorLexico.retornarErros().size() == 0)
			return true;
		else {
			eH.writeError();
			return false;
		}

	}

	private void procS() {
		Token token = analisadorLexico.nextToken();
		//Caso começe com program
		if ("PROGRAM".equals(token.getToken())) {
			token = analisadorLexico.nextToken();
			//Se próximo token for ID
			if ("ID".equals(token.getToken())){
				//verifica se ID existe
				analisadorLexico.addIdDeclarado(token);
				token = analisadorLexico.nextToken();
				//Se próximo token for TERM
				if ("TERM".equals(token.getToken())) {
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}else{
					eH.setError("Token ';' era esperado. Verifique a ortografia");
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}
			}else{
				//Caso não seja ID, vai para o próximo
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}else{
					eH.setError("Token ';' era esperado. Verifique a ortografia");
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}
			}
//Caso não começe com 'program'
		} else {
			token = analisadorLexico.nextToken();
			if ("ID".equals(token.getToken())) {
				//verifica se ID existe
				analisadorLexico.verificaDeclaracaoId(token);
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}else{
					eH.setError("Token ';' era esperado. Verifique a ortografia");
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}
			}else{
				//verifica se ID existe
				analisadorLexico.verificaDeclaracaoId(token);
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}else{
					eH.setError("Token ';' era esperado. Verifique a ortografia");
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");

					}else{
						eH.setError("Token 'end_prog' era esperado. Verifique a ortografia");
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("Token ';' era esperado. Verifique a ortografia");
					}
				}
			}
		}
	}

	// tabelaSimbolos.put("true", new Token(12,"LOGIC_VAL", "true"));
	// tabelaSimbolos.put("false", new Token(12,"LOGIC_VAL", "false"));
	// tabelaSimbolos.put("not", new Token(13,"LOGIC_OP", "not"));
	// tabelaSimbolos.put("or", new Token(13,"LOGIC_OP", "or"));
	// tabelaSimbolos.put("and", new Token(13,"LOGIC_OP", "and"));
	// tabelaSimbolos.put("bool", new Token(14,"TYPE", "bool"));
	// tabelaSimbolos.put("text", new Token(14,"TYPE", "text"));
	// tabelaSimbolos.put("int", new Token(14,"TYPE", "int"));
	// tabelaSimbolos.put("float", new Token(14,"TYPE", "float"));
	// tabelaSimbolos.put("program", new Token(15,"PROGRAM", "program"));
	// tabelaSimbolos.put("end_prog", new Token(16,"END_PROG", "end_prog"));
	// tabelaSimbolos.put("begin", new Token(17,"BEGIN", "begin"));
	// tabelaSimbolos.put("end", new Token(18,"END", "end"));
	// tabelaSimbolos.put("if", new Token(19,"IF", "if"));
	// tabelaSimbolos.put("then", new Token(20,"THEM", "then"));
	// tabelaSimbolos.put("else", new Token(21,"ELSE", "else"));
	// tabelaSimbolos.put("for", new Token(22,"FOR", "for"));
	// tabelaSimbolos.put("while", new Token(23,"WHILE", "while"));
	// tabelaSimbolos.put("declare", new Token(24,"DECLARE", "declare"));
	// tabelaSimbolos.put("to", new Token(25,"TO", "to"));

	public void bloco() {
		Token token = analisadorLexico.nextToken();
		if ("BEGIN".equals(token.getToken())) {
			cmds();
			token = analisadorLexico.nextToken();
			if (!"END".equals(token.getToken())) 
				eH.setError("Token end era esperado. Verifique a ortografia");
		
		}
	
		else if("DECLARE".equals(token.getToken()) || "ID".equals(token.getToken()) || "IF".equals(token.getToken()) || "FOR".equals(token.getToken()) || "WHILE".equals(token.getToken()))
			cmd();
		
		else{
			eH.setError("Token "+ token.getLexema()+" não era esperado. Verifique a ortografia");
			while(!("ID".equals(token.getToken()) || "END_PROG".equals(token.getToken()) || "END".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "IF".equals(token.getToken()) || "ELSE".equals(token.getToken())|| "FOR".equals(token.getToken()) || "WHILE".equals(token.getToken()))){
				token = analisadorLexico.nextToken();
			}
			analisadorLexico.armazenaToken(token);
		}
	};

	public void cmds() { // -- Gera vazio.
		Token token = analisadorLexico.nextToken();
		if ("DECLARE".equals(token.getToken())){
			dcflw();
		}
		 else if ("IF".equals(token.getToken())){
			ifflw();
		 }
		 else if ("FOR".equals(token.getToken())) {
			 analisadorLexico.armazenaToken(token);
			 repf();
			 cmds();
		}
		 else if ("WHILE".equals(token.getToken())) {
			 analisadorLexico.armazenaToken(token);
			 repw();
			 cmds();
		 }
		 else if ("ID".equals(token.getToken())){
			    analisadorLexico.verificaDeclaracaoId(token);
				idflw();
		 }
		else
			analisadorLexico.armazenaToken(token);

	};

	public void ifflw() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if ("L_PAR".equals(token.getToken())) {
			expl();
			token = analisadorLexico.nextToken();
			if ("R_PAR".equals(token.getToken())) {
				token = analisadorLexico.nextToken();
				if ("THEN".equals(token.getToken())) {
					bloco();
					cmds();
				}else{
					eH.setError("Token 'then' era esperado. Verifique a ortografia");
					erro = true;
					}
			}else{
				eH.setError("Token 'r_par' era esperado. Verifique a ortografia");
				erro = true;
			}
		}
		else{
			eH.setError("Token 'r_par' era esperado. Verifique a ortografia");
			erro = true;
		}
		
		if (erro){
			while (!("END".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			
			analisadorLexico.armazenaToken(token);
		}
	};

	public void idflw() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if ("ATTRB_OP".equals(token.getToken())) {
			exp();
			token = analisadorLexico.nextToken();
			if ("TERM".equals(token.getToken())) 
				cmds();
			else{
				eH.setError("Token 'term' era esperado. Verifique a ortografia");
				erro = true;
			}
		}
		else{
			eH.setError("Token 'attrb_op' era esperado. Verifique a ortografia");
			erro = true;
		}
		
		if(erro){
			while (!("END".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			
			analisadorLexico.armazenaToken(token);
		}
	};

	public void dcflw() {
		Token token = analisadorLexico.nextToken();
		if ("ID".equals(token.getToken())) {
			Token id = token;
			token = analisadorLexico.nextToken();
			if ("TYPE".equals(token.getToken())) {
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					analisadorLexico.addIdDeclarado(id);
					cmds();
				}
			}
		}
	};

	public void cmd() {
		Token token = analisadorLexico.nextToken();
		
		
		switch(token.getToken()){
			case "DECLARE":
				analisadorLexico.armazenaToken(token);
				decl();
				break;
			case "IF":
				analisadorLexico.armazenaToken(token);
				cond();
				break;
			case "WHILE":
			case "FOR":	
				analisadorLexico.armazenaToken(token);
				rep();
				break;
			case "ID":
				analisadorLexico.armazenaToken(token);
				atrib();
				break;
			default:
				eH.setError("Token "+token.getToken()+" não era esperado. Verifique a ortografia");
				while (!("WHILE".equals(token.getToken())|| "ID".equals(token.getToken())|| "FOR".equals(token.getToken()) || "ELSE".equals(token.getToken()) || "END".equals(token.getToken()) || "END_PROG".equals(token.getToken())))
					token = analisadorLexico.nextToken();
				
				analisadorLexico.armazenaToken(token);
		}
	};

	// declare id type term
	public void decl() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if ("DECLARE".equals(token.getToken())) {
			token = analisadorLexico.nextToken();
			if ("ID".equals(token.getToken())) {
				Token id = token;
				token = analisadorLexico.nextToken();
				if ("TYPE".equals(token.getToken())) {
					token = analisadorLexico.nextToken();
					if ("TERM".equals(token.getToken())) 
						analisadorLexico.addIdDeclarado(id);
						
					else{
						eH.setError("Token 'term' era esperado. Verifique a ortografia");
						erro = true;
					}
				}else{
					eH.setError("Token 'type' era esperado. Verifique a ortografia");
					erro = true;
				}

			}else{
				eH.setError("Token 'id' era esperado. Verifique a ortografia");
				erro = true;
			}

		}else{
			eH.setError("Token 'declare' era esperado. Verifique a ortografia");
			erro = true;
		}
		
		if(erro){
			while (!("WHILE".equals(token.getToken()) || "ID".equals(token.getToken())|| "FOR".equals(token.getToken()) || "ELSE".equals(token.getToken()) || "IF".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "END".equals(token.getToken())|| "END_PROG".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			
			analisadorLexico.armazenaToken(token);
		}
	};

	public void cond() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if ("IF".equals(token.getToken())) {
			token = analisadorLexico.nextToken();
			if ("L_PAR".equals(token.getToken())) {
				expl();
				token = analisadorLexico.nextToken();
				if ("R_PAR".equals(token.getToken())) {
					token = analisadorLexico.nextToken();
					if ("THEN".equals(token.getToken())) {
						bloco();
						cndb();
				}else{
					eH.setError("Token 'then' era esperado. Verifique a ortografia");
					erro = true;
				}
			}else{
				eH.setError("Token 'r_par' era esperado. Verifique a ortografia");
				erro = true;
			}
		}else{
			eH.setError("Token 'l_par' era esperado. Verifique a ortografia");
			erro = true;
		}
	}else{
		eH.setError("Token 'if' era esperado. Verifique a ortografia");
		erro = true;
	}
		
		if(erro){
			while (!("WHILE".equals(token.getToken()) || "ID".equals(token.getToken())|| "FOR".equals(token.getToken()) || "ELSE".equals(token.getToken()) || "IF".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "END".equals(token.getToken())|| "END_PROG".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			
			analisadorLexico.armazenaToken(token);
		}
	};

	public void cndb() { // -- Gera vazio
		Token token = analisadorLexico.nextToken();
		if ("ELSE".equals(token.getToken())) {
			bloco();
		}
		else if(("WHILE".equals(token.getToken()) || "ID".equals(token.getToken())|| "FOR".equals(token.getToken()) || "ELSE".equals(token.getToken()) || "IF".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "END".equals(token.getToken())|| "END_PROG".equals(token.getToken()))){
			analisadorLexico.armazenaToken(token);
		}
		else
			eH.setError("Token "+token.getToken()+" não era esperado. Verifique a ortografia");
	};
// -- FIM MEUS MÉTODOS!
	public void atrib() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("ID")){
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("ATTRIB_OP")){
				exp();
				token = analisadorLexico.nextToken();
				if(!token.getToken().equals("TERM"))
					eH.setError("Faltou um ; no final da linha."
							,token.getLinha(),token.getColuna());
			}else
				eH.setError("Faltou um sÃ­mbolo de atribuiÃ§Ã£o da variÃ¡vel."
						,token.getLinha(),token.getColuna());
		}else
			eH.setError("Faltou um ; no final da linha."
					,token.getLinha(),token.getColuna());
	}

	public void exp() {
		Token token = analisadorLexico.nextToken();
		switch(token.getToken()){
			case "LOGIC_VAL":
				logflw();
				break;
			case "ID":
				genflw();
				break;
			case "NUM_INT":
			case "NUM_FLOAT":
				genflw1();
				break;
			case "L_PAR":
				expn();
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("R_PAR"))
					genflw1();
				else
					eH.setError("ERRO: Era esperado um ) "
							,token.getLinha(),token.getColuna());
				break;
			case "LITERAL":
				break;
			default:
				eH.setError("ERRO: Era esperada alguma expressÃ£o "
						,token.getLinha(),token.getColuna());
		}
	}

	public void expl() {
		Token token = analisadorLexico.nextToken();
		
		switch(token.getToken()){
			case "LOGIC_VAL":
				logflw();
				break;
			case "ID":
				genflw();
				break;
			case "NUM_INT":
			case "NUM_FLOAT":
				genflw1();
				break;
			case "L_PAR":
				expn();
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("R_PAR"))
					genflw1();
				else
					eH.setError("ERRO: Era esperado um ) "
							,token.getLinha(),token.getColuna());
				break;
			default:
				eH.setError("ERRO: Era esperada alguma expressÃ£o "
						,token.getLinha(),token.getColuna());
		}
	}

	public void logflw() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expl();
		}else
			analisadorLexico.armazenaToken(token); // quando pode ser vazio
	}

	public void genflw() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expl();
		}else{
			analisadorLexico.armazenaToken(token); // retorna token para ser processado novamente
			genflw1();
		}
	}

	public void genflw1() {
		termon1();
		expn1();
		genflw2();
	}

	public void genflw2() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("REL_OP")){
			expn();
			genflw3();
		}else
			analisadorLexico.armazenaToken(token); // quando pode ser vazio
	}

	public void genflw3() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expr();
		}
		else
			analisadorLexico.armazenaToken(token); // quando pode ser vazio
	}

	public void expr() {
		expn();
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("REL_OP")){
			expn();
		}else
			eH.setError("ERRO: era esperado um operador relacional (and, or, not) "
					,token.getLinha(),token.getColuna());
	}

	public void expn() {
		termon();
		expn1(); 
	}

	public void expn1() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("ADDSUB_OP")){
			termon();
			expn1();
		}else
			analisadorLexico.armazenaToken(token); // quando pode ser vazio
	}

	public void termon() {
		valn();
		termon1(); 
	}
	
	public void termon1() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("MULTDIV_OP")){
			valn();
			termon1();
		}
		else
			analisadorLexico.armazenaToken(token); // quando pode ser vazio
	}

	public void valn() {
		Token token = analisadorLexico.nextToken();
		switch(token.getToken()){
		case "NUM_INT":
		case "NUM_FLOAT":
		case "ID":
			break;
		case "L_PAR":
			expn();
			token = analisadorLexico.nextToken();
			if(!token.getToken().equals("R_PAR"))
				eH.setError("ERRO: Era esperado um \")\" "
						,token.getLinha(),token.getColuna());
			break;
		default:
			eH.setError("ERRO: Era esperada algum número, ID ou expressao "
					,token.getLinha(),token.getColuna());
			break;
		}
	}

	public void rep() {
		Token token = analisadorLexico.nextToken();
		switch(token.getToken()){
		case "FOR":
			repf();
			break;
		case "WHILE":
			repw();
			break;
		default:
			eH.setError("ERRO: Era esperado um FOR ou WHILE "
					,token.getLinha(),token.getColuna());
			break;
		}
	}

	public void repf() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("ID")){
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("ATTRIB_OP")){
				expn();
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("TO")){
					expn();
					bloco();
				}else
					eH.setError("ERRO: Era esperado um TO "
							,token.getLinha(),token.getColuna());
				
			}else
				eH.setError("ERRO: Era esperado uma atribuicao "
						,token.getLinha(),token.getColuna());
		}else
			eH.setError("ERRO: Era esperado um ID "
					,token.getLinha(),token.getColuna());
	}

	public void repw() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("L_PAR")){
			expl();
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("R_PAR")){
				bloco();
			}else
				eH.setError("ERRO: Era esperado um \")\" "
						,token.getLinha(),token.getColuna());
		}else
			eH.setError("ERRO: Era esperado um \"(\" "
					,token.getLinha(),token.getColuna());
	}
}
