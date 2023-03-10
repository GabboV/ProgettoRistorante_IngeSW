package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class AddettoPrenotazioniView {
	
	private static String[] OPZIONI = new String[] {
			"Aggiungi prenotazione",
			"Rimuovi prenotazione",
			"Visualizza prenotazioni",
			"Visualizza posti disposinibili in una certa data"
	};
	
	private static String[] ORDINA = new String[] {
			"Ordina menu tematici",
			"Ordina dal menu alla carta"
	};
	
	private MyMenu menu;

	public AddettoPrenotazioniView() {
		super();
		this.menu = new MyMenu("Addetto Prenotazioni",OPZIONI);
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}
	
	public void stampaMsg(String messaggio) {
		System.out.println(messaggio);
	}
	
	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}
	
	public int printSelezioneMenu() {
		MyMenu selezione = new MyMenu("Ordina",ORDINA);
		return selezione.scegli();
	}
	
	public int richiestaNumeroPorzioni(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public boolean richiestaNuovaAggiunta(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}
	
	public int printMenu() {
		return menu.scegli();
	}
	
	
	

}
