package Principale;
/**
 * 
 * @author Kevin
 * 
 *
 */
public class Ingrediente extends Prodotto{
	
	private double dose;

	public Ingrediente(String nome, Misura unita, double _dose) {
		super(nome, unita);
		this.dose = _dose;
		// TODO Auto-generated constructor stub
	}

	public double getDose() {
		return dose;
	}

	public void setDose(double dose) {
		this.dose = dose;
	}
	
	
}
