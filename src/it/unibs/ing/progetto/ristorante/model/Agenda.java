package it.unibs.ing.progetto.ristorante.model;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda implements Serializable {

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

	/**
	 * Restituisce il numero di posti prenotati per una data giornata
	 * 
	 * @param date
	 * @return
	 */
	public int getNumeroClientiPrenotatiInData(LocalDate date) {
		return this.elencoPrenotazioni.stream().filter(p -> p.getDataPrenotazione().equals(date))
				.mapToInt(Prenotazione::getNumeroCoperti).sum();
	}

	public void addPrenotazione(String cliente, LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.elencoPrenotazioni.add(new Prenotazione(cliente, coperti, comanda, data));
	}

	/**
	 * Restituisce il carico di lavoro da sostenere in una data
	 * @param data
	 * @return
	 */
	public float getCaricoLavoroDaSostenereInData(LocalDate data) {
		float caricoDaSostenere = 0;
		for (Prenotazione p : this.getPrenotazioniInData(data)) { // Recupera le prenotazioni per la data indicata
			caricoDaSostenere += p.getCaricoLavoroTotalePrenotazione(); // somma i carichi di lavoro per ogni prenotazione
		}
		return caricoDaSostenere;
	}

	/**
	 * Restituisce le prenotazioni per una certa data
	 * 
	 * @param data
	 * @return
	 */
	public List<Prenotazione> getPrenotazioniInData(LocalDate date) {
		return elencoPrenotazioni.stream().filter(p -> p.getDataPrenotazione().equals(date)).collect(Collectors.toList());
	}

	/**
	 * Rimuove tutte le prenotazioni scadute
	 */
	public void removePrenotazioniScadute(LocalDate dataCorrente) {
		elencoPrenotazioni = (ArrayList<Prenotazione>) this.elencoPrenotazioni.stream()
				.filter(p -> !p.getDataPrenotazione().isBefore(dataCorrente)).collect(Collectors.toList());
	}

	public void removePrenotazione(int indice){
		if(indice >= 0 && indice < elencoPrenotazioni.size()) {
			elencoPrenotazioni.remove(indice);
		}
	}

}
