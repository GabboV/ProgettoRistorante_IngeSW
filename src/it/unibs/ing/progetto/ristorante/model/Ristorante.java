package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Ristorante {

	private LocalDate dataCorrente;
	private int caricoLavoroPerPersona;
	private int numeroPostiASedere;
	private double caricoLavoroRistorante;
	
	// Contiene tutte le ricette memorizzate nel database
	private ArrayList<Ricetta> elencoRicette;
	// Contiene tutti i piatti memorizzati nel database
	private ArrayList<Piatto> elencoPiatti;
	// Contiene tutti i menu tematici memorizzati nel database
	private ArrayList<MenuTematico> elencoMenuTematici;
	private ArrayList<Prodotto> insiemeBevande;
	private ArrayList<Prodotto> insiemeGeneriExtra;
	//perchè serve una HashMap? 
	private HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta;
	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;
	private ArrayList<Prenotazione> elencoPrenotazioni;
	private ArrayList<Piatto> menuAllaCartaValido;
	private ArrayList<MenuTematico> menuTematiciValidi;
	private ArrayList<Prenotazione> elencoPrenotazioniValide;
	

	
	
	

	public Ristorante() {
		this.dataCorrente = null;
		this.caricoLavoroPerPersona = 1;
		this.numeroPostiASedere = 1;
		this.caricoLavoroRistorante = 1;
		this.elencoRicette = new ArrayList<Ricetta>();
		this.elencoPiatti = new ArrayList<Piatto>();
		this.elencoMenuTematici = new ArrayList<MenuTematico>();
		this.insiemeBevande = new ArrayList<Prodotto>();
		this.insiemeGeneriExtra = new ArrayList<Prodotto>();
		this.corrispondenzePiattoRicetta = new HashMap<Piatto, Ricetta>();
		this.registroMagazzino = new ArrayList<Prodotto>();
		this.listaSpesa = new ArrayList<Prodotto>();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
		this.menuAllaCartaValido = new ArrayList<Piatto>();
		this.menuTematiciValidi = new ArrayList<MenuTematico>();
		this.elencoPrenotazioniValide = new ArrayList<Prenotazione>();
	}

	//GETTER AND SETTER
	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public int getCaricoDilavoroPerPersona() {
		return caricoLavoroPerPersona;
	}

	public void setCaricoDilavoroPerPersona(int caricoDilavoroPerPersona) {
		this.caricoLavoroPerPersona = caricoDilavoroPerPersona;
	}

	public int getNumeroPostiASedere() {
		return numeroPostiASedere;
	}

	public void setNumeroPostiASedere(int numeroPostiASedere) {
		this.numeroPostiASedere = numeroPostiASedere;
	}

	public double getCaricoLavoroRistorante() {
		return caricoLavoroRistorante;
	}

	public void setCaricoLavoroRistorante(double caricoLavoroRistorante) {
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public ArrayList<Ricetta> getElencoRicette() {
		return elencoRicette;
	}

	public void setElencoRicette(ArrayList<Ricetta> elencoRicette) {
		this.elencoRicette = elencoRicette;
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public void setElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return elencoMenuTematici;
	}

	public void setElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		this.elencoMenuTematici = elencoMenuTematici;
	}

	public ArrayList<Prodotto> getInsiemeBevande() {
		return insiemeBevande;
	}

	public void setInsiemeBevande(ArrayList<Prodotto> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public ArrayList<Prodotto> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setInsiemeGeneriExtra(ArrayList<Prodotto> insiemeGeneriExtra) {
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	public HashMap<Piatto, Ricetta> getCorrispondenzePiattoRicetta() {
		return corrispondenzePiattoRicetta;
	}

	public void setCorrispondenzePiattoRicetta(HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta) {
		this.corrispondenzePiattoRicetta = corrispondenzePiattoRicetta;
	}

	public ArrayList<Prodotto> getRegistroMagazzino() {
		return registroMagazzino;
	}

	public void setRegistroMagazzino(ArrayList<Prodotto> registroMagazzino) {
		this.registroMagazzino = registroMagazzino;
	}

	public ArrayList<Prodotto> getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(ArrayList<Prodotto> listaSpesa) {
		this.listaSpesa = listaSpesa;
	}

	public ArrayList<Prenotazione> getElencoPrenotazioni() {
		return elencoPrenotazioni;
	}

	public void setElencoPrenotazioni(ArrayList<Prenotazione> elencoPrenotazioni) {
		this.elencoPrenotazioni = elencoPrenotazioni;
	}

	public ArrayList<Piatto> getMenuAllaCartaValido() {
		return menuAllaCartaValido;
	}

	public void setMenuAllaCartaValido(ArrayList<Piatto> menuAllaCartaValido) {
		this.menuAllaCartaValido = menuAllaCartaValido;
	}

	public ArrayList<MenuTematico> getMenuTematiciValidi() {
		return menuTematiciValidi;
	}

	public void setMenuTematiciValidi(ArrayList<MenuTematico> menuTematiciValidi) {
		this.menuTematiciValidi = menuTematiciValidi;
	}

	public ArrayList<Prenotazione> getElencoPrenotazioniValide() {
		return elencoPrenotazioniValide;
	}

	public void setElencoPrenotazioniValide(ArrayList<Prenotazione> elencoPrenotazioniValide) {
		this.elencoPrenotazioniValide = elencoPrenotazioniValide;
	}

	
	
	
	
	
	
	//METODI DA CONTROLLARE SE ANCORA VALIDI O SERVONO
	
	// MenuCarta contiene elenco dei piatti validi nella data
	// serve classe MenuCarta? No, perche' e' piu facile generarlo con un metodo
	// solo quando serve
	// altrimenti dovrei ogni volta che e' richiesta generare una istanza di
	// MenuCarta per quando mi serve,
	// ma non ha senso conservarla da qualche parte perche ogni giorno puo
	// cambiare

	
	// ritorna un ArrayList<Piatto> contenente solo i piatti singoli memorizzati nel
	// DataBase e validi nella data
	public ArrayList<Piatto> getMenuCartaInData(LocalDate date) {
		ArrayList<Piatto> menuCartaValido = new ArrayList<Piatto>();
		for (Piatto p : elencoPiatti) {
			if (p.isValidoInData(date))
				menuCartaValido.add(p);
		}
		return menuCartaValido;
	}

	// ritorna un ArrayList<MenuTematico> contenente solo i menu tematici
	// memorizzati nel DataBase e validi nella data
	public ArrayList<MenuTematico> getMenuTematiciInData(LocalDate date) {
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for (MenuTematico m : menuTematiciValidi) {
			if (m.isValidoInData(date))
				menuTematiciValidi.add(m);
		}
		return menuTematiciValidi;
	}

	
	public void addPiattoRicetta(Piatto piatto, Ricetta ricetta) {
		boolean esiste = false;
		for(Piatto p : elencoPiatti) {
			if(p.getNomePiatto().equalsIgnoreCase(piatto.getNomePiatto())) {
				System.out.println("Il nome del piatto e' gia' stato utilizzato. L'aggiunta dell'elemento nel menu e' stata annullata.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			elencoPiatti.add(piatto);
			elencoRicette.add(ricetta);
			corrispondenzePiattoRicetta.put(piatto, ricetta);
			//da mettere in GestoreView?
			System.out.println();
			System.out.println("E' stato aggiunto un nuovo elemento al menu.");
			System.out.println("Nome del piatto: " + piatto.getNomePiatto());
			System.out.println("Numero porzioni: " + ricetta.getNumeroPorzioni());
			System.out.println("Carico di lavoro per porzione: " + piatto.getCaricoLavoro());
			System.out.println("RICETTA: ");
			for(Prodotto i : ricetta.getElencoIngredienti()) {
				System.out.println("Nome ingrediente: " + i.getNome());
				System.out.println("Dose: " + i.getQuantita() + i.getUnitaMisura());
			}
			System.out.println("Periodi di validita': ");
			for(DatePair d : piatto.getPeriodiValidita()) {
				LocalDate dInizio = d.getDataInizio();
				LocalDate dFine = d.getDataFine();
				//forse con overwrite di LocalDate?
				System.out.println(dInizio.getDayOfMonth() + "/" + dInizio.getMonthValue() + "/" + dInizio.getYear() 
						+ " --> " + dFine.getDayOfMonth() + "/" + dFine.getMonthValue() + "/" + dFine.getYear() );
			}
			System.out.println();
		}
	}
	
	//Aggiunge una nuova ricetta a quelle esistenti
	public void addRicetta(Ricetta r) {
		elencoRicette.add(r);
	}

	//Aggiunge un piatto a quelli esistenti
	public void addPiatto(Piatto p) {
		elencoPiatti.add(p);
	}

	//Aggiunge una nuova corrispondenza piatto-ricetta
	public void addCorrispondenza(Piatto p, Ricetta r) {
		corrispondenzePiattoRicetta.put(p, r);
	}

	
	//Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a insiemeBevande e stampa msg a video
	public void addBevanda(Prodotto bevanda) {
		boolean esiste = false;
		for(Prodotto b : insiemeBevande) {
			if(b.getNome().equalsIgnoreCase(bevanda.getNome())) {
				System.out.println("La bevanda gia' esiste.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			insiemeBevande.add(bevanda);
			System.out.println("E' stata aggiunta una bevanda.");
			System.out.println("Nome: " + bevanda.getNome());
			System.out.println("ConsumoProCapite: " + bevanda.getQuantita() + bevanda.getUnitaMisura());
		}
	}

	//Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome uguale a qullo che si vuole aggiungere
	//Se gia' esiste, stampa a video un msg
	//Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	public void addGenereExtra(Prodotto genereExtra) {
		boolean esiste = false;
		for(Prodotto g : insiemeBevande) {
			if(g.getNome().equalsIgnoreCase(genereExtra.getNome())) {
				System.out.println("Il genere extra gia' esiste.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			insiemeGeneriExtra.add(genereExtra);
			System.out.println("E' stata aggiunta un genere extra.");
			System.out.println("Nome: " + genereExtra.getNome());
			System.out.println("ConsumoProCapite: " + genereExtra.getQuantita() + genereExtra.getUnitaMisura());
		}
	}
}
