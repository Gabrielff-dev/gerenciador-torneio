package jogos.model;

public class Jogo {

	protected String nome_jogo;
	private String tema;
	private int pontuacaoMax;
	private int id;
	
	public Jogo(String nome_jogo, String tema, int pontuacaoMax, int id) {
		super();
		this.id=id;
		this.nome_jogo = nome_jogo;
		this.tema = tema;
		this.pontuacaoMax = pontuacaoMax;
	}
	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public String getNome_jogo() {
		return nome_jogo;
	}
	public void setNome_jogo(String nome_jogo) {
		this.nome_jogo = nome_jogo;
	}
	public String getTema() {
		return tema;
	}
	public void setTema(String tema) {
		this.tema = tema;
	}
	public int getPontuacaoMax() {
		return pontuacaoMax;
	}
	public void setPontuacaoMax(int pontuacaoMax) {
		this.pontuacaoMax = pontuacaoMax;
	}

	@Override
	public String toString() {
		return "Nome: " + nome_jogo + ", tema: " + tema + ", pontuaçãomáxima: " + pontuacaoMax + ", id: " + id + "\n";
	}




}
