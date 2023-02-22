package it.unibs.ing.progetto.ristorante;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility Class per per la classe gestore
 * 
 * @author Kevin
 *
 */
public class DataBase {

	/*
	 * Contiene tutte le ricette memorizzate in un certo momento
	 */
	private ArrayList<Ricetta> ricettario;
	
	private int caricoDilavoroPerPersona;
	private int numeroPostiASedere;
	private double caricoLavoroRistorante;

	/*
	 * Contiene tutti i piatti memorizzati in un certo momento
	 */
	private ArrayList<Piatto> pietanze;
	private int places;
	private double singleWorkLoad;
	private ArrayList<ProductSheet> insiemeBevande;
	private ArrayList<ProductSheet> insiemeGeneriExtra;
	private HashMap<Piatto,Ricetta> corrispondenze;
	

	/**
	 * Se inizializzazione SENZA dati preesistenti
	 */
	public DataBase() {
		super();
		this.ricettario = new ArrayList<Ricetta>();
		this.pietanze = new ArrayList<Piatto>();
		this.insiemeBevande = new ArrayList<ProductSheet>();
		this.insiemeGeneriExtra = new ArrayList<ProductSheet>();
		this.corrispondenze = new HashMap<Piatto,Ricetta>();
	}

	/**
	 * Inizializzazione con dati preesistente (non comprende la logica di retrieve
	 * dei dati da eventuale file di salvataggio)
	 * 
	 * @param ricettario
	 * @param piatti
	 */
	public DataBase(ArrayList<Ricetta> ricettario, ArrayList<Piatto> piatti, HashMap<Piatto,Ricetta> corrispondenze) {
		super();
		this.ricettario = ricettario;
		this.pietanze = piatti;
		this.corrispondenze = corrispondenze;
	}

	/**
	 * 
	 * @return ricettario
	 */
	public ArrayList<Ricetta> getRicettario() {
		return ricettario;
	}

	/**
	 * 
	 * @return piatti
	 */
	public ArrayList<Piatto> getPiatti() {
		return pietanze;
	}

	/**
	 * Aggiunge un piatto a quelli esistenti
	 * 
	 * @param p
	 */
	public void addPiatto(Piatto p) {
		this.pietanze.add(p);
	}

	/**
	 * Aggiunge una nuova ricetta a quelli esistenti
	 * 
	 * @param r
	 */
	public void addRicetta(Ricetta r) {
		this.ricettario.add(r);
	}

	public ArrayList<Piatto> getPietanze() {
		return pietanze;
	}

	public int getPlaces() {
		return places;
	}

	public double getSingleWorkLoad() {
		return singleWorkLoad;
	}

	public ArrayList<ProductSheet> getInsiemeBevande() {
		return insiemeBevande;
	}

	public ArrayList<ProductSheet> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setPietanze(ArrayList<Piatto> pietanze) {
		this.pietanze = pietanze;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public void setSingleWorkLoad(double singleWorkLoad) {
		this.singleWorkLoad = singleWorkLoad;
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

	public HashMap<Piatto, Ricetta> getCorrispondenze() {
		return corrispondenze;
	}

	public void setCorrispondenze(HashMap<Piatto, Ricetta> corrispondenze) {
		this.corrispondenze = corrispondenze;
	}

	public void setRicettario(ArrayList<Ricetta> ricettario) {
		this.ricettario = ricettario;
	}

	public void setInsiemeBevande(ArrayList<ProductSheet> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public void setInsiemeGeneriExtra(ArrayList<ProductSheet> insiemeGeneriExtra) {
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	
}
