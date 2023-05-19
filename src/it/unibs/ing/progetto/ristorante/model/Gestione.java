package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Gestione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int caricoLavoroPerPersona;
	private int numeroPostiASedere;
	private int caricoLavoroRistorante;

	private ArrayList<Piatto> elencoPiatti;
	private ArrayList<MenuTematico> elencoMenuTematici;
	private ArrayList<Prodotto> insiemeBevande;
	private ArrayList<Prodotto> insiemeGeneriExtra;

	public Gestione(int caricoLavoroPerPersona, int numeroPostiASedere, int caricoLavoroRistorante,
			ArrayList<Piatto> elencoPiatti, ArrayList<MenuTematico> elencoMenuTematici,
			ArrayList<Prodotto> insiemeBevande, ArrayList<Prodotto> insiemeGeneriExtra) {
		this.caricoLavoroPerPersona = caricoLavoroPerPersona;
		this.numeroPostiASedere = numeroPostiASedere;
		this.caricoLavoroRistorante = caricoLavoroRistorante;
		this.elencoPiatti = elencoPiatti;
		this.elencoMenuTematici = elencoMenuTematici;
		this.insiemeBevande = insiemeBevande;
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	public Gestione() {
		super();
		this.caricoLavoroPerPersona = 0;
		this.numeroPostiASedere = 0;
		this.caricoLavoroRistorante = 0;
		this.elencoPiatti = new ArrayList<>();
		this.elencoMenuTematici = new ArrayList<>();
		this.insiemeBevande = new ArrayList<>();
		this.insiemeGeneriExtra = new ArrayList<>();
	}

	public int getCaricoLavoroPerPersona() {
		return caricoLavoroPerPersona;
	}

	public int getNumeroPostiASedere() {
		return numeroPostiASedere;
	}

	public int getCaricoLavoroRistorante() {
		return caricoLavoroRistorante;
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return elencoMenuTematici;
	}

	public ArrayList<Prodotto> getInsiemeBevande() {
		return insiemeBevande;
	}

	public ArrayList<Prodotto> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setCaricoLavoroPerPersona(int caricoLavoroPerPersona) {
		this.caricoLavoroPerPersona = caricoLavoroPerPersona;
	}

	public void setNumeroPostiASedere(int numeroPostiASedere) {
		this.numeroPostiASedere = numeroPostiASedere;
	}

	public void setCaricoLavoroRistorante(int caricoLavoroRistorante) {
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public void setElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}

	public void setElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		this.elencoMenuTematici = elencoMenuTematici;
	}

	public void setInsiemeBevande(ArrayList<Prodotto> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public void setInsiemeGeneriExtra(ArrayList<Prodotto> insiemeGeneriExtra) {
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	public boolean ciSonoMenuTematiciValidiInData(LocalDate data) {
		return (this.getMenuTematiciInData(data).size() > 0);
	}

	public boolean ciSonoPiattiValidiInData(LocalDate data) {
		return (this.getMenuCartaInData(data).size() > 0);
	}

	// ritorna un ArrayList<Piatto> contenente solo i piatti singoli memorizzati nel
	// DataBase e validi nella data
	public List<Piatto> getMenuCartaInData(LocalDate date) {
		return elencoPiatti.stream().filter(p -> p.isDisponibileInData(date)).collect(Collectors.toList());
	}

	// ritorna un ArrayList<MenuTematico> contenente solo i menu tematici
	// memorizzati nel DataBase e validi nella data
	public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
		return this.elencoMenuTematici.stream().filter(m -> m.isDisponibileInData(date)).collect(Collectors.toList());
	}

	/**
	 * Restiuisce true se il genere extra è gia presente
	 * 
	 * @param nome
	 * @return
	 */
	public boolean esisteGenereExtra(String nome) {
		return insiemeGeneriExtra.stream().anyMatch(g -> g.getNome().equalsIgnoreCase(nome));
	}

	/**
	 * Restituisce true se la bevanda esiste gia
	 * 
	 * @param nome
	 * @return
	 */
	public boolean esisteBevanda(String nome) {
		return insiemeBevande.stream().anyMatch(b -> b.getNome().equalsIgnoreCase(nome));
	}

	// Aggiunge un piatto e una ricetta dopo averli creati
	public void addPiattoRicetta(ArrayList<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro,
			String nomePiatto, ArrayList<Periodo> periodi) {
		Ricetta ricetta = new Ricetta(elencoIngredienti, porzioni, caricoLavoro);
		Piatto piatto = new Piatto(nomePiatto, caricoLavoro, ricetta, periodi);
		elencoPiatti.add(piatto);
	}

	// Aggiunge un menu tematico
	public void addMenuTematico(String nomeMenuTematico, ArrayList<Piatto> piatti, int caricoLavoroMenuTematico,
			ArrayList<Periodo> periodi) {
		MenuTematico menuTematico = new MenuTematico(nomeMenuTematico, piatti, caricoLavoroMenuTematico, periodi);
		elencoMenuTematici.add(menuTematico);
	}

	// aggiunge una nuova benvanda a insiemeBevande
	public void addBevanda(String nomeBevanda, float consumoProCapiteBevanda) {
		Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, UnitaMisura.L);
		insiemeBevande.add(bevanda);
	}

	// aggiunge un nuovo genere extra a insiemeGeneriExtra
	public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra) {
		Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, UnitaMisura.HG);
		insiemeGeneriExtra.add(genereExtra);
	}

	public Piatto piattoScelto(int scelta) {
		if (scelta >= 0 && scelta < elencoPiatti.size()) {
			return elencoPiatti.get(scelta);
		}
		return null;
	}

}
