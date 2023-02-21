package it.unibs.ing.progetto.ristorante;

/**
 * Scheda di prodotto / Voce 
 * 
 * @author Kevin
 *
 */
public class ProductSheet {
	
	private static final int UNDEFINED = 0;
	private Ingrediente ingrendient;
	private double amount;
	private double consumoProCapite;
	
	public ProductSheet(Ingrediente ingrendient, double amount) {
		super();
		this.ingrendient = ingrendient;
		this.amount = amount;
		this.consumoProCapite = UNDEFINED;
	}
	
	public ProductSheet(Ingrediente ingrendient, double amount, double consumoProCapite) {
		super();
		this.ingrendient = ingrendient;
		this.amount = amount;
		this.consumoProCapite = consumoProCapite;
	}


	public Ingrediente getIngrendient() {
		return ingrendient;
	}


	public void setIngrendient(Ingrediente ingrendient) {
		this.ingrendient = ingrendient;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public double getConsumoProCapite() {
		return consumoProCapite;
	}


	public void setConsumoProCapite(double consumoProCapite) {
		this.consumoProCapite = consumoProCapite;
	}

	@Override
	public String toString() {
		return "ProductSheet [ingrendient=" + ingrendient + ", amount=" + amount + ", consumoProCapite="
				+ consumoProCapite + "]";
	}
	
	
	
	

}
