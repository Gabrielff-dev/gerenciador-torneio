package jogos.dao.impl;

import jogos.ConnectionFactory;
import jogos.model.Jogador;
import jogos.model.Jogo;
import jogos.model.Pontuacao;
import jogos.dao.JogadoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exceptions.JogoDuplicadoException;
import exceptions.NomeDuplicadoException;
import exceptions.PontuacaoDuplicadaException;
import exceptions.PontuacaoMaxException;
import exceptions.PontuacaoParametroException;

public class DBJogadorDao implements JogadoDao {

	public void inserir(Jogador jogador) throws NomeDuplicadoException {
		String sql = "INSERT INTO tb_jogador(id, nome, apelido) VALUES (?, ?, ?)";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			if (jogadorJaExiste(c, jogador.getNome())) {
				throw new NomeDuplicadoException("Jogador já existe no banco de dados");
			}

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, jogador.getId());
			ps.setString(2, jogador.getNome());
			ps.setString(3, jogador.getApelido());
			ps.execute();
		} catch (NomeDuplicadoException e) {
			throw e; 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean jogadorJaExiste(Connection c, String nome) throws SQLException {
		String sql = "SELECT COUNT(*) FROM tb_jogador WHERE nome = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int count = rs.getInt(1);
			return count > 0;
		}
		return false;
	}


	public void atualizar(Jogador jogador) throws NomeDuplicadoException {

		String sql = "UPDATE tb_jogador SET nome = ?, apelido = ? WHERE id = ?";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			if (jogadorJaExiste(c, jogador.getNome())) {
				throw new NomeDuplicadoException("Este nome já existe no banco de dados");
			}
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, jogador.getNome());
			ps.setString(2, jogador.getApelido());
			ps.setInt(3, jogador.getId());
			ps.execute();
		} catch (NomeDuplicadoException e) {
			throw e; 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean apagar(int id) {
		Jogador jogador = mostraPorId(id);
		if(Objects.isNull(jogador)) {
			return false;
		}
		
		String sql = "DELETE FROM tb_jogador WHERE id = ?";
		
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			
			PreparedStatement ps = c.prepareStatement(sql);
		
			ps.setInt(1, id);
			
			ps.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<Jogador> listar() {
		List<Jogador> jogadores = new ArrayList<>();

		String sql = "SELECT * FROM tb_jogador";
	
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
	
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String apelido = rs.getString("apelido");

				Jogador p = new Jogador(nome, apelido, id);
				p.setId(id);
				jogadores.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jogadores;
	}

	public Jogador mostraPorId(int id) {
		String sql = "SELECT * FROM tb_jogador WHERE id = ?";
		String nome = "";
		String apelido = "";
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection c = factory.obtemConexao()) {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				nome = rs.getString("nome");
				apelido = rs.getString("apelido");
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Jogador jogador = new Jogador(nome, apelido, id);
		jogador.setId(id);
		return jogador;
	}



}
