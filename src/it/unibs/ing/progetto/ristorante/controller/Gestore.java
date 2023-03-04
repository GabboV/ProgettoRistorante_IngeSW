package it.unibs.ing.progetto.ristorante.controller;

import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.DatePair;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

/**
 * 
 * @author Kevin Da implementare le funzioni e responsabilità
 *
 */
public class Gestore extends Employee{

	private static final String[] OPZIONI = new String[] { "Crea ricetta", "Setta numero posti a sedere",
			"Setta carico di Lavoro del Ristorante", "Visualizza ricette" };
	@SuppressWarnings("unused")


	/*
	 * 
	 */
	public Gestore(Ristorante ristoranteDB) {
		super(ristoranteDB);
	}

	/**
	 * Retrieve dei dati per ricetta
	 * 
	 * @return Ricetta
	 */

	public void creaRicetta(ArrayList<Prodotto> ingredienti, double workLoad, int porzioni, String nome,
			ArrayList<DatePair> periodiValidita) {
		Ricetta r = new Ricetta(ingredienti, porzioni, workLoad);

		Piatto p = new Piatto(nome, periodiValidita, workLoad / porzioni);

		this.getRistorante().addRicetta(r);
		this.getRistorante().addPiatto(p);
		this.getRistorante().addCorrispondenza(p, r);
	}

	/*
	public void creaBevanda(Prodotto bevanda, double consumoProCapite) {
		Prodotto e = new Prodotto();
		this.getRistorante().addBevanda(e);
	}

	public void creaExtra(Prodotto extra, double consumoProCapite) {
		Prodotto e = new Prodotto();
		this.getRistorante().addExtra(e);
	}
	*/

	/**
	 * 
	 * @return lista ingredienti con dosaggi
	 */

	public void inizializzaWorkload(double w) {
		this.getRistorante().setCaricoLavoroRistorante(w);
	}

	public void setPostiASedere(int posti) {
		this.getRistorante().setNumeroPostiASedere(posti);
	}

	public void inserisciCorrispondenza(Piatto p, Ricetta r) {
		this.getRistorante().addCorrispondenza(p, r);
	}

	public String visualizzaDB() {
		return this.getRistorante().toString();
	}

	public static String[] getOpzioni() {
		return OPZIONI;
	}

	public String visualizzaRicette() {
		return this.getRistorante().ottieniRicette();
	}

}
