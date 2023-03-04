package it.unibs.ing.progetto.ristorante.model;

import java.util.ArrayList;

public class Ricetta {

	private ArrayList<Prodotto> ingredienti;
	private int numeroPorzioni;
	private double caricoLavoroPerPorzione;

	public Ricetta(ArrayList<Prodotto> ingredienti, int numeroPorzioni, double caricoLavoroPerPorzione) {
		super();
		this.ingredienti = ingredienti;
		this.numeroPorzioni = numeroPorzioni;
		this.caricoLavoroPerPorzione = caricoLavoroPerPorzione;
	}

	public ArrayList<Prodotto> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(ArrayList<Prodotto> ingredienti) {
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
	public void addIngrediente(Prodotto ingrediente) {
		this.ingredienti.add(ingrediente);
	}

	public boolean esisteIngrediente(Prodotto x) {
		String xNome = x.getNome();
		for (int i = 0; i < this.ingredienti.size(); i++) {
			if (ingredienti.get(i).getNome().equals(xNome))
				return true;
		}
		return false;
	}

	public void removeIngrediente(Prodotto x) {
		if (this.esisteIngrediente(x)) {
			int rmv = posIngrediente(x);
			this.ingredienti.remove(rmv);
		}

	}

	private int posIngrediente(Prodotto x) {
		String xNome = x.getNome();
		int i = 0;
		for (; i < this.ingredienti.size(); i++) {
			if (ingredienti.get(i).getNome().equals(xNome))
				return i;
		}
		return i;
	}

	/**
	 * Cose
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("Ricetta: %s\n", 2));
		b.append(String.format("Porzioni: %d\n", this.numeroPorzioni));
		b.append(String.format("Carico di lavoro: %f\n", this.caricoLavoroPerPorzione));
		int indice = 1;
		b.append("Ingrediente \t quantità");
		for(Prodotto i: ingredienti) {
			b.append(String.format("%d. %s - %f\n", indice,i.getNome(), i.getQuantita()));
			indice++;
		}
		
		return b.toString();
	}
	
	

}
