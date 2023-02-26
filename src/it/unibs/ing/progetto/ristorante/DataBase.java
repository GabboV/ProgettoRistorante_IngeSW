package it.unibs.ing.progetto.ristorante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.Menu.MenuTematico;

/**
 * Utility Class per per la classe gestore
 * 
 * @author Kevin
 *
 */
public class DataBase {
	private int caricoDilavoroPerPersona;
	private int numeroPostiASedere;
	private double caricoLavoroRistorante;
	//Contiene tutte le ricette memorizzate nel database
	private ArrayList<Ricetta> ricettario;
	//Contiene tutti i piatti memorizzati nel database
	private ArrayList<Piatto> menuPiatti;
	//Contiene tutti i menu tematici memorizzati nel database
	private ArrayList<MenuTematico> menuTematici;
	private ArrayList<ProductSheet> insiemeBevande;
	private ArrayList<ProductSheet> insiemeGeneriExtra;
	private HashMap<Piatto,Ricetta> corrispondenzePiattoRicetta;

	/**
	 * Se inizializzazione SENZA dati preesistenti
	 */
	public DataBase() {
		super();
		this.ricettario = new ArrayList<Ricetta>();
		this.menuPiatti = new ArrayList<Piatto>();
		this.menuTematici = new ArrayList<MenuTematico>();
		this.insiemeBevande = new ArrayList<ProductSheet>();
		this.insiemeGeneriExtra = new ArrayList<ProductSheet>();
		this.corrispondenzePiattoRicetta = new HashMap<Piatto,Ricetta>();
	}

	/**
	 * Inizializzazione con dati preesistente (non comprende la logica di retrieve
	 * dei dati da eventuale file di salvataggio)
	 */
	public DataBase(ArrayList<Ricetta> ricettario, ArrayList<Piatto> menuPiatti, ArrayList<MenuTematico> menuTematici, 
			ArrayList<ProductSheet> insiemeBevande, ArrayList<ProductSheet> insiemeGeneriExtra, HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta) {
		super();
		this.ricettario = ricettario;
		this.menuPiatti = menuPiatti;
		this.menuTematici = menuTematici;
		this.insiemeBevande = insiemeBevande;
		this.insiemeGeneriExtra = insiemeGeneriExtra;
		this.corrispondenzePiattoRicetta = corrispondenzePiattoRicetta;
	}
	
	
	//MenuCarta contiene elenco dei piatti validi nella data
	//serve classe MenuCarta? No, perch� � piu facile generarlo con un metodo solo quando serve
	//	altrimenti dovrei ogni volta che � richiesta generare una istanza di MenuCarta per quando mi serve,
	//	ma non ha senso conservarla da qualche parte perch� ogni giorno pu� cambiare
	
	//ritorna un ArrayList<Piatto> contenente solo i piatti singoli memorizzati nel DataBase e validi nella data
	public ArrayList<Piatto> getMenuCartaInData(LocalDate date){
		ArrayList<Piatto> menuCartaValido = new ArrayList<Piatto>();
		for(Piatto p : menuPiatti) {
			if(p.isValidoInData(date)) menuCartaValido.add(p);
		}
		return menuCartaValido;
	}
	
	//ritorna un ArrayList<MenuTematico> contenente solo i menu tematici memorizzati nel DataBase e validi nella data
	public ArrayList<MenuTematico> getMenuTematiciInData(LocalDate date){
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for(MenuTematico m : menuTematiciValidi) {
			if(m.isValidoInData(date)) menuTematiciValidi.add(m);
		}
		return menuTematiciValidi;
	}
  
	//getter and setters
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

	public ArrayList<Ricetta> getRicettario() {
		return ricettario;
	}

	public void setRicettario(ArrayList<Ricetta> ricettario) {
		this.ricettario = ricettario;
	}

	public ArrayList<Piatto> getMenuPiatti() {
		return menuPiatti;
	}

	public void setMenuPiatti(ArrayList<Piatto> menuPiatti) {
		this.menuPiatti = menuPiatti;
	}

	public ArrayList<MenuTematico> getMenuTematici() {
		return menuTematici;
	}

	public void setMenuTematici(ArrayList<MenuTematico> menuTematici) {
		this.menuTematici = menuTematici;
	}

	public ArrayList<ProductSheet> getInsiemeBevande() {
		return insiemeBevande;
	}

	public void setInsiemeBevande(ArrayList<ProductSheet> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public ArrayList<ProductSheet> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setInsiemeGeneriExtra(ArrayList<ProductSheet> insiemeGeneriExtra) {
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	public HashMap<Piatto, Ricetta> getCorrispondenzePiattoRicetta() {
		return corrispondenzePiattoRicetta;
	}

	public void setCorrispondenzePiattoRicetta(HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta) {
		this.corrispondenzePiattoRicetta = corrispondenzePiattoRicetta;
	}
	//fine getters e setters
	
	/**
	 * Aggiunge una nuova ricetta a quelli esistenti
	 * @param r
	 */
	public void addRicetta(Ricetta r) {
		this.ricettario.add(r);
	}
	
	/**
	 * Aggiunge un piatto a quelli esistenti
	 * @param p
	 */
	public void addPiatto(Piatto p) {
		this.menuPiatti.add(p);
	}
	
	/**
	 * Aggiunge una nuova corrispondenza piatto-ricetta
	 * @param 
	 */
	public void addCorrispondenza(Piatto p, Ricetta r) {
		this.corrispondenzePiattoRicetta.put(p, r);

	public void addBevanda(ProductSheet e) {
		this.insiemeBevande.add(e);
	}

	public void addExtra(ProductSheet e) {
		this.insiemeBevande.add(e);
	}

	@Override
	public String toString() {
		return "DataBase [ricettario=" + ricettario + ", caricoDilavoroPerPersona=" + caricoDilavoroPerPersona
				+ ", numeroPostiASedere=" + numeroPostiASedere + ", caricoLavoroRistorante=" + caricoLavoroRistorante
				+ ", pietanze=" + pietanze + ", insiemeBevande=" + insiemeBevande + ", insiemeGeneriExtra="
				+ insiemeGeneriExtra + ", corrispondenze=" + corrispondenze + "]";
	}
}
