package com.br.analisadorlexico.componentes;

public class Token {
private int id;
private String token;
private String lexema;
public Token(int id, String token,String lexema){
this.id = id;
this.token = token;
this.lexema = lexema;
}
public int getId() {
return id;
}
public String getToken() {
return token;
}
public String getLexema() {
return lexema;
}
public Token(){
	super();
}
}