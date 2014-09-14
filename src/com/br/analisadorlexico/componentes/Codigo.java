public class Codigo {
	private int linha;
	private int coluna;
	private String token;
	
	public Codigo(int linha, int coluna, String token){
		this.linha = linha;
		this.coluna = coluna;
		this.token = token;
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
}
