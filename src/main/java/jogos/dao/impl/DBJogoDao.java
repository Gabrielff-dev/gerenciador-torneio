package jogos.dao.impl;

import jogos.ConnectionFactory;
import jogos.model.Jogo;
import jogos.dao.JogoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.JogoDuplicadoException;
import exceptions.NomeDuplicadoException;

public class DBJogoDao implements JogoDao {

	public void inserir(Jogo jogo) throws JogoDuplicadoException {
		String sql = "INSERT INTO tb_jogo(id, nome_jogo, tema, pontuacaoMax) VALUES (?, ?, ?, ?)";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			if (jogoJaExiste(c, jogo.getNome_jogo())) {
				throw new JogoDuplicadoException("Jogo já existe no banco de dados");
			}
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, jogo.getId());
			ps.setString(2, jogo.getNome_jogo());
			ps.setString(3, jogo.getTema());
			ps.setInt(4, jogo.getPontuacaoMax());
			ps.execute();
		}catch (JogoDuplicadoException e) {
			throw e; 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private boolean jogoJaExiste(Connection c, String nome_jogo) throws SQLException {
		String sql = "SELECT COUNT(*) FROM tb_jogo WHERE nome_jogo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, nome_jogo);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int count = rs.getInt(1);
			return count > 0;
		}
		return false;
	}

	public void atualizar(Jogo jogo) throws JogoDuplicadoException {
	    String sql = "UPDATE tb_jogo SET nome_jogo = ?, tema = ?, pontuacaoMax = ? WHERE id = ?";
	    ConnectionFactory factory = new ConnectionFactory();
	    try (Connection c = factory.obtemConexao()) {
	        if (jogoJaExiste(c, jogo.getNome_jogo())) {
	            throw new JogoDuplicadoException("Este jogo já existe no banco de dados");
	        }
	        PreparedStatement ps = c.prepareStatement(sql);
	        ps.setString(1, jogo.getNome_jogo());
	        ps.setString(2, jogo.getTema());
	        ps.setInt(3, jogo.getPontuacaoMax());
	        ps.setInt(4, jogo.getId());
	        ps.execute();
	    } catch (JogoDuplicadoException e) {
	        throw e;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void apagar(int id) {
		String sql = "DELETE FROM tb_jogo WHERE id = ?";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Jogo> listar() {
		List<Jogo> jogos = new ArrayList<>();
		String sql = "SELECT * FROM tb_jogo";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome_jogo = rs.getString("nome_jogo");
				String tema = rs.getString("tema");
				int pontuacaoMax = rs.getInt("pontuacaoMax");

				Jogo jogo = new Jogo(nome_jogo, tema, pontuacaoMax, id);
				jogo.setId(id);
				jogos.add(jogo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jogos;
	}

	public Jogo mostraPorId(int id) {
		String sql = "SELECT * FROM tb_jogo	 WHERE id = ?";
		String nome_jogo = "";
		String tema = "";
		int pontuacaoMax = 0;
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nome_jogo = rs.getString("nome_jogo");
				tema = rs.getString("tema");
				pontuacaoMax = rs.getInt("pontuacaoMax");
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Jogo jogo = new Jogo(nome_jogo, tema, pontuacaoMax, id);
		jogo.setId(id);
		return jogo;
	}

}
