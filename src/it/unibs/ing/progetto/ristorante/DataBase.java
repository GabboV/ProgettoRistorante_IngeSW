package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;

/**
 * Utility Class per per la classe gestore
 * 
 * @author Kevin
 *
 */
public class DataBase {

	/*
	 * Contiene tutte le ricette memorizzate in un certo momento
	 */
	private ArrayList<Ricetta> ricettario;

	/*
	 * Contiene tutti i piatti memorizzati in un certo momento
	 */
	private ArrayList<Piatto> pietanze;

	/**
	 * Se inizializzazione SENZA dati preesistenti
	 */
	public DataBase() {
		super();
		this.ricettario = new ArrayList<Ricetta>();
		this.pietanze = new ArrayList<Piatto>();
	}

	/**
	 * Inizializzazione con dati preesistente (non comprende la logica di retrieve
	 * dei dati da eventuale file di salvataggio)
	 * 
	 * @param ricettario
	 * @param piatti
	 */
	public DataBase(ArrayList<Ricetta> ricettario, ArrayList<Piatto> piatti) {
		super();
		this.ricettario = ricettario;
		this.pietanze = piatti;
	}

	/**
	 * 
	 * @return ricettario
	 */
	public ArrayList<Ricetta> getRicettario() {
		return ricettario;
	}

	/**
	 * 
	 * @return piatti
	 */
	public ArrayList<Piatto> getPiatti() {
		return pietanze;
	}

	/**
	 * Aggiunge un piatto a quelli esistenti
	 * 
	 * @param p
	 */
	public void addPiatto(Piatto p) {
		this.pietanze.add(p);
	}

	/**
	 * Aggiunge una nuova ricetta a quelli esistenti
	 * 
	 * @param r
	 */
	public void addRicetta(Ricetta r) {
		this.ricettario.add(r);
	}

}
