package it.unibs.ing.progetto.ristorante.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.interfacce.IGestore;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

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
	private static final int AGGIUNGI_INGREDIENTE = 1;

	private GestoreView view;
	private IGestore model;

	public GestoreController(IGestore model) {
		this.model = model;
		this.view = new GestoreView();
	}

	// richiede all'utente i parametri del ristorante e almeno una ricetta, minimo
	// necessario per il buon funzionamento del programma
	public void inizializzaRistorante() {
		view.stampaMsgBenvenutoInizializzazione();
		LocalDate dataCorrente = view.richiestaData("Inserisci la data corrente: ");
		int nPosti = view.richiestaInteroPositivo("Inserisci il numero di posti del ristorante: ");
		int caricoLavoroPersona = view.richiestaInteroPositivo("Inserisci il carico di lavoro per persona: ");
		int caricoLavoroRistorante = caricoLavoroPersona * nPosti;

		caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);

		model.setDataCorrente(dataCorrente);
		model.setNumeroPostiASedere(nPosti);
		model.setCaricoLavoroPerPersona(caricoLavoroPersona);
		model.setCaricoLavoroRistorante(caricoLavoroRistorante);

		view.stampaParametriRistorante(dataCorrente, nPosti, caricoLavoroPersona, caricoLavoroRistorante);

		view.stampaMsg("E' necessario inserire almeno una ricetta.");
		boolean altraRicetta;
		do {
			aggiungiPiattoRicetta(model.getElencoPiatti());
			altraRicetta = view.yesOrNo("Vuoi aggiungere un altra ricetta? ");
		} while (altraRicetta);

		view.stampaMsg("\nHai completato l'inizializzazione del programma.\n");
		avviaSessione();
	}

	// presenta al gestore le operazioni che puo' eseguire e la esegue
	public void avviaSessione() {
		boolean sessioneOn = true;
		do {
			int scelta = view.printMenu();
			switch (scelta) {
			case AGGIUNGI_INGREDIENTE:
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
				view.stampaInsiemeProdotti(model.getInsiemeBevande(), "\nELENCO BEVANDE");
				break;
			case VISUALIZZA_GENERI_EXTRA:
				view.stampaInsiemeProdotti(model.getInsiemeGeneriExtra(), "\nELENCO GENERI EXTRA");
				break;
			case 10:
				model.giornoDopo();
				view.stampaMsg("Ora il giorno corrente e': " + view.ottieniStringaData(model.getDataCorrente()) + "\n");
				break;
			case ESCI:
				sessioneOn = false;
				view.stampaMsg("Fine sessione Gestore... ");
				break;
			}
		} while (sessioneOn);

	}

	// Richiede gli input per la creazione di un piatto e della sua ricetta
	// Aggiunge al model gli oggetti creati
	private void aggiungiPiattoRicetta(List<Piatto> elencoPiatti) {
		Predicate<String> condizioneValidita = nome -> elencoPiatti.stream().anyMatch(p -> p.getNomePiatto().equalsIgnoreCase(nome));
		String nomePiatto = richiestaNomeValido(elencoPiatti, condizioneValidita, "Inserisci il nome del piatto: ", "Il piatto gia' esiste, riprovare\n");
		
		int porzioni = view.richiestaInteroPositivo("Inserisci il numero di porzioni del piatto: ");
		int caricoLavoro = view.richiestaInteroConMinimoMassimo("Inserisci il carico di lavoro per porzione: ", 0,
				model.getCaricoLavoroPerPersona());

		view.stampaMsg("\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.");
		ArrayList<Prodotto> elencoIngredienti = richiestaElencoIngredienti();

		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		model.addPiattoRicetta(elencoIngredienti, porzioni, caricoLavoro, nomePiatto, periodi);
		view.stampaMsg("\nE' stato aggiunto un nuovo elemento al menu.");
	}

	//richiede un nome, verifica la condizioneValidita nel nome su elencoOggetti
	//se il nome non e' valido chiede un altro nome, altrimenti ritorna il nome valido
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
	
	//QUESTI METODI SONO STATI SOSTITUITI DAL METODO richiestaNomeValido
	
	//1
	/**
	 * richiede un nome di piatto. Se gia' esiste in elencoPiatti, lo richiede.
	 * Ritorna il nome valido
	 * 
	 * @param elencoPiatti
	 * @return
	 */
	/*private String richiestaNomePiattoValido(ArrayList<Piatto> elencoPiatti) {
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
	}*/

	//2
	// richiede un nome di ingrediente. Se gia' esiste in elencoIngredienti della
		// ricetta in questione, chiede un altro nome
		// Ritorna il nome valido
		/*private String richiestaNomeIngredienteValido(List<Prodotto> elencoIngredienti) {
			String nomeProdotto;
			boolean nomeValido;
			do {
				nomeValido = true;
				nomeProdotto = view.richiestaNome("Inserisci il nome di un ingrediente: ");
				for (Prodotto p : elencoIngredienti) {
					if (p.getNome().equalsIgnoreCase(nomeProdotto)) {
						nomeValido = false;
						view.stampaMsg("Prodotto gia presente, riprovare\n");
						break;
					}
				}
			} while (!nomeValido);
			return nomeProdotto;
		}*/
	
	//3
	// richiede un nome di bevanda. Se gia' esiste in insiemeBavande del model,
		// chiede un altro nome
		// Ritorna il nome valido
		/*private String richiestaNomeBevandaValido() {
			String nomeProdotto;
			boolean esiste = false;
			do {
				nomeProdotto = view.richiestaNome("Inserisci il nome di un prodotto: ");
				esiste = model.esisteBevanda(nomeProdotto);
			} while (esiste);
			return nomeProdotto;
		}*/
	
	//4
	// richiede un nome di genere extra. Se gia' esiste in insimeGeneriExtra del
		// model, chiede un altro nome
		// Ritorna il nome valido
		/*private String richiestaNomeGenereExtraValido() {
			String nomeProdotto;
			boolean esiste = false;
			do {
				nomeProdotto = view.richiestaNome("Inserisci il nome di un prodotto: ");
				esiste = model.esisteGenereExtra(nomeProdotto);
			} while (esiste);
			return nomeProdotto;
		}*/
	
	//5
	// chiede un nome di menu tematico. Se gia' esiste in elencoMenuTematici, lo
		// richiede. Ritorna il nome valido
		/*private String richiestaNomeMenuTematicoValido(ArrayList<MenuTematico> elencoMenuTematici) {
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
		}*/
	
	
	// crea un elenco di ingredienti diversi (per nome), presi da input utente, con
	// dose e unita' di misura
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
				view.stampaMsg("L'elemento non è valido");
			}
			altroIngrediente = view.yesOrNo("Vuoi aggiungere un altro ingrediente? ");
		} while (altroIngrediente);
		return elencoIngredienti;
	}


	// Chiede a utente dati di una nuova bevanda
	// Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	private void aggiungiBevanda(List<Prodotto> elencoBevande) {
		Predicate<String> condizioneValidita = nome -> elencoBevande.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomeBevanda = richiestaNomeValido(elencoBevande, condizioneValidita, "Inserisci il nome di una bevanda: ", "Bevanda gia' presente, riprovare\n");
		Float consumoProCapiteBevanda = view
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeBevanda + " (litri) : ");

		model.addBevanda(nomeBevanda, consumoProCapiteBevanda);
		view.stampaMsg("E' stata aggiunta una bevanda.");
	}	
	
	
	// Chiede a utente dati di un nuovo genere extra
	// Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome
	// uguale
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	private void aggiungiGenereExtra(ArrayList<Prodotto> elencoGeneriExtra) {
		Predicate<String> condizioneValidita = nome -> elencoGeneriExtra.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
		String nomeGenereExtra = richiestaNomeValido(elencoGeneriExtra, condizioneValidita, "Inserisci il nome di un genere extra: ", "Genere extra gia' presente, riprovare\n");
		Float consumoProCapiteGenereExtra = view
				.richiestaFloatPositivo("Inserisci il consumo pro capite di " + nomeGenereExtra + " (hg) : ");

		model.addGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
		view.stampaMsg("E' stata aggiunto un genere extra.");
	}


	// possibile implementazione di menu tematici con soli piatti validi
	// crea un menu tematico e lo aggiunge a elencoMenuTematici del model
	private void aggiungiMenuTematico(List<MenuTematico> elencoMenuTematici) {
		Predicate<String> condizioneValidita = nome -> elencoMenuTematici.stream().anyMatch(m -> m.getNome().equalsIgnoreCase(nome));
		String nomeMenuTematico = richiestaNomeValido(elencoMenuTematici, condizioneValidita, "Inserisci il nome del menu tematico: ", "Il menu tematico gia' esiste, riprovare\n");

		ArrayList<Piatto> elencoPiatti = richiediElencoPiattiDelMenuTematico();
		int caricoLavoroMenuTematico = calcolaCaricoLavoroMenuTematico(elencoPiatti);

		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		model.addMenuTematico(nomeMenuTematico, elencoPiatti, caricoLavoroMenuTematico, periodi);
		view.stampaMsg("\nE' stato aggiunto un nuovo menu tematico.");
	}


	//Crea una lista di piatti richiesti all'utente, controllando che non superino
	//il carico lavoro massimo del menu tematico
	private ArrayList<Piatto> richiediElencoPiattiDelMenuTematico() {
		ArrayList<Piatto> elencoPiatti = new ArrayList<>();
		int caricoLavoroMenuTematico = 0;
		int caricoLavoroMax = (int) Math.floor(model.getCaricoLavoroPerPersona() * 4.0 / 3);
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

	// presenta l'elenco contenente tutti i piatti del model e chiede
	// all'utente quale vuole aggiungere al menu tematico
	private Piatto richiestaPiattoDaElenco(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		int numPiattiEsistenti = model.getElencoPiatti().size();
		view.stampaMsg("\nELENCO PIATTI DISPONIBILI: ");
		view.stampaElencoPiatti(model.getElencoPiatti());
		view.stampaMsg("\nCarico di lavoro ancora disponibile per il menu tematico --> "
				+ (caricoLavoroMax - caricoLavoroMenuTematico));
		int scelta = view.richiestaInteroConMinimoMassimo(
				"Inserisci il numero del piatto che vuoi aggiungere al menu tematico: ", 0, numPiattiEsistenti - 1);
		Piatto piatto = model.piattoScelto(scelta);
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

	// controlla se e' possibile aggiungere ancora un piatto al menu tematico
	// questo e' possibile se il carico lavoro del menu tematico e' minore del
	// carico lavoro massimo
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
			valido = periodoValidita.isValido(model.getDataCorrente());
			if (valido) {
				periodi.add(periodoValidita);
			} else {
				view.stampaMsg("Il periodo inserito deve essere valido in futuro.");
			}
		} while (!valido);
	}

	// ritorna un ArrayList di periodi validi che chiede all'utente
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
