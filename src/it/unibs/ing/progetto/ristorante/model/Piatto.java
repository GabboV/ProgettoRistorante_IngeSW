package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Piatto implements Serializable, MenuComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomePiatto;
	private int caricoLavoro;
	private Ricetta ricetta;
	private List<Periodo> periodiValidita;

	public Piatto(String nomePiatto, int caricoLavoro, Ricetta ricetta, List<Periodo> periodiValidita) {
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
	public String getNome() {
		return nomePiatto;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public Ricetta getRicetta() {
		return ricetta;
	}

	public List<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}

	@Override
	public List<Piatto> getContenuto() {
		List<Piatto> list = new ArrayList<>();
		list.add(this);
		return list;
	}

}
