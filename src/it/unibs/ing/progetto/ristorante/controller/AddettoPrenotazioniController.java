package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.AddettoPrenotazioniView;

public class AddettoPrenotazioniController extends Controller {

	private static final String NO_PRENOTAZIONI = "Non ci sono prenotazioni \n";
	private static final int VISUALIZZA_PRENOTAZIONI = 3;
	private static final int RIMUOVI_PRENOTAZIONE = 2;
	private static final int AGGIUNGI_PRENOTAZIONE = 1;
	private static final int LOGOUT = 0;
	
	private AddettoPrenotazioniView view;

	public AddettoPrenotazioniController(Ristorante model) {
		super(model);
		this.view =new AddettoPrenotazioniView();
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
		if (this.getModel().getElencoPrenotazioni().isEmpty()) {
			view.stampaMsg(NO_PRENOTAZIONI);
		} else {
			view.stampaListaPrenotazioni(this.getModel().getElencoPrenotazioni());
		}

	}

	public void removePrenotazioniScadute() {
		LocalDate data = LocalDate.now();
		this.getModel().removePrenotazioniScadute(data);
	}

	
	public void inserisciPrenotazione() {
		LocalDate dataPrenotazione;
		dataPrenotazione = getDataFeriale();
		if (dataPrenotazione != null ) {
			int numCoperti = 0;
			if (!this.getModel().isRistorantePienoInData(dataPrenotazione)) {
				int postiLiberi = this.getModel().getPostiDisponibiliInData(dataPrenotazione);
				view.stampaMsg(String.format("Ci sono %d posti liberi", postiLiberi));
				numCoperti = view.leggiInteroCompreso("Inserire il numero di persone -> ", 1, postiLiberi);
			}
			ArrayList<Piatto> piattiOrdinati = new ArrayList<>();
			boolean discard = ordinazioneHandler(dataPrenotazione, numCoperti, piattiOrdinati);
			HashMap<Piatto, Integer> comanda;
			if (!discard && !piattiOrdinati.isEmpty()) {
				comanda = this.creaComandaConListaPiatti(piattiOrdinati);
				boolean check = this.getModel().verificaAccettabilitaPrenotazione(dataPrenotazione, comanda, numCoperti);
				if (check) {
					this.getModel().addPrenotazione(dataPrenotazione, comanda, numCoperti);
				} else {
					view.stampaMsg("Prenotazione non fattibile");
				}
			} else {
				view.stampaMsg("Hai annullato la prenotazione");
			}
		}
	}

	private LocalDate getDataFeriale() {
		LocalDate dataPrenotazione;
		boolean feriale = false;
		do {
			dataPrenotazione = view.richiestaData("Inserire data della prenotazione:\n");
			if(!this.isGiornoFeriale(dataPrenotazione)) {
				this.view.stampaMsg("Il ristorante � aperto solo nei giorni feriali, riprovare!\n");
			} else {
				feriale = true;
			}
		} while(!feriale);
		return dataPrenotazione;
	}
	
	private boolean isGiornoFeriale(LocalDate data) {
		DayOfWeek dayOfWeek = data.getDayOfWeek();
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}

	private boolean ordinazioneHandler(LocalDate dataPrenotazione, int numCoperti, ArrayList<Piatto> piattiOrdinati) {
		boolean discard = false;
		for (int i = 0; i < numCoperti; i++) {
			boolean ordinato = false;
			do {
				view.stampaMsg(String.format("Ordinazione per il cliente: %d\n", i));
				int scelta = view.printSelezioneMenu();
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
		this.getModel().addPrenotazione(data, comanda, coperti);
	}

	// def
	private boolean sceltaMenuTematico(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (this.getModel().ciSonoMenuTematiciValidiInData(dataPrenotazione)) {
			MenuTematico scelto = this.selezionaMenuTematico(dataPrenotazione);
			piatti.addAll(scelto.getElencoPiatti());
			return true;
		} else {
			view.stampaMsg("Non ci sono menu validi");
			return false;
		}
	}

	// def
	private boolean sceltaDaMenuCarta(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (this.getModel().esisteMenuCartaValidoInData(dataPrenotazione)) {
			ArrayList<Piatto> piattiScelti = this.selezionaMenuCarta(dataPrenotazione);
			piatti.addAll(piattiScelti);
			return true;
		} else {
			view.stampaMsg("Non ci sono piatti validi");
			return false;
		}
	}

	// def
	public MenuTematico selezionaMenuTematico(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = (ArrayList<MenuTematico>) this.getModel().getMenuTematiciInData(date);
		view.stampaElencoMenuTematici(menuTematici);
		int indiceScelto = view.leggiInteroCompreso("Selezione l'indice del menu da ordinare -> ", 0,
				menuTematici.size() - 1);
		return menuTematici.get(indiceScelto);

	}

	// def
	public ArrayList<Piatto> selezionaMenuCarta(LocalDate date) {
		ArrayList<Piatto> piattiValidi = (ArrayList<Piatto>) this.getModel().getMenuCartaInData(date);
		ArrayList<Piatto> piattiScelti = new ArrayList<>();
		boolean continua = false;
		do {
			view.stampaElencoPiatti(piattiValidi);
			int scelto = view.leggiInteroCompreso("Scegli il piatto -> ", 0, piattiValidi.size() - 1);
			piattiScelti.add(piattiValidi.get(scelto));
			continua = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro piatto? ");
		} while (continua);
		return piattiScelti;
	}

}
