package it.unibs.ing.progetto.ristorante.UI.controllerMVC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import it.unibs.ing.progetto.ristorante.UI.view.GestioneView;
import it.unibs.ing.progetto.ristorante.controllerGRASP.GestioneController;
import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.interfacce.IGestore;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

/**
 * Controller MVC
 */
public class GestoreController implements Controller {

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
	private static final String MSG_INSERISCI_RICETTA = "E' necessario inserire almeno una ricetta.";
	private static final String MSG_AGGIUNGI_ALTRA_RICETTA = "Vuoi aggiungere un'altra ricetta? ";
	private static final String MSG_COMPLETATA_INIZIALIZZAZIONE = "\nHai completato l'inizializzazione del programma.\n";
	private static final String MSG_ELENCO_BEVANDE = "\nELENCO BEVANDE";
	private static final String MSG_ELENCO_GENERI_EXTRA = "\nELENCO GENERI EXTRA";
	private static final String MSG_GIORNO_CORRENTE = "Ora il giorno corrente �: ";
	private static final String MSG_FINE_SESSIONE_GESTORE = "Fine sessione Gestore... ";
	private static final String MSG_INSERISCI_NOME_PIATTO = "Inserisci il nome del piatto: ";
	private static final String MSG_PIATTO_GIA_ESISTE = "Il piatto gi� esiste, riprovare.";
	private static final String MSG_INSERISCI_PORZIONI = "Inserisci il numero di porzioni del piatto: ";
	private static final String MSG_INSERISCI_CARICO_LAVORO = "Inserisci il carico di lavoro per porzione: ";
	private static final String MSG_INSERISCI_INGREDIENTI = "\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.";
	private static final String MSG_AGGIUNTA_ELEMENTO_MENU = "\nE' stato aggiunto un nuovo elemento al menu.";
	
	private GestoreView view;
	private IGestore model;
	
	public GestoreController(IGestore model) {
		this.model = model;
		this.view = new GestoreView();
	}
	
	/**
	 * richiede all'utente i parametri del ristorante e almeno una ricetta, minimo
	 * necessario per il buon funzionamento del programma
	 */
	public void inizializzaRistorante() {
		view.stampaMsgBenvenutoInizializzazione();
	    LocalDate dataCorrente = view.richiestaData(MSG_INSERISCI_DATA_CORRENTE);
	    int nPosti = view.richiestaInteroPositivo(MSG_INSERISCI_NUMERO_POSTI);
	    int caricoLavoroPersona = view.richiestaInteroPositivo(MSG_INSERISCI_CARICO_LAVORO_PERSONA);
	    int caricoLavoroRistorante = caricoLavoroPersona * nPosti;

	    caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);

	    model.setDataCorrente(dataCorrente);
	    model.setNumeroPostiASedere(nPosti);
	    model.setCaricoLavoroPerPersona(caricoLavoroPersona);
	    model.setCaricoLavoroRistorante(caricoLavoroRistorante);

	    view.stampaParametriRistorante(dataCorrente, nPosti, caricoLavoroPersona, caricoLavoroRistorante);

	    view.stampaMsg(MSG_INSERISCI_RICETTA);
	    boolean altraRicetta;
	    do {
	        aggiungiPiattoRicetta(model.getElencoPiatti());
	        altraRicetta = view.yesOrNo(MSG_AGGIUNGI_ALTRA_RICETTA);
	    } while (altraRicetta);

	    view.stampaMsg(MSG_COMPLETATA_INIZIALIZZAZIONE);
	    avviaSessione();
	}

	/**
	 * presenta al gestore le operazioni che puo' eseguire e la esegue
	 */
	public void avviaSessione() {
		boolean sessioneOn = true;
		do {
			int scelta = view.scegliMenu();
			switch (scelta) {
			case AGGIUNGI_RICETTA:
				aggiungiPiattoRicetta(model.getElencoPiatti());
				break;
			case AGGIUNGI_MENU_TEMATICO:
				aggiungiMenuTematico(model.getElencoMenuTematici());
				break;
			case AGGIUNGI_BEVANDA:
				aggiungiBevanda(model.getInsiemeBevande());
				break;
			case AGGIUNGI_GENERE_EXTRA:
				aggiungiGenereExtra(model.getInsiemeGeneriExtra());
				break;
			case VISUALIZZA_PARAMETRI:
				view.stampaParametriRistorante(model.getDataCorrente(), model.getNumeroPostiASedere(),
						model.getCaricoLavoroPerPersona(), model.getCaricoLavoroRistorante());
				break;
			case VISUALIZZA_RICETTE:
				view.stampaElencoPiattiRicette(model.getElencoPiatti());
				break;
			case VISUALIZZA_MENU_TEMATICI:
				view.stampaElencoMenuTematici(model.getElencoMenuTematici());
				break;
			case VISUALIZZA_BEVANDE:
				view.stampaInsiemeProdotti(model.getInsiemeBevande(), MSG_ELENCO_BEVANDE);
				break;
			case VISUALIZZA_GENERI_EXTRA:
				view.stampaInsiemeProdotti(model.getInsiemeGeneriExtra(), MSG_ELENCO_GENERI_EXTRA);
				break;
			case 10:
				model.giornoDopo();
				view.stampaMsg(MSG_GIORNO_CORRENTE + view.ottieniStringaData(model.getDataCorrente()) + "\n");
				break;
			case ESCI:
				sessioneOn = false;
				view.stampaMsg(MSG_FINE_SESSIONE_GESTORE);
				break;
			}
		} while (sessioneOn);

	}
	
	/**
	 * Richiede gli input per la creazione di un piatto e della sua ricetta
	 * Aggiunge al model gli oggetti creati
	 * @param elencoPiatti l'elenco dei piatti presenti nel ristorante
	 */
	private void aggiungiPiattoRicetta(List<Piatto> elencoPiatti) {
		Predicate<String> condizioneValidita = nome -> elencoPiatti.stream()
	            .anyMatch(p -> p.getNomePiatto().equalsIgnoreCase(nome));
	    String nomePiatto = richiestaNomeValido(elencoPiatti, condizioneValidita, MSG_INSERISCI_NOME_PIATTO,
	            MSG_PIATTO_GIA_ESISTE);

	    int porzioni = view.richiestaInteroPositivo(MSG_INSERISCI_PORZIONI);
	    int caricoLavoro = view.richiestaInteroConMinimoMassimo(MSG_INSERISCI_CARICO_LAVORO, 0,
	            model.getCaricoLavoroPerPersona());

	    view.stampaMsg(MSG_INSERISCI_INGREDIENTI);
	    ArrayList<Prodotto> elencoIngredienti = richiestaElencoIngredienti();

	    ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

	    model.addPiattoRicetta(elencoIngredienti, porzioni, caricoLavoro, nomePiatto, periodi);
	    view.stampaMsg(MSG_AGGIUNTA_ELEMENTO_MENU);
	}

	/**
	 * richiede un nome, verifica la condizioneValidita nel nome su elencoOggetti
	 * se il nome non e' valido chiede un altro nome, altrimenti ritorna il nome valido
	 * @param elencoOggetti lista degli oggetti con cui confrontare il nuovo nuovo nome
	 * @param condizioneValidita Predicate che indica la condizione di verifica del nome
	 * @param messaggioRichiesta messaggio di richiesta del nome
	 * @param messaggioErrore messaggio di errore
	 * @return nome valido
	 */
	private String richiestaNomeValido(List<?> elencoOggetti, Predicate<String> condizioneValidita, String messaggioRichiesta, String messaggioErrore) {
	    String nomeOggetto;
	    boolean nomeValido;
	    do {
	        nomeValido = true;
	        nomeOggetto = view.richiestaNome(messaggioRichiesta);
	        for (Object o : elencoOggetti) {
	            if (condizioneValidita.test(nomeOggetto)) {
	                nomeValido = false;
	                view.stampaMsg(messaggioErrore);
	                break;
	            }
	        }
	    } while (!nomeValido);
	    return nomeOggetto;
	}
	

	
	/**
	 * crea un elenco di ingredienti diversi (per nome), presi da input utente, con
	 * dose e unita' di misura
	 * @return elenco degli ingredienti inseriti dall'utente
	 */
	private ArrayList<Prodotto> richiestaElencoIngredienti() {
		ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
		boolean altroIngrediente;
		do {
			Predicate<String> condizioneValidita = nome -> elencoIngredienti.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
			String nomeIngrediente = richiestaNomeValido(elencoIngredienti, condizioneValidita, "Inserisci il nome di un ingrediente: ", "Ingrediente gia' aggiunto, riprovare\n");
			UnitaMisura unitaMisura = view.leggiUnitaMisura();
			float dose = view.richiestaFloatPositivo("Inserisci dose: ");

			if (nomeIngrediente != null && dose > 0f && unitaMisura != null) {
				Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
				elencoIngredienti.add(ingrediente);
				view.stampaMsg("E' stata aggiunto un ingrediente.");
			} else {
				view.stampaMsg("L'elemento non e' valido");
			}
			altroIngrediente = view.yesOrNo("Vuoi aggiungere un altro ingrediente? ");
		} while (altroIngrediente);
		return elencoIngredienti;
	}


	/**
	 * Chiede a utente dati di una nuova bevanda
	 * Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale
	 * Se gia' esiste, stampa a video un msg
	 * Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	 * @param elencoBevande elenco delle bevande presenti nel model ristorante
	 */
	private void aggiungiBevanda(List<Prodotto> elencoBevande) {
		Predicate<String> condizioneValidita = nome -> elencoBevande.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomeBevanda = richiestaNomeValido(elencoBevande, condizioneValidita, "Inserisci il nome di una bevanda: ", "Bevanda gia' presente, riprovare\n");
		Float consumoProCapiteBevanda = view
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeBevanda + " (litri) : ");

		model.addBevanda(nomeBevanda, consumoProCapiteBevanda);
		view.stampaMsg("E' stata aggiunta una bevanda.");
	}	
	
	
	/**
	 * Chiede a utente dati di un nuovo genere extra
	 * Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale
	 * Se gia' esiste, stampa a video un msg
	 * Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	 * @param elencoGeneriExtra elenco dei generi extra presenti nel model ristorante
	 */
	private void aggiungiGenereExtra(List<Prodotto> elencoGeneriExtra) {
		Predicate<String> condizioneValidita = nome -> elencoGeneriExtra.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomeGenereExtra = richiestaNomeValido(elencoGeneriExtra, condizioneValidita, "Inserisci il nome di un genere extra: ", "Genere extra gia' presente, riprovare\n");
		Float consumoProCapiteGenereExtra = view
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeGenereExtra + " (hg) : ");

		model.addGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
		view.stampaMsg("E' stata aggiunto un genere extra.");
	}


	/**
	 * possibile implementazione di menu tematici con soli piatti validi
	 * crea un menu tematico e lo aggiunge a elencoMenuTematici del model
	 */
	private void aggiungiMenuTematico(List<MenuTematico> elencoMenuTematici) {
		Predicate<String> condizioneValidita = nome -> elencoMenuTematici.stream().anyMatch(m -> m.getNome().equalsIgnoreCase(nome));
		String nomeMenuTematico = richiestaNomeValido(elencoMenuTematici, condizioneValidita, "Inserisci il nome del menu tematico: ", "Il menu tematico gia' esiste, riprovare\n");

		ArrayList<Piatto> elencoPiatti = richiediElencoPiattiDelMenuTematico();
		int caricoLavoroMenuTematico = calcolaCaricoLavoroMenuTematico(elencoPiatti);

		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		model.addMenuTematico(nomeMenuTematico, elencoPiatti, caricoLavoroMenuTematico, periodi);
		view.stampaMsg("\nE' stato aggiunto un nuovo menu tematico.");
	}


	/**
	 * Crea una lista di piatti richiesti all'utente, controllando che non superino
	 * il carico lavoro massimo del menu tematico
	 * @return elenco di piatti scelti valido per un nuovo menu tematico
	 */
	private ArrayList<Piatto> richiediElencoPiattiDelMenuTematico() {
		ArrayList<Piatto> elencoPiatti = new ArrayList<>();
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
	 * presenta l'elenco contenente tutti i piatti del model e chiede
	 * all'utente quale vuole aggiungere al menu tematico
	 * @param caricoLavoroMenuTematico carico di lavoro del menu tematico attuale
	 * @param caricoLavoroMax carico di lavoro massimo raggiungibile dal menu tematico
	 * @return piatto scelto che deve essere aggiunto al menu tematico
	 */
	private Piatto richiestaPiattoDaElenco(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		int numPiattiEsistenti = controller.getElencoPiatti().size();
		view.stampaMsg("\nELENCO PIATTI DISPONIBILI: ");
		view.stampaElencoPiatti(controller.getElencoPiatti());
		view.stampaMsg("\nCarico di lavoro ancora disponibile per il menu tematico --> "
				+ (caricoLavoroMax - caricoLavoroMenuTematico));
		int scelta = view.richiestaInteroConMinimoMassimo(
				"Inserisci il numero del piatto che vuoi aggiungere al menu tematico: ", 0, numPiattiEsistenti - 1);
		Piatto piatto = controller.piattoScelto(scelta);
		return piatto;
	}

	/**
	 * dato un piatto, controlla se l'aggiunta di tale piatto al menu tematico
	 * supererebbe il carico lavoro massimo di un menu tematico
	 * se non lo supera ritorna true
	 * @param piatto nuovo piatto scelto da aggiungere al menu tematico
	 * @param caricoLavoroMenuTematico carico di lavoro raggiunto dal menu tematico
	 * @param caricoLavoroMax carico di lavoro massimo raggiungibile dal menu tematico
	 * @return piatto aggiungibile al menu tematico perche' rispetta i vincoli di carico di lavoro
	 */
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

	/**
	 * controlla se e' possibile aggiungere ancora un piatto al menu tematico
	 * questo e' possibile se il carico lavoro del menu tematico e' minore del
	 * carico lavoro massimo
	 * @param caricoLavoroMenuTematico carico di lavoro raggiunto dal menu tematico
	 * @param caricoLavoroMax carico di lavoro massimo raggiungibile dal menu tematico
	 * @return true se si puo aggiungere un altro piatto al menu tematico, false altrimenti
	 */
	private boolean controllaAggiuntaNuovoPiattoAlMenuTematico(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		boolean altroPiatto = true;
		if (caricoLavoroMenuTematico < caricoLavoroMax) {
			altroPiatto = view.yesOrNo("Vuoi aggiungere un altro piatto? ");
		} else {
			view.stampaMsg("Hai raggiunto il limite di carico di lavoro del menu.");
			altroPiatto = false;
		}
		return altroPiatto;
	}

	/**
	 * prende la lista di piatti di un menu tematico e ne calcola il carico di
	 * lavoro totale
	 * @param piatti elenco di piatti che compongono il menu tematico
	 * @return carico di lavoro del menu tematico
	 */
	private int calcolaCaricoLavoroMenuTematico(ArrayList<Piatto> piatti) {
		int caricoLavoroMenuTematico = 0;
		for (Piatto p : piatti) {
			caricoLavoroMenuTematico += p.getCaricoLavoro();
		}
		return caricoLavoroMenuTematico;
	}

	/**
	 * richiede un periodo e controlla se avra' validita in almeno un giorno futuro
	 * @param periodi elenco di periodi validi
	 */
	private void aggiungiPeriodoValido(ArrayList<Periodo> periodi) {
		boolean valido = false;
		do {
			LocalDate dataInizio = view.richiestaData("Inserire data di inizio validita'. ");
			LocalDate dataFine = view.richiestaData("Inserire data di fine validita': ");
			Periodo periodoValidita = new Periodo(dataInizio, dataFine);
			valido = periodoValidita.isValido(controller.getDataCorrente());
			if (valido) {
				periodi.add(periodoValidita);
			} else {
				view.stampaMsg("Il periodo inserito deve essere valido in futuro.");
			}
		} while (!valido);
	}

	/**
	 * chiede all'utente almeno un periodo valido
	 * @return elenco di periodi di validita' validi
	 */
	private ArrayList<Periodo> richiestaElencoPeriodiValidi() {
		view.stampaMsg("E' necessario indicare il periodo di validita' del piatto.");
		ArrayList<Periodo> periodi = new ArrayList<>();
		boolean altroPeriodo;
		do {
			aggiungiPeriodoValido(periodi);
			altroPeriodo = view.yesOrNo("Vuoi aggiungere un altro periodo di validita'? ");
		} while (altroPeriodo);
		return periodi;
	}

}
