package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.interfacce.IPrenotazioni;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.view.AddettoPrenotazioniView;

public class AddettoPrenotazioniController implements Controller {

	private static final String ERRORE = "Errore";
	/**
	 *
	 */
	private static final String INSERIRE_IL_NUMERO_DI_PERSONE = "Inserire il numero di persone -> ";
	private static final String CI_SONO_D_POSTI_LIBERI = "Ci sono %d posti liberi";
	private static final String NO_PRENOTAZIONI = "Non ci sono prenotazioni \n";
	private static final int VISUALIZZA_PRENOTAZIONI = 3;
	private static final int RIMUOVI_PRENOTAZIONE = 2;
	private static final int AGGIUNGI_PRENOTAZIONE = 1;
	private static final int LOGOUT = 0;

	private AddettoPrenotazioniView view;
	private IPrenotazioni model;

	public AddettoPrenotazioniController(IPrenotazioni model) {
		this.model = model;
		this.view = new AddettoPrenotazioniView();
	}

	public void avviaSessione() {
		view.stampaMsg("Addetto Prenotazioni\n");
		model.removePrenotazioniScadute();
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
					this.removePrenotazione();
					break;
				case VISUALIZZA_PRENOTAZIONI:
					this.visualizzaPrenotazioni();
					break;
				default:
					view.stampaMsg(ERRORE);
					break;
			}
		} while (sessioneON);

	}

	/**
	 * Visualizza le prenotazioni se presenti
	 */
	public void visualizzaPrenotazioni() {
		if (model.getElencoPrenotazioni().isEmpty()) {
			view.stampaMsg(NO_PRENOTAZIONI);
		} else {
			view.stampaListaPrenotazioni(model.getElencoPrenotazioni());
		}
	}

	/**
	 * Metodo per creare ed effettuare una prenotazione
	 */
	public void inserisciPrenotazione() {
		LocalDate dataPrenotazione;
		String cliente = view.richiestaNome("Inserisci il nome del cliente -> ");
		dataPrenotazione = getDataValida();
		if (dataPrenotazione != null) {
			int numCoperti = 0;
			if (!model.isRistorantePienoInData(dataPrenotazione)) {
				int postiLiberi = model.getPostiDisponibiliInData(dataPrenotazione);
				view.stampaMsg(String.format(CI_SONO_D_POSTI_LIBERI, postiLiberi));
				numCoperti = view.leggiInteroCompreso(INSERIRE_IL_NUMERO_DI_PERSONE, 1, postiLiberi);
			}
			ArrayList<Piatto> piattiOrdinati = new ArrayList<>();
			boolean discard = ordinazioneHandler(dataPrenotazione, numCoperti, piattiOrdinati);
			HashMap<Piatto, Integer> comanda;
			if (!discard && !piattiOrdinati.isEmpty()) {
				comanda = this.creaComandaConListaPiatti(piattiOrdinati);
				boolean check = model.verificaAccettabilitaPrenotazione(dataPrenotazione, comanda, numCoperti);
				if (check) {
					model.addPrenotazione(cliente, dataPrenotazione, comanda, numCoperti);
				} else {
					view.stampaMsg("Prenotazione non fattibile");
				}
			} else {
				view.stampaMsg("Hai annullato la prenotazione");
			}
		}
	}

	/**
	 * Metodo per richiedere una data valida sotto le ipotesi del tema
	 * 
	 * @return
	 */
	private LocalDate getDataValida() {
		LocalDate dataPrenotazione;
		boolean feriale = false;
		do {
			dataPrenotazione = view.richiestaData("Inserire data della prenotazione:\n");
			boolean tematici = model.ciSonoMenuTematiciValidiInData(dataPrenotazione);
			boolean carta = model.ciSonoPiattiValidiInData(dataPrenotazione);
			if (!this.isGiornoFeriale(dataPrenotazione) || !(tematici || carta)
					|| !almenoUnGiornoFeriale(model.getDataCorrente(), dataPrenotazione)) {
				if (!this.isGiornoFeriale(dataPrenotazione)) {
					this.view.stampaMsg("Il ristorante is aperto solo nei giorni feriali, riprovare!\n");
				}
				if (tematici == false && carta == false) {
					this.view.stampaMsg("Non ci sono piatti validi da scegliere, scegli un altro giorno\n");
				}
				if (!almenoUnGiornoFeriale(model.getDataCorrente(), dataPrenotazione)) {
					this.view.stampaMsg("La prenotazione deve pervenire con almeno un giorno feriale in anticipo");
				}
			} else {
				feriale = true;
			}
		} while (!feriale);
		return dataPrenotazione;
	}

	/**
	 * Controlla se c'e' almeno un giorno feriale di differenza tra due date
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean almenoUnGiornoFeriale(LocalDate date1, LocalDate date2) {
		LocalDate nextWorkingDay = getGiornoFerialeSuccessivo(date1);
		return date2.isAfter(nextWorkingDay) || date2.isEqual(nextWorkingDay);
	}

	/**
	 * Calcola il giorno feriale successivo
	 * 
	 * @param date
	 * @return
	 */
	private static LocalDate getGiornoFerialeSuccessivo(LocalDate date) {
		do {
			date = date.plusDays(1);
		} while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
		return date;
	}

	/**
	 * Restituisce true se la data is giorno feriale
	 * 
	 * @param data
	 * @return
	 */
	private boolean isGiornoFeriale(LocalDate data) {
		DayOfWeek dayOfWeek = data.getDayOfWeek();
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}

	/**
	 * Ordina per ogni cliente una ordinazione, restituisce true se la prenotazione
	 * viene annullata
	 * 
	 * @param dataPrenotazione
	 * @param numCoperti
	 * @param piattiOrdinati
	 * @return
	 */
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
						view.stampaMsg(ERRORE);
						break;
				}
			} while (!ordinato && !discard);
			if (discard)
				break;
		}
		return discard;
	}

	/**
	 * Prende una lista di piatti e li ordina in una mappa con la loro molteplicità
	 * 
	 * @param piatti
	 * @return
	 */
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

	/**
	 * Passa i parametri di una nuova prenotazione al modello
	 * 
	 * @param data
	 * @param comanda
	 * @param coperti
	 */
	public void addPrenotazione(String cliente, LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		model.addPrenotazione(cliente, data, comanda, coperti);
	}

	/**
	 * Metodo per scegliere un menu tematico
	 * 
	 * @param dataPrenotazione
	 * @param piatti
	 * @return
	 */
	private boolean sceltaMenuTematico(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.ciSonoMenuTematiciValidiInData(dataPrenotazione)) {
			MenuTematico scelto = this.selezionaMenuTematico(dataPrenotazione);
			piatti.addAll(scelto.getElencoPiatti());
			return true;
		} else {
			view.stampaMsg("Non ci sono menu validi");
			return false;
		}
	}

	/**
	 * Metodo per scegliere un menu carta
	 * 
	 * @param dataPrenotazione
	 * @param piatti
	 * @return
	 */
	private boolean sceltaDaMenuCarta(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.ciSonoPiattiValidiInData(dataPrenotazione)) {
			ArrayList<Piatto> piattiScelti = this.selezionaMenuCarta(dataPrenotazione);
			piatti.addAll(piattiScelti);
			return true;
		} else {
			view.stampaMsg("Non ci sono piatti validi");
			return false;
		}
	}

	/**
	 * Metodo per selezionare un menu tematico
	 * 
	 * @param date
	 * @return
	 */
	public MenuTematico selezionaMenuTematico(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = (ArrayList<MenuTematico>) model.getMenuTematiciInData(date);
		view.stampaElencoMenuTematici(menuTematici);
		int indiceScelto = view.leggiInteroCompreso("Selezione l'indice del menu da ordinare -> ", 0,
				menuTematici.size() - 1);
		return menuTematici.get(indiceScelto);

	}

	/**
	 * Metodo per selezionare dei piatti dal menu alla carta
	 * 
	 * @param date
	 * @return
	 */
	public ArrayList<Piatto> selezionaMenuCarta(LocalDate date) {
		ArrayList<Piatto> piattiValidi = (ArrayList<Piatto>) model.getMenuCartaInData(date);
		ArrayList<Piatto> piattiScelti = new ArrayList<>();
		boolean continua = false;
		do {
			view.stampaElencoPiatti(piattiValidi);
			int scelto = view.leggiInteroCompreso("Scegli il piatto -> ", 0, piattiValidi.size() - 1);
			piattiScelti.add(piattiValidi.get(scelto));
			continua = view.yesOrNo("Vuoi aggiungere un altro piatto? ");
		} while (continua);
		return piattiScelti;
	}

	public void removePrenotazione() {
		ArrayList<Prenotazione> lista = model.getElencoPrenotazioni();
		if (lista != null && !lista.isEmpty()) {
			view.stampaListaPrenotazioni(model.getElencoPrenotazioni());
			int indice = view.richiestaInteroConMinimoMassimo("Scegli la prenotazione da rimuovere > ", 0,
					lista.size() - 1);
			model.removePrenotazione(indice);
			view.stampaMsg("Prenotazione rimossa");
		} else {
			view.stampaMsg("Non sono prensenti prenotazioni!");
		}
	}

}
