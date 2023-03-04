package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.Date;

import it.unibs.fp.mylib.InputDati;

public class GestoreView {

	
	public void stampaMsgBenvenutoInizializzazione() {
		System.out.println("Benvenuto Gestore.");
		System.out.println("Siccome si tratta del primo avvio del programma e' necessaria l'inizializzazione dei parametri.");
	}
	
	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}
	
	public int richiestaNumeroPostiRistorante(String messaggio) {
		return InputDati.leggiInteroConMinimo(messaggio, 1);
	}
	
	public int richiestaCaricoLavoro(String messaggio) {
		return InputDati.leggiInteroConMinimo(messaggio, 1);
	}
	
	
}
