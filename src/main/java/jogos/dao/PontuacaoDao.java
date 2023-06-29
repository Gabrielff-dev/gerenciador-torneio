package jogos.dao;

import jogos.model.Jogador;
import jogos.model.Pontuacao;
import java.util.List;

import exceptions.PontuacaoDuplicadaException;
import exceptions.PontuacaoMaxException;
import exceptions.PontuacaoParametroException;

public interface PontuacaoDao {

	public void inserir(int jogadorId, int jogoId, int pontuacaoValor) throws PontuacaoMaxException, PontuacaoDuplicadaException, PontuacaoParametroException;
	
	public void atualizar(Pontuacao pontuacao);
	
	public void apagar(int id);

	public List<Pontuacao> listar();

	public Pontuacao mostraPontuacao(int pontuacao);

	Jogador mostraPorId(int id);

	
}
