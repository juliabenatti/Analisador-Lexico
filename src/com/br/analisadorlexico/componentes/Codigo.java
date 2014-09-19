package com.br.analisadorlexico.componentes;


public class Codigo {
private int linha;
private int coluna;
private String token;
private String lexema;
public Codigo(int linha, int coluna, String token, String lexema){
this.linha = linha;
this.coluna = coluna;
this.token = token;
this.lexema = lexema;
}
public int getLinha(){
return this.linha;
}
public int getColuna(){
return this.coluna;
}
public String getToken(){
return this.token;
}
public String getLexema(){
return this.lexema;
}
}