package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class MagazziniereView {

	private static final String[] OPZIONI = new String[] { "Crea Lista Spesa", "Aggiorna registro Magazzino",

			"Visualizza lista spesa", "Aggiungi prodotto nel magazzino", "Rimuovi prodotto nel magazzino", "Visualizza inventario" };

	private MyMenu menu = new MyMenu("Magazziniere", OPZIONI);

	public int printMenu() {
		return menu.scegli();
	}

	public void print(String msg) {
		System.out.println(msg);
	}

	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}

	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}

}
