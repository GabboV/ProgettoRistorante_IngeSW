package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibs.ing.progetto.ristorante.interfacce.IGestore;
import it.unibs.ing.progetto.ristorante.interfacce.IMagazzino;
import it.unibs.ing.progetto.ristorante.interfacce.IPrenotazioni;

public class Ristorante implements Serializable, IGestore, IPrenotazioni, IMagazzino {

	private static final long serialVersionUID = 1L;

	private LocalDate dataCorrente;

	private Gestione gestione;
	private Agenda agenda;
	private Magazzino magazzino;

	public Ristorante(Gestione gestione, Agenda agenda, Magazzino magazzino) {
		this.gestione = gestione;
		this.agenda = agenda;
		this.magazzino = magazzino;
	}

	public Ristorante() {
		this.gestione = new Gestione();
		this.magazzino = new Magazzino();
		this.agenda = new Agenda();
	}

	public boolean isRistorantePienoInData(LocalDate data) {
		return (this.getPostiDisponibiliInData(data) == 0);
	}

	public int getPostiDisponibiliInData(LocalDate data) {
		return gestione.getNumeroPostiASedere() - agenda.getNumeroClientiPrenotatiInData(data);
	}

	public void addPrenotazione(String cliente, LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.agenda.addPrenotazione(cliente, data, comanda, coperti);
	}

	public boolean ciSonoMenuTematiciValidiInData(LocalDate data) {
		return gestione.ciSonoMenuTematiciValidiInData(data);
	}

	public boolean ciSonoPiattiValidiInData(LocalDate data) {
		return gestione.ciSonoPiattiValidiInData(data);
	}

	public float getCaricoLavoroSostenibileRimasto(LocalDate data) {
		return gestione.getCaricoLavoroRistorante() - agenda.getCaricoLavoroDaSostenereInData(data);
	}

	public boolean esisteGenereExtra(String nome) {
		return gestione.esisteGenereExtra(nome);
	}

	public boolean esisteBevanda(String nome) {
		return gestione.esisteBevanda(nome);
	}

	public void removePrenotazioniScadute() {
		this.agenda.removePrenotazioniScadute(dataCorrente);
	}

	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public ArrayList<Prenotazione> getElencoPrenotazioni() {
		return agenda.getElencoPrenotazioni();
	}

	public int getCaricoLavoroPerPersona() {
		return gestione.getCaricoLavoroPerPersona();
	}

	public int getNumeroPostiASedere() {
		return gestione.getNumeroPostiASedere();
	}

	public int getCaricoLavoroRistorante() {
		return gestione.getCaricoLavoroRistorante();
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return gestione.getElencoPiatti();
	}

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return gestione.getElencoMenuTematici();
	}

	public ArrayList<Prodotto> getInsiemeBevande() {
		return gestione.getInsiemeBevande();
	}

	public ArrayList<Prodotto> getInsiemeGeneriExtra() {
		return gestione.getInsiemeGeneriExtra();
	}

	public ArrayList<Prodotto> getRegistroMagazzino() {
		return magazzino.getRegistroMagazzino();
	}

	public ArrayList<Prodotto> getListaSpesa() {
		return magazzino.getListaSpesa();
	}

	/*
	 * ritorna il valore del piatto in posizione data da scelta
	 */
	public Piatto piattoScelto(int scelta) {
		return gestione.piattoScelto(scelta);
	}

	// aggiunge un piatto e la rispettiva ricetta a elencoPiatti
	public void addPiattoRicetta(ArrayList<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro,
			String nomePiatto, ArrayList<Periodo> periodi) {
		gestione.addPiattoRicetta(elencoIngredienti, porzioni, caricoLavoro, nomePiatto, periodi);
	}

	// aggiunge un nuovo menu tematico
	public void addMenuTematico(String nomeMenuTematico, ArrayList<Piatto> piatti, int caricoLavoroMenuTematico,
			ArrayList<Periodo> periodi) {
		gestione.addMenuTematico(nomeMenuTematico, piatti, caricoLavoroMenuTematico, periodi);
	}

	/**
	 * aggiunge una nuova benvanda a insiemeBevande
	 */
	public void addBevanda(String nomeBevanda, float consumoProCapiteBevanda) {
		gestione.addBevanda(nomeBevanda, consumoProCapiteBevanda);
	}

	/**
	 * aggiunge un nuovo genere extra a insiemeGeneriExtra
	 */
	public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra) {
		gestione.addGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
	}

	/**
	 * Genera la lista della spesa
	 */
	public void generaListaSpesa() {
		this.magazzino.generaListaSpesa((ArrayList<Prenotazione>) agenda.getPrenotazioniInData(dataCorrente),
				gestione.getInsiemeBevande(), gestione.getInsiemeGeneriExtra(),
				agenda.getNumeroClientiPrenotatiInData(dataCorrente));
	}

	/**
	 * Metodo che restituisce true se una data ais feriale
	 * 
	 * @param data
	 * @return
	 */
	private boolean isGiornoFeriale(LocalDate data) {
		DayOfWeek dayOfWeek = data.getDayOfWeek();
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}

	/**
	 * Verifica l'accettabilita di una prenotazione
	 * 
	 * @param date
	 * @param comanda
	 * @param numeroCoperti
	 * @return
	 */
	public boolean verificaAccettabilitaPrenotazione(LocalDate date, HashMap<Piatto, Integer> comanda,
			int numeroCoperti) {
		if (!isGiornoFeriale(date)) {
			return false;
		}
		int postiDisponibili = this.getPostiDisponibiliInData(date);
		if (numeroCoperti > postiDisponibili) {
			return false;
		}
		float caricoLavoroSostenibileRimasto = this.getCaricoLavoroSostenibileRimasto(date);
		float caricoLavoroRichiesto = Prenotazione.calcolaCaricoLavoroComanda(comanda);
		if (caricoLavoroRichiesto > caricoLavoroSostenibileRimasto) {
			return false;
		}
		return true;
	}

	/**
	 * Aggiunge una voce alla lista con un nuovo prodotto
	 * 
	 * @param nome
	 * @param quantita
	 * @param unitaMisura
	 */
	public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura) {
		magazzino.addProdottoInventario(nome, quantita, unitaMisura);
		this.generaListaSpesa();
	}

	/**
	 * Dando in input un prodotto e una quantita, la cerca nel registro e ne riduce
	 * della quantita indicata
	 * 
	 * @param prodotto
	 * @param quantita
	 */
	public void rimuoviQuantitaProdottoMagazzino(Prodotto prodotto, float quantita) {
		this.magazzino.rimuoviQuantitaProdottoMagazzino(prodotto, quantita);
		this.generaListaSpesa();
	}



	/**
	 * Passo successivo
	 */
	public void giornoDopo() {
		this.dataCorrente = dataCorrente.plusDays(1);
		this.agenda.removePrenotazioniScadute(dataCorrente);
		this.magazzino.generaListaSpesa((ArrayList<Prenotazione>) agenda.getPrenotazioniInData(dataCorrente),
				getInsiemeGeneriExtra(), getInsiemeBevande(), agenda.getNumeroClientiPrenotatiInData(dataCorrente));
	}

	/**
	 * Ritorna i piatti disponibili in una certa data
	 */
	public List<Piatto> getMenuCartaInData(LocalDate date) {
		return gestione.getMenuCartaInData(date);
	}

	/**
	 * Ritorna i menu tematici disponibili per una certa data
	 */
	public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
		return gestione.getMenuTematiciInData(date);
	}

	/**
	 * Imposta il carico di lavoro per persona
	 */
	public void setCaricoLavoroPerPersona(int caricoDilavoroPerPersona) {
		this.gestione.setCaricoLavoroPerPersona(caricoDilavoroPerPersona);
	}

	/**
	 * Imposta il numero di posti a sedere
	 */
	public void setNumeroPostiASedere(int numeroPostiASedere) {
		this.gestione.setNumeroPostiASedere(numeroPostiASedere);
	}

	/**
	 * Imposta il carico di lavoro del ristorante
	 */
	public void setCaricoLavoroRistorante(int caricoLavoroRistorante) {
		this.gestione.setCaricoLavoroRistorante(caricoLavoroRistorante);
	}

	@Override
	public void removePrenotazione(int indice) {
		this.agenda.removePrenotazione(indice);
	}
	
	public void setInsiemeBevande(ArrayList<Prodotto> insiemeBevande) {
		this.gestione.setInsiemeBevande(insiemeBevande);
	}
	
	public void setInsiemeGeneriExtra(ArrayList<Prodotto> insiemeGeneriExtra) {
		this.gestione.setInsiemeGeneriExtra(insiemeGeneriExtra);
	}

	@Override
	public boolean esisteProdottoInMagazzino(String nome) {
		return this.magazzino.esisteProdottoInMagazzino(nome);
	}

	@Override
	public void addQuantitaProdottoMagazzino(Prodotto prodotto, float quantita) {
		this.magazzino.addQuantitaProdottoMagazzino(prodotto, quantita);
	}

	

}
