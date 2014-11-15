package com.br.analisadorlexico.analisadores;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class ErrorHandler {
	private static ErrorHandler instancia = new ErrorHandler();
	private List<String> erros = new ArrayList<String>();

	public void setError(String erro) {
		erros.add(erro);
	}
	public void setError(String erro, long l, long c) {
		erros.add("Linha "+l+" Coluna "+c+" "+erro);
	}
	public List <String> getError() {
		return erros;
	}

	public void writeError() {
		try {
			int indice = AnLexico.caminhoArquivo.lastIndexOf("/");
			String caminhoArquivo = AnLexico.caminhoArquivo.substring(0, indice) ;
			File file = new File(caminhoArquivo, "errorLog.txt");
			FileWriter arquivo = new FileWriter(file);
			for (String e : erros) {
				arquivo.write(e+"\n");
			}
			arquivo.close();
		} catch (Exception e) {
			System.out.println("Arquivo não encontrado.");
		}
	}

	public static ErrorHandler getInstance() {
		return instancia;
	}
}