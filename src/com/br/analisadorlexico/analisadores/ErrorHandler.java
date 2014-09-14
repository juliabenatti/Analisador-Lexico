package com.br.analisadorlexico.analisadores;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

	try {  
	     File file = new File("filename");  
	   
	     // Create file if it does not exist  
	     boolean success = file.createNewFile();  
	     if (success) {  
	         // File did not exist and was created  
	     } else {  
	         // File already exists  
	     }  
	     BufferedWriter out = new BufferedWriter(new FileWriter("outfilename"));  
	        out.write("aString");  
	        out.close();
	 } catch (IOException e) {  
	 }
}

