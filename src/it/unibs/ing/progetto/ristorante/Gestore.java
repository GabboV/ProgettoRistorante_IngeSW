package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;

/**
 * 
 * @author Kevin Da implementare le funzioni e responsabilità
 *
 */
public class Gestore {

	/*
	 * 
	 */
	private static final String DEFAULT = "DEFAULT";
	@SuppressWarnings("unused")
	private String userID;
	private DataBase ristoranteDB;

	/*
	 * 
	 */
	public Gestore(String userID, int caricoDilavoroPerPersona, int numeroPostiASedere, double caricoLavoroRistorante,
			DataBase ristoranteDB) {
		super();
		this.userID = userID;
		this.ristoranteDB = ristoranteDB;
	}

	/**
	 * Retrieve dei dati per ricetta
	 * 
	 * @return Ricetta
	 */
	public Ricetta creaRicetta() {

		int porzioni = InputDati.leggiInteroConMinimo("inserisci numero di porzioni --> ", 1);
		double workLoad = InputDati.leggiDoubleConMinimo("Inserisci il workLoad", 0.0);
		ArrayList<ProductSheet> ingredienti = estraiIngredienti();
		return new Ricetta(ingredienti, porzioni, workLoad);
	}

	/**
	 * 
	 * @return lista ingredienti con dosaggi
	 */
	private ArrayList<ProductSheet> estraiIngredienti() {
		boolean on = false;
		ArrayList<ProductSheet> ingredienti = new ArrayList<>();
		do {
			String nomeIngrediente = InputDati.leggiStringaNonVuota("Inserisci nome dell'ingrediente --> ");
			double dose = InputDati.leggiDoubleConMinimo("Inserisci dose (dose must be > 0.0) --> ", 0.0);
			ingredienti.add(new ProductSheet(new Ingrediente(nomeIngrediente, Misura.DEFAULT), dose));
			on = InputDati.yesOrNo("Vuoi aggiungere un altro ingrediente? (Y/N)");
		} while (on);
		return ingredienti;
	}

	
	
}
