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
	private static final String[] OPZIONI = new String[] {
			"Crea ricetta",
			"Setta numero posti a sedere",
			"Setta carico di Lavoro del Ristorante",
			"Visualizza ricette"
	};
	@SuppressWarnings("unused")
	private String userID;
	private DataBase ristoranteDB;

	/*
	 * 
	 */
	public Gestore(String userID, DataBase ristoranteDB) {
		super();
		this.userID = userID;
		this.ristoranteDB = ristoranteDB;
	}

	/**
	 * Retrieve dei dati per ricetta
	 * 
	 * @return Ricetta
	 */
	public void creaRicetta(ArrayList<ProductSheet> schedeIngredienti, double workLoad, int porzioni, String nome) {
		Ricetta r = new Ricetta(schedeIngredienti, porzioni, workLoad);
		Piatto p = new Piatto(nome, workLoad / porzioni);
		this.ristoranteDB.addRicetta(r);
		this.ristoranteDB.addPiatto(p);
		this.ristoranteDB.addCorrispondenza(p, r);
	}

	public void creaBevanda(Ingrediente bevanda, double consumoProCapite) {
		ProductSheet e = new ProductSheet(bevanda, consumoProCapite);
		this.ristoranteDB.addBevanda(e);
	}
	
	public void creaExtra(Ingrediente extra, double consumoProCapite) {
		ProductSheet e = new ProductSheet(extra, consumoProCapite);
		this.ristoranteDB.addExtra(e);
	}

	/**
	 * 
	 * @return lista ingredienti con dosaggi
	 */

	public void inizializzaWorkload(double w) {
		this.ristoranteDB.setCaricoLavoroRistorante(w);
	}

	public void setPostiASedere(int posti) {
		this.ristoranteDB.setNumeroPostiASedere(posti);
	}

	public void inserisciInsiemeBevande(ArrayList<ProductSheet> bevande) {
		this.ristoranteDB.setInsiemeBevande(bevande);
	}

	public void inserisciGeneriExtra(ArrayList<ProductSheet> generiExtra) {
		this.ristoranteDB.setInsiemeBevande(generiExtra);
	}

	public void inserisciCorrispondenza(Piatto p, Ricetta r) {
		this.ristoranteDB.addCorrispondenza(p, r);
	}

	public String visualizzaDB() {
		return this.ristoranteDB.toString();
	}

	public static String[] getOpzioni() {
		return OPZIONI;
	}
	
	public String visualizzaRicette() {
		return this.ristoranteDB.getRicettario().toString();
	}

}
