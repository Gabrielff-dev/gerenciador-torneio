package jogos.dao;

import jogos.model.Jogador;

import java.util.List;

import exceptions.NomeDuplicadoException;

public interface JogadoDao {

    public void inserir(Jogador jogador) throws NomeDuplicadoException;

    public void atualizar(Jogador jogador) throws NomeDuplicadoException;

    public boolean apagar(int id);

    public List<Jogador> listar();

    public Jogador mostraPorId(int id);
}
