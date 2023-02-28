package it.unibs.ing.progetto.ristorante;

/**
 * 
 * @author Kevin
 * 
 */
public class Ingrediente {

	private String nome;
	private Misura unitofMeasure;

	public Ingrediente(String nome, Misura unitofMeasure) {
		super();
		this.nome = nome;
		this.unitofMeasure = unitofMeasure;
	}

	public Ingrediente(String nome, Misura unitofMeasure, TipoIngrediente tipo) {
		super();
		this.nome = nome;
		this.unitofMeasure = unitofMeasure;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Misura getUnitofMeasure() {
		return unitofMeasure;
	}

	public void setUnitofMeasure(Misura unitofMeasure) {
		this.unitofMeasure = unitofMeasure;
	}

	@Override
	public String toString() {
		return "Ingrediente [nome=" + nome + ", unitofMeasure=" + unitofMeasure + "]";
	}


	

}
