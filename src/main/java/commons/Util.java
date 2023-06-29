package commons;

import java.util.List;

import jogos.model.Jogador;
import jogos.model.Jogo;

public class Util {

	public static String criaStringComNomeJogos(List<Jogo> jogos) {
		StringBuilder nomeJogos =  new StringBuilder("Os jogos são: ");
		
		for(Jogo jogo : jogos) {
			nomeJogos.append("\n" + jogo.toString());
		}
		return nomeJogos.toString();
	}
	
	public static String criaStringComNomeJogadores(List<Jogador> jogadores) {
		StringBuilder nomeJogador =  new StringBuilder("Os jogadores são: ");
		
		for(Jogador jogador : jogadores) {
			nomeJogador.append("\n" + jogador.toString());
		}
		return nomeJogador.toString();
	}

}
