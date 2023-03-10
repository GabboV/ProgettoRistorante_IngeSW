package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.RowFilter.ComparisonType;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

public class GestoreController{

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
	
	private Ristorante ristorante;
	private GestoreView view;
	
	//CONSTRUCTORS
	public GestoreController(Ristorante ristorante) {
		this.ristorante = ristorante;
	}
	
	public GestoreController() {
	}
	
	//GETTER AND SETTER
	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public GestoreView getView() {
		return view;
	}

	public void setView(GestoreView view) {
		this.view = view;
	}
	
	//CONTROLLER METHODS
	
	//richiede all'utente i parametri del ristorante e almeno una ricetta
	public Ristorante inizializzaRistorante() {
		ristorante = new Ristorante();
		view = new GestoreView();
		
		view.stampaMsgBenvenutoInizializzazione();
		
		LocalDate dataCorrente = view.richiestaData("Inserisci la dataCorrente.");
		ristorante.setDataCorrente(dataCorrente);
		
		int nPosti = view.richiestaNumeroPostiRistorante("Inserisci il numero di posti del ristorante: ");
		ristorante.setNumeroPostiASedere(nPosti);
		
		int caricoLavoroPersona = view.richiestaCaricoLavoro("Inserisci il carico di lavoro per persona: ");
		ristorante.setCaricoLavoroPerPersona(caricoLavoroPersona);
		
		int caricoLavoroRistorante = caricoLavoroPersona * nPosti;
		caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);
		ristorante.setCaricoLavoroRistorante(caricoLavoroRistorante);
		
		view.stampaParametriRistorante(dataCorrente, nPosti, caricoLavoroPersona, caricoLavoroRistorante);
		
		//sistema puo funzionare con solo una ricetta-piatto e parametri?
		//da finire (aggiunta in ristorante e controllo duplicati e aggiunta piu periodoValidi)
		view.stampaMsg("E' necessario inserire almeno una ricetta.");
		boolean altraRicetta;
		do {
			aggiungiPiattoRicetta();
			altraRicetta = view.richiestaNuovaAggiunta("Vuoi aggiungere un altra ricetta? ");
		} while(altraRicetta);
	
		view.stampaMsg("\nHai completato l'inizializzazione del programma.\n");
		apriMenuGestore();
		return ristorante;
	}

	
	//presenta al gestore le operazioni che puo' eseguire e la esegue
	public void apriMenuGestore() {
		//forse da mettere tutte le final insieme
		view = new GestoreView();
		//da fare ciclo while per apriMenuGestore
		boolean sessioneOn = true;
		do {
			int scelta = view.stampaMenuGestore();
			switch(scelta) {
			case AGGIUNGI_INGREDIENTE:
				aggiungiPiattoRicetta();
				break;
			case AGGIUNGI_MENU_TEMATICO:
				//DA IMPLEMENTARE
				break;
			case AGGIUNGI_BEVANDA:
				aggiungiBevanda();
				break;
			case AGGIUNGI_GENERE_EXTRA:
				aggiungiGenereExtra();
				break;
			case VISUALIZZA_PARAMETRI:
				//potrei dare tutto il model come parametro, ma così do solo parametri necessari
				view.stampaParametriRistorante(ristorante.getDataCorrente(), ristorante.getNumeroPostiASedere(), ristorante.getCaricoLavoroPerPersona(), ristorante.getCaricoLavoroRistorante());
				System.out.println();
				break;
			case VISUALIZZA_RICETTE:
				//ciclo che stampa ricette (con contatore)
				//DA SPOSTARE IN GESTORE VIEW
				view.stampaMsg("\nELENCO PIATTI-RICETTE");
				view.stampaElencoPiattiRicette(ristorante.getElencoPiatti(), ristorante.getElencoRicette(), ristorante.getCorrispondenzePiattoRicetta());
				break;
			case VISUALIZZA_MENU_TEMATICI:
				System.out.println();
				//DA IMPLEMENTARE
				break;
			case VISUALIZZA_BEVANDE:
				view.stampaMsg("\nELENCO BEVANDE");
				view.stampaInsiemeProdotti(ristorante.getInsiemeBevande());
				System.out.println();
				break;
			case VISUALIZZA_GENERI_EXTRA:
				view.stampaMsg("\nELENCO GENERI EXTRA");
				view.stampaInsiemeProdotti(ristorante.getInsiemeGeneriExtra());
				System.out.println();
				break;
			case ESCI:
				sessioneOn = false;
				System.out.println("Fine sessione Gestore...");
				break;
			}
		} while(sessioneOn);
		
	}
	
	//richiede dati di un nuovo piatto e ricetta rispettiva al gestore e la aggiunge al ristorante
	private void aggiungiPiattoRicetta() {
		String nomePiatto;
		boolean nomeValido = true;
		//richiede il nome inserito se esiste gia' nell'elenco dei piatti
		do {
			nomePiatto = view.richiestaNome("Inserisci il nome del piatto: ");
			for(Piatto p : ristorante.getElencoPiatti()) {
				if(p.getNomePiatto().equalsIgnoreCase(nomePiatto)) {
					view.stampaMsg("Il nome del piatto e' gia' stato utilizzato.");
					nomeValido = false;
					break;
				} else {
					nomeValido = true;
				}
			}
		} while(!nomeValido);
			
		int porzioni = view.richiestaNumeroPorzioni("Inserisci il numero di porzioni del piatto: ");
		int caricoLavoro = view.richiestaCaricoLavoro("Inserisci il carico di lavoro per porzione: ");
		//forse da creare istanze nel model?
		Ricetta r = new Ricetta(porzioni, caricoLavoro);
		Piatto p = new Piatto(nomePiatto, caricoLavoro);
		
		view.stampaMsg("\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.");
		
		boolean altroIngrediente;
		do {
			aggiungiIngrediente(r);
			altroIngrediente = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro ingrediente? ");
		} while(altroIngrediente);

		view.stampaMsg("E' necessario indicare il periodo di validita' del piatto.");
		boolean altroPeriodo;
		do {
			aggiungiPeriodoValido(p);
			altroPeriodo = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro periodo di validita'? ");
		} while(altroPeriodo);
		ristorante.addPiattoRicetta(p, r);
		view.stampaMsg("\nE' stato aggiunto un nuovo elemento al menu.");
		view.stampaPiattoRicetta(p, r);
	}

	
	//richiede un periodo e controlla se avra' validita in almeno un giorno futuro
	private void aggiungiPeriodoValido(Piatto p) {
		boolean valido = true;
		do {
			LocalDate dataInizio = view.richiestaData("Inserire data di inizio validita'. ");
			LocalDate dataFine = view.richiestaData("Inserire data di fine validita': ");
			if((dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine)) && dataFine.isAfter(ristorante.getDataCorrente())) {
				Periodo periodoValidita = new Periodo(dataInizio, dataFine);
				p.addPeriodoValidita(periodoValidita);
				valido = true;
			} else {
				view.stampaMsg("Il periodo inserito deve essere valido in futuro.");
				valido = false;
			}
		} while(!valido);	
	}

	
	//richiede al gestore i dati di un ingrediente e li aggiunge alla ricetta data come parametro
	private void aggiungiIngrediente(Ricetta r) {
		String nomeIngrediente = view.richiestaNome("Inserisci il nome dell'ingrediente: ");
		String unitaMisura = view.richiestaNome("Inserisci unita di misura: ");
		float dose = view.richiestaQuantita("Inserisci dose: ");
		
		Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
		
		boolean esiste = false;
		for(Prodotto i : r.getElencoIngredienti()) {
			if(i.getNome().equalsIgnoreCase(ingrediente.getNome())) {
				view.stampaMsg("Hai gia' aggiunto questo ingrediente alla ricetta.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			r.addIngrediente(ingrediente);
			view.stampaMsg("E' stata aggiunto un ingrediente.");
			view.stampaProdotto(ingrediente);
		}
	}
	
	
	//Chiede a utente dati di una nuova bevanda
	//Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	//Chiede a utente se vuole aggiungere un'altra bevanda
	public void aggiungiBevanda() {
		boolean altraBevanda = true;
		boolean esiste = false;
		do {
			String nomeBevanda = view.richiestaNome("Inserisci il nome di una bevanda: ");
			Float consumoProCapiteBevanda =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeBevanda + ": ");
			
			Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, "l");
			
			for (Prodotto b : ristorante.getInsiemeBevande()) {
				if (b.getNome().equalsIgnoreCase(nomeBevanda)) {
					view.stampaMsg("La bevanda gia' esiste.");
					esiste = true;
					break;
				}
			}
			if (!esiste) {
				ristorante.addBevanda(bevanda);
				view.stampaMsg("E' stata aggiunta una bevanda.");
				view.stampaProdotto(bevanda);
			}
			altraBevanda = view.richiestaNuovaAggiunta("Vuoi aggiungere un'altra bevanda? ");
		} while(altraBevanda);
		
	}
	
	
	//Chiede a utente dati di un nuovo genere extra
	//Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	//Chiede a utente se vuole aggiungere un altro genere extra
	public void aggiungiGenereExtra() {
		boolean altroGenereExtra = true;
		boolean esiste = false;
		do {
			String nomeGenereExtra = view.richiestaNome("Inserisci il nome di un genere extra: ");
			Float consumoProCapiteGenereExtra =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeGenereExtra + ": ");
			
			Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, "hg");
			
			for (Prodotto g : ristorante.getInsiemeGeneriExtra()) {
				if (g.getNome().equalsIgnoreCase(nomeGenereExtra)) {
					view.stampaMsg("Il genere extra gia' esiste.");
					esiste = true;
					break;
				}
			}
			if (!esiste) {
				ristorante.addGenereExtra(genereExtra);
				view.stampaMsg("E' stata aggiunta un genere extra.");
				view.stampaProdotto(genereExtra);
			}
			altroGenereExtra = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro genere extra? ");
		} while(altroGenereExtra);
	}
	
	
	private void aggiungiMenuTematico() {
		String nomeMenuTematico;
		boolean nomeValido = true;
		do {
			nomeMenuTematico = view.richiestaNome("Inserisci il nome del menuTematico: ");
			for(MenuTematico m : ristorante.getElencoMenuTematici()) {
				if(m.getNome().equalsIgnoreCase(nomeMenuTematico)) {
					view.stampaMsg("Il nome del menu tematico e' gia' stato utilizzato.");
					nomeValido = false;
					break;
				} else {
					nomeValido = true;
				}
			}
		} while(!nomeValido);
			
		//devo presentare al gestore l'elenco di piatti
		//il gestore sceglie alcuni piatti (almeno 2)
		//controllo se il nuovo menu tematico rispetta i controlli (piatti validi nel periodo, carico di lavoro valido)
		
		view.stampaMsg("\nE' stato aggiunto un nuovo menu tematico.");
	}
	
	
	
	
	
	
	
	
	
}
