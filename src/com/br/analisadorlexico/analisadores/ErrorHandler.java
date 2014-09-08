package com.br.analisadorlexico.analisadores;

import java.util.List;

public class ErrorHandler {
	public void setError(List<String> erros){
		for (String erro : erros)
		System.out.println(erro);
	}
}
