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

	private static final String[] OPZIONI = new String[] { "Crea ricetta", "Setta numero posti a sedere", "Setta carico di Lavoro del Ristorante", "Visualizza ricette" }; @SuppressWarnings("unused")
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
	public void inizializzaRistorante() {
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
		
		//e' necessario inserire una bevanda/genere extra?
		//sistema puo funzionare con solo una ricetta-piatto e parametri?
		
		//da finire (aggiunta in ristorante e controllo duplicati)
		System.out.println("E' necessario inserire almeno una ricetta.");
		boolean altraRicetta = false;
		do {
			String nomePiatto = view.richiestaNome("Inserisci il nome del piatto: ");
			//da aggiungere controllo su piatti gia' aggiunti
			int porzioni = view.richiestaNumeroPorzioni("Inserisci il numero di porzioni del piatto: ");
			int caricoLavoro = view.richiestaCaricoLavoro("Inserisci il carico di lavoro per porzione: ");
			
			Ricetta r = new Ricetta(porzioni, caricoLavoro);
			System.out.println("Ora bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.");
			
			boolean continua = true;
			do {
				String nomeIngrediente = view.richiestaNome("Inserisci il nome dell'ingrediente: ");
				//da aggiungere controllo su ingredienti gia' aggiunti
				String unitaMisura = view.richiestaUnitaMisura("Inserisci unita di misura: ");
				float dose = view.richiestaQuantita("Inserisci dose: ");
				
				Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
				r.addIngrediente(ingrediente);
				
				continua = view.richiestaNuovaAggiunta("Vuoi aggiungere un altro ingrediente? ");
			} while(continua);
			
			Piatto p = new Piatto(nomePiatto, caricoLavoro);
			System.out.println("E' necessario indicare il periodo di validita' del piatto.");
			//forse come metodo in GestoreView?
			boolean valido = true;
			do {
				LocalDate dataInizio = view.richiestaData("Inserire data di inizio validita'. ");
				LocalDate dataFine = view.richiestaData("Inserire data di fine validita': ");
				if((dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine)) && dataFine.isAfter(dataCorrente)) {
					DatePair periodoValidita = new DatePair(dataInizio, dataFine);
					//DA MODIFICARE PER AGGIUNGERE PIU PERIODI
					p.addDatePair(periodoValidita);
					valido = true;
				} else {
					System.out.println("Il periodo inserito deve essere valido in futuro.");
					valido = false;
				}
			} while(!valido);	
			ristorante.addPiattoRicetta(p, r);
			altraRicetta = view.richiestaNuovaAggiunta("Vuoi aggiungere un altra ricetta? ");
		} while(altraRicetta);
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
	
	
	
	
	
	
	
	
	
	
	//METODI DA RIVEDERE

	public void creaPiattoRicetta(ArrayList<Prodotto> ingredienti, int workLoad, int porzioni, String nome,
			ArrayList<DatePair> periodiValidita) {
		Ricetta r = new Ricetta(porzioni, workLoad);

		Piatto p = new Piatto(nome, workLoad);

		ristorante.addRicetta(r);
		ristorante.addPiatto(p);
		ristorante.addCorrispondenza(p, r);
	}
	
	public void inizializzaWorkload(int w) {
		ristorante.setCaricoLavoroRistorante(w);
	}

	public void setPostiASedere(int posti) {
		ristorante.setNumeroPostiASedere(posti);
	}

	public void inserisciCorrispondenza(Piatto p, Ricetta r) {
		ristorante.addCorrispondenza(p, r);
	}

	public String visualizzaDB() {
		return ristorante.toString();
	}

	public static String[] getOpzioni() {
		return OPZIONI;
	}

}
