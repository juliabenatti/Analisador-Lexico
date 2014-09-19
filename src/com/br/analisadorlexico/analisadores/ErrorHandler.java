package com.br.analisadorlexico.analisadores;

import java.util.ArrayList;
import java.util.List;

import com.br.analisadorlexico.componentes.TabSimbolos;

public class ErrorHandler {
	private static ErrorHandler instancia = new ErrorHandler();
	private List<String> erros = new ArrayList();
	
	public void setError(String erro){
		System.out.println(erro);
		erros.add(erro);
	}
	public static ErrorHandler getInstance() {
		return instancia;
	}
	public List<String> getErrors(){
		return erros;
	}
	
}
