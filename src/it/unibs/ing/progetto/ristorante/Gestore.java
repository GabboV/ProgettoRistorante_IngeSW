package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.prototipo.Employee;

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
