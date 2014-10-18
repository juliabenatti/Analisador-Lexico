package com.br.analisadorlexico.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;
import com.br.analisadorlexico.utils.FileHandler;

public class AnLexico {

	private StringBuilder lexema = new StringBuilder();
	private char caracter;
	private ArrayList<Character> caractersPendentes = new ArrayList();
	private boolean primeiroCaracter = true;
	private FileHandler leitorArquivo;
	private TabSimbolos tabSimbolos = TabSimbolos.getInstance();
	private ErrorHandler errorHandler = ErrorHandler.getInstance();
	private Token token = new Token();
	public static String caminhoArquivo;

	public Token nextToken() {

		try {

			if (primeiroCaracter) {
				primeiroCaracter = false;
				// Chama, da classe do professor, a leitura do arquivo, passando
				// como parâmetro o caminho
				caracter = leitorArquivo.getNextChar();
			}

			while (true) {

				lexema.delete(0, lexema.length());

				switch (caracter) {

				// Verifique se o caracter (que é único) é válido
				case '+':
				case '-':
				case '*':
				case '/':
				case ';':
				case '(':
				case ')':
					primeiroCaracter = true;
					return tabSimbolos.pesquisaPalavra(
							String.valueOf(caracter), leitorArquivo.getLine(),
							leitorArquivo.getColumn());

					// Verifica _ se é seguido pelos valores aceitos na
					// linguagem
				case '_':
					lexema.append(caracter);

					while (Character.isDigit(caracter)
							|| Character.isLetter(caracter) || caracter == '_') {

						caracter = leitorArquivo.getNextChar();
						lexema.append(caracter);
					}
					token = tabSimbolos.pesquisaPalavra(String.valueOf(lexema),
							leitorArquivo.getLine(), leitorArquivo.getColumn());
					if (token == null) {
						// insere na tabela
					}
					return tabSimbolos.pesquisaPalavra(String.valueOf(lexema),
							leitorArquivo.getLine(), leitorArquivo.getColumn());

					// Verifica aspas simples, se é seguido pelos valores
					// aceitos na linguagem
				case '\'':
					lexema.append(caracter);
					caracter = leitorArquivo.getNextChar();

					while (caracter != '\'') {
						lexema.append(caracter);
						caracter = leitorArquivo.getNextChar();
					}
					if (caracter == '\'') {
						lexema.append(caracter);
						caracter = leitorArquivo.getNextChar();
					}
					return tabSimbolos.pesquisaPalavra(String.valueOf(lexema),
							leitorArquivo.getLine(), leitorArquivo.getColumn());

					// verifica operador <-
				case '<':
					caracter = leitorArquivo.getNextChar();
					if (caracter == '-') {
						lexema.append('<');
						lexema.append(caracter);
						caracter = leitorArquivo.getNextChar();
						return tabSimbolos.pesquisaPalavra(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());
					} else
						errorHandler
								.setError(leitorArquivo.getLine()
										+ ", "
										+ leitorArquivo.getColumn()
										+ " | O caracter < deveria ser seguido por \"-\". ");
					caracter = leitorArquivo.getNextChar();
					break;

				// Verificação para operadores
				case '&':
					lexema.append(caracter);
					caracter = leitorArquivo.getNextChar();
					boolean primeiraRodada = true;
					while (caracter != '&'
							&& (caracter == '<' || caracter == '>' || caracter == '=')) {
						if (!primeiraRodada) {
							caracter = leitorArquivo.getNextChar();
						}
						primeiraRodada = false;
						lexema.append(caracter);
					}
					caracter = leitorArquivo.getNextChar();
					String lexAnalise = lexema.toString().trim();
					// teste para os lexemas válidos. Caso não seja

					if (lexAnalise.equals("&<&") || lexAnalise.equals("&>&")
							|| lexAnalise.equals("&>=&")
							|| lexAnalise.equals("&<=&")
							|| lexAnalise.equals("&=&")
							|| lexAnalise.equals("&<>&"))
						return tabSimbolos.pesquisaPalavra(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());

					else {
						errorHandler.setError(leitorArquivo.getLine() + ", "
								+ leitorArquivo.getColumn() + " | Operador "
								+ lexema + " não é válido.");
					}
					break; // em caso de erro, realiza a leitura do caracter
							// novamente, para ver se não pertence à outro
							// padrão

				// ** CASO NÃO SEJA SOMENTE UM SÍMBOLO E SIM UM CONJUNTO **//
				default:

					// Verifica se é espaço em branco e ignora, continuando para
					// a próxima interação do while (checar um novo caracter)
					if (Character.isWhitespace(caracter)) {
						caracter = leitorArquivo.getNextChar();
						break;
					}
					// Verifica se /n e ignora
					if (caracter == Character.LINE_SEPARATOR) {
						caracter = leitorArquivo.getNextChar();
						break;
					}

					// Verifica se é comentário e ignora, continuando para a
					// próxima interação do while (checar um novo caracter)
					if (caracter == '#') {
						caracter = leitorArquivo.getNextChar();
						continueLeituraComentario();
						caracter = leitorArquivo.getNextChar();
						continue;
					}

					// ** Análise para números (inteiros ou decimais)
					// Verifica se é um número e faz a análise se a continuação
					// dele é válida e retorna o token (float e int)
					if (Character.isDigit(caracter)) {
						int indicePonto = 0, indiceE = 0;
						// Enquando não sair do padrão de número, . (somente uma
						// ocorrencia) e E (somente uma ocorrencia)
						while (Character.isDigit(caracter)
								|| (caracter == '.' && indicePonto <= 1)
								|| (caracter == 'E' && indiceE <= 1)) {
							lexema.append(caracter);
							if (caracter == 'E') {
								indiceE = exponencialEncontrado();
								if (indiceE == 0) // caso + não seja encontrado,
									// mantem somente os número
									// e retorna.
									return tabSimbolos.pesquisaPalavra(
											String.valueOf(lexema),
											leitorArquivo.getLine(),
											leitorArquivo.getColumn());// E irá
																		// para
																		// erro
								// na próxima
								// execução
							}
							if (caracter == '.') {
								caracter = leitorArquivo.getNextChar();
								if (Character.isDigit(caracter)
										|| caracter == 'E') {
									// verifica se existe um E depois do .
									if (caracter == 'E') {
										caracter = leitorArquivo.getNextChar();
										if (caracter == '+') {
											lexema.append(".E");
											lexema.append(caracter);
											indiceE++;
										} else {
											caracter = 'E';
											return tabSimbolos.pesquisaPalavra(
													String.valueOf(lexema),
													leitorArquivo.getLine(),
													leitorArquivo.getColumn());// E
											// irá
											// para
											// erro
											// na
											// próxima
											// execução
										}
									}
									// Somente adiciona o . no lexema
									else {
										lexema.append(".");
										lexema.append(caracter);
										indicePonto++;
									}
								}
								// Seta erro para ".", já que não existem
								// lexemas começados com ".".
								else {
									errorHandler
											.setError(leitorArquivo.getLine()
													+ ", "
													+ leitorArquivo.getColumn()
													+ " | O uso de . mais de uma vez para um número é inválido.");
									caracter = leitorArquivo.getNextChar();
									return tabSimbolos.pesquisaPalavra(
											String.valueOf(lexema),
											leitorArquivo.getLine(),
											leitorArquivo.getColumn());// . irá
																		// para
																		// erro
									// na próxima
									// execução
								}
							}
							// Pede um novo caracter ao arquivo
							caracter = leitorArquivo.getNextChar();
						}
						return tabSimbolos.pesquisaPalavra(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());// retorna token e
															// finaliza a
						// execução do método
					}

					// ** Análise para letras (possíveis palavras reservadas
					if (Character.isLetter(caracter)) {
						lexema.append(caracter);
						while (Character.isDigit(caracter)
								|| Character.isLetter(caracter)
								|| caracter == '_') {
							caracter = leitorArquivo.getNextChar();
							if (Character.isDigit(caracter)
									|| Character.isLetter(caracter)
									|| caracter == '_')
								lexema.append(caracter);
						}
						return tabSimbolos.pesquisaPalavra(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());
					}

					// Erros gerais não pegos anteriormente.
					else {
						errorHandler.setError(leitorArquivo.getLine() + ", "
								+ leitorArquivo.getColumn() + " | Caracter "
								+ caracter + " não é válido. ");
						caracter = leitorArquivo.getNextChar();
					}
					break;

				}
			}

		} catch (EOFException eo) {
			return tabSimbolos.pesquisaPalavra("eof", leitorArquivo.getLine(),
					leitorArquivo.getColumn());
		}

		catch (IOException io) {
			return tabSimbolos.pesquisaPalavra("eof", leitorArquivo.getLine(),
					leitorArquivo.getColumn());
		} catch (Exception e) {
			System.out.println("Não foi possível recuperar caracter.");
		}
		return null; // caso nada seja encontrado.
	}

	public void continueLeituraComentario() throws EOFException, IOException {
		// enquanto não encontrar o fim do comentário
		while (true) {

			caracter = leitorArquivo.getNextChar();

			if (caracter == '#')
				break;

		}
	}

	public List<String> retornarErros() {
		return errorHandler.getError();
	}

	public int exponencialEncontrado() throws EOFException, IOException { // Verifica
																			// se
																			// depois
																			// do
																			// E,
																			// o
																			// próximo
		// caracter é +. Caso não, retorna
		// 0, para o apontamento de um erro
		// e retorna E para que outro lexema
		// possa ser formado.
		int indiceE = 0;

		caracter = leitorArquivo.getNextChar();

		if (caracter == '+') {
			// lexema.append("E");
			lexema.append(caracter);
			indiceE++;
		} else
			caracter = 'E';

		return indiceE;// E deve pertencer à outra palavra
	}

	public AnLexico(String caminhoArquivo) {
		try {
			this.caminhoArquivo = caminhoArquivo;
			leitorArquivo = new FileHandler(caminhoArquivo);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
		}
	}

	public AnLexico() {
		super();
	}

}