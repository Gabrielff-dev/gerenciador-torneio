package jogos.model;

public class Jogador {
	protected  int id;
    private String nome;
    protected String apelido;

    public Jogador(String nome, String apelido, int id) {
        this.nome = nome;
        this.apelido = apelido;
        this.id=id;
    }

    
    

	public Jogador(String nome2, String apelido2) {
		// TODO Auto-generated constructor stub
	}


	public Jogador(int pontuacao, int jogador_id, int jogo_id) {
		// TODO Auto-generated constructor stub
	}


	public Jogador(int pontuacao, int jogador_id, int jogo_id, int id2) {
		// TODO Auto-generated constructor stub
	}


	
	
	
	public Jogador(int idJogador, String nomeJogador) {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    @Override
    public String toString() {
        return "nome: " + nome + ", id: " + id + ", apelido: " + apelido + "\n";
    }
}
