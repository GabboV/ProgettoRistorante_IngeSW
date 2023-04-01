package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Piatto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nomePiatto;
	private int caricoLavoro;
	private Ricetta ricetta;
	private ArrayList<Periodo> periodiValidita;

	public Piatto(String nomePiatto, int caricoLavoro, Ricetta ricetta, ArrayList<Periodo> periodiValidita) {
		super();
		if (ricetta == null || periodiValidita == null || caricoLavoro <= 0) {
			throw new IllegalArgumentException("Problemi nella creazione del piatto");
		}
		this.nomePiatto = nomePiatto;
		this.caricoLavoro = caricoLavoro;
		this.ricetta = ricetta;
		this.periodiValidita = periodiValidita;
		
	}

	public boolean isDisponibileInData(LocalDate data) {
		for (Periodo d : periodiValidita) {
			if (d.contieneDataEstremiInclusi(data))
				return true;
		}
		return false;
	}

	public void addPeriodoValidita(Periodo periodoValidita) {
		periodiValidita.add(periodoValidita);
	}

	// getters e setters
	public String getNomePiatto() {
		return nomePiatto;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(int caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}

	public Ricetta getRicetta() {
		return ricetta;
	}

	public ArrayList<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}

}
