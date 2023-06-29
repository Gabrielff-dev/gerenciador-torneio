package exceptions;

public class PontuacaoParametroException extends Exception{
	private Class aclass;
	
	public PontuacaoParametroException(String mensagem, Class aclass) {
		super(mensagem);
		this.aclass=aclass;
	}
	
}
