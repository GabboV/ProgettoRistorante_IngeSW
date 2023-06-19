package it.unibs.ing.progetto.ristorante.UI.controllerMVC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import it.unibs.ing.progetto.ristorante.UI.view.GestioneView;
import it.unibs.ing.progetto.ristorante.controllerGRASP.GestioneController;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

/**
 * Controller MVC
 */
public class GestoreController {

	private static final int ESCI = 0;
	private static final int VISUALIZZA_GENERI_EXTRA = 9;
	private static final int VISUALIZZA_BEVANDE = 8;
	private static final int VISUALIZZA_MENU_TEMATICI = 7;
	private static final int VISUALIZZA_RICETTE = 6;
	private static final int VISUALIZZA_PARAMETRI = 5;
	private static final int AGGIUNGI_GENERE_EXTRA = 4;
	private static final int AGGIUNGI_BEVANDA = 3;
	private static final int AGGIUNGI_MENU_TEMATICO = 2;
	private static final int AGGIUNGI_RICETTA = 1;
	private static final String MSG_INSERISCI_DATA_CORRENTE = "Inserisci la data corrente: ";
	private static final String MSG_INSERISCI_NUMERO_POSTI = "Inserisci il numero di posti del ristorante: ";
	private static final String MSG_INSERISCI_CARICO_LAVORO_PERSONA = "Inserisci il carico di lavoro per persona: ";
	private static final String MSG_COMPLETATA_INIZIALIZZAZIONE = "\nHai completato l'inizializzazione del programma.\n";
	private static final String MSG_ELENCO_BEVANDE = "\nELENCO BEVANDE";
	private static final String MSG_ELENCO_GENERI_EXTRA = "\nELENCO GENERI EXTRA";
	private static final String MSG_GIORNO_CORRENTE = "\nOra il giorno corrente e': ";
	private static final String MSG_FINE_SESSIONE_GESTORE = "Fine sessione Gestore... ";
	private static final String MSG_INSERISCI_NOME_PIATTO = "Inserisci il nome del piatto: ";
	private static final String MSG_PIATTO_GIA_ESISTE = "Il piatto gia' esiste, riprovare.";
	private static final String MSG_INSERISCI_PORZIONI = "Inserisci il numero di porzioni del piatto: ";
	private static final String MSG_INSERISCI_CARICO_LAVORO = "Inserisci il carico di lavoro per porzione: ";
	private static final String MSG_INSERISCI_INGREDIENTI = "\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.";
	private static final String MSG_AGGIUNTA_ELEMENTO_MENU = "\nE' stato aggiunto un nuovo elemento al menu.";

	private GestioneView window;
	private GestioneController controller;

	public GestoreController(GestioneController controller) {
		this.controller = controller;
		window = new GestioneView();
	}

	/**
	 * richiede all'utente i parametri del ristorante e almeno una ricetta, minimo
	 * necessario per il buon funzionamento del programma
	 */
	public void inizializzaRistorante() {
		window.stampaMsgBenvenutoInizializzazione();
		LocalDate dataCorrente = window.richiestaData(MSG_INSERISCI_DATA_CORRENTE);
		int nPosti = window.richiestaInteroPositivo(MSG_INSERISCI_NUMERO_POSTI);
		int caricoLavoroPersona = window.richiestaInteroPositivo(MSG_INSERISCI_CARICO_LAVORO_PERSONA);
		controller.setConfigurazioneRistorante(caricoLavoroPersona, nPosti, dataCorrente);
		// window.stampaParametriRistorante();
		window.stampaMsg(MSG_COMPLETATA_INIZIALIZZAZIONE);
	}

	/**
	 * presenta al gestore le operazioni che puo' eseguire e la esegue
	 */
	public void avviaSessione() {
		boolean sessioneOn = true;
		do {
			int scelta = window.scegliMenu();
			switch (scelta) {
				case AGGIUNGI_RICETTA:
					aggiungiPiattoRicetta(controller.getElencoPiatti());
					break;
				case AGGIUNGI_MENU_TEMATICO:
					aggiungiMenuTematico(controller.getElencoMenuTematici());
					break;
				case AGGIUNGI_BEVANDA:
					aggiungiBevanda(controller.getInsiemeBevande());
					break;
				case AGGIUNGI_GENERE_EXTRA:
					aggiungiGenereExtra(controller.getInsiemeGeneriExtra());
					break;
				case VISUALIZZA_PARAMETRI:
					window.stampaParametriRistorante(controller.getDataCorrente(), controller.getNumeroPostiASedere(),
							controller.getCaricoLavoroPerPersona(), controller.getCaricoLavoroRistorante());
					break;
				case VISUALIZZA_RICETTE:
					window.stampaElencoPiattiRicette(controller.getElencoPiatti());
					break;
				case VISUALIZZA_MENU_TEMATICI:
					window.stampaElencoMenuTematici(controller.getElencoMenuTematici());
					break;
				case VISUALIZZA_BEVANDE:
					window.stampaInsiemeProdotti(controller.getInsiemeBevande(), MSG_ELENCO_BEVANDE);
					break;
				case VISUALIZZA_GENERI_EXTRA:
					window.stampaInsiemeProdotti(controller.getInsiemeGeneriExtra(), MSG_ELENCO_GENERI_EXTRA);
					break;
				case 10:
					controller.giornoDopo();
					window.stampaMsg(MSG_GIORNO_CORRENTE + window.ottieniStringaData(controller.getDataCorrente()) + "\n");
					break;
				case ESCI:
					sessioneOn = false;
					window.stampaMsg(MSG_FINE_SESSIONE_GESTORE);
					break;
			}
		} while (sessioneOn);

	}

	/**
	 * Richiede gli input per la creazione di un piatto e della sua ricetta Aggiunge
	 * al model gli oggetti creati
	 * 
	 * @param elencoPiatti l'elenco dei piatti presenti nel ristorante
	 */
	public void aggiungiPiattoRicetta(List<Piatto> elencoPiatti) {
		Predicate<String> condizioneValidita = nome -> elencoPiatti.stream()
				.anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomePiatto = richiestaNomeValido(condizioneValidita, MSG_INSERISCI_NOME_PIATTO,
				MSG_PIATTO_GIA_ESISTE);

		int porzioni = window.richiestaInteroPositivo(MSG_INSERISCI_PORZIONI);
		int caricoLavoro = window.richiestaInteroConMinimoMassimo(MSG_INSERISCI_CARICO_LAVORO, 0,
				controller.getCaricoLavoroPerPersona());

		window.stampaMsg(MSG_INSERISCI_INGREDIENTI);
		ArrayList<Prodotto> elencoIngredienti = richiestaElencoIngredienti();
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		controller.aggiungiPiattoRicetta(nomePiatto, porzioni, caricoLavoro, elencoIngredienti, periodi);
		window.stampaMsg(MSG_AGGIUNTA_ELEMENTO_MENU);
	}

	/**
	 * richiede un nome, verifica la condizioneValidita nel nome su elencoOggetti se
	 * il nome non e' valido chiede un altro nome, altrimenti ritorna il nome valido
	 * 
	 * @param elencoOggetti      lista degli oggetti con cui confrontare il nuovo
	 *                           nuovo nome
	 * @param condizioneValidita Predicate che indica la condizione di verifica del
	 *                           nome
	 * @param messaggioRichiesta messaggio di richiesta del nome
	 * @param messaggioErrore    messaggio di errore
	 * @return nome valido
	 */
	private String richiestaNomeValido(Predicate<String> condizioneValidita,
			String messaggioRichiesta, String messaggioErrore) {
		String nomeOggetto;
		boolean nomeValido;
		do {
			nomeValido = true;
			nomeOggetto = window.richiestaNome(messaggioRichiesta);
			if (condizioneValidita.test(nomeOggetto)) {
				nomeValido = false;
				window.stampaMsg(messaggioErrore);
			}
		} while (!nomeValido);
		return nomeOggetto;
	}

	/**
	 * crea un elenco di ingredienti diversi (per nome), presi da input utente, con
	 * dose e unita' di misura
	 * 
	 * @return elenco degli ingredienti inseriti dall'utente
	 */
	private ArrayList<Prodotto> richiestaElencoIngredienti() {
		ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
		boolean altroIngrediente;
		do {
			Predicate<String> condizioneValidita = nome -> elencoIngredienti.stream()
					.anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
			String nomeIngrediente = richiestaNomeValido(condizioneValidita,
					"Inserisci il nome di un ingrediente: ", "Ingrediente gia' aggiunto, riprovare\n");
			UnitaMisura unitaMisura = window.leggiUnitaMisura();
			float dose = window.richiestaFloatPositivo("Inserisci dose: ");

			if (nomeIngrediente != null && dose > 0f && unitaMisura != null) {
				Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
				elencoIngredienti.add(ingrediente);
				window.stampaMsg("E' stata aggiunto un ingrediente.");
			} else {
				window.stampaMsg("L'elemento non e' valido");
			}
			altroIngrediente = window.yesOrNo("Vuoi aggiungere un altro ingrediente? ");
		} while (altroIngrediente);
		return elencoIngredienti;
	}

	/**
	 * Chiede a utente dati di una nuova bevanda Controlla se in insiemeBevande
	 * esiste gia' una bevanda con il nome uguale Se gia' esiste, stampa a video un
	 * msg Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	 * 
	 * @param elencoBevande elenco delle bevande presenti nel model ristorante
	 */
	private void aggiungiBevanda(List<Prodotto> elencoBevande) {
		Predicate<String> condizioneValidita = nome -> elencoBevande.stream()
				.anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomeBevanda = richiestaNomeValido(condizioneValidita,
				"Inserisci il nome di una bevanda: ", "Bevanda gia' presente, riprovare\n");
		Float consumoProCapiteBevanda = window
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeBevanda + " (litri) : ");

		controller.aggiungiBevanda(nomeBevanda, consumoProCapiteBevanda);
		window.stampaMsg("E' stata aggiunta una bevanda.");
	}

	/**
	 * Chiede a utente dati di un nuovo genere extra Controlla se in
	 * insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale Se gia'
	 * esiste, stampa a video un msg Se non esiste la aggiunge a inisemeGeneriExtra
	 * e stampa a video un msg
	 * 
	 * @param elencoGeneriExtra elenco dei generi extra presenti nel model
	 *                          ristorante
	 */
	public void aggiungiGenereExtra(List<Prodotto> elencoGeneriExtra) {
		Predicate<String> condizioneValidita = nome -> elencoGeneriExtra.stream()
				.anyMatch(p -> p.getNome().equalsIgnoreCase(nome));

				
		String nomeGenereExtra = richiestaNomeValido(condizioneValidita,
				"Inserisci il nome di un genere extra: ", "Genere extra gia' presente, riprovare\n");
		Float consumoProCapiteGenereExtra = window
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeGenereExtra + " (hg) : ");

		controller.aggiungiGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
		window.stampaMsg("E' stata aggiunto un genere extra.");
	}

	/**
	 * possibile implementazione di menu tematici con soli piatti validi crea un
	 * menu tematico e lo aggiunge a elencoMenuTematici del model
	 */
	public void aggiungiMenuTematico(List<MenuTematico> elencoMenuTematici) {
		Predicate<String> condizioneValidita = nome -> elencoMenuTematici.stream()
				.anyMatch(m -> m.getNome().equalsIgnoreCase(nome));
		String nomeMenuTematico = richiestaNomeValido(condizioneValidita,
				"Inserisci il nome del menu tematico: ", "Il menu tematico gia' esiste, riprovare\n");
		List<MenuComponent> elencoPiatti = richiediElencoPiattiDelMenuTematico();
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		controller.aggiungiMenuTematico(nomeMenuTematico, elencoPiatti, periodi);;
		window.stampaMsg("\nE' stato aggiunto un nuovo menu tematico.");
	}

	/**
	 * Crea una lista di piatti richiesti all'utente, controllando che non superino
	 * il carico lavoro massimo del menu tematico
	 * 
	 * @return elenco di piatti scelti valido per un nuovo menu tematico
	 */
	public List<MenuComponent> richiediElencoPiattiDelMenuTematico() {
		ArrayList<MenuComponent> elencoPiatti = new ArrayList<>();
		int caricoLavoroMenuTematico = 0;
		int caricoLavoroMax = (int) Math.floor(controller.getCaricoLavoroPerPersona() * 4.0 / 3);
		boolean altroPiatto = false;
		do {
			Piatto piatto = richiestaPiattoDaElenco(caricoLavoroMenuTematico, caricoLavoroMax);
			boolean piattoValido = controllaCaricoLavoroPiattoPerMenuTematico(piatto, caricoLavoroMenuTematico,
					caricoLavoroMax);
			if (piattoValido) {
				elencoPiatti.add(piatto);
				caricoLavoroMenuTematico += piatto.getCaricoLavoro();
			}
			altroPiatto = controllaAggiuntaNuovoPiattoAlMenuTematico(caricoLavoroMenuTematico, caricoLavoroMax);
		} while (altroPiatto);
		return elencoPiatti;
	}

	/**
	 * presenta l'elenco contenente tutti i piatti del model e chiede all'utente
	 * quale vuole aggiungere al menu tematico
	 * 
	 * @param caricoLavoroMenuTematico carico di lavoro del menu tematico attuale
	 * @param caricoLavoroMax          carico di lavoro massimo raggiungibile dal
	 *                                 menu tematico
	 * @return piatto scelto che deve essere aggiunto al menu tematico
	 */
	private Piatto richiestaPiattoDaElenco(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		int numPiattiEsistenti = controller.getElencoPiatti().size();
		window.stampaMsg("\nELENCO PIATTI DISPONIBILI: ");
		window.stampaElencoPiatti(controller.getElencoPiatti());
		window.stampaMsg("\nCarico di lavoro ancora disponibile per il menu tematico --> "
				+ (caricoLavoroMax - caricoLavoroMenuTematico));
		int scelta = window.richiestaInteroConMinimoMassimo(
				"Inserisci il numero del piatto che vuoi aggiungere al menu tematico: ", 0, numPiattiEsistenti - 1);
		Piatto piatto = controller.piattoScelto(scelta);
		return piatto;
	}

	/**
	 * dato un piatto, controlla se l'aggiunta di tale piatto al menu tematico
	 * supererebbe il carico lavoro massimo di un menu tematico se non lo supera
	 * ritorna true
	 * 
	 * @param piatto                   nuovo piatto scelto da aggiungere al menu
	 *                                 tematico
	 * @param caricoLavoroMenuTematico carico di lavoro raggiunto dal menu tematico
	 * @param caricoLavoroMax          carico di lavoro massimo raggiungibile dal
	 *                                 menu tematico
	 * @return piatto aggiungibile al menu tematico perche' rispetta i vincoli di
	 *         carico di lavoro
	 */
	private boolean controllaCaricoLavoroPiattoPerMenuTematico(Piatto piatto, int caricoLavoroMenuTematico,
			int caricoLavoroMax) {
		boolean piattoValido = false;
		if (caricoLavoroMenuTematico + piatto.getCaricoLavoro() <= caricoLavoroMax) {
			piattoValido = true;
		} else {
			window.stampaMsg(
					"Il carico di lavoro del menu tematico supera il limite massimo. Il piatto non e' stato aggiunto.");
			piattoValido = false;
		}
		return piattoValido;
	}

	/**
	 * controlla se e' possibile aggiungere ancora un piatto al menu tematico questo
	 * e' possibile se il carico lavoro del menu tematico e' minore del carico
	 * lavoro massimo
	 * 
	 * @param caricoLavoroMenuTematico carico di lavoro raggiunto dal menu tematico
	 * @param caricoLavoroMax          carico di lavoro massimo raggiungibile dal
	 *                                 menu tematico
	 * @return true se si puo aggiungere un altro piatto al menu tematico, false
	 *         altrimenti
	 */
	private boolean controllaAggiuntaNuovoPiattoAlMenuTematico(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		boolean altroPiatto = true;
		if (caricoLavoroMenuTematico < caricoLavoroMax) {
			altroPiatto = window.yesOrNo("Vuoi aggiungere un altro piatto? ");
		} else {
			window.stampaMsg("Hai raggiunto il limite di carico di lavoro del menu.");
			altroPiatto = false;
		}
		return altroPiatto;
	}

	/**
	 * richiede un periodo e controlla se avra' validita in almeno un giorno futuro
	 * 
	 * @param periodi elenco di periodi validi
	 */
	private void aggiungiPeriodoValido(List<Periodo> periodi) {
		boolean valido = false;
		do {
			LocalDate dataInizio = window.richiestaData("Inserire data di inizio validita'. ");
			LocalDate dataFine = window.richiestaData("Inserire data di fine validita': ");
			Periodo periodoValidita = new Periodo(dataInizio, dataFine);
			valido = periodoValidita.isValido(controller.getDataCorrente());
			if (valido) {
				periodi.add(periodoValidita);
			} else {
				window.stampaMsg("Il periodo inserito deve essere valido in futuro.");
			}
		} while (!valido);
	}

	/**
	 * chiede all'utente almeno un periodo valido
	 * 
	 * @return elenco di periodi di validita' validi
	 */
	private ArrayList<Periodo> richiestaElencoPeriodiValidi() {
		window.stampaMsg("E' necessario indicare il periodo di validita' del piatto.");
		ArrayList<Periodo> periodi = new ArrayList<>();
		boolean altroPeriodo;
		do {
			aggiungiPeriodoValido(periodi);
			altroPeriodo = window.yesOrNo("Vuoi aggiungere un altro periodo di validita'? ");
		} while (altroPeriodo);
		return periodi;
	}

}
