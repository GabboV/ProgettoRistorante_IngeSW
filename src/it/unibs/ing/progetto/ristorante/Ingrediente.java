package it.unibs.ing.progetto.ristorante;

/**
 * 
 * @author Kevin
 * 
 */
public class Ingrediente {

	private String nome;
	private Misura unitofMeasure;
	private TipoIngrediente type;

	public Ingrediente(String nome, Misura unitofMeasure) {
		super();
		this.nome = nome;
		this.unitofMeasure = unitofMeasure;
		this.type= TipoIngrediente.RAW;
	}
	
	public Ingrediente(String nome, Misura unitofMeasure, TipoIngrediente tipo) {
		super();
		this.nome = nome;
		this.unitofMeasure = unitofMeasure;
		this.type= tipo;
	}
	
	public TipoIngrediente getType() {
		return type;
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
