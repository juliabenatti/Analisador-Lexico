package com.br.analisadorlexico.analisadores;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.br.analisadorlexico.componentes.Token;

public class AnSintatico {
		
	public boolean analise(String caminho) {
		
		AnLexico analisadorLexico = new AnLexico(caminho);
		List<Token> listaTokens = new ArrayList<Token>();
		Token tk;
		int contador = 0;
		
		System.out.println("TOKENS ENCONTRADOS:");
		System.out.println("ORDEM | TOKEN | LEXEMA | POSIÇÃO (lin, col)");
		
		do {
			tk = new Token();
			tk = analisadorLexico.nextToken();
			if(tk == null){
				//Não exibe nada
			}
			else{
				listaTokens.add(tk);
				System.out.println(contador + " | "+tk.getToken()+" | "+tk.getLexema()+" | "+(tk.getLinha()+", "+tk.getColuna()));
			}

		} while (tk.getToken()!= "EOF");
		System.out.println("");
		
		System.out.println("RELATÓRIO DE ERROS:");
		System.out.println("POSIÇÃO (lin, col) | MENSAGEM ");
		
		for(String erro : analisadorLexico.retornarErros())
			System.out.println(erro);
		
		if(analisadorLexico.retornarErros().size() == 0)
			System.out.println("Nenhum erro léxico foi encontrado.");
		
		System.out.println("");
		
		System.out.println("ESTADO DA TABELA DE SÍMBOLOS:");
		System.out.println("ORDEM | TOKEN | LEXEMA | POSIÇÃO FINAL (lin, col) ");
		//retorna tabela de simbolos - Alvinho
		
		if (analisadorLexico.retornarErros().size() == 0)
			return true;
		else
			return false;
		
	}
}