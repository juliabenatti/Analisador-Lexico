package com.br.analisadorlexico.analisadores;

import java.io.FileNotFoundException;
import java.util.List;

import com.br.analisadorlexico.componentes.TabSimbolos;
import com.br.analisadorlexico.componentes.Token;
import com.br.analisadorlexico.leitorarquivo.FileHandler;


public class AnLexico {

	private String lexema;
	private char caracter;
	private boolean primeiroCaracter = true;
	private FileHandler leitorArquivo;
	private TabSimbolos tabSimbolos = TabSimbolos.getInstance();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();
    private Token token = new Token();  
	public Token nextToken() {
		try{
		
			if (primeiroCaracter){
				primeiroCaracter = false;
				// Chama, da classe do professor, a leitura do arquivo, passando
				// como parâmetro o caminho
				caracter = leitorArquivo.getNextChar();
			}
			 
		while (true) {
			
			lexema = "";
			
			switch (caracter) {
			// Verifique se o caracter (que é único, é válido)
			case '+':
			case '-':
			case '*':
			case '/':
			case ';':
			case '(':
			case ')':
				return tabSimbolos.pesquisaPalavra(String.valueOf(caracter));
			
				//Verifica _ se é seguido pelos valores aceitos na linguagem
			case '_':			
				lexema += String.valueOf(caracter);
				
				while(Character.isDigit(caracter) || Character.isLetter(caracter) || caracter == '_'){
					
					caracter = leitorArquivo.getNextChar();
					lexema += caracter;
				}
				token = tabSimbolos.pesquisaPalavra(lexema);
				if(token == null){
					//insere na tabela
				}
				return tabSimbolos.pesquisaPalavra(lexema);
				//Verifica aspas simples, se é seguido pelos valores aceitos na linguagem
			case '\'':
				lexema += caracter;
				caracter = leitorArquivo.getNextChar();
				while(caracter != '\''){
					lexema += caracter;
					caracter = leitorArquivo.getNextChar();
				}
				if(caracter == '\''){
					lexema += caracter;
					caracter = leitorArquivo.getNextChar();
				}
				return tabSimbolos.pesquisaPalavra(lexema);
				
				//verifica operador <-
			case '<':
				caracter = leitorArquivo.getNextChar();
				if (caracter == '-'){
					lexema += '<'+caracter;
					return tabSimbolos.pesquisaPalavra(lexema);
				}
				//POSIÇÃO (lin, col) | MENSAGEM 
				else
					errorHandler.setError(leitorArquivo.getLine()+", "+leitorArquivo.getColumn()+" | O caracter < deveria ser seguido por \"-\". ");
				break;
				
				//Verificação para operadores
			case '&':
				lexema += caracter;
				while (caracter !='&' && (caracter =='<' || caracter =='>' || caracter =='=') ){
					caracter = leitorArquivo.getNextChar();
					lexema += caracter;
				}
				caracter = leitorArquivo.getNextChar();
				//teste para os lexemas válidos. Caso não seja
				if(lexema == "&<&" || lexema == "&>&" || lexema == "&>=&" || lexema == "&<=&" || lexema == "&=&" || lexema == "&<>&" )
					return tabSimbolos.pesquisaPalavra(lexema);
				else{
					errorHandler.setError(leitorArquivo.getLine()+", "+leitorArquivo.getColumn()+" | Operador " +lexema+ " não é válido.");
					}
				break; // em caso de erro, realiza a leitura do caracter novamente, para ver se não pertence à outro padrão

				//** CASO NÃO SEJA SOMENTE UM SÍMBOLO E SIM UM CONJUNTO **//
			default:
				
				// Verifica se é espaço em branco e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (Character.isWhitespace(caracter)) {
					caracter = leitorArquivo.getNextChar();
					break;
				}
				
				
				// Verifica se é comentário e ignora, continuando para a próxima interação do while (checar um novo caracter)
				if (caracter == '#') {
					caracter = leitorArquivo.getNextChar();
					continueLeituraComentario();
					continue;
				}
				
				//** Análise para números (inteiros ou decimais)
				// Verifica se é um número e faz a análise se a continuação dele é válida e retorna o token (float e int)
				if (Character.isDigit(caracter)) {
					
					int indicePonto = 0, indiceE = 0;

					//Enquando não sair do padrão de número, . (somente uma ocorrencia) e E (somente uma ocorrencia)
					while (Character.isDigit(caracter) || (caracter == '.' && indicePonto <= 1 ) || (caracter == 'E'&& indiceE <= 1)) {
						lexema += String.valueOf(caracter);
							if (caracter == 'E'){
								indiceE = exponencialEncontrado();
								if(indiceE == 0) //caso + não seja encontrado, mantem somente os número e retorna.
									return tabSimbolos.pesquisaPalavra(lexema);//E irá para erro na próxima execução	
							}
							
							if(caracter == '.'){
								caracter = leitorArquivo.getNextChar();
								
								if(Character.isDigit(caracter) || caracter == 'E'){
									
									//verifica se existe um E depois do .
									if (caracter == 'E'){
										caracter = leitorArquivo.getNextChar();
										if(caracter == '+'){
											lexema += ".E"+String.valueOf(caracter);	
											indiceE++;
										}
										else{
											caracter = 'E';
											return tabSimbolos.pesquisaPalavra(lexema);//E irá para erro na próxima execução
										}
									}
									
									//Somente adiciona o . no lexema
									else{
									lexema += "."+String.valueOf(caracter);	
									indicePonto++;
									}
									
								}
								//Seta erro para ".", já que não existem lexemas começados com ".".
								else{
									errorHandler.setError(leitorArquivo.getLine()+", "+leitorArquivo.getColumn()+" | O uso de . mais de uma vez para um número é inválido.");
									
									caracter = leitorArquivo.getNextChar();
									return tabSimbolos.pesquisaPalavra(lexema);//. irá para erro na próxima execução	
								}
							}
						// Pede um novo caracter ao arquivo
						caracter = leitorArquivo.getNextChar();
					}
					return tabSimbolos.pesquisaPalavra(lexema);// retorna token e finaliza a execução do método
						
				}
				
				//** Análise para números (inteiros ou decimais)
				if (Character.isLetter(caracter)) {
					lexema += caracter;
					while (Character.isDigit(caracter) || Character.isLetter(caracter) || caracter == '_' ){
						caracter = leitorArquivo.getNextChar();
						lexema += caracter;
					}
					token = tabSimbolos.pesquisaPalavra(lexema);
					if(token == null){
						//insere na tabela
					}
					return tabSimbolos.pesquisaPalavra(lexema);
				}
				
				//Erros gerais não pegos anteriormente.
				else{
					errorHandler.setError(leitorArquivo.getLine()+", "+leitorArquivo.getColumn()+" | Caracter " +caracter+ " não é válido. ");
					caracter = leitorArquivo.getNextChar();
				}
				break;

				}
			}
		}catch(Exception e){
				System.out.println("Não foi possível recuperar caracter.");
			}
		return null; //caso nada seja encontrado.
	}
		

	public AnLexico(String caminhoArquivo) {
		try {
			leitorArquivo = new FileHandler(caminhoArquivo);
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
		}
	}

	public AnLexico() {
		super();
	}

	public void continueLeituraComentario() {
		// enquanto não encontrar o fim do comentário
		while (true) {
			
			try{
				caracter = leitorArquivo.getNextChar();
				
				if (caracter == '#') 
					break;
				
			}catch(Exception e){
				System.out.println("Não foi possível recuperar caracter. ");
			}
		}
	}
	public List <String> retornarErros(){
		return errorHandler.getErrors();
	}
	public int exponencialEncontrado(){ //Verifica se depois do E, o próximo caracter é +. Caso não, retorna 0, para o apontamento de um erro e retorna E para que outro lexema possa ser formado.
		int indiceE = 0;
		try {
			caracter = leitorArquivo.getNextChar();
		} catch (Exception e) {
			System.out.println("Não foi possível recuperar caracter. ");
		} 
		if(caracter == '+'){
			lexema += "E"+String.valueOf(caracter);	
			indiceE++;
		}
		else
			caracter = 'E';
		
		return indiceE;//E deve pertencer à outra palavra
	}

	
}
