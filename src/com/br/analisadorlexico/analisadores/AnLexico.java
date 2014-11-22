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
	private List<Token> caractersPendentes = new ArrayList<Token>();
	private boolean primeiroCaracter = true;
	private FileHandler leitorArquivo;
	private TabSimbolos tabSimbolos = TabSimbolos.getInstance();
	private ErrorHandler errorHandler = ErrorHandler.getInstance();
	public  static String caminhoArquivo;
	private List<Token> idsDeclarados = new ArrayList<Token>();

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
				// como parÃ¢metro o caminho
				caracter = leitorArquivo.getNextChar();
			}

			while (true) {

				lexema.delete(0, lexema.length());

				switch (caracter) {

				// Verifique se o caracter (que Ã© Ãºnico) Ã© vÃ¡lido

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

					// Verifica _ se Ã© seguido pelos valores aceitos na
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

					// Verifica aspas simples, se Ã© seguido pelos valores
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

				// VerificaÃ§Ã£o para operadores
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
					// teste para os lexemas vÃ¡lidos. Caso nÃ£o seja

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
								+ lexema + " nÃ£o Ã© vÃ¡lido.");
					}
					break; // em caso de erro, realiza a leitura do caracter
							// novamente, para ver se nÃ£o pertence Ã  outro
							// padrÃ£o

				// ** CASO NÃƒO SEJA SOMENTE UM SÃ�MBOLO E SIM UM CONJUNTO **//
				default:

					// Verifica se Ã© espaÃ§o em branco e ignora, continuando
					// para
					// a prÃ³xima interaÃ§Ã£o do while (checar um novo caracter)
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

					// Verifica se Ã© comentÃ¡rio e ignora, continuando para a
					// prÃ³xima interaÃ§Ã£o do while (checar um novo caracter)
					if (caracter == '#') {
						caracter = leitorArquivo.getNextChar();
						continueLeituraComentario();
						caracter = leitorArquivo.getNextChar();
						continue;
					}

					// ** AnÃ¡lise para nÃºmeros (inteiros ou decimais)
					// Verifica se Ã© um nÃºmero e faz a anÃ¡lise se a
					// continuaÃ§Ã£o
					// dele Ã© vÃ¡lida e retorna o token (float e int)
					if (Character.isDigit(caracter)) {
						int indicePonto = 0, indiceE = 0;
						boolean primeiroLooping = true;

						// Enquando nÃ£o sair do padrÃ£o de nÃºmero, . (somente
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

					// ** AnÃ¡lise para letras (possÃ­veis palavras reservadas
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

					// Erros gerais nÃ£o pegos anteriormente.
					else {
						errorHandler.setError(leitorArquivo.getLine() + ", "
								+ leitorArquivo.getColumn() + " | Caracter "
								+ caracter + " nÃ£o Ã© vÃ¡lido. ");
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
			System.out.println("NÃ£o foi possÃ­vel recuperar caracter.");
		}
		return null; // caso nada seja encontrado.
	}

	public void continueLeituraComentario() throws EOFException, IOException {
		// enquanto nÃ£o encontrar o fim do comentÃ¡rio
		while (true) {

			caracter = leitorArquivo.getNextChar();

			if (caracter == '#')
				break;

		}
	}
	public boolean verificaDeclaracaoId(Token token) {
		if (idsDeclarados.contains(token))
			return true;
		else {
			errorHandler.setError("Variavel nao declarada no codigo: "
					+ token.getLexema());
			return false;
		}
	}
	public void addIdDeclarado(Token token) {
		idsDeclarados.add(token);
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

		return indiceE;// E deve pertencer Ã  outra palavra
	}

	public AnLexico(String caminhoArquivo) {
		try {
			this.caminhoArquivo = caminhoArquivo;
			leitorArquivo = new FileHandler(caminhoArquivo);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo nÃ£o encontrado.");
		}
	}

	public AnLexico() {
		super();
	}

}
