package it.unibs.ing.progetto.ristorante.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Prenotazione> elencoPrenotazioni;

	
	public Agenda(ArrayList<Prenotazione> elencoPrenotazioni) {
		this.elencoPrenotazioni = elencoPrenotazioni;
	}

	public Agenda() {
		super();
		this.elencoPrenotazioni = new ArrayList<>();
	}

	public ArrayList<Prenotazione> getElencoPrenotazioni() {
		return elencoPrenotazioni;
	}

	public void setElencoPrenotazioni(ArrayList<Prenotazione> elencoPrenotazioni) {
		this.elencoPrenotazioni = elencoPrenotazioni;
	}

	/**
	 * Restituisce il numero di posti prenotati per una data giornata
	 * 
	 * @param date
	 * @return
	 */
	public int getNumeroClientiPrenotatiInData(LocalDate date) {
		return this.elencoPrenotazioni.stream().filter(p -> p.isPrenotazioneInData(date))
				.mapToInt(Prenotazione::getNumeroCoperti).sum();
	}

	public void addPrenotazione(LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.elencoPrenotazioni.add(new Prenotazione(coperti, comanda, data));
	}

	public float getCaricoLavoroDaSostenereInData(LocalDate data) {
		float caricoDaSostenere = 0;
		for (Prenotazione p : this.getPrenotazioniInData(data)) {
			caricoDaSostenere += p.getCaricoLavoroTotalePrenotazione();
		}
		return caricoDaSostenere;
	}

	/**
	 * Restituisce le prenotazioni per una certa data
	 * 
	 * @param data
	 * @return
	 */
	public List<Prenotazione> getPrenotazioniInData(LocalDate data) {
		return elencoPrenotazioni.stream().filter(p -> p.isPrenotazioneInData(data)).collect(Collectors.toList());
	}

	/**
	 * Rimuove tutte le prenotazioni scadute
	 */
	public void removePrenotazioniScadute(LocalDate dataCorrente) {
		elencoPrenotazioni = (ArrayList<Prenotazione>) this.elencoPrenotazioni.stream()
				.filter(p -> !p.getDataPrenotazione().isBefore(dataCorrente)).collect(Collectors.toList());
	}

}
