package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.RowFilter.ComparisonType;

import it.unibs.fp.mylib.InputDati;
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
	
	//da spostare msg in GestoreView se possibile
	//creare metodi separati se possibile e aggiungere controlli
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

	
	
	public void apriMenuGestore() {
		//forse da mettere tutte le final insieme
		view = new GestoreView();
		int scelta = view.stampaMenuGestore();
		//in base a scelta, rimanda a diversi metodi del programma
		switch(scelta) {
		case AGGIUNGI_INGREDIENTE:
			aggiungiPiattoRicetta();
			apriMenuGestore();
			break;
		case AGGIUNGI_MENU_TEMATICO:
			apriMenuGestore();
			break;
		case AGGIUNGI_BEVANDA:
			aggiungiBevanda();
			apriMenuGestore();
			break;
		case AGGIUNGI_GENERE_EXTRA:
			aggiungiGenereExtra();
			apriMenuGestore();
			break;
		case VISUALIZZA_PARAMETRI:
			//potrei dare tutto il model come parametro, ma così do solo parametri necessari
			view.stampaParametriRistorante(ristorante.getDataCorrente(), ristorante.getNumeroPostiASedere(), ristorante.getCaricoLavoroPerPersona(), ristorante.getCaricoLavoroRistorante());
			System.out.println();
			apriMenuGestore();
			break;
		case VISUALIZZA_RICETTE:
			//ciclo che stampa ricette (con contatore)
			int contatore = 0;
			for(Piatto p : ristorante.getElencoPiatti()) {
				Ricetta r = ristorante.getCorrispondenzePiattoRicetta().get(p);
				view.stampaMsg(" ----------- " + contatore + " ----------- ");
				view.stampaPiattoRicetta(p, r);
				contatore++;
			}
			apriMenuGestore();
			break;
		case VISUALIZZA_MENU_TEMATICI:
			System.out.println();
			apriMenuGestore();
			break;
		case VISUALIZZA_BEVANDE:
			view.stampaInsiemeProdotti(ristorante.getInsiemeBevande());
			System.out.println();
			apriMenuGestore();
			break;
		case VISUALIZZA_GENERI_EXTRA:
			view.stampaInsiemeProdotti(ristorante.getInsiemeGeneriExtra());
			System.out.println();
			apriMenuGestore();
			break;
		case ESCI:
			System.out.println("Fine sessione Gestore...");
			break;
		}
	}
	
	
	private void aggiungiPiattoRicetta() {
		//controllo nome da fare gia' all'inizio?
		String nomePiatto;
		boolean nomeValido = true;
		do {
			nomePiatto = view.richiestaNome("Inserisci il nome del piatto: ");
			for(Piatto p : ristorante.getElencoPiatti()) {
				if(p.getNomePiatto().equalsIgnoreCase(nomePiatto)) {
					view.stampaMsg("Il nome del piatto e' gia' stato utilizzato. L'aggiunta dell'elemento nel menu e' stata annullata.");
					nomeValido = false;
					break;
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
			//controllare se aggiunge alla ricetta la lista di ingredienti
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

	//DA CONTROLLARE SE PERIODO SI SOVRAPPONE CON UN ALTRO
	private void aggiungiPeriodoValido(Piatto p) {
		boolean valido = true;
		do {
			LocalDate dataInizio = view.richiestaData("Inserire data di inizio validita'. ");
			LocalDate dataFine = view.richiestaData("Inserire data di fine validita': ");
			if((dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine)) && dataFine.isAfter(ristorante.getDataCorrente())) {
				Periodo periodoValidita = new Periodo(dataInizio, dataFine);
				p.addDatePair(periodoValidita);
				valido = true;
			} else {
				view.stampaMsg("Il periodo inserito deve essere valido in futuro.");
				valido = false;
			}
		} while(!valido);	
	}

	private void aggiungiIngrediente(Ricetta r) {
		String nomeIngrediente = view.richiestaNome("Inserisci il nome dell'ingrediente: ");
		UnitaMisura unitaMisura = view.richiestaUnitaMisura("Inserisci unita di misura: ");
		float dose = view.richiestaQuantita("Inserisci dose: ");
		
		Prodotto ingrediente = new Prodotto(nomeIngrediente, dose);
		
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
	//Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	//Chiede a utente se vuole aggiungere un'altra bevanda
	public void aggiungiBevanda() {
		boolean altraBevanda = true;
		boolean esiste = false;
		do {
			String nomeBevanda = view.richiestaNome("Inserisci il nome di una bevanda: ");
			Float consumoProCapiteBevanda =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeBevanda + ": ");
			
			Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda);
			
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
	//Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	//Chiede a utente se vuole aggiungere un altro genere extra
	public void aggiungiGenereExtra() {
		boolean altroGenereExtra = true;
		boolean esiste = false;
		do {
			String nomeGenereExtra = view.richiestaNome("Inserisci il nome di un genere extra: ");
			Float consumoProCapiteGenereExtra =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeGenereExtra + ": ");
			
			Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra);
			
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
	
	
	
	
	
	
	
	
	
	
	
}
