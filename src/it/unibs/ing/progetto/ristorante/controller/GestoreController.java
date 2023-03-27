package it.unibs.ing.progetto.ristorante.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.Agenda;
import it.unibs.ing.progetto.ristorante.model.Gestione;
import it.unibs.ing.progetto.ristorante.model.Magazzino;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

public class GestoreController extends Controller {

	private static final int ESCI = 0;
	private static final int VISUALIZZA_GENERI_EXTRA = 9;
	private static final int VISUALIZZA_BEVANDE = 8;
	private static final int VISUALIZZA_MENU_TEMATICI = 7;
	private static final int VISUALIZZA_RICETTE = 6;
	private static final int VISUALIZZA_PARAMETRI = 5;
	private static final int AGGIUNGI_GENERE_EXTRA = 4;
	private static final int AGGIUNGI_BEVANDA = 3;
	private static final int AGGIUNGI_MENU_TEMATICO = 2;
	private static final int AGGIUNGI_INGREDIENTE = 1;

	private GestoreView view;
	private IGestore iGestore;

	public GestoreController(Ristorante model) {
		super(model);
		this.view = new GestoreView();
	}

	// GETTER AND SETTER
	// CONTROLLER METHODS

	// richiede all'utente i parametri del this.getModel() e almeno una ricetta,
	// minimo
	// necessario per un buon funzionamento del programma
	public Ristorante inizializzaRistorante() {
		Ristorante ristorante = new Ristorante();

		view.stampaMsgBenvenutoInizializzazione();

		LocalDate dataCorrente = LocalDate.now(); // view.richiestaData("Inserisci la dataCorrente.");
		this.getModel().setDataCorrente(dataCorrente);

		int nPosti = view.richiestaInteroPositivo("Inserisci il numero di posti del this.getModel(): ");
		this.getModel().setNumeroPostiASedere(nPosti);

		int caricoLavoroPersona = view.richiestaInteroPositivo("Inserisci il carico di lavoro per persona: ");
		this.getModel().setCaricoLavoroPerPersona(caricoLavoroPersona);

		int caricoLavoroRistorante = caricoLavoroPersona * nPosti;
		caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);
		this.getModel().setCaricoLavoroRistorante(caricoLavoroRistorante);

		view.stampaParametriRistorante(dataCorrente, nPosti, caricoLavoroPersona, caricoLavoroRistorante);

		view.stampaMsg("E' necessario inserire almeno una ricetta.");
		boolean altraRicetta;
		do {
			aggiungiPiattoRicetta();
			altraRicetta = view.richiestaNuovaAggiunta("Vuoi aggiungere un altra ricetta? ");
		} while (altraRicetta);

		view.stampaMsg("\nHai completato l'inizializzazione del programma.\n");
		avviaSessione();
		return ristorante;
	}

	// presenta al gestore le operazioni che puo' eseguire e la esegue
	public void avviaSessione() {
		// forse da mettere tutte le final insieme
		boolean sessioneOn = true;
		do {
			int scelta = view.printMenu();
			switch (scelta) {
				case AGGIUNGI_INGREDIENTE:
					aggiungiPiattoRicetta();
					break;
				case AGGIUNGI_MENU_TEMATICO:
					aggiungiMenuTematico();
					break;
				case AGGIUNGI_BEVANDA:
					aggiungiBevanda();
					break;
				case AGGIUNGI_GENERE_EXTRA:
					aggiungiGenereExtra();
					break;
				case VISUALIZZA_PARAMETRI:
					view.stampaParametriRistorante(this.getModel().getDataCorrente(),
							this.getModel().getNumeroPostiASedere(), this.getModel().getCaricoLavoroPerPersona(),
							this.getModel().getCaricoLavoroRistorante());
					view.stampaMsg("");
					break;
				case VISUALIZZA_RICETTE:
					view.stampaMsg("\nELENCO PIATTI-RICETTE");
					view.stampaElencoPiattiRicette(this.getModel().getElencoPiatti());
					view.stampaMsg("");
					break;
				case VISUALIZZA_MENU_TEMATICI:
					view.stampaMsg("\nELENCO MENU TEMATICI");
					view.stampaElencoMenuTematici(this.getModel().getElencoMenuTematici());
					view.stampaMsg("");
					break;
				case VISUALIZZA_BEVANDE:
					view.stampaMsg("\nELENCO BEVANDE");
					view.stampaInsiemeProdotti(this.getModel().getInsiemeBevande());
					view.stampaMsg("");
					break;
				case VISUALIZZA_GENERI_EXTRA:
					view.stampaMsg("\nELENCO GENERI EXTRA");
					view.stampaInsiemeProdotti(this.getModel().getInsiemeGeneriExtra());
					view.stampaMsg("");
					break;
				case ESCI:
					sessioneOn = false;
					System.out.println("Fine sessione Gestore...");
					break;
			}
		} while (sessioneOn);

	}

	// richiede dati di un nuovo piatto e ricetta rispettiva al gestore e la
	// aggiunge al this.getModel()
	private void aggiungiPiattoRicetta() {
		String nomePiatto = richiestaNomePiattoValido(this.getModel().getElencoPiatti());
		int porzioni = view.richiestaInteroPositivo("Inserisci il numero di porzioni del piatto: ");
		int caricoLavoro = view.richiestaInteroConMinimoMassimo("Inserisci il carico di lavoro per porzione: ", 0,
				this.getModel().getCaricoLavoroPerPersona());

		view.stampaMsg("\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.");
		ArrayList<Prodotto> elencoIngredienti = richiestaElencoIngredienti();

		view.stampaMsg("E' necessario indicare il periodo di validita' del piatto.");
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		this.getModel().addPiattoRicetta(elencoIngredienti, porzioni, caricoLavoro, nomePiatto, periodi);
		view.stampaMsg("\nE' stato aggiunto un nuovo elemento al menu.");
	}

	// richiede un nome di piatto. Se gia' esiste in elencoPiatti, lo richiede.
	// Ritorna il nome valido
	private String richiestaNomePiattoValido(ArrayList<Piatto> elencoPiatti) {
		String nomePiatto;
		boolean nomeValido = true;
		do {
			nomeValido = true;
			nomePiatto = view.richiestaNome("Inserisci il nome di un piatto: ");
			for (Piatto p : elencoPiatti) {
				if (p.getNomePiatto().equalsIgnoreCase(nomePiatto)) {
					view.stampaMsg("Il piatto gia' esiste.");
					nomeValido = false;
					break;
				}
			}
		} while (!nomeValido);
		return nomePiatto;
	}

	// crea un elenco di ingredienti diversi (per nome), presi da input utente, con
	// dose e unita' di misura
	private ArrayList<Prodotto> richiestaElencoIngredienti() {
		ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
		boolean altroIngrediente;
		do {
			String nomeIngrediente = richiestaNomeIngredienteValido(elencoIngredienti);
			UnitaMisura unitaMisura = view.richiestaUnitaMisura("Inserisci unita di misura: ");
			float dose = view.richiestaFloatPositivo("Inserisci dose: ");

			Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
			elencoIngredienti.add(ingrediente);
			view.stampaMsg("E' stata aggiunto un ingrediente.");

			altroIngrediente = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro ingrediente? ");
		} while (altroIngrediente);
		return elencoIngredienti;
	}
	
	// richiede un nome di ingrediente. Se gia' esiste in elencoIngredienti della ricetta in questione, chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeIngredienteValido(ArrayList<Prodotto> elencoIngredienti) {
		String nomeProdotto;
		boolean nomeValido;
		do {
			nomeValido = true;
			nomeProdotto = view.richiestaNome("Inserisci il nome di un prodotto: ");
			for (Prodotto p : elencoIngredienti) {
				if (p.getNome().equalsIgnoreCase(nomeProdotto)) {
					nomeValido = false;
					view.stampaMsg("Prodotto gia presente, riprovare\n");
					break;
				}
			}
		} while (!nomeValido);
		return nomeProdotto;
	}

	// Chiede a utente dati di una nuova bevanda
	// Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	// Chiede a utente se vuole aggiungere un'altra bevanda
	private void aggiungiBevanda() {
		boolean altraBevanda = true;
		do {
			String nomeBevanda = richiestaNomeBevandaValido();
			Float consumoProCapiteBevanda = view
					.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeBevanda + " (litri) : ");

			this.getModel().addBevanda(nomeBevanda, consumoProCapiteBevanda);
			view.stampaMsg("E' stata aggiunta una bevanda.");
			altraBevanda = view.richiestaNuovaAggiunta("Vuoi aggiungere un'altra bevanda? ");
		} while (altraBevanda);

	}

	// richiede un nome di bevanda. Se gia' esiste in insiemeBavande del model, chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeBevandaValido() {
		String nomeProdotto;
		boolean esiste = false;
		do {
			nomeProdotto = view.richiestaNome("Inserisci il nome di un prodotto: ");
			esiste = this.getModel().esisteBevanda(nomeProdotto);
		} while (esiste);
		return nomeProdotto;
	}

	// Chiede a utente dati di un nuovo genere extra
	// Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	// Chiede a utente se vuole aggiungere un altro genere extra
	private void aggiungiGenereExtra() {
		boolean altroGenereExtra = true;
		do {
			String nomeGenereExtra = richiestaNomeGenereExtraValido();
			Float consumoProCapiteGenereExtra = view
					.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeGenereExtra + " (hg) : ");

			this.getModel().addGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
			view.stampaMsg("E' stata aggiunto un genere extra.");
			altroGenereExtra = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro genere extra? ");
		} while (altroGenereExtra);
	}

	// richiede un nome di genere extra. Se gia' esiste in insimeGeneriExtra del model, chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeGenereExtraValido() {
		String nomeProdotto;
		boolean esiste = false;
		do {
			nomeProdotto = view.richiestaNome("Inserisci il nome di un prodotto: ");
			esiste = this.getModel().esisteGenereExtra(nomeProdotto);
		} while (esiste);
		return nomeProdotto;
	}

	// possibile implementazione di menu tematici con soli piatti validi
	// crea un menu tematico e lo aggiunge a elencoMenuTematici del this.getModel()
	private void aggiungiMenuTematico() {
		String nomeMenuTematico = richiestaNomeMenuTematicoValido(this.getModel().getElencoMenuTematici());

		ArrayList<Piatto> elencoPiatti = richiediElencoPiattiDelMenuTematico();
		int caricoLavoroMenuTematico = calcolaCaricoLavoroMenuTematico(elencoPiatti);

		view.stampaMsg("E' necessario indicare il periodo di validita'.");
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		this.getModel().addMenuTematico(nomeMenuTematico, elencoPiatti, caricoLavoroMenuTematico, periodi);
		view.stampaMsg("\nE' stato aggiunto un nuovo menu tematico.");
	}

	// chiede un nome di menu tematico. Se gia' esiste in elencoMenuTematici, lo
	// richiede. Ritorna il nome valido
	private String richiestaNomeMenuTematicoValido(ArrayList<MenuTematico> elencoMenuTematici) {
		String nomeMenuTematico;
		boolean nomeValido = true;
		do {
			nomeValido = true;
			nomeMenuTematico = view.richiestaNome("Inserisci il nome del menu tematico: ");

			for (MenuTematico m : elencoMenuTematici) {
				if (m.getNome().equalsIgnoreCase(nomeMenuTematico)) {
					view.stampaMsg("Il nome del menu tematico e' gia' stato utilizzato.");
					nomeValido = false;
					break;
				}
			}
		} while (!nomeValido);
		return nomeMenuTematico;
	}

	// crea una lista di piatti richiesti all'utente, controllando che non superino
	// il carico lavoro massimo del menu tematico
	private ArrayList<Piatto> richiediElencoPiattiDelMenuTematico() {
		ArrayList<Piatto> elencoPiatti = new ArrayList<>();
		int caricoLavoroMenuTematico = 0;
		int caricoLavoroMax = (int) Math.floor(this.getModel().getCaricoLavoroPerPersona() * 4.0 / 3);
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

	// presenta l'elenco contenente tutti i piatti del this.getModel() e chiede
	// all'utente quale vuole aggiungere al menu tematico
	private Piatto richiestaPiattoDaElenco(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		int numPiattiEsistenti = this.getModel().getElencoPiatti().size();
		view.stampaMsg("\nELENCO PIATTI DISPONIBILI: ");
		view.stampaElencoPiatti(this.getModel().getElencoPiatti());
		view.stampaMsg("\nCarico di lavoro ancora disponibile per il menu tematico --> "
				+ (caricoLavoroMax - caricoLavoroMenuTematico));
		int scelta = view.richiestaInteroConMinimoMassimo(
				"Inserisci il numero del piatto che vuoi aggiungere al menu tematico: ", 0, numPiattiEsistenti - 1);
		Piatto piatto = this.getModel().piattoScelto(scelta);
		return piatto;
	}

	// dato un piatto, controlla se l'aggiunta di tale piatto al menu tematico
	// supererebbe il carico lavoro massimo di un menu tematico
	// se non lo supera ritorna true
	private boolean controllaCaricoLavoroPiattoPerMenuTematico(Piatto piatto, int caricoLavoroMenuTematico,
			int caricoLavoroMax) {
		boolean piattoValido = false;
		if (caricoLavoroMenuTematico + piatto.getCaricoLavoro() <= caricoLavoroMax) {
			piattoValido = true;
		} else {
			view.stampaMsg(
					"Il carico di lavoro del menu tematico supera il limite massimo. Il piatto non e' stato aggiunto.");
			piattoValido = false;
		}
		return piattoValido;
	}

	// controlla se � possibile aggiungere ancora un piatto al menu tematico
	// questo � possibile se il carico lavoro del menu tematico e' minore del
	// carico
	// lavoro massimo
	private boolean controllaAggiuntaNuovoPiattoAlMenuTematico(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		boolean altroPiatto = true;
		if (caricoLavoroMenuTematico < caricoLavoroMax) {
			altroPiatto = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro piatto? ");
		} else {
			view.stampaMsg("Hai raggiunto il limite di carico di lavoro del menu.");
			altroPiatto = false;
		}
		return altroPiatto;
	}

	// prende la lista di piatti di un menu tematico e ne calcola il carico di
	// lavoro totale
	private int calcolaCaricoLavoroMenuTematico(ArrayList<Piatto> piatti) {
		int caricoLavoroMenuTematico = 0;
		for (Piatto p : piatti) {
			caricoLavoroMenuTematico += p.getCaricoLavoro();
		}
		return caricoLavoroMenuTematico;
	}

	// richiede un periodo e controlla se avra' validita in almeno un giorno futuro
	private void aggiungiPeriodoValido(ArrayList<Periodo> periodi) {
		boolean valido = false;
		do {
			LocalDate dataInizio = view.richiestaData("Inserire data di inizio validita'. ");
			LocalDate dataFine = view.richiestaData("Inserire data di fine validita': ");
			Periodo periodoValidita = new Periodo(dataInizio, dataFine);
			valido = periodoValidita.isValido(this.getModel().getDataCorrente());
			if (valido) {
				periodi.add(periodoValidita);
			} else {
				view.stampaMsg("Il periodo inserito deve essere valido in futuro.");
			}
		} while (!valido);
	}

	// ritorna un ArrayList di periodi validi che chiede all'utente
	private ArrayList<Periodo> richiestaElencoPeriodiValidi() {
		ArrayList<Periodo> periodi = new ArrayList<>();
		boolean altroPeriodo;
		do {
			aggiungiPeriodoValido(periodi);
			altroPeriodo = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro periodo di validita'? ");
		} while (altroPeriodo);
		return periodi;
	}

}
