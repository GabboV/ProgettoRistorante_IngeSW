package it.unibs.ing.progetto.ristorante.UI.controllerMVC;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibs.ing.progetto.ristorante.UI.view.PrenotazioneView;
import it.unibs.ing.progetto.ristorante.controllerGRASP.PrenotazioneController;
import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;

/**
 * Controller MVC
 */
public class AddettoPrenotazioniController implements Controller {

	private static final int VISUALIZZA_PRENOTAZIONI = 3;
	private static final int RIMUOVI_PRENOTAZIONE = 2;
	private static final int AGGIUNGI_PRENOTAZIONE = 1;
	private static final int LOGOUT = 0;

	private PrenotazioneController controller;
	private PrenotazioneView window;

	public AddettoPrenotazioniController(PrenotazioneController controller) {
		super();
		this.controller = controller;
		this.window = new PrenotazioneView();
	}

	public void avviaSessione() {
		boolean sessioneON = true;
		do {
			int scelta = window.gestioneMenu();
			switch (scelta) {
				case LOGOUT:
					sessioneON = false;
					break;
				case AGGIUNGI_PRENOTAZIONE:
					this.enterPrenotazione();
					break;
				case RIMUOVI_PRENOTAZIONE:
					this.removePrenotazione();
					break;
				case VISUALIZZA_PRENOTAZIONI:
					this.visualizzaPrenotazioni();
					break;
				default:
					break;
			}
		} while (sessioneON);

	}

	/**
	 * Visualizza le prenotazioni se presenti
	 */
	public void visualizzaPrenotazioni() {
		window.printPrenotazioni(controller.getElencoPrenotazioni());
	}

	public void enterPrenotazione() {
		window.printInserimento();
		String cliente = window.richiestaNomeCliente();
		LocalDate dataPrenotazione = window.richiestaDataPrenotazione();
		if (controller.giornoValido(dataPrenotazione)) {
			window.stampaRiepilogo(controller.getPostiLiberiInData(dataPrenotazione),
					controller.getPiattiInData(dataPrenotazione).size(),
					controller.getMenuTematiciInData(dataPrenotazione).size(), dataPrenotazione);
			boolean conferma = window.confermaProsecuzione();
			if (conferma) {
				int coperti = window.richiestaPosti(controller.getPostiLiberiInData(dataPrenotazione));
				List<Piatto> piattiOrdinabili = controller.getPiattiInData(dataPrenotazione);
				List<MenuTematico> menuOrdinabili = controller.getMenuTematiciInData(dataPrenotazione);
				if (!(piattiOrdinabili.isEmpty() && menuOrdinabili.isEmpty())) {
					List<MenuComponent> ordine = window.enterOrdine(coperti, piattiOrdinabili, menuOrdinabili);
					if (controller.confermaOrdine(dataPrenotazione, ordine, coperti)) {
						controller.addPrenotazione(cliente, dataPrenotazione, ordine, coperti);
						window.printPrenotazioneEffettuata();
					} else {
						window.printMsgPrenotazioneNonEffettuabile();
					}
				} else {
					window.printMsgNonCiSonoElementi();
				}
			} else {
				window.printMsgOperazioneConclusa();
			}
		} else {
			window.printMsgPrenotazioneNonAccettabile();
		}
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
	public void addPrenotazione(String cliente, LocalDate data, List<MenuComponent> comanda, int coperti) {
		controller.addPrenotazione(cliente, data, comanda, coperti);
	}

	public void removePrenotazione() {
		List<Prenotazione> lista = controller.getElencoPrenotazioni();
		if (lista != null && !lista.isEmpty()) {
			window.printPrenotazioni(lista);
			int indice = window.richiestaPrenotazioneEliminare(lista.size());
			controller.removePrenotazione(indice);
		} else {
			window.printMsgNonCiSonoPrenotazioni();
		}
	}

}
