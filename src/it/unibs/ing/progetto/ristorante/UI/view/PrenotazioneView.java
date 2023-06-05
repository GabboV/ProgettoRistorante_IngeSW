package it.unibs.ing.progetto.ristorante.UI.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Iterator;
import java.util.Map.Entry;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;

public class PrenotazioneView {

	private static final String ADDETTO_PRENOTAZIONI = "Addetto prenotazioni menu";
	private static final String VISUALIZZA_PRENOTAZIONI = "Visualizza prenotazioni";
	private static final String RIMUOVI_PRENOTAZIONE = "Rimuovi prenotazione";
	private static final String AGGIUNGI_PRENOTAZIONE = "Aggiungi prenotazione";

	private static String[] OPZIONI = new String[] { AGGIUNGI_PRENOTAZIONE, RIMUOVI_PRENOTAZIONE,
			VISUALIZZA_PRENOTAZIONI };

	public int gestioneMenu() {
		MyMenu menu = new MyMenu(ADDETTO_PRENOTAZIONI, OPZIONI);
		return menu.scegli();
	}

	public void printMsgPrenotazioneNonAccettabile() {
		System.out.println(
				"La data inserita non e' valida secondo i criteri di accettazione, probabilmente hai scelto un giorno non feriale");
	}

	public void printMsgOperazioneConclusa() {
		System.out.println("Operazione conclusa");
	}

	public void printMsgNonCiSonoElementi() {
		System.out.println("Non ci sono elementi ordinabili");
	}

	public void printMsgPrenotazioneNonEffettuabile() {
		System.out.println("Non e' stato possibile registrare la prenotazione");
	}

	public void printPrenotazioneEffettuata() {
		System.out.println("Prenotazione effettuata");
	}

	public int richiestaPosti(int max) {
		return InputDati.leggiIntero("Inserisci il numero di posti da riservare > ", 1,
				max);
	}

	public LocalDate richiestaDataPrenotazione() {
		return InputDati.leggiData("Inserisci la data della prenotazione");
	}

	public String richiestaNomeCliente() {
		return InputDati.leggiStringaNonVuota("Inserire il nome del cliente > ");
	}

	public void printInserimento() {
		System.out.println("Inserimento prenotazione");
	}

	public boolean confermaProsecuzione() {
		boolean conferma = InputDati.yesOrNo("Proseguire con la prenotazione?");
		return conferma;
	}

	public List<MenuComponent> enterOrdine(int coperti, List<Piatto> carta, List<MenuTematico> menu) {
		List<MenuComponent> ordine = new ArrayList<>();
		for (int i = 0; i < coperti; i++) {
			System.out.println("Ordinazione cliente: " + i);
			boolean finito = false;
			do {
				int scelta = sceltaOrdine();
				switch (scelta) {
					case 1:
						if (!carta.isEmpty()) {
							ordine.addAll(enterSingoloOrdineCarta(carta));
							finito = true;
						} else {
							System.out.println("Non ci sono piatti nel menu alla carta");
						}
						break;
					case 2:
						if (!menu.isEmpty()) {
							ordine.add(enterSingoloOrdineMenuTematico(menu));
							finito = true;
						} else {
							System.out.println("Non ci sono menu tematici da scegliere");
						}
						break;
				}

			} while (!finito);
		}

		return ordine;
	}

	private List<Piatto> enterSingoloOrdineCarta(List<Piatto> carta) {
		List<Piatto> ordine = new ArrayList<>();
		stampaMenuCarta(carta);
		boolean ancora = false;
		do {
			int scelto = InputDati.leggiIntero("Scegli un piatto (indice) > ", 0, carta.size() - 1);
			ordine.add(carta.get(scelto));
			ancora = InputDati.yesOrNo("Aggiungere altro? ");
		} while (ancora);
		return ordine;
	}

	/**
	 * Restiuisce il menu tematico scelto
	 * @param menu
	 * @return
	 */
	private MenuTematico enterSingoloOrdineMenuTematico(List<MenuTematico> menu) {
		stampaMenuTematici(menu);
		int scelta = InputDati.leggiIntero("Scegli il menu tematico (indice) > ", 0, menu.size() - 1);
		return menu.get(scelta);
	}

	public void stampaMenuTematici(List<MenuTematico> menu) {
		for (int i = 0; i < menu.size(); i++) {
			System.out.println(i + " - " + menu.get(i).getNome());
		}
	}

	public void stampaMenuCarta(List<Piatto> carta) {
		for (int i = 0; i < carta.size(); i++) {
			System.out.println(i + " - " + carta.get(i).getNome());
		}
	}

	private int sceltaOrdine() {
		System.out.println("Scegli un opzione:  ");
		System.out.println("1 - Menu alla Carta ");
		System.out.println("2 - Menu tematici   ");
		int scelta = InputDati.leggiIntero("Digita il menu da cui ordinare > ", 1, 2);
		return scelta;
	}

	/**
	 * Stampa il riepilogo di una giornata
	 * 
	 * @param date
	 */
	public void stampaRiepilogo(int postiLiberi, int numeroPiatti, int numeroMenu, LocalDate date) {
		System.out.println("Riepilogo giorno:  " + date);
		System.out.println("Posti liberi:      " + postiLiberi);
		System.out.println("Piatti ordinabili: " + numeroPiatti);
		System.out.println("Menu ordinabili:   " + numeroMenu);
	}

	public int richiestaPrenotazioneEliminare(int max) {
		return InputDati.leggiIntero("Scegli la prenotazione da rimuovere > ", 0,
				max - 1);
	}

	/**
	 * Stampa prenotazioni
	 * 
	 * @param prenotazioni
	 */
	public void printPrenotazioni(List<Prenotazione> prenotazioni) {
		int contatore = 0;
		if (!prenotazioni.isEmpty()) {
			System.out.println();
			for (Prenotazione p : prenotazioni) {
				System.out.println("+-------------------------+ " + contatore + " +-------------------------+");
				this.stampaPrenotazione(p);
				contatore++;
			}
		} else {
			System.out.println("Non ci sono prenotazioni");
		}

	}

	/**
	 * Stampa prenotazione
	 * 
	 * @param prenotazione
	 */
	public void stampaPrenotazione(Prenotazione prenotazione) {
		String codice_cliente = prenotazione.getCodiceCliente();
		LocalDate data = prenotazione.getDataPrenotazione();
		int day = data.getDayOfMonth(), month = data.getMonthValue(), year = data.getYear();
		int coperti = prenotazione.getNumeroCoperti();
		HashMap<Piatto, Integer> comanda = prenotazione.getComanda();
		System.out.printf("| Data: %d-%d-%d    Cliente: %-9s    Coperti: %-4d |\n", day, month, year, codice_cliente,
				coperti);
		System.out.println("+-------------------------------------------------------+");
		System.out.println(buildComandaString(comanda));
		System.out.println("+-------------------------------------------------------+");
		System.out.println();
	}

	/**
	 * Restituisce una comanda formattata
	 * 
	 * @param hash
	 * @return
	 */
	public String buildComandaString(HashMap<Piatto, Integer> hash) {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<Piatto, Integer>> iter = hash.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			Piatto p = entry.getKey();
			int i = entry.getValue();
			String voce = String.format("| Piatto: %-17s   carico: %d   porzioni: %3d |", p.getNome(),
					p.getCaricoLavoro(), i);
			builder.append(voce);
			if (iter.hasNext())
				builder.append("\n");
		}
		return builder.toString();
	}

	public void stampaMsgPrenotazioneRifiutata() {
		System.out.println("Prenotazione non effettuabile");
	}

	public void printMsgNonCiSonoPrenotazioni() {
		System.out.println("Non ci sono prenotazioni");
	}

}
