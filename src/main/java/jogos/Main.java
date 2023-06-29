package jogos;

import jogos.dao.JogadoDao;
import jogos.dao.JogoDao;
import jogos.dao.PontuacaoDao;
import jogos.dao.impl.DBJogadorDao;
import jogos.dao.impl.DBJogoDao;
import jogos.dao.impl.DBPontuacaoDao;
import jogos.model.Jogador;
import jogos.model.Jogo;
import jogos.model.Pontuacao;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import commons.Util;
import exceptions.JogoDuplicadoException;
import exceptions.NomeDuplicadoException;
import exceptions.PontuacaoDuplicadaException;
import exceptions.PontuacaoMaxException;
import exceptions.PontuacaoParametroException;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {

		String resposta = JOptionPane.showInputDialog("Digite o número de qual aba deseja acessar:" + "\n1-Gerenciar jogadores" + "\n2-Gerenciar jogos" +  "\n3-Gerenciar pontuação");

		while(!StringUtils.isNumeric(resposta)) {
			mensagemOpInvalida();
			resposta = JOptionPane.showInputDialog("Digite o número de qual aba deseja acessar:" + "\n1-Gerenciar jogadores" + "\n2-Gerenciar jogos" +  "\n3-Gerenciar pontuação");
		}
		if(resposta.equals("1")){
			DBJogadorDao servico = new DBJogadorDao();
			String menu = "1-Cadastrar\n2-Atualizar\n3-Apagar\n4-Listar\n5-Mostrar Um\n0-Sair";
			String op;

			do {
				op = (JOptionPane.showInputDialog(menu));

				while(!StringUtils.isNumeric(op)) {
					mensagemOpInvalida();
					op = (JOptionPane.showInputDialog(menu));
				}

				switch (Integer.valueOf(op)) {
				case 1: {
					int id = 0;
					String nome = JOptionPane.showInputDialog("Insira o nome do jogador:");
					String apelido = JOptionPane.showInputDialog("Insita o apelido do jogador:");
					Jogador p = new Jogador(nome, apelido, id);


					if (validaJogador(nome, apelido)) {
						try {
							servico.inserir(p);
							JOptionPane.showMessageDialog(null, "Jogador inserido com sucesso!");

						} catch (NomeDuplicadoException e) {
							JOptionPane.showMessageDialog(null, "Erro ao inserir jogador: " + e.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "O Nome e o apelido não podem ser vazios!");
					}

					break;

				}
				case 2: {

					int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do jogador que deseja atualizar:"));
					String nome = JOptionPane.showInputDialog("Nome:");
					String apelido = JOptionPane.showInputDialog("Apelido:");


					Jogador p = new Jogador(apelido, apelido, id);
					p.setNome(nome);
					p.setApelido(apelido);
					p.setId(id);
					if (validaJogador(nome, apelido)) {
						try {
							servico.atualizar(p);
							break;
						} catch(NomeDuplicadoException e){
							JOptionPane.showMessageDialog(null, "Erro ao atualizar jogador: " + e.getMessage());
						} catch(Exception e){
							JOptionPane.showMessageDialog(null, "Erro ao atualizar jogador: " + e.getMessage());
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "O Nome e o apelido não podem ser vazios!");
					}
					break;
				}
				case 3: {
					boolean apagado = false;
					String id = JOptionPane.showInputDialog("Insira o id do jogador que deseja remover");
					if(StringUtils.isNumeric(id)) {
						apagado = servico.apagar(Integer.valueOf(id));
						if(apagado) {
							JOptionPane.showMessageDialog(null, "Jogador deletado");
						}
					} 
					if(!StringUtils.isNumeric(id) || !apagado) {
						JOptionPane.showMessageDialog(null, "Id inválido");
					}

					break;
				}
				case 4: {
					List<Jogador> jogadores = servico.listar();

					JOptionPane.showMessageDialog(null, Util.criaStringComNomeJogadores(jogadores));

					break;
				}
				case 5: {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do jogador que deseja ver:"));
					Jogador jogador = new Jogador(menu, menu, id);
					jogador = servico.mostraPorId(id);
					if (!jogador.getNome().equals("")) {
						JOptionPane.showMessageDialog(null, jogador);
					}
				}

				case 0:
					break;
				default:
					JOptionPane.showMessageDialog(null, "Opção inválida");
				}
			} while (confirmaSair(op));
		}


		else if(resposta.equals("2")) {
			DBJogoDao servico = new DBJogoDao();
			String menu = "1-Cadastrar\n2-Atualizar\n3-Apagar\n4-Listar\n5-Mostrar Um\n0-Sair";
			String op;


			do {
				op = (JOptionPane.showInputDialog(menu));

				while(!StringUtils.isNumeric(op)) {
					mensagemOpInvalida();
					op = (JOptionPane.showInputDialog(menu));
				}

				switch (Integer.valueOf(op)) {
				case 1: {
					int id = 0;
					String nome_jogo = JOptionPane.showInputDialog("Insira o nome do jogo:");
					String tema = JOptionPane.showInputDialog("Insira o tema do jogo:");
					String pontuacaoMax = JOptionPane.showInputDialog("Insira a pontuação máxima do jogo:");
					Jogo jogo = new Jogo(nome_jogo, tema, Integer.valueOf(pontuacaoMax), id);

					if (validaJogo(nome_jogo, tema, pontuacaoMax)) {
						try {
							servico.inserir(jogo);
							JOptionPane.showMessageDialog(null, "Jogo inserido com sucesso!");
						} catch(JogoDuplicadoException e) {
							JOptionPane.showMessageDialog(null, "Erro ao inserir jogo: " + e.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Preencha o nome e tema com apenas letras e a pontuação com números");
					}
					break;
				}

				case 2: {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do jogo que deseja atualizar:"));
					String nome_jogo = JOptionPane.showInputDialog("Nome do jogo:");
					String tema = JOptionPane.showInputDialog("Tema do jogo:");
					String pontuacaoMax = JOptionPane.showInputDialog("Pontuação máxima:");

					Jogo jogo = new Jogo(nome_jogo, tema, Integer.valueOf(pontuacaoMax), id);
					if (validaJogo(nome_jogo, tema, pontuacaoMax)) {
						try {
							servico.atualizar(jogo);
							JOptionPane.showMessageDialog(null, "Jogo atualizado com sucesso!");
						} catch (JogoDuplicadoException e) {
							JOptionPane.showMessageDialog(null, "Erro ao atualizar jogo: " + e.getMessage());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Erro ao atualizar jogo: " + e.getMessage());
							e.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Preencha o nome e tema com apenas letras e a pontuação com números");
					}
					break;
				}
				case 3: {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do jogo que deseja remover:"));
					servico.apagar(id);
					break;
				}
				case 4: {
					List<Jogo> jogos = servico.listar();

					JOptionPane.showMessageDialog(null, Util.criaStringComNomeJogos(jogos));

					break;
				}
				case 5: {
					int id = Integer.parseInt(JOptionPane.showInputDialog("Insira o id do jogo que quer ver:"));
					Jogo jogo = servico.mostraPorId(id);
					if (jogo != null && !jogo.getNome_jogo().equals("")) {
						JOptionPane.showMessageDialog(null, jogo);
					}
					break;
				}
				case 0:
					break;
				default:
					JOptionPane.showMessageDialog(null, "Opção inválida");
				}
			} while (confirmaSair(op));

		}



		else if(resposta.equals("3")) {
			DBPontuacaoDao pontuacaoDao = new DBPontuacaoDao(null, null);
			DBJogadorDao jogadorDao = new DBJogadorDao();
			DBJogoDao jogoDao = new DBJogoDao();

			String menu = "1-Cadastrar pontuação\n2-Ver pontuação de um jogador por id\n0-Sair";
			String op;

			do {
				op = (JOptionPane.showInputDialog(menu));

				while(!StringUtils.isNumeric(op)) {
					mensagemOpInvalida();
					op = (JOptionPane.showInputDialog(menu));
				}

				switch (Integer.valueOf(op)) {
				case 1: {
					String id_jogador = JOptionPane.showInputDialog("Insira o ID do jogador:");
					String id_jogo = JOptionPane.showInputDialog("Insira o ID do jogo:");
					String pontuacao = JOptionPane.showInputDialog("Insira a pontuação:");

					if(validaPontuacao(id_jogador, id_jogo, pontuacao)) {
						try {
							pontuacaoDao.inserir(Integer.valueOf(id_jogador), Integer.valueOf(id_jogo), Integer.valueOf(pontuacao));
							JOptionPane.showMessageDialog(null, "Pontuação cadastrada com sucesso");
						} catch (PontuacaoMaxException | PontuacaoDuplicadaException | PontuacaoParametroException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} 
					} else {
						JOptionPane.showMessageDialog(null, "Entre apenas com números");
					}
					break;
				}
				case 2: {
					String id_jogo = JOptionPane.showInputDialog("Insira o ID do jogo que quer ver:");
					String id_jogador = JOptionPane.showInputDialog("Insira o ID do jogador que quer ver:");
					if(vePontuacao(id_jogo, id_jogador)) {
						Pontuacao pontuacao = pontuacaoDao.mostraPorIdJogoEIdJogador(Integer.valueOf(id_jogo), Integer.valueOf(id_jogador));

						if (pontuacao != null) {
							String mensagem = "Pontuação do jogador:\n" +
									"Tema do jogo: " + pontuacao.getJogo().getTema() + "\n" +
									"Nome do jogo: " + pontuacao.getJogo().getNome_jogo() + "\n" +
									"Jogador: " + pontuacao.getJogador().getNome() + "/" + pontuacao.getJogador().getApelido() +"\n" +
									"Pontuação: " + pontuacao.getPontuacao();

							JOptionPane.showMessageDialog(null, mensagem);
						} else {
							JOptionPane.showMessageDialog(null, "Pontuação não encontrada para o jogador e jogo informados.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Entre apenas com números");
					}
				} 
				break;

				case 0: 
					break;
				default:

					JOptionPane.showMessageDialog(null, "Opção inválida");
				}
			} while (confirmaSair(op));
		} else {
			JOptionPane.showMessageDialog(null, "Opção inválida!");
		}
	}



	private static boolean confirmaSair(String op) {
		return !op.equals("0");
	}

	private static void mensagemOpInvalida() {
		JOptionPane.showMessageDialog(null, "Opção invalida, entre apenas com números!");
	}

	private static boolean validaJogador(String nome, String apelido) {
		return StringUtils.isAlpha(nome) && StringUtils.isAlpha(apelido);
	}

	private static boolean validaJogo(String nome_jogo, String tema, String pontuacaoMax) {
		return StringUtils.isAlpha(nome_jogo) && StringUtils.isAlpha(tema);
	}

	private static boolean validaPontuacao(String id_jogador, String id_jogo, String pontuacao) {
		return id_jogador.matches("\\d+") && id_jogo.matches("\\d+") && pontuacao.matches("\\d+");
	}

	private static boolean vePontuacao(String id_jogador, String id_jogo) {
		return id_jogador.matches("\\d+") && id_jogo.matches("\\d+");
	}
}
