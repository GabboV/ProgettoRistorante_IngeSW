package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.ArrayList;

public class AddettoPrenotazioni {
	
	private ArrayList<Prenotazione> elencoPrenotazioni;


	public AddettoPrenotazioni() {
		super();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
	}
	
	public void addPrenotazione(Prenotazione p) {
		this.elencoPrenotazioni.add(p);
	}
	
	public void removePrenotazioniScadute() {
		
	}
	
	public void creaPrenotazione() {
		
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Prenotazione p : elencoPrenotazioni) {
			s = s.concat(p.toString());
		}
		return "AddettoPrenotazioni\n\n" + s;
	}
}
