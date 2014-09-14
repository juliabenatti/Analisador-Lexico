import java.util.ArrayList;


public class TabSimbolos {
	private static TabSimbolos instancia = new TabSimbolos();
	private ArrayList<Token> listaToken = new ArrayList<Token>();
	
	private TabSimbolos(){
		listaToken.add(new Token(1,"NUM_INT"));
		listaToken.add(new Token(2,"NUM_FLOAT"));
		listaToken.add(new Token(3,"LITERAL"));
		listaToken.add(new Token(4,"ID"));
		listaToken.add(new Token(5,"REL_OP"));
		listaToken.add(new Token(6,"ADDSUB_OP"));
		listaToken.add(new Token(7,"MULTDIV_OP"));
		listaToken.add(new Token(8,"ATTRIB_OP"));
		listaToken.add(new Token(9,"TERM"));
		listaToken.add(new Token(10,"L_PAR"));
		listaToken.add(new Token(11,"R_PAR"));
		listaToken.add(new Token(12,"LOGIC_VAL"));
		listaToken.add(new Token(13,"LOGIC_OP"));
		listaToken.add(new Token(14,"TYPE"));
		listaToken.add(new Token(15,"PROGRAM"));
		listaToken.add(new Token(16,"END_PROG"));
		listaToken.add(new Token(17,"BEGIN"));
		listaToken.add(new Token(18,"END"));
		listaToken.add(new Token(19,"IF"));
		listaToken.add(new Token(20,"THEN"));
		listaToken.add(new Token(21,"ELSE"));
		listaToken.add(new Token(22,"FOR"));
		listaToken.add(new Token(23,"WHILE"));
		listaToken.add(new Token(24,"DECLARE"));
		listaToken.add(new Token(25,"TO"));
	}
	public int pesquisaPalavra(String token){
		int result=0;
		for (Token ltoken : listaToken){
			if(ltoken.getToken().equals(token))
				result = ltoken.getId();
			else
				result = 0;
		}
		return result;
	}
	public TabSimbolos getInstance(){
		return instancia;
	}
}
