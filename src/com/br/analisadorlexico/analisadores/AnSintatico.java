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
			System.out.println("RELATÃƒâ€œRIO DE ERROS:");
			System.out.println("POSIÃƒâ€¡ÃƒÆ’O (lin, col) | MENSAGEM ");
			for (String erro : analisadorLexico.retornarErros())
				System.out.println(erro);
			
			eH.writeError();
		}

		System.out.println("");
		System.out.println("ESTADO DA TABELA DE SÃƒï¿½MBOLOS:");
		System.out
				.println("ORDEM | TOKEN | LEXEMA | POSIÃƒâ€¡ÃƒÆ’O FINAL (lin, col) ");

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
		if ("PROGRAM".equals(token.getToken())) {
			token = analisadorLexico.nextToken();
			if ("ID".equals(token.getToken())){
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}
			}else{
				eH.setError("Token esperado: ID.  Token recebido: "+token.getToken()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}
			}
//Caso nÃƒÂ£o comeÃƒÂ§e com 'program'
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
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prong'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
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
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					analisadorLexico.armazenaToken(token);
					bloco();
					token = analisadorLexico.nextToken();
					if ("END_PROG".equals(token.getToken())) {
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());

					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'end prog'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						token = analisadorLexico.nextToken();
						if (!"TERM".equals(token.getToken())) 
							eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
									,token.getLinha(),token.getColuna());
					}
				}
			}
		}
	}

	public void bloco() {
		Token token = analisadorLexico.nextToken();
		if ("BEGIN".equals(token.getToken())) {
			cmds();
			token = analisadorLexico.nextToken();
			if (!"END".equals(token.getToken())) 
				eH.setError("SÃƒÂ­mbolo esperado: 'end'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
						,token.getLinha(),token.getColuna());
		
		}
	
		else if("DECLARE".equals(token.getToken()) || "ID".equals(token.getToken()) || "IF".equals(token.getToken()) || "FOR".equals(token.getToken()) || "WHILE".equals(token.getToken()))
			cmd();
		
		else{
			eH.setError("SÃƒÂ­mbolo "+ token.getLexema()+" nÃƒÂ£o era esperado. Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
			while(!("ID".equals(token.getToken()) || "END_PROG".equals(token.getToken()) || "END".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "IF".equals(token.getToken()) || "ELSE".equals(token.getToken())|| "FOR".equals(token.getToken()) || "WHILE".equals(token.getToken()))){
				token = analisadorLexico.nextToken();
			}
			analisadorLexico.armazenaToken(token);
		}
	};

	public void cmds() { 
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
		
		 if ("BEGIN".equals(token.getToken())){
				bloco();
				cmds();
		 }

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
					cndb();
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: 'then'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					erro = true;
					}
			}else{
				eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				erro = true;
			}
		}
		else{
			eH.setError("SÃƒÂ­mbolo esperado: '('.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
					,token.getLinha(),token.getColuna());
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
		if ("ATTRIB_OP".equals(token.getToken())) {
			exp();
			token = analisadorLexico.nextToken();
			if ("TERM".equals(token.getToken()))
				cmds();
			else{
				eH.setError("SÃƒÂ­mbolo esperado: 'term'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				
				erro = true;
			}
		}else{
			eH.setError("SÃƒÂ­mbolo esperado: '<-'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
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
		boolean erro = false;
		if ("ID".equals(token.getToken())) {
			Token id = token;
			token = analisadorLexico.nextToken();
			if ("TYPE".equals(token.getToken())) {
				token = analisadorLexico.nextToken();
				if ("TERM".equals(token.getToken())) {
					analisadorLexico.addIdDeclarado(id);
					cmds();
				}
				else{
					eH.setError("SÃƒÂ­mbolo esperado: 'term'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					erro = true;
				}
			}else{
				eH.setError("SÃƒÂ­mbolo esperado: 'bool, int, float ou text'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
						,token.getLinha(),token.getColuna());
					erro = true;
			}
		}
		else{
			eH.setError("Token esperado: 'ID'.  Token recebido: "+token.getToken()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
			erro = true;
		}
		
		if (erro){
			while (!("END".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token);
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
				eH.setError("SÃƒÂ­mbolo "+token.getToken()+" nÃƒÂ£o era esperado. Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				
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
						eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
					
						erro = true;
					}
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: 'bool, int, float ou text'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					
					erro = true;
				}

			}else{
				eH.setError("Token esperado: 'ID'.  Token recebido: "+token.getToken()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				
				erro = true;
			}

		}else{
			eH.setError("SÃƒÂ­mbolo esperado: 'declare'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
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
					eH.setError("SÃƒÂ­mbolo esperado: 'bool, int, float ou text'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					erro = true;
				}
			}else{
				eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());

				erro = true;
			}
		}else{
			eH.setError("SÃƒÂ­mbolo esperado: '('.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
			erro = true;
		}
	}else{
		eH.setError("SÃƒÂ­mbolo esperado: 'if'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
				,token.getLinha(),token.getColuna());
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
			eH.setError("SÃƒÂ­mbolo "+token.getToken()+" nÃƒÂ£o era esperado. Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			
	};

	public void atrib() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("ID")){
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("ATTRIB_OP")){
				exp();
				token = analisadorLexico.nextToken();
				if(!token.getToken().equals("TERM"))
					eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
			}else
				eH.setError("SÃƒÂ­mbolo esperado: '<-'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
		}else
			eH.setError("SÃƒÂ­mbolo esperado: ';'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
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
					eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
				break;
			case "LITERAL":
				break;
			default:
				eH.setError("SÃƒÂ­mbolo "+token.getLexema()+" nÃƒÂ£o era esperado. Verifique a ortografia"
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
					eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
				break;

			default:
				eH.setError("SÃƒÂ­mbolo "+token.getToken()+" nÃƒÂ£o era esperado. Verifique a ortografia"
						,token.getLinha(),token.getColuna());
		}
	}

	public void logflw() { //Pode gerar vazio
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expl();
		}else
			analisadorLexico.armazenaToken(token); 
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

	public void genflw2() { //Pode gerar vazio
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("REL_OP")){
			expn();
			genflw3();
		}else
			analisadorLexico.armazenaToken(token); 
	}

	public void genflw3() { //Pode gerar vazio
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expr();
		}
		else
			analisadorLexico.armazenaToken(token); 
	}

	public void expr() {
		expn();
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("REL_OP")){
			expn();
		}else{
			eH.setError("SÃƒÂ­mbolo esperado: 'and, or ou not'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			while(!(token.getToken().equals("TERM")|| token.getToken().equals("R_PAR")))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token); 
		}
	}

	public void expn() {
		termon();
		expn1(); 
	}

	public void expn1() { //Pode gerar vazio
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

	public void termon1() { //Pode gerar vazio
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("MULTDIV_OP")){
			valn();
			termon1();
		}
		else
			analisadorLexico.armazenaToken(token); 
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
				eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
			break;
		default:
			eH.setError("SÃƒÂ­mbolo "+token.getLexema()+" nÃƒÂ£o era esperado. Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			while(("WHILE".equals(token.getToken()) || "ID".equals(token.getToken())|| "FOR".equals(token.getToken()) || "IF".equals(token.getToken())|| "DECLARE".equals(token.getToken())|| "TERM".equals(token.getToken())|| "BEGIN".equals(token.getToken()) || "R_PAR".equals(token.getToken()) || "LOGIC_OP".equals(token.getToken()) || "REL_OP".equals(token.getToken()) || "TO".equals(token.getToken()) || "ADDSUB_OP".equals(token.getToken()) || "MULTDIV_OP".equals(token.getToken())))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token);
		}
	}

	public void rep() {
		Token token = analisadorLexico.nextToken();
		switch(token.getToken()){
		case "FOR":
			analisadorLexico.armazenaToken(token);
			repf();
			break;
		case "WHILE":
			analisadorLexico.armazenaToken(token);
			repw();
			break;
		default:
			eH.setError("SÃƒÂ­mbolo esperado: 'for ou while'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+". Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			while(!(token.getToken().equals("ID") || token.getToken().equals("END_PROG")|| token.getToken().equals("END")|| token.getToken().equals("DECLARE")|| token.getToken().equals("IF")|| token.getToken().equals("ELSE")|| token.getToken().equals("FOR")|| token.getToken().equals("WHILE")))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token);
				break;
		}
	}

	public void repf() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if(token.getToken().equals("FOR")){
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("ID")){
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("ATTRIB_OP")){
					expn();
					token = analisadorLexico.nextToken();
					if(token.getToken().equals("TO")){
						expn();
						bloco();
					}else{
						eH.setError("SÃƒÂ­mbolo esperado: 'to'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
								,token.getLinha(),token.getColuna());
						erro = true;
					}
					
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: '<-'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					erro = true;
				}
			}else{
				eH.setError("SÃƒÂ­mbolo esperado: '<-'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				erro = true;
			}
		}else{
			eH.setError("Simbolo esperado: 'FOR'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
					,token.getLinha(),token.getColuna());
			erro = true;
		}
		if (erro){
			while(!(token.getToken().equals("ID") || token.getToken().equals("END_PROG")|| token.getToken().equals("END")|| token.getToken().equals("DECLARE")|| token.getToken().equals("IF")|| token.getToken().equals("ELSE")|| token.getToken().equals("FOR")|| token.getToken().equals("WHILE")))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token);
		}
	}

	public void repw() {
		Token token = analisadorLexico.nextToken();
		boolean erro = false;
		if(token.getToken().equals("WHILE")){
			token = analisadorLexico.nextToken();
			if(token.getToken().equals("L_PAR")){
				expl();
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("R_PAR")){
					bloco();
				}else{
					eH.setError("SÃƒÂ­mbolo esperado: ')'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
							,token.getLinha(),token.getColuna());
					erro = true;
				}
			}else{
				eH.setError("SÃƒÂ­mbolo esperado: '('.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
						,token.getLinha(),token.getColuna());
				erro = true;	
			}
	}else{
		eH.setError("SImbolo esperado: 'WHILE'.  SÃƒÂ­mbolo recebido: "+token.getLexema()+" .Verifique a ortografia"
				,token.getLinha(),token.getColuna());
		erro = true;	
	}
		if (erro){
			while(!(token.getToken().equals("ID") || token.getToken().equals("END_PROG")|| token.getToken().equals("END")|| token.getToken().equals("DECLARE")|| token.getToken().equals("IF")|| token.getToken().equals("ELSE")|| token.getToken().equals("FOR")|| token.getToken().equals("WHILE")))
				token = analisadorLexico.nextToken();
			analisadorLexico.armazenaToken(token);
		}
	}
}
