package com.br.analisadorlexico.analisadores;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
	OutputStream outfile = new FileOutputStream('saida.html');
	OutputStreamWriter outwriter = new OutputStreamWriter(outfile);
	BufferedWriter bw = new BufferedWriter(outwriter);
	List <String> erros = new ArrayList<String>();
	public void setError(List<String> erros){
		for (String erro : erros)
		//System.out.println(erro);
	}
	public List <String> getErrors(){
		bw.write(erros);
		bw.close();
		return erros;
	}
	
}