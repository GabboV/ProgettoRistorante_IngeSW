package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ricetta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Prodotto> elencoIngredienti;
	private int numeroPorzioni;
	private int caricoLavoroPorzione;

	public Ricetta(ArrayList<Prodotto> elencoIngredienti, int numeroPorzioni, int caricoLavoroPerPorzione) {
		this.elencoIngredienti = elencoIngredienti;
		this.numeroPorzioni = numeroPorzioni;
		this.caricoLavoroPorzione = caricoLavoroPerPorzione;
	}

	/**
	 * Crea una lista degli stessi ingredienti, con quantita misurate in proporzione al numero di porzioni. 
	 * (La nuova quantita, per ogni ingrediente, viene calcolata con una proporzione)
	 * @param porzioni
	 * @return
	 */
	public List<Prodotto> getElencoIngredientiPerPorzioni(int porzioni) {
		ArrayList<Prodotto> elenco = new ArrayList<Prodotto>();
		elenco = (ArrayList<Prodotto>) this.elencoIngredienti.stream().map(
				p -> new Prodotto(p.getNome(), (porzioni * p.getQuantita()) / this.numeroPorzioni, p.getUnitaMisura()))
				.collect(Collectors.toList());
		//Meglio usare gli stream, google sostiene che siano piu leggibili e mantenibili
		/* Vecchia implementazione
		 * for (Prodotto p
		 * : elencoIngredienti) { float quantita = (porzioni * p.getQuantita()) /
		 * this.numeroPorzioni; elenco.add(new Prodotto(p.getNome(), quantita,
		 * p.getUnitaMisura())); }
		 */
		return elenco;
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
		elencoIngredienti.add(ingrediente);
	}

}
