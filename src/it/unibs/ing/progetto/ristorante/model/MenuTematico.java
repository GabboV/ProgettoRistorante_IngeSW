package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe immutabile (Attributi privati e solo metodi getters)
 * 
 *
 */
public class MenuTematico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private ArrayList<Piatto> elencoPiatti;
	private int caricoLavoro;
	private ArrayList<Periodo> periodiValidita;

	public MenuTematico(String nome, ArrayList<Piatto> elencoPiatti, int caricoLavoro,
			ArrayList<Periodo> periodiValidita) {
		super();
		this.nome = nome;
		this.elencoPiatti = elencoPiatti;
		this.caricoLavoro = caricoLavoro;
		this.periodiValidita = periodiValidita;
	}

	public boolean isDisponibileInData(LocalDate data) {
		// prende ciascun Periodo presente in periodiValidita
		for (Periodo d : periodiValidita) {
			// se la data è compresa tra dataInizio e dataFine oppure coincide con una delle
			// due date ritorna true
			if (d.contieneDataEstremiInclusi(data))
				return true;
		}
		return false;
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public ArrayList<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}

}
