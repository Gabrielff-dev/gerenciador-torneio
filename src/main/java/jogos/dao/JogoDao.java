package jogos.dao;

import jogos.model.Jogador;

import jogos.model.Jogo;

import java.util.List;

import exceptions.JogoDuplicadoException;


public interface JogoDao {
	
    public void inserir(Jogo jogo) throws JogoDuplicadoException;

    public void atualizar(Jogo jogo) throws JogoDuplicadoException;

    public void apagar(int id);

    public List<Jogo> listar();

    public Jogo mostraPorId(int id);


}
