package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;

public class AddettoPrenotazioniView {

	private static String[] OPZIONI = new String[] { "Aggiungi prenotazione", "Rimuovi prenotazione",
			"Visualizza prenotazioni" };

	private static String[] ORDINA = new String[] { "Ordina menu tematici", "Ordina dal menu alla carta" };

	private MyMenu menu;

	public void stampaElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		if (elencoMenuTematici.isEmpty()) {
			stampaMsg("La lista e' vuota.");
		} else {
			int contatore = 0;
			for (MenuTematico m : elencoMenuTematici) {
				stampaMsg(" ----------- " + contatore + " ----------- ");
				stampaMenuTematico(m);
				contatore++;
			}
		}
	}

	public void stampaMenuTematico(MenuTematico m) {
		System.out.println("Nome menu tematico: " + m.getNome());
		System.out.println("Carico lavoro: " + m.getCaricoLavoro());
		System.out.println("Piatti del menu: ");
		for (Piatto p : m.getElencoPiatti()) {
			System.out.println("Nome: " + p.getNomePiatto());
		}
		System.out.println("Periodi di validita': ");
		for (Periodo periodo : m.getPeriodiValidita()) {
			LocalDate dInizio = periodo.getDataInizio();
			LocalDate dFine = periodo.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
	}

	public void stampaElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaMsg("Nome: " + p.getNomePiatto());
			stampaMsg("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
	}

	public void stampaData(LocalDate data) {
		System.out.print(data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
	}

	public void stampaPeriodo(LocalDate dataInizio, LocalDate dataFine) {
		stampaData(dataInizio);
		System.out.print(" --> ");
		stampaData(dataFine);
		System.out.println();
	}

	public AddettoPrenotazioniView() {
		super();
		this.menu = new MyMenu("Addetto Prenotazioni", OPZIONI);
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
		MyMenu selezione = new MyMenu("Ordina", ORDINA);
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
