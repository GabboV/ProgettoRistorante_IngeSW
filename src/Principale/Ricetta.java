package Principale;

import java.util.ArrayList;

public class Ricetta {

	private ArrayList<Ingrediente> ingredienti;
	private int numeroPorzioni;
	private double caricoLavoroPerPorzione;
	private Piatto piatto;

	public Ricetta(ArrayList<Ingrediente> ingredienti, int numeroPorzioni, double caricoLavoroPerPorzione,
			Piatto piatto) {
		super();
		this.ingredienti = ingredienti;
		this.numeroPorzioni = numeroPorzioni;
		this.caricoLavoroPerPorzione = caricoLavoroPerPorzione;
		this.piatto = piatto;
	}

	public ArrayList<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public int getNumeroPorzioni() {
		return numeroPorzioni;
	}

	public void setNumeroPorzioni(int numeroPorzioni) {
		this.numeroPorzioni = numeroPorzioni;
	}

	public double getCaricoLavoroPerPorzione() {
		return caricoLavoroPerPorzione;
	}

	public void setCaricoLavoroPerPorzione(double caricoLavoroPerPorzione) {
		this.caricoLavoroPerPorzione = caricoLavoroPerPorzione;
	}

	public Piatto getPiatto() {
		return piatto;
	}

	public void setPiatto(Piatto piatto) {
		this.piatto = piatto;
	}

	/**
	 * Aggiunge ingrediente alla lista
	 * @param ingrediente
	 */
	public void addIngrediente(Ingrediente ingrediente) {
		this.ingredienti.add(ingrediente);
	}
	
	public boolean esisteIngrediente(Ingrediente x) {
		String xNome = x.getNome();
		for(int i = 0; i < this.ingredienti.size();i++) {
			if(ingredienti.get(i).getNome().equals(xNome)) return true;
		}
		return false;
	}
	
	public void removeIngrediente(Ingrediente x) {
		if(this.esisteIngrediente(x)) {
			int rmv = posIngrediente(x);
			this.ingredienti.remove(rmv);
		}
		
	}

	private int posIngrediente(Ingrediente x) {
		String xNome = x.getNome();
		int i=0;
		for(; i < this.ingredienti.size();i++) {
			if(ingredienti.get(i).getNome().equals(xNome)) return i;
		}
		return i;
	}

}
