package com.br.analisadorlexico.analisadores;

import java.util.List;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;
import com.br.analisadorlexico.leitorarquivo.LeitorArquivo;

public class AnLexico {
	private String caminhoArquivo = "";
	private String lexema;
	private char caracter;
	private boolean codigoValido = false;
	private LeitorArquivo leitorArquivo;
	private TabSimbolos tabSimbolos = TabSimbolos.getInstance();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();
	public Token nextToken() {
		while (true) {
			// Chama, da classe do professor, a leitura do arquivo, passando
			// como parâmetro o caminho
			lexema = "";
			caracter = leitorArquivo.retornaChar(caminhoArquivo); // modificar
																	// para a
																	// classe
																	// real.
			switch (caracter) {
			// Verifique se o caracter (que é único, é válido)
			case '+':
			case '-':
			case '*':
			case '/':
			case ';':
			case '(':
				return tabSimbolos.pesquisaPalavra(String.valueOf(caracter));
				
			case '_':
				
				lexema += String.valueOf(caracter);
				
				while(!Character.isWhitespace(caracter)){
					
					caracter = leitorArquivo.retornaChar(caminhoArquivo);
					
					//Caso o caracter saia dos padrões (_, número ou letra), vai para o erro.
					if(!Character.isDigit(caracter) || !Character.isLetter(caracter) || caracter != '_'){
						codigoValido = false;
						while (!Character.isWhitespace(caracter)) {
							lexema += leitorArquivo
									.retornaChar(caminhoArquivo);
							
						}
						// Salva erro.
						 errorHandler.setError("Token "+ lexema+ " inválido. O número tem caracters que não fazem parte da gramática de números ");//+leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna()
						break;
					}
					
					else if(Character.isDigit(caracter) || Character.isLetter(caracter) || caracter == '_'){
						codigoValido = true;
						lexema += caracter;						
						}
					return tabSimbolos.pesquisaPalavra(lexema);
					}
				break;
				
				
				//** CASO NÃO SEJA SOMENTE UM SÍMBOLO E SIM UM CONJUNTO **//
			default:
				
				// Verifica se é espaço em branco e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (Character.isWhitespace(caracter)) {
					continue;
				}
				
				
				// Verifica se é comentário e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (caracter == '#') {
					caracter = leitorArquivo.retornaChar(caminhoArquivo);
					continueLeituraComentario();
					continue;
				}
				
				//** Análise para números (inteiros ou decimais)
				// Verifica se é um número e faz a análise se a continuação dele é válida e retorna o token (float e int)
				if (Character.isDigit(caracter)) {
					
					int indicePonto = 0, indiceE = 0;
					
					while (true) {
						
						lexema += String.valueOf(caracter);
						
						// Pede um novo caracter ao arquivo
						caracter = leitorArquivo.retornaChar(caminhoArquivo);
						
						// Caso o caracter em análise seja um espaço em branco e o número está ok (não termina com um .), pesquisar token.
						if (Character.isWhitespace(caracter) && !lexema.endsWith(".")) {
							return tabSimbolos.pesquisaPalavra(lexema);// retorna token e finaliza a execução do método
							  
						}
						// Caso o caracter em análise seja um ponto, pela primeira vez
						if (caracter == '.' && indicePonto == 0) {
							lexema += String.valueOf(caracter);
							indicePonto++;
						}
						// Caso o caracter em análise seja um E, pela primeira vez
						//Falar com o professor
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
							 errorHandler.setError("Token "+lexema+" inválido. O número não está definido corretamente, já que . poderia ser incluso no número somente uma vez.");//+leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna()
							break;
						}
						if (caracter == 'E' && indiceE == 1) {
							while (!Character.isWhitespace(caracter)) {
								lexema += leitorArquivo
										.retornaChar(caminhoArquivo);
							}
							// Salva erro.
							errorHandler.setError("Token "+ lexema + " inválido. O número não está definido corretamente, já que E poderia ser incluso no número somente uma vez.");//,leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna()
							break;
						}
				
						if (!Character.isDigit(caracter) && caracter != '.'
								&& caracter != 'E'
								&& !Character.isWhitespace(caracter)) {
							while (!Character.isWhitespace(caracter)) {
								lexema += leitorArquivo
										.retornaChar(caminhoArquivo);
							}
							// Salva erro.
							 errorHandler .setError("Token "+ lexema+" inválido. O número tem caracters que não fazem parte da gramática de números ");//,leitorArquivo.retornaLinha(),leitorArquivo.retornaColuna()
							break;
						}
					}
					continue; // caso nenhum break ou return seja acionado, a
								// execução é feita novamente (looping infito
								// principal desse case de dígitos.
				}
				
				//** Análise para números (inteiros ou decimais)
				// Verifica se é uma letra
				if (Character.isLetter(caracter)) {
					lexema += caracter;
					while (true){
						caracter = leitorArquivo.retornaChar(caminhoArquivo);
						
						if (!Character.isDigit(caracter) && Character.isLetter(caracter) && caracter != '_' ){
							
						}
					}
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

	public void continueLeituraComentario() {
		// enquanto não encontrar o fim do comentário
		while (true) {
			if (caracter != '#') {
				break;
			}
			caracter = leitorArquivo.retornaChar(caminhoArquivo);
		}
	}
	public List <String> retornarErros(){
		return errorHandler.getErrors();
	}
}
// DICAS PARA O TRATAMENTO DE DÍGITOS E LETRAS
// Descartar espaços em branco
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