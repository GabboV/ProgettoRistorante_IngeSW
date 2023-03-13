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

	private static final int VISUALIZZA_POSTI_LIBERI_IN_DATA = 4;
	private static final int VISUALIZZA_PRENOTAZIONI = 3;
	private static final int RIMUOVI_PRENOTAZIONE = 2;
	private static final int AGGIUNGI_PRENOTAZIONE = 1;
	private static final int LOGOUT = 0;
	private static final int ORDINA_MENU_CARTA = 2;
	private static final int ORDINA_MENU_TEMATICO = 1;
	private static final int ANNULLA = 0;
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
				this.inserisciPrenotazione();;
				break;
			case RIMUOVI_PRENOTAZIONE:
				// da implementare
				break;
			case VISUALIZZA_PRENOTAZIONI:
				this.visualizzaPrenotazioni();
				break;
			case VISUALIZZA_POSTI_LIBERI_IN_DATA:
				//
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

	public void removePrenotazioniScadute(LocalDate data) {
		this.model.removePrenotazioniScadute(data);
	}

	public void creaPrenotazione() {

		LocalDate dataPrenotazione = this.view.richiestaData("Inserire data della prenotazione:\n");
		int numCoperti = 0;
		if (!this.model.isRistorantePienoInData(dataPrenotazione)) {
			int postiLiberi = this.model.getPostiDisponibiliInData(dataPrenotazione);
			this.view.stampaMsg(String.format("Ci sono %d posti liberi", postiLiberi));
			numCoperti = view.leggiInteroCompreso("Inserire il numero di persone -> ", 1, postiLiberi);
		}
		boolean ordinazione = true;
		boolean annullamento = false;
		int devonoOrdinare = numCoperti;
		int hannoOrdinato = 0;
		HashMap<Piatto, Integer> comanda = new HashMap<>();
		do {
			int scelta = this.view.printSelezioneMenu();
			switch (scelta) {
			case ANNULLA:
				ordinazione = false;
				annullamento = true;
				this.view.stampaMsg("Hai annullato la prenotazione");
				break;
			case ORDINA_MENU_TEMATICO:
				hannoOrdinato = this.ordinaMenuTematico(comanda, dataPrenotazione, devonoOrdinare);
				break;
			case ORDINA_MENU_CARTA:
				hannoOrdinato = this.ordinaDaMenuCarta(comanda, dataPrenotazione, devonoOrdinare);
				break;
			default:
				this.view.stampaMsg("ERRORE");
				break;
			}
			devonoOrdinare = devonoOrdinare - hannoOrdinato;
			if (devonoOrdinare == 0) {
				ordinazione = false;
				this.view.stampaMsg("Tutti i clienti hanno ordinato");
			}
		} while (ordinazione);

		if (!annullamento) {
			Prenotazione prenotazione = new Prenotazione(null, numCoperti, comanda, null);
			boolean finito = false;
			do {
				if (this.model.isPrenotazioneFattibileInData(prenotazione, dataPrenotazione)) {
					prenotazione.setDataPrenotazione(dataPrenotazione);
					//this.addPrenotazione(prenotazione);
					finito = true;
				} else {
					view.stampaMsg("Prenotazione non eseguibile in tale data sceglierne un altra\n");
					boolean altraData = view.richiestaNuovaAggiunta("Provare un altra data? altrimenti annullamento ");
					if (altraData) {
						dataPrenotazione = view.richiestaData("Altra data: ");
					} else {
						this.view.stampaMsg("Hai annullato la prenotazione");
						finito = true;
					}
				}

			} while (!finito);

		}

	}

	public void inserisciPrenotazione() {

		LocalDate dataPrenotazione = this.view.richiestaData("Inserire data della prenotazione:\n");
		int numCoperti = 0;
		if (!this.model.isRistorantePienoInData(dataPrenotazione)) {
			int postiLiberi = this.model.getPostiDisponibiliInData(dataPrenotazione);
			this.view.stampaMsg(String.format("Ci sono %d posti liberi", postiLiberi));
			numCoperti = view.leggiInteroCompreso("Inserire il numero di persone -> ", 1, postiLiberi);
		}

		ArrayList<Piatto> piattiOrdinati = new ArrayList<>();
		boolean discard = false;
		for (int i = 0; i < numCoperti ; i++) {
			boolean ordinato = false;
			this.view.stampaMsg(String.format("Ordinazione per il cliente: ", i + 1));
			do {

				int scelta = this.view.printSelezioneMenu();
				switch (scelta) {
				case 0:
					discard = true;
					break;
				case 1:
					sceltaMenuTematico(dataPrenotazione, piattiOrdinati);
					ordinato = true;
					break;
				case 2:
					sceltaDaMenuCarta(dataPrenotazione, piattiOrdinati);
					ordinato = true;
					break;
				default:
					view.stampaMsg("Errore");
					break;
				}
			} while (!ordinato && !discard);
			if (discard)
				break; // esce dal ciclo for
		}

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
	
	public void addPrenotazione(LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.model.addPrenotazione(data, comanda, coperti);
	}

	private void sceltaMenuTematico(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.ciSonoMenuTematiciValidiInData(dataPrenotazione)) {
			MenuTematico scelto = this.selezionaMenuTematico(dataPrenotazione);
			piatti.addAll(scelto.getElencoPiatti());
		}
	}

	private void sceltaDaMenuCarta(LocalDate dataPrenotazione, ArrayList<Piatto> piatti) {
		if (model.esisteMenuCartaValidoInData(dataPrenotazione)) {
			ArrayList<Piatto> piattiScelti = this.selezionaMenuCarta(dataPrenotazione);
			piatti.addAll(piattiScelti);
		}
	}


	public MenuTematico selezionaMenuTematico(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = this.model.getMenuTematiciValidiInData(date);

		view.stampaElencoMenuTematici(menuTematici);
		int indiceScelto = this.view.leggiInteroCompreso("Selezione l'indice del menu da ordinare -> ", 0,
				menuTematici.size());
		return menuTematici.get(indiceScelto);

	}

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

	public int ordinaMenuTematico(HashMap<Piatto, Integer> comanda, LocalDate date, int coperti) {

		ArrayList<MenuTematico> menuTematici = this.model.getMenuTematiciValidiInData(date);
		String menuStampa = OutputFormatter.formatListaMenuTematici(menuTematici);
		view.stampaMsg(menuStampa);

		int selezione = this.view.leggiInteroCompreso("Seleziona il menu, inserisci l'indice -> ", 1,
				menuTematici.size() + 1);
		int persone = this.view.leggiInteroCompreso("Inserire il numero di persone che ordinano il menu selezionato", 1,
				coperti);

		MenuTematico selezionato = menuTematici.get(selezione - 1);
		ArrayList<Piatto> piattiInMenu = selezionato.getElencoPiatti();

		for (Piatto p : piattiInMenu) {
			aggiornaComandaConPiatto(comanda, p, persone);
		}
		return persone;

	}

	public int ordinaDaMenuCarta(HashMap<Piatto, Integer> comanda, LocalDate date, int coperti) {

		ArrayList<Piatto> piattiValidi = model.getMenuCartaValidiInData(date);
		HashMap<Piatto, Integer> scelti = new HashMap<>();
		String menuStampa = OutputFormatter.formatMenuCarta(piattiValidi);
		boolean continuaOrdine = true;

		do {
			view.stampaMsg(menuStampa);
			int selezione = view.leggiInteroCompreso("Scegli un piatto -> ", 1, piattiValidi.size() + 1);
			Piatto scelto = piattiValidi.get(selezione - 1);
			int porzioni = view.richiestaNumeroPorzioni("Inserire numero di porzioni -> ");
			this.aggiornaComandaConPiatto(scelti, scelto, porzioni);
			continuaOrdine = view.richiestaNuovaAggiunta("Aggiungere un altro piatto? ");
		} while (continuaOrdine);

		int persone = view.leggiInteroCompreso("Inserire numero di persone che ordinano questi piatti -> ", 1, coperti);
		this.moltiplicaComandaN(scelti, persone);
		unisciDueComande(comanda, scelti);
		return persone;
	}

	private void moltiplicaComandaN(HashMap<Piatto, Integer> comanda, int n) {
		ArrayList<Piatto> piatti = new ArrayList<Piatto>(comanda.keySet());
		for (Piatto p : piatti) {
			int q = comanda.get(p) * n;
			comanda.replace(p, q);
		}
	}

	private void aggiornaComandaConPiatto(HashMap<Piatto, Integer> comanda, Piatto p, int porzioni) {
		if (contienePiatto(comanda, p)) {
			int oldValue = comanda.get(p);
			int newValue = oldValue + porzioni;
			comanda.replace(p, newValue);
		} else {
			comanda.put(p, porzioni);
		}
	}

	private static boolean contienePiatto(HashMap<Piatto, Integer> comanda, Piatto p) {
		ArrayList<Piatto> piatti = new ArrayList<Piatto>(comanda.keySet());
		String nome = p.getNomePiatto();
		for (Piatto q : piatti) {
			if (q.getNomePiatto().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}

	private void unisciDueComande(HashMap<Piatto, Integer> comanda, HashMap<Piatto, Integer> daAggiungere) {
		ArrayList<Piatto> aggiunte = new ArrayList<Piatto>(daAggiungere.keySet());
		for (Piatto p : aggiunte) {
			this.aggiornaComandaConPiatto(comanda, p, daAggiungere.get(p));
		}
	}

	private static int personeInComanda(HashMap<Piatto, Integer> comanda) {
		int somma = 0;
		ArrayList<Integer> persone = new ArrayList<>(comanda.values());
		for (Integer i : persone) {
			somma += i;
		}
		return somma;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	// serve un metodo per input parametri prenotazione
	public void creaPrenotazione(String nomeCliente, int nCoperti, LocalDate dataPrenotazione,
			ArrayList<String> ordine) {

		// devo creare all'inizio un MenuSpecialiInData e MenuCartaInData poi
		// controllare in quali metodi servono

		boolean valido = true;
		Prenotazione p = null;
		// messo fuori dall'IF così poi posso creare menuSpecialiOrdinati e
		// piattiSingoliOrdinati corretti e usarli per i successivi controlli
		// controllo se tutti i nomi nell'ordine sono un menuTematicoValido o un
		// piattoValido
		valido = controlloEsistenzaElemOrdine(ordine, dataPrenotazione);
		if (valido) {
			// creo un ArrayList<MenuTematico> contenente tutti i menu tematici presenti
			// nell'ordine
			ArrayList<MenuTematico> menuSpecialiOrdinati = selezionaMenuSpeciali(ordine, dataPrenotazione);
			// creo un ArrayList<Piatto> contenente tutti i piatti singoli presenti
			// nell'ordine
			ArrayList<Piatto> piattiSingoliOrdinati = selezionaPiattiSingoli(ordine, dataPrenotazione);
			// eseguo dei controlli sui parametri della prenotazione
			valido = controllaPrenotazione(nomeCliente, nCoperti, dataPrenotazione, menuSpecialiOrdinati,
					piattiSingoliOrdinati);
			// creo una HashMap<Piatto, Integer> in cui metto ogni piatto (anche
			// appartenente a un MenuTematico) presente nell'ordine e il corrispondente
			// value quantità
			HashMap<Piatto, Integer> comanda = generaComanda(menuSpecialiOrdinati, piattiSingoliOrdinati);

			// creo l'istanza prenotazione
			p = new Prenotazione(nomeCliente, nCoperti, comanda, dataPrenotazione);
		}

		// se la prenotazione ha superato i controlli, la aggiunge a elencoPrenotazioni
		boolean prenotazioneConfermata = aggiungiPrenotazione(p, valido);
		// stampa il msg finale di conferma prenotazione o rifiuto
		stampaMsgConfermaPrenotazione(p, prenotazioneConfermata);

		// devo aggiornare nPostiLiberiInData e caricoLavoroRistoranteInData (con metodo
		// di gestore?) !!!
	}

	public boolean controlloEsistenzaElemOrdine(ArrayList<String> ordine, LocalDate dataPrenotazione) {
		// genero elencoMenuSpecialiInData e menuCartaInData
		// controllo ogni elem se presente (corrispondenza nome) in uno dei due elenchi
		// ELSE return false
		return true;
	}

	// controllo se il nome nell'ordine corrisponde al nome di un menuTematico
	// valido nella data
	public ArrayList<MenuTematico> selezionaMenuSpeciali(ArrayList<String> ordine, LocalDate dataPrenotazione) {
		ArrayList<MenuTematico> elencoMenuSpecialiOrdinati = new ArrayList<MenuTematico>();
		// genero elencoMenuSpecialiInData con metodo da Database
		// se nome nell'ordine corrisponde a uno nel MenuSpecialeInData
		// THEN creo una nuova istanza e lo aggiungo a elencoMenuSpecialiOrdinati
		return elencoMenuSpecialiOrdinati;
	}

	// controllo se il nome nell'ordine corrisponde al nome di un piatto valido
	// nella data
	public ArrayList<Piatto> selezionaPiattiSingoli(ArrayList<String> ordine, LocalDate dataPrenotazione) {
		ArrayList<Piatto> elencoPiattiSingoliOrdinati = new ArrayList<Piatto>();
		// genero menuCartaInData con metodo da Database
		// se nome nell'ordine corrisponde a uno nel menuCartaInData
		// THEN creo una nuova istanza e lo aggiungo a elencoPiattiSingoliOrdinati
		return elencoPiattiSingoliOrdinati;
	}

	// controlla se il numeroCoperti della prenotazione è < del numero di posti
	// liberi in una certa data
	public boolean controlloPostiLiberiInData(int nCoperti, LocalDate dataPrenotazione) {
		// controllo se il nPostiLiberiInData >= nCoperti THEN return true
		// ELSE return false
		return false;
	}

	// controlla se la somma di counterMenuSpecialiOrdinati e
	// counterPiattiSingoliOrdinati è rispetta i vincoli:
	// counterPiattiSingoliOrdinati + counterMenuSpecialiOrdinati >= numeroCoperti
	// counterMenuSpecialiOrdinati <= numeroCoperti
	public boolean controlloNumeroPersoneValido(int nCoperti, ArrayList<MenuTematico> menuSpecialiOrdinati,
			ArrayList<Piatto> piattiSingoliOrdinati) {
		// conto menuTematiciOrdinati e PiattiSingoliOrdinati
		int counterMenuSpecialiOrdinati = menuSpecialiOrdinati.size();
		int counterPiattiSingoliOrdinati = piattiSingoliOrdinati.size();

		// confronto nCoperti e counterMenuSpeciali e counterPiattiSingoli
		// IF (counterPiattiSingoli + counterMenuSpeciali) < numeroCoperti THEN return
		// false
		// IF counterMenuTematici > numeroCoperti THEN return false
		// ELSE return true
		return true;
	}

	// ha bisogno di altri parametri come corrispondenza Piatto-Ricetta
	// metodo LUNGO perchè
	// prende ogni piatto o menuTematico, trova il suo carico di lavoro e fa la
	// sommatoria e controlla se è < caricoLavoroRistorante per una data
	public boolean controlloCaricoLavoroPrenotazione(ArrayList<MenuTematico> menuSpecialiOrdinati,
			ArrayList<Piatto> piattiSingoliOrdinati, LocalDate dataPrenotazione) {
		// da implementare
		return true;
	}

	// meglio boolean per la stampa del msg?
	public boolean controllaPrenotazione(String nomeCliente, int nCoperti, LocalDate dataPrenotazione,
			ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {

		boolean postiLiberiValido = controlloPostiLiberiInData(nCoperti, dataPrenotazione);
		if (postiLiberiValido == false) {
			// stampa msg
			return false;
		}

		boolean nPersoneValido = controlloNumeroPersoneValido(nCoperti, menuSpecialiOrdinati, piattiSingoliOrdinati);
		if (nPersoneValido == false) {
			// stampa msg
			return false;
		}

		boolean caricoLavoroPrenotazioneValido = controlloCaricoLavoroPrenotazione(menuSpecialiOrdinati,
				piattiSingoliOrdinati, dataPrenotazione);
		if (caricoLavoroPrenotazioneValido == false) {
			// stampa msg
			return false;
		}

		return true;
	}

	// forse meglio con metodo che da ArrayList<MenuTematico> e ArrayList<Piatto>
	// ritorna una ArrayList<Piatto> contenente tutti i piatti?
	public HashMap<Piatto, Integer> generaComanda(ArrayList<MenuTematico> menuSpecialiOrdinati,
			ArrayList<Piatto> piattiSingoliOrdinati) {
		HashMap<Piatto, Integer> comanda = new HashMap<Piatto, Integer>();
		// aggiungo alla comanda ogni piatto e la quantità
		return comanda;
	}

	public boolean aggiungiPrenotazione(Prenotazione p, boolean prenotazioneValida) {
		boolean prenotazioneConfermata = false;
		if (prenotazioneValida == true) {
			prenotazioneConfermata = true;
			// elencoPrenotazioni.add(p);
		}
		return prenotazioneConfermata;
	}

	// stampa msg per dire se la prenotazione è stata aggiunta o rifiutata
	// utile per testing
	public void stampaMsgConfermaPrenotazione(Prenotazione p, boolean prenotazioneConfermata) {
		if (prenotazioneConfermata) {
			// print msg conferma con nome e data
		}
	}

	// metodo utile per il magazziniere
	// prende tutte le prenotazioni di una data e ritorna una HashMap<Piatto,
	// Integer> contenente tutti i piatti ordinati
	public HashMap<Piatto, Integer> getComandaInData(LocalDate data) {
		HashMap<Piatto, Integer> comandaInData = new HashMap<Piatto, Integer>();
		// da implementare
		return comandaInData;
	}

	// funzione che confronta il nuovo piatto con la HashMap della comanda attuale
	// se il piatto gia esiste nella comanda allora aumenta il value corrispondente
	// di 1
	// se il piatto non esiste nella comanda allora lo aggiunge con value = 1;
	// INCOLLATO, DA CONTROLLARE
	private static HashMap<Piatto, Integer> aggiungiPiattoInComanda(HashMap<Piatto, Integer> comanda, Piatto p) {
		if (comanda.containsKey(p)) {
			int numeroOrdini = comanda.get(p);
			comanda.replace(p, numeroOrdini + 1);
		} else {
			comanda.put(p, 1);
		}
		return comanda;
	}

	// Calcola il numero di piatti ordinati in una comanda
	// INCOLLATO, DA CONTROLLARE
	private static int calcolaNumeroPersoneComanda(HashMap<Piatto, Integer> comanda) {
		int somma = 0;
		for (int i : comanda.values()) {
			somma += i;
		}
		return somma;
	}

	// Se chiedo prenotazioni un piatto alla volta ci sono troppe complicazioni
	// nell'aggiornare valori e controllare validita (troppe alternative)
	// Per esempio se caricoLavoroRistorante quasi raggiunto, devo sperare che
	// cliente chiede piatto "leggero" e continuare a richiedere, o dargli delle
	// opzioni, o eliminare la prenotazione?
	// Devo raccogliere prenotazioni come elecoPiattiSingoliOrdinati e
	// elencoMenuSpecialiOrdinati
}
