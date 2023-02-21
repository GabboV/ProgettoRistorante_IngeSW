package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;

/**
 * Classe box dei dati
 * @author Kevin
 *
 */
public class DataBase {
	
	private ArrayList<Ricetta> ricettario;
	private ArrayList<Piatto> piatti;
	
	public DataBase() {
		super();
		this.ricettario = new ArrayList<Ricetta>();
		this.piatti = new ArrayList<Piatto>();
	}
	
	public DataBase(ArrayList<Ricetta> ricettario, ArrayList<Piatto> piatti) {
		super();
		this.ricettario = ricettario;
		this.piatti = piatti;
	}

	public ArrayList<Ricetta> getRicettario() {
		return ricettario;
	}
	
	public ArrayList<Piatto> getPiatti() {
		return piatti;
	}
	
	/**
	 * Aggiunge un piatto a quelli esistenti
	 * @param p
	 */
	public void addPiatto(Piatto p) {
		this.piatti.add(p);
	}
	
	/**
	 * Aggiunge una nuova ricetta a quelli esistenti
	 * @param r
	 */
	public void addRicetta(Ricetta r) {
		this.ricettario.add(r);
	}
	
	

	
	
	
	


}
