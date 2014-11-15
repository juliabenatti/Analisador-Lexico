package com.br.analisadorlexico.analisadores;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;

public class AnSintatico {
	private AnLexico analisadorLexico;

	public boolean analise(String caminho) {
		analisadorLexico = new AnLexico(caminho);

		procS();
		
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
			ErrorHandler eH = ErrorHandler.getInstance();
			eH.writeError();
			return false;
		}
		
	}
	
	private void procS(){
		Token token = analisadorLexico.nextToken();
		if(token.getToken() == "program"){
			
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
				eH.setError("Faltou um símbolo de atribuição da variável."
						,token.getLinha(),token.getColuna());
		}else
			eH.setError("Faltou um ; no final da linha."
					,token.getLinha(),token.getColuna());
	};

	public void exp() {
		Token token = analisadorLexico.nextToken();
		switch(token.getToken()){
			case "LOGIC_VAL":
				logflw();//falta implementar
				break;
			case "ID":
				genflw();//falta implementar
				break;
			case "NUM_INT":
			case "NUM_FLOAT":
				genflw1();//falta implementar
				break;
			case "L_PAR":
				expn();//falta implementar
				token = analisadorLexico.nextToken();
				if(token.getToken().equals("R_PAR"))
					genflw1();
				break;
			case "LITERAL":
				break;
			default:
				eH.setError("ERRO: Era esperada alguma expressão "
						,token.getLinha(),token.getColuna());
		}
	};

	public void expl() {
	};

	public void logflw() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expl();//falta implementar
		}else{
			analisadorLexico.armazenaToken(token);
		}
	}

	public void genflw() {
		Token token = analisadorLexico.nextToken();
		if(token.getToken().equals("LOGIC_OP")){
			expl();//falta implementar
		}else{
			genflw1();//falta implementar
		}
	}

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
