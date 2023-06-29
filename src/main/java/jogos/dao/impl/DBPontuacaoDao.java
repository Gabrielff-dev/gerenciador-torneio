package jogos.dao.impl;

import jogos.ConnectionFactory;
import jogos.dao.JogadoDao;
import jogos.dao.JogoDao;
import jogos.model.Jogador;
import jogos.model.Jogo;
import jogos.model.Pontuacao;
import jogos.dao.PontuacaoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import exceptions.PontuacaoMaxException;
import exceptions.PontuacaoParametroException;
import exceptions.PontuacaoDuplicadaException;

public class DBPontuacaoDao implements PontuacaoDao {

	JogoDao dbJogoDao;
	JogadoDao dbJogadorDao;

	public DBPontuacaoDao(JogoDao dbJogoDao, JogadoDao dbJogadorDao) {
		this.dbJogoDao = dbJogoDao;
		this.dbJogadorDao = dbJogadorDao;
	}

	@Override
	public void inserir(int jogadorId, int jogoId, int pontuacaoValor) throws PontuacaoMaxException, PontuacaoDuplicadaException, PontuacaoParametroException {
		validaPontuacao(jogadorId, jogoId, pontuacaoValor);

		String sql = "INSERT INTO tb_pontuacao(jogador_id, jogo_id, pontuacao) VALUES (?, ?, ?);";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, jogadorId);
			ps.setInt(2, jogoId);
			ps.setInt(3, pontuacaoValor);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void validaPontuacao(int jogadorId, int jogoId, int pontuacaoValor)
			throws PontuacaoDuplicadaException, PontuacaoParametroException, PontuacaoMaxException {
		DBJogadorDao dbJogadorDao = new DBJogadorDao();
		DBJogoDao dbJogoDao = new DBJogoDao();		
		Pontuacao pontuacao = mostraPorIdJogoEIdJogador(jogoId, jogadorId);
		if(Objects.nonNull(pontuacao)){	
			throw new PontuacaoDuplicadaException("Uma pontuação do " + pontuacao.getJogador().getNome() + " já foi inserida no jogo " + pontuacao.getJogo().getNome_jogo() + ". A pontuação atual dele é " + pontuacao.getPontuacao());
		}
		Jogador jogador = dbJogadorDao.mostraPorId(jogadorId);
		if(Objects.isNull(jogador)) {
			throw new PontuacaoParametroException("O jogador selecionado não existe", Jogador.class);
		} 
		Jogo jogo = dbJogoDao.mostraPorId(jogoId);
		if(Objects.isNull(jogo)) {
			throw new PontuacaoParametroException("O selecionado não existe", Jogo.class);
		}
		if(pontuacaoValor>jogo.getPontuacaoMax()) {
			throw new PontuacaoMaxException("Você passou uma pontuação maior que a permitida, a pontuação máxima é " + jogo.getPontuacaoMax() + ", a pontuação inserida foi: " + pontuacaoValor);
		}
	}

	@Override
	public void atualizar(Pontuacao pontuacao) {

	}

	@Override
	public void apagar(int id) {

	}

	@Override
	public List<Pontuacao> listar() {
		return null;
	}

	public void mostrarPontuacao() {
		String sql = "SELECT j.nome_jogo, jo.nome, p.pontuacao " +
				"FROM tb_pontuacao p " +
				"JOIN tb_jogo j ON p.jogo_id = j.id " +
				"JOIN tb_jogador jo ON p.jogador_id = jo.id";

		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			System.out.println("Pontuações:");

			while (rs.next()) {
				String nomeJogo = rs.getString("nome_jogo");
				String nomeJogador = rs.getString("nome");
				int pontuacao = rs.getInt("pontuacao");

				System.out.println("Jogo: " + nomeJogo);
				System.out.println("Jogador: " + nomeJogador);
				System.out.println("Pontuação: " + pontuacao);
				System.out.println("------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Jogador mostraPorId(int id_jogo, int id_jogador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pontuacao mostraPontuacao(int pontuacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jogador mostraPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pontuacao mostraPorIdJogoEIdJogador(int idJogo, int idJogador) {

		Pontuacao pontuacao = null;
		try (Connection c = new ConnectionFactory().obtemConexao()){
			DBJogoDao dbJogoDao = new DBJogoDao();
			DBJogadorDao dbJogadorDao = new DBJogadorDao();

			Jogo jogo = dbJogoDao.mostraPorId(idJogo);
			Jogador jogador = dbJogadorDao.mostraPorId(idJogador);

			if(Objects.nonNull(jogo) && Objects.nonNull(jogador)){
				String sql = "SELECT pontuacao FROM tb_pontuacao WHERE jogador_id = ? AND jogo_id = ?;";
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, idJogador); 
				ps.setInt(2, idJogo);     
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
				pontuacao = new Pontuacao(rs.getInt("pontuacao"), jogador, jogo);
				return pontuacao;
				}
			}
			return pontuacao;


		} catch (Exception e) {
			e.printStackTrace();
			return pontuacao;
		} 
	}


	public JogoDao getDbJogoDao() {
		return dbJogoDao;
	}

	private void setDbJogoDao(JogoDao dbJogoDao) {
		this.dbJogoDao = dbJogoDao;
	}

	public JogadoDao getDbJogadorDao() {
		return dbJogadorDao;
	}

	public void setDbJogadorDao(JogadoDao dbJogadorDao) {
		this.dbJogadorDao = dbJogadorDao;
	}
}
