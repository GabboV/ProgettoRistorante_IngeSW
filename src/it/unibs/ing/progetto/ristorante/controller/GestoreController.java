package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.model.DatePair;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

public class GestoreController{

	private static final String[] OPZIONI = new String[] { "Crea ricetta", "Setta numero posti a sedere", "Setta carico di Lavoro del Ristorante", "Visualizza ricette" }; @SuppressWarnings("unused")

	private Ristorante ristorante;
	private GestoreView view;
	
	//CONSTRUCTORS
	public GestoreController(Ristorante ristorante) {
		this.ristorante = ristorante;
	}
	
	public GestoreController() {
	}
	
	//GETTER AND SETTER
	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public GestoreView getView() {
		return view;
	}

	public void setView(GestoreView view) {
		this.view = view;
	}
	
	//CONTROLLER METHODS
	public void inizializzaRistorante() {
		view = new GestoreView();
		view.stampaMsgBenvenutoInizializzazione();
		LocalDate dataCorrente = view.richiestaData("Inserisci la dataCorrente.");
		int nPosti = view.richiestaNumeroPostiRistorante("Inserisci il numero di posti del ristorante: ");
		int caricoLavoroPersona = view.richiestaCaricoLavoro("Inserisci il carico di lavoro per persona: ");
		int caricoLavoroRistorante = caricoLavoroPersona * nPosti;
		caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);
		
		HashMap<Prodotto, Float> insiemeBevande = new HashMap<>();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//METODI DA RIVEDERE

	public void creaRicetta(ArrayList<Prodotto> ingredienti, double workLoad, int porzioni, String nome,
			ArrayList<DatePair> periodiValidita) {
		Ricetta r = new Ricetta(ingredienti, porzioni, workLoad);

		Piatto p = new Piatto(nome, periodiValidita, workLoad / porzioni);

		ristorante.addRicetta(r);
		ristorante.addPiatto(p);
		ristorante.addCorrispondenza(p, r);
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
		ristorante.setCaricoLavoroRistorante(w);
	}

	public void setPostiASedere(int posti) {
		ristorante.setNumeroPostiASedere(posti);
	}

	public void inserisciCorrispondenza(Piatto p, Ricetta r) {
		ristorante.addCorrispondenza(p, r);
	}

	public String visualizzaDB() {
		return ristorante.toString();
	}

	public static String[] getOpzioni() {
		return OPZIONI;
	}

	public String visualizzaRicette() {
		return ristorante.ottieniRicette();
	}

}
