package com.br.analisadorlexico.analisadores;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.br.analisadorlexico;

public class UseErrorHandler(){
public static void main(String args[]){
ErrorHandler errorhandler = new ErrorHandler();
ErrorHandler.setError('test');
ErrorHandler.getError();
}
}