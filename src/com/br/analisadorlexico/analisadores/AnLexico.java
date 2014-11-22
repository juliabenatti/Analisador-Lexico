package com.br.analisadorlexico.analisadores;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;
import com.br.analisadorlexico.leitorarquivo.FileHandler;

public class AnLexico {

	private StringBuilder lexema = new StringBuilder();
	private char caracter;
	private List<Token> caractersPendentes = new ArrayList<Token>();
	private boolean primeiroCaracter = true;
	private FileHandler leitorArquivo;
	private TabSimbolos tabSimbolos = TabSimbolos.getInstance();
	private ErrorHandler errorHandler = ErrorHandler.getInstance();
	public  static String caminhoArquivo;
	private List<String> idsDeclarados = new ArrayList<String>();

	public Token nextToken() {
		try {
			if(caractersPendentes.size()>0){
				Token token = caractersPendentes.get(caractersPendentes.size()-1);
				caractersPendentes.remove(caractersPendentes.size()-1);
				return token;
			}
			if (primeiroCaracter) {
				primeiroCaracter = false;
				// Chama, da classe do professor, a leitura do arquivo, passando
				// como parÃƒÂ¢metro o caminho
				caracter = leitorArquivo.getNextChar();
			}

			while (true) {

				lexema.delete(0, lexema.length());

				switch (caracter) {

				// Verifique se o caracter (que ÃƒÂ© ÃƒÂºnico) ÃƒÂ© vÃƒÂ¡lido

				case '+':
				case '-':
					primeiroCaracter = true;
					return tabSimbolos.retornaOperadorAddSub(
							String.valueOf(caracter), leitorArquivo.getLine(),
							leitorArquivo.getColumn());
				case '*':
				case '/':
					primeiroCaracter = true;
					return tabSimbolos.retornaOperadorMultDiv(
							String.valueOf(caracter), leitorArquivo.getLine(),
							leitorArquivo.getColumn());
				case ';':
					primeiroCaracter = true;
					return tabSimbolos.retornaTerm(String.valueOf(caracter),
							leitorArquivo.getLine(), leitorArquivo.getColumn());
				case '(':
				case ')':
					primeiroCaracter = true;
					return tabSimbolos.retornaParenteses(
							String.valueOf(caracter), leitorArquivo.getLine(),
							leitorArquivo.getColumn());

					// Verifica _ se ÃƒÂ© seguido pelos valores aceitos na
					// linguagem
				case '_':
					lexema.append(caracter);

					while (Character.isDigit(caracter)
							|| Character.isLetter(caracter) || caracter == '_') {

						caracter = leitorArquivo.getNextChar();
						lexema.append(caracter);
					}
					return tabSimbolos.retornaSalvaPalavra(
							String.valueOf(lexema), leitorArquivo.getLine(),
							leitorArquivo.getColumn());

					// Verifica aspas simples, se ÃƒÂ© seguido pelos valores
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
					return tabSimbolos.retornaLiteral(String.valueOf(lexema),
							leitorArquivo.getLine(), leitorArquivo.getColumn());

					// verifica operador <-
				case '<':
					caracter = leitorArquivo.getNextChar();
					if (caracter == '-') {
						lexema.append('<');
						lexema.append(caracter);
						caracter = leitorArquivo.getNextChar();
						return tabSimbolos.retornaOperadorAtr(
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

				// VerificaÃƒÂ§ÃƒÂ£o para operadores
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
					// teste para os lexemas vÃƒÂ¡lidos. Caso nÃƒÂ£o seja

					if (lexAnalise.equals("&<&") || lexAnalise.equals("&>&")
							|| lexAnalise.equals("&>=&")
							|| lexAnalise.equals("&<=&")
							|| lexAnalise.equals("&=&")
							|| lexAnalise.equals("&<>&"))
						return tabSimbolos.retornaOperadorRelacional(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());

					else {
						errorHandler.setError(leitorArquivo.getLine() + ", "
								+ leitorArquivo.getColumn() + " | Operador "
								+ lexema + " nÃƒÂ£o ÃƒÂ© vÃƒÂ¡lido.");
					}
					break; // em caso de erro, realiza a leitura do caracter
							// novamente, para ver se nÃƒÂ£o pertence ÃƒÂ  outro
							// padrÃƒÂ£o

				// ** CASO NÃƒÆ’O SEJA SOMENTE UM SÃƒï¿½MBOLO E SIM UM CONJUNTO **//
				default:

					// Verifica se ÃƒÂ© espaÃƒÂ§o em branco e ignora, continuando
					// para
					// a prÃƒÂ³xima interaÃƒÂ§ÃƒÂ£o do while (checar um novo caracter)
					if (Character.isWhitespace(caracter)) {
						caracter = leitorArquivo.getNextChar();
						break;
					}
					// Verifica se /n e ignora
					if (caracter == Character.LINE_SEPARATOR
							|| caracter == '\n') {
						caracter = leitorArquivo.getNextChar();
						break;
					}

					// Verifica se ÃƒÂ© comentÃƒÂ¡rio e ignora, continuando para a
					// prÃƒÂ³xima interaÃƒÂ§ÃƒÂ£o do while (checar um novo caracter)
					if (caracter == '#') {
						caracter = leitorArquivo.getNextChar();
						continueLeituraComentario();
						caracter = leitorArquivo.getNextChar();
						continue;
					}

					// ** AnÃƒÂ¡lise para nÃƒÂºmeros (inteiros ou decimais)
					// Verifica se ÃƒÂ© um nÃƒÂºmero e faz a anÃƒÂ¡lise se a
					// continuaÃƒÂ§ÃƒÂ£o
					// dele ÃƒÂ© vÃƒÂ¡lida e retorna o token (float e int)
					if (Character.isDigit(caracter)) {
						int indicePonto = 0, indiceE = 0;
						boolean primeiroLooping = true;

						// Enquando nÃƒÂ£o sair do padrÃƒÂ£o de nÃƒÂºmero, . (somente
						// uma
						// ocorrencia) e E (somente uma ocorrencia)
						try {
							while ((Character.isDigit(caracter)
									|| caracter == 'E' || caracter == '+' || caracter == '.')) {
								if (primeiroLooping) {
									primeiroLooping = false;

									lexema.append(caracter);
									caracter = leitorArquivo.getNextChar();

								}

								if (Character.isDigit(caracter)
										&& lexema.charAt(lexema.length() - 1) != 'E') {
									lexema.append(caracter);
									caracter = leitorArquivo.getNextChar();
								}

								else if (caracter == 'E' && indiceE == 0) {
									indiceE++;
									caracter = leitorArquivo.getNextChar();
									if (caracter == '+') {
										lexema.append("E" + caracter);
										caracter = leitorArquivo.getNextChar();
									} else {
										// devolve dos simbolos
										leitorArquivo.reset();
										leitorArquivo.reset();
										return (tabSimbolos.retornaNumero(
												String.valueOf(lexema),
												leitorArquivo.getLine(),
												leitorArquivo.getColumn()));
									}
								}

								else if (caracter == '.' && indicePonto == 0) {
									indicePonto++;
									caracter = leitorArquivo.getNextChar();
									if (Character.isDigit(caracter)) {
										lexema.append("." + caracter);
										caracter = leitorArquivo.getNextChar();
									} else {
										// devolve dos simbolos
										leitorArquivo.reset();
										leitorArquivo.reset();
										return (tabSimbolos.retornaNumero(
												String.valueOf(lexema),
												leitorArquivo.getLine(),
												leitorArquivo.getColumn()));
									}
								} else {
									leitorArquivo.reset();
									return (tabSimbolos.retornaNumero(
											String.valueOf(lexema),
											leitorArquivo.getLine(),
											leitorArquivo.getColumn()));
								}
							}
							return (tabSimbolos.retornaNumero(
									String.valueOf(lexema),
									leitorArquivo.getLine(),
									leitorArquivo.getColumn()));
						} catch (Exception e) {
							caracter = ' ';
							return (tabSimbolos.retornaNumero(
									String.valueOf(lexema),
									leitorArquivo.getLine(),
									leitorArquivo.getColumn()));
						}
					}

					// ** AnÃƒÂ¡lise para letras (possÃƒÂ­veis palavras reservadas
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
						return tabSimbolos.retornaSalvaPalavra(
								String.valueOf(lexema),
								leitorArquivo.getLine(),
								leitorArquivo.getColumn());
					}

					// Erros gerais nÃƒÂ£o pegos anteriormente.
					else {
						errorHandler.setError(leitorArquivo.getLine() + ", "
								+ leitorArquivo.getColumn() + " | Caracter "
								+ caracter + " nÃƒÂ£o ÃƒÂ© vÃƒÂ¡lido. ");
						caracter = leitorArquivo.getNextChar();
						continue;
					}

				}
			}
		} catch (EOFException eo) {
			return new Token(1, "EOF", "EOF", leitorArquivo.getLine(),
					leitorArquivo.getColumn());
		}

		catch (IOException io) {
			return new Token(1, "EOF", "EOF", leitorArquivo.getLine(),
					leitorArquivo.getColumn());
		} catch (Exception e) {
			System.out.println("NÃƒÂ£o foi possÃƒÂ­vel recuperar caracter.");
		}
		return null; // caso nada seja encontrado.
	}

	public void continueLeituraComentario() throws EOFException, IOException {
		// enquanto nÃƒÂ£o encontrar o fim do comentÃƒÂ¡rio
		while (true) {

			caracter = leitorArquivo.getNextChar();

			if (caracter == '#')
				break;

		}
	}

	public boolean verificaDeclaracaoId(Token token) {
		if (idsDeclarados.contains(token.getLexema()))
			return true;
		 else {
			 errorHandler.setError("Variavel nao declarada no codigo: "
					+ token.getLexema());
			return false;
		}
	}
	
	public void addIdDeclarado(Token token) {
				idsDeclarados.add(token.getLexema());
			}
	
	public void armazenaToken(Token token){
		caractersPendentes.add(token);
	}

	public List<String> retornarErros() {
		return errorHandler.getError();
	}

	public int exponencialEncontrado() throws EOFException, IOException {
		int indiceE = 0;

		caracter = leitorArquivo.getNextChar();

		if (caracter == '+') {
			// lexema.append("E");
			lexema.append(caracter);
			indiceE++;
		} else
			caracter = 'E';

		return indiceE;// E deve pertencer ÃƒÂ  outra palavra
	}

	public AnLexico(String caminhoArquivo) {
		try {
			this.caminhoArquivo = caminhoArquivo;
			leitorArquivo = new FileHandler(caminhoArquivo);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nÃƒÂ£o encontrado.");
		}
	}

	public AnLexico() {
		super();
	}

}
