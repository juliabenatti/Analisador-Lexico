package com.br.analisadorlexico.analisadores;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import com.br.analisadorlexico.componentes.TabSimbolos;

public class ErrorHandler {
	private static ErrorHandler instancia = new ErrorHandler();
	private List<String> erros = new ArrayList();

	public void setError(String erro) {
		erros.add(erro);
	}
	public List <String> getError() {
		return erros;
	}

	public void writeError() {
		try {
			File file = new File(AnLexico.caminhoArquivo, "banco.txt");
			FileWriter arquivo = new FileWriter(file);
			for (String e : erros) {
				arquivo.write(e);
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