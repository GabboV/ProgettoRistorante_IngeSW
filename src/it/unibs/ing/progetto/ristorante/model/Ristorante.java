package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Ristorante {

	private LocalDate dataCorrente;
	private int caricoDilavoroPerPersona;
	private int numeroPostiASedere;
	private double caricoLavoroRistorante;
	
	// Contiene tutte le ricette memorizzate nel database
	private ArrayList<Ricetta> elencoRicette;
	// Contiene tutti i piatti memorizzati nel database
	private ArrayList<Piatto> elencoPiatti;
	// Contiene tutti i menu tematici memorizzati nel database
	private ArrayList<MenuTematico> elencoMenuTematici;
	private HashMap<Prodotto, Float> insiemeBevande;
	private HashMap<Prodotto, Float> insiemeGeneriExtra;
	private HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta;
	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;
	private ArrayList<Prenotazione> elencoPrenotazioni;
	private ArrayList<Piatto> menuAllaCartaValido;
	private ArrayList<MenuTematico> menuTematiciValidi;
	private ArrayList<Prenotazione> elencoPrenotazioniValide;
	

	
	
	

	//GETTER AND SETTER
	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public int getCaricoDilavoroPerPersona() {
		return caricoDilavoroPerPersona;
	}

	public void setCaricoDilavoroPerPersona(int caricoDilavoroPerPersona) {
		this.caricoDilavoroPerPersona = caricoDilavoroPerPersona;
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

	public HashMap<Prodotto, Float> getInsiemeBevande() {
		return insiemeBevande;
	}

	public void setInsiemeBevande(HashMap<Prodotto, Float> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public HashMap<Prodotto, Float> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setInsiemeGeneriExtra(HashMap<Prodotto, Float> insiemeGeneriExtra) {
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

	//Aggiunge una nuova ricetta a quelle esistenti
	public void addRicetta(Ricetta r) {
		this.elencoRicette.add(r);
	}

	//Aggiunge un piatto a quelli esistenti
	public void addPiatto(Piatto p) {
		this.elencoPiatti.add(p);
	}

	//Aggiunge una nuova corrispondenza piatto-ricetta
	public void addCorrispondenza(Piatto p, Ricetta r) {
		this.corrispondenzePiattoRicetta.put(p, r);
	}

	public void addBevanda(Prodotto p, Float f) {
		this.insiemeBevande.put(p,f);
	}

	public void addExtra(Prodotto p, Float f) {
		this.insiemeBevande.put(p, f);
	}

	public String ottieniRicette() {
		StringBuilder s = new StringBuilder();
		int indice = 1;
		for (Ricetta r : elencoRicette) {
			s.append(indice + ".\n" + r.toString() + "\n");
		}
		return s.toString();
	}
}
