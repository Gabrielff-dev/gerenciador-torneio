package jogos.model;

public class Pontuacao {


	@Override
	public String toString() {
		return "Pontuacao [pontuacao=" + pontuacao + ", jogador=" + jogador + ", jogo=" + jogo + "]";
	}


	private int pontuacao;
	private Jogador jogador;
	private Jogo jogo;

	

	public Pontuacao(int pontuacao, Jogador jogador, Jogo jogo) {
		super();
		this.pontuacao = pontuacao;
		this.jogador = jogador;
		this.jogo = jogo;
	}


	public int getPontuacao() {
		return pontuacao;
	}


	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}


	public Jogador getJogador() {
		return jogador;
	}


	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}


	public Jogo getJogo() {
		return jogo;
	}


	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}


	
	
	
}





