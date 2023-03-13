package it.unibs.ing.progetto.ristorante.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.AddettoPrenotazioniView;
import it.unibs.ing.progetto.ristorante.view.OutputFormatter;

public class AddettoPrenotazioniController {

	private static final int VISUALIZZA_PRENOTAZIONI = 3;
	private static final int RIMUOVI_PRENOTAZIONE = 2;
	private static final int AGGIUNGI_PRENOTAZIONE = 1;
	private static final int LOGOUT = 0;

	private Ristorante model;
	private AddettoPrenotazioniView view;

	public AddettoPrenotazioniController(Ristorante model) {
		this.model = model;
		this.view = new AddettoPrenotazioniView();
	}

	public void avviaSessione() {

		view.stampaMsg("Addetto Prenotazioni\n");
		boolean sessioneON = true;
		do {
			int scelta = view.printMenu();
			switch (scelta) {
			case LOGOUT:
				sessioneON = false;
				break;
			case AGGIUNGI_PRENOTAZIONE:
				this.inserisciPrenotazione();
				;
				break;
			case RIMUOVI_PRENOTAZIONE:
				// da implementare
				break;
			case VISUALIZZA_PRENOTAZIONI:
				this.visualizzaPrenotazioni();
				break;
			default:
				view.stampaMsg("Errore");
				break;

			}

		} while (sessioneON);

	}

	public void visualizzaPrenotazioni() {
		String prenotazioni = null;
		if (model.nonCiSonoPrenotazioni()) {
			prenotazioni = "Non ci sono prenotazioni \n";
		} else {
			prenotazioni = OutputFormatter.formatPrenotazioni(this.model.getElencoPrenotazioni());
		}
		view.stampaMsg(prenotazioni);

	}

	// (?) in base a che data?
	public void removePrenotazioniScadute(LocalDate data) {
		this.model.removePrenotazioniScadute(data);
	}

	// semi-def
	public void inserisciPrenotazione() {

		LocalDate dataPrenotazione = this.view.richiestaData("Inserire data della prenotazione:\n");
		int numCoperti = 0;
		if (!this.model.isRistorantePienoInData(dataPrenotazione)) {
			int postiLiberi = this.model.getPostiDisponibiliInData(dataPrenotazione);
			this.view.stampaMsg(String.format("Ci sono %d posti liberi", postiLiberi));
			numCoperti = view.leggiInteroCompreso("Inserire il numero di persone -> ", 1, postiLiberi);
		}

		ArrayList<Piatto> piattiOrdinati = new ArrayList<>();
		boolean discard = ordinazioneHandler(dataPrenotazione, numCoperti, piattiOrdinati);

		HashMap<Piatto, Integer> comanda;
		if (!discard && !piattiOrdinati.isEmpty()) {
			comanda = this.creaComandaConListaPiatti(piattiOrdinati);
			boolean check = this.model.checkPrenotazione(dataPrenotazione, comanda, numCoperti);
			if (check) {
				this.model.addPrenotazione(dataPrenotazione, comanda, numCoperti);
			} else {
				view.stampaMsg("Prenotazione non fattibile");
			}
		} else {
			this.view.stampaMsg("Hai annullato la prenotazione");
		}

	}

	private boolean ordinazioneHandler(LocalDate dataPrenotazione, int numCoperti, ArrayList<Piatto> piattiOrdinati) {
		boolean discard = false;
		for (int i = 0; i < numCoperti; i++) {
			boolean ordinato = false;
			do {
				this.view.stampaMsg(String.format("Ordinazione per il cliente: %d\n", i));
				int scelta = this.view.printSelezioneMenu();
				switch (scelta) {
				case 0:
					discard = true;
					break;
				case 1:
					ordinato = sceltaMenuTematico(dataPrenotazione, piattiOrdinati);
					break;
				case 2:
					ordinato = sceltaDaMenuCarta(dataPrenotazione, piattiOrdinati);
					break;
				default:
					view.stampaMsg("Errore");
					break;
				}
			} while (!ordinato && !discard);
			if (discard)
				break;
		}
		return discard;
	}

	// def
	public HashMap<Piatto, Integer> creaComandaConListaPiatti(ArrayList<Piatto> piatti) {
		HashMap<Piatto, Integer> comanda = new HashMap<>();
		for (Piatto p : piatti) {
			if (!comanda.containsKey(p)) {
				comanda.put(p, 1);
			} else {
				int oldValue = comanda.get(p);
				comanda.replace(p, oldValue + 1);
			}
		}
		return comanda;
	}

	// def
	public void addPrenotazione(LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.model.addPrenotazione(data, comanda, coperti);
	}

	// def
	private boolean sceltaMenuTematico(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.ciSonoMenuTematiciValidiInData(dataPrenotazione)) {
			MenuTematico scelto = this.selezionaMenuTematico(dataPrenotazione);
			piatti.addAll(scelto.getElencoPiatti());
			return true;
		} else {
			this.view.stampaMsg("Non ci sono menu validi");
			return false;
		}
	}

	// def
	private boolean sceltaDaMenuCarta(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.esisteMenuCartaValidoInData(dataPrenotazione)) {
			ArrayList<Piatto> piattiScelti = this.selezionaMenuCarta(dataPrenotazione);
			piatti.addAll(piattiScelti);
			return true;
		} else {
			this.view.stampaMsg("Non ci sono piatti validi");
			return false;
		}
	}

	// def
	public MenuTematico selezionaMenuTematico(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = this.model.getMenuTematiciValidiInData(date);
		view.stampaElencoMenuTematici(menuTematici);
		int indiceScelto = this.view.leggiInteroCompreso("Selezione l'indice del menu da ordinare -> ", 0,
				menuTematici.size() - 1);
		return menuTematici.get(indiceScelto);

	}

	// def
	public ArrayList<Piatto> selezionaMenuCarta(LocalDate date) {
		ArrayList<Piatto> piattiValidi = model.getMenuCartaValidiInData(date);
		ArrayList<Piatto> piattiScelti = new ArrayList<>();
		boolean continua = false;
		do {
			this.view.stampaElencoPiatti(piattiValidi);
			int scelto = this.view.leggiInteroCompreso("Scegli il piatto -> ", 0, piattiValidi.size() - 1);
			piattiScelti.add(piattiValidi.get(scelto));
			continua = this.view.richiestaNuovaAggiunta("Vuoi aggiungere un altro piatto? ");
		} while (continua);
		return piattiScelti;
	}


}
