package com.br.analisadorlexico.analisadores;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
	List <String> erros = new ArrayList<String>();
	public void setError(List<String> erros){
		for (String erro : erros)
		System.out.println(erro);
	}
	public List <String> getErrors(){
		return erros;
	}
}
