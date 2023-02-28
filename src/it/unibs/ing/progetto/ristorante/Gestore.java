package it.unibs.ing.progetto.ristorante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.prototipo.Employee;

/**
 * 
 * @author Kevin Da implementare le funzioni e responsabilità
 *
 */
public class Gestore extends Employee {

	private static final String DEFAULT = "DEFAULT";
	private static final String[] OPZIONI = new String[] { "Crea ricetta", "Setta numero posti a sedere",
			"Setta carico di Lavoro del Ristorante", "Visualizza ricette" };
	@SuppressWarnings("unused")
	private String userID;

	/*
	 * 
	 */
	public Gestore(String userID, Ristorante ristoranteDB) {
		super(ristoranteDB);
		this.userID = userID;
	}

	/**
	 * Retrieve dei dati per ricetta
	 * 
	 * @return Ricetta
	 */

	public void creaRicetta(ArrayList<ProductSheet> ingredienti, double workLoad, int porzioni, String nome,
			ArrayList<DatePair> periodiValidita) {
		Ricetta r = new Ricetta(ingredienti, porzioni, workLoad);
		Piatto p = new Piatto(nome, periodiValidita, workLoad / porzioni);

		this.getRistorante().addRicetta(r);
		this.getRistorante().addPiatto(p);
		this.getRistorante().addCorrispondenza(p, r);
	}

	public void creaBevanda(Ingrediente bevanda, double consumoProCapite) {
		ProductSheet e = new ProductSheet(bevanda, consumoProCapite);
		this.getRistorante().addBevanda(e);
	}

	public void creaExtra(Ingrediente extra, double consumoProCapite) {
		ProductSheet e = new ProductSheet(extra, consumoProCapite);
		this.getRistorante().addExtra(e);
	}

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
