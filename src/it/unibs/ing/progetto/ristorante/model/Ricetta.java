package it.unibs.ing.progetto.ristorante.model;

import java.util.ArrayList;

public class Ricetta {

	private ArrayList<Prodotto> elencoIngredienti;
	private int numeroPorzioni;
	private int caricoLavoroPorzione;

	public Ricetta(int numeroPorzioni, int caricoLavoroPerPorzione) {
		this.elencoIngredienti = new ArrayList<Prodotto>();
		this.numeroPorzioni = numeroPorzioni;
		this.caricoLavoroPorzione = caricoLavoroPerPorzione;
	}

	public ArrayList<Prodotto> getElencoIngredienti() {
		return elencoIngredienti;
	}

	public void setElencoIngredienti(ArrayList<Prodotto> ingredienti) {
		this.elencoIngredienti = ingredienti;
	}

	public int getNumeroPorzioni() {
		return numeroPorzioni;
	}

	public void setNumeroPorzioni(int numeroPorzioni) {
		this.numeroPorzioni = numeroPorzioni;
	}

	public int getCaricoLavoroPorzione() {
		return caricoLavoroPorzione;
	}

	public void setCaricoLavoroPorzione(int caricoLavoroPerPorzione) {
		this.caricoLavoroPorzione = caricoLavoroPerPorzione;
	}

	
	
	public void addIngrediente(Prodotto ingrediente) {
		boolean esiste = false;
		for(Prodotto i : elencoIngredienti) {
			if(i.getNome().equalsIgnoreCase(ingrediente.getNome())) {
				System.out.println("Hai gia' aggiunto questo ingrediente alla ricetta.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			elencoIngredienti.add(ingrediente);
			System.out.println("E' stata aggiunto un ingrediente.");
			System.out.println("Nome: " + ingrediente.getNome());
			System.out.println("Dose: " + ingrediente.getQuantita() + ingrediente.getUnitaMisura());
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("Ricetta: %s\n", 2));
		b.append(String.format("Porzioni: %d\n", this.numeroPorzioni));
		b.append(String.format("Carico di lavoro: %f\n", this.caricoLavoroPorzione));
		int indice = 1;
		b.append("Ingrediente \t quantità");
		for(Prodotto i: elencoIngredienti) {
			b.append(String.format("%d. %s - %f\n", indice,i.getNome(), i.getQuantita()));
			indice++;
		}
		
		return b.toString();
	}
	
	

}
