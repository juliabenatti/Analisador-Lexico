package com.br.analisadorlexico.analisadores;

import com.br.analisadorlexico.componentes.Token;
import com.br.analisadorlexico.leitorarquivo.LeitorDeArquivo;

public class AnLexico {
	private String caminhoArquivo = "";
	private String lexema;
	private char caracter;
	private LeitorDeArquivo leitorArquivo;
	//private ErrorHandler errorHandler = ErrorHandler.getInstance();

	public Token nextToken() {

		while (true) {
			// Chama, da classe do professor, a leitura do arquivo, passando
			// como parâmetro o caminho
			caracter = leitorArquivo.retornaChar(caminhoArquivo); // modificar para a classe real.

			switch (caracter) {
			// Verifique se o caracter (que é único, é válido)
			case '+':
			case '-':
			case '*':
			case '/':
			case ';':
			case '(':
				return pesquisaToken(String.valueOf(caracter));
				
			default:
				// Verifica se é espaço em branco e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (Character.isWhitespace(caracter)) {
					continueLeitura(' ');
					continue;
				}
				// Verifica se é comentário e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (caracter == '#') {
					continueLeitura('#');
					continue;
				}
				// Verifica se é um número e faz a análise se a continuação dele é válida e retorna o token (float e int)
				if (Character.isDigit(caracter)) {
					int indicePonto = 0, indiceE = 0;
					Token token = new Token();

					while (true) {
						lexema += String.valueOf(caracter);
						
						//Pede um novo caracter ao arquivo
						caracter = leitorArquivo.retornaChar(caminhoArquivo);
						
						//Caso o caracter em análise seja um espaço em branco e o número está ok, pesquisar token.
						if (Character.isWhitespace(caracter)&& String.valueOf(lexema.charAt(lexema.length()-2))!=".") {
							token = pesquisaToken(lexema);
							return token; //retorna token e finaliza a execução do método
						}
						
						//Caso o caracter em análise seja um ponto, pela primeira vez
						if (caracter == '.' && indicePonto == 0) {
							lexema += String.valueOf(caracter);
							indicePonto++;
						}
						
						//Caso o caracter em análise seja um E, pela primeira vez
						// Falar com o professor
						if (caracter == 'E' && indiceE == 0) {
							lexema += String.valueOf(caracter);
							indiceE++;
						}


						if (caracter == '.' && indicePonto == 1) {
							while (!Character.isWhitespace(caracter)) {
								lexema += leitorArquivo
										.retornaChar(caminhoArquivo);
							}
							// Salva erro.
							//errorHandler.setError("Token "+lexema+ " inválido. O número não está definido corretamente, já que . poderia ser incluso no número somente uma vez.",leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna());
							lexema = "";
							break;
						}

						if (caracter == 'E' && indiceE == 1) {
							while (!Character.isWhitespace(caracter)) {
								lexema += leitorArquivo
										.retornaChar(caminhoArquivo);
							}
							// Salva erro.
							//errorHandler.setError("Token "+ lexema + " inválido. O número não está definido corretamente, já que E poderia ser incluso no número somente uma vez.",leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna());

							lexema = "";
							break;
						}
						if (Character.isWhitespace(caracter)&& String.valueOf(lexema.charAt(lexema.length()-2))== ".") {
							// Salva erro.
							//errorHandler
							//		.setError("Token "+ lexema+ " inválido. O número não está definido corretamente, já que um número não deve terminar com \".\"",leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna());

							lexema = "";
							break;
						}
						if (!Character.isDigit(caracter) || caracter != '.'
								|| caracter != 'E'
								|| !Character.isWhitespace(caracter)) {
							while (!Character.isWhitespace(caracter)) {
								lexema += leitorArquivo
										.retornaChar(caminhoArquivo);
							}
							// Salva erro.
							//errorHandler		.setError("Token "+ lexema+ " inválido. O número tem caracters que não fazem parte da gramática de números ",leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna());
							lexema = "";
							break;
						}
					}
					continue; //caso nenhum break ou return seja acionado, a execução é feita novamente (looping infito principal desse case de dígitos.
				}
				
				
				// Verifica se é uma letra
				if (Character.isLetter(caracter)) {

				}
				// Verifica se é um "_"
				if (caracter == '_') {

				}

			}
		}
	}

	public AnLexico(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	public AnLexico() {
		super();
	}

	public Token pesquisaToken(String palavra) {
		return null;
	}

	public void continueLeitura(char c) {
		// enquanto não encontrar o fim do comentário
		while (true) {
			if (caracter == c) {
				break;
			}
			caracter = leitorArquivo.retornaChar(caminhoArquivo);
		}
	}
}
// DICAS PARA O TRATAMENTO DE DÍGITOS E LETRAS
//Descartar espaços em branco
// Descarta comentários
// switch (c){
//
// case '(':
// faça algo
// case '"':
// faça algo
// default:
// if (Character.isLetter(c) ou _){
// while (Character.isLetter(c) ou número ou _){
// lexema += c;
// c = Chama, da classe do professor, a leitura do arquivo, passando como
// parâmetro o caminho novamente (se necessário)
//
// }
// }
// Token de retorno = chama o método reconhecerId(lexema)
// retorno token;
// caso seja null, tratar
// no caso de letras, inserir na tabela de simbolos, caso seja válido, com
// linha e coluna (oferecidos pelo professor)
// Senão, salvar erro
