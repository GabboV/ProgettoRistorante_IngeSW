package Principale;

public class Prodotto {
	
	private String nome;
	private Misura unita;
	
	public Prodotto(String nome, Misura unita) {
		super();
		this.nome = nome;
		this.unita = unita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Misura getUnita() {
		return unita;
	}

	public void setUnita(Misura unita) {
		this.unita = unita;
	}
	
	

}
