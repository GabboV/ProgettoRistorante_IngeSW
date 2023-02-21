package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;

public class Ricetta {

	private ArrayList<ProductSheet> ingredienti;
	private int numeroPorzioni;
	private double caricoLavoroPerPorzione;

	public Ricetta(ArrayList<ProductSheet> ingredienti, int numeroPorzioni, double caricoLavoroPerPorzione) {
		super();
		this.ingredienti = ingredienti;
		this.numeroPorzioni = numeroPorzioni;
		this.caricoLavoroPerPorzione = caricoLavoroPerPorzione;
	}

	public ArrayList<ProductSheet> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(ArrayList<ProductSheet> ingredienti) {
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

	/**
	 * Aggiunge ingrediente alla lista
	 * 
	 * @param ingrediente
	 */
	public void addIngrediente(ProductSheet ingrediente) {
		this.ingredienti.add(ingrediente);
	}

	public boolean esisteIngrediente(ProductSheet x) {
		String xNome = x.getIngrendient().getNome();
		for (int i = 0; i < this.ingredienti.size(); i++) {
			if (ingredienti.get(i).getIngrendient().getNome().equals(xNome))
				return true;
		}
		return false;
	}

	public void removeIngrediente(ProductSheet x) {
		if (this.esisteIngrediente(x)) {
			int rmv = posIngrediente(x);
			this.ingredienti.remove(rmv);
		}

	}

	private int posIngrediente(ProductSheet x) {
		String xNome = x.getIngrendient().getNome();
		int i = 0;
		for (; i < this.ingredienti.size(); i++) {
			if (ingredienti.get(i).getIngrendient().getNome().equals(xNome))
				return i;
		}
		return i;
	}

	@Override
	public String toString() {
		return "Ricetta [ingredienti=" + ingredienti + ", numeroPorzioni=" + numeroPorzioni
				+ ", caricoLavoroPerPorzione=" + caricoLavoroPerPorzione + "]";
	}
	
	

}
