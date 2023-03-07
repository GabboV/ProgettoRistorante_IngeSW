package it.unibs.ing.progetto.ristorante.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.model.DatePair;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.GestoreView;

public class GestoreController{

	private static final String UNITA_MISURA_BEVANDE = "l";
	private static final String UNITA_MISURA_GENERI_EXTRA = "hg";
	
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
		ristorante.setCaricoDilavoroPerPersona(caricoLavoroPersona);
		
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
		view.stampaMsg("Hai completato l'inizializzazione del programma.\n");
		apriMenuGestore();
		return ristorante;
	}

	
	
	public void apriMenuGestore() {
		int scelta = view.stampaMenuGestore();
		//in base a scelta, rimanda a diversi metodi del programma
		if(scelta == 1) {
			
		}
		if(scelta == 2) {
			
		}
		if(scelta == 3) {
			aggiungiBevanda();
		}
		if(scelta == 4) {
			aggiungiGenereExtra();
		}
		if(scelta == 5) {
			
		}
		if(scelta == 6) {
			
		}
		if(scelta == 7) {
			
		}
		if(scelta == 8) {
			
		}
		if(scelta == 9) {
			
		}
		if(scelta == 0) {
			System.out.println("Fine sessione Gestore...");
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
				DatePair periodoValidita = new DatePair(dataInizio, dataFine);
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
		String unitaMisura = view.richiestaUnitaMisura("Inserisci unita di misura: ");
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
		}
	}
	
	
	//Chiede a utente dati di una nuova bevanda
	//Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a insiemeBevande e stampa a video un msg
	//Chiede a utente se vuole aggiungere un'altra bevanda
	public void aggiungiBevanda() {
		boolean continua = true;
		do {
			String nomeBevanda = view.richiestaNome("Inserisci il nome di una bevanda: ");
			Float consumoProCapiteBevanda =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeBevanda + ": ");
			Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, UNITA_MISURA_BEVANDE);
			ristorante.addBevanda(bevanda);
			continua = view.richiestaNuovaAggiunta("Vuoi aggiungere un'altra bevanda? ");
		} while(continua);
	}
	
	
	//Chiede a utente dati di un nuovo genere extra
	//Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	//Chiede a utente se vuole aggiungere un altro genere extra
	public void aggiungiGenereExtra() {
		boolean continua = true;
		do {
			String nomeGenereExtra = view.richiestaNome("Inserisci il nome di un genere extra: ");
			Float consumoProCapiteGenereExtra =  view.richiestaConsumoProCapite("Inserisci il consumo pro capite di " + nomeGenereExtra + ": ");
			Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, UNITA_MISURA_GENERI_EXTRA);
			ristorante.addGenereExtra(genereExtra);
			continua = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro genere extra? ");
		} while(continua);
	}
}
