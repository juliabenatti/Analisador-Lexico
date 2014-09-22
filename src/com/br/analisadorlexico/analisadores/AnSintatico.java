package com.br.analisadorlexico.analisadores;

import javax.swing.JOptionPane;

import com.br.analisadorlexico.componentes.Token;

public class AnSintatico {
		
	public static void main(String[] args) {
		String caminho = JOptionPane
				.showInputDialog("Digite o caminho do arquivo");
		AnLexico analisadorLexico = new AnLexico(caminho);
		Token tk = new Token(1, "teste", "teste");
		do {
			tk = analisadorLexico.nextToken();
			if(tk == null){
				
			}

		} while (true);
	}
}