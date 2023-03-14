package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.sound.midi.MidiChannel;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class GestoreView {

	private static final String VISUALIZZA_GENERI_EXTRA = "Visualizza generi extra";
	private static final String VISUALIZZA_BEVANDE = "Visualizza bevande";
	private static final String VISUALIZZA_MENU_TEMATICI = "Visualizza menu tematici";
	private static final String VISUALIZZA_RICETTE = "Visualizza ricette";
	private static final String VISUALIZZA_PARAMETRI = "Visualizza parametri";
	private static final String AGGIUNGI_GENERE_EXTRA = "Aggiungi genere extra";
	private static final String AGGIUNGI_BEVANDA = "Aggiungi bevanda";
	private static final String AGGIUNGI_MENU_TEMATICO = "Aggiungi menu tematico";
	private static final String AGGIUNGI_RICETTA = "Aggiungi ricetta";
	final static private String TITOLO = "COMANDI GESTORE";
	final static private String[] ELENCO_COMANDI = {AGGIUNGI_RICETTA, AGGIUNGI_MENU_TEMATICO, AGGIUNGI_BEVANDA,
			AGGIUNGI_GENERE_EXTRA, VISUALIZZA_PARAMETRI, VISUALIZZA_RICETTE,
			VISUALIZZA_MENU_TEMATICI, VISUALIZZA_BEVANDE, VISUALIZZA_GENERI_EXTRA};
	
	public void stampaMsgBenvenutoInizializzazione() {
		System.out.println("Benvenuto Gestore.");
		System.out.println("Siccome si tratta del primo avvio del programma e' necessaria l'inizializzazione dei parametri.");
	}
	
	public void stampaMsg(String messaggio) {
		System.out.println(messaggio);
	}
	
	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}
	
	public int richiestaInteroPositivo(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public int richiestaInteroNonNegativo(String messaggio) {
		return InputDati.leggiInteroNonNegativo(messaggio);
	}
	
	public int richiestaInteroConMinimoMassimo(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}
	
	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}
	
	public UnitaMisura richiestaUnitaMisura(String messaggio) {
		return InputDati.leggiUnitaMisura(messaggio);
	}
	
	public Float richiestaFloatPositivo(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}
	
	public boolean richiestaNuovaAggiunta(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}
	
	public void stampaParametriRistorante(LocalDate dataCorrente, int nPosti, int caricoLavoroPersona, int caricoLavoroRistorante) {
		System.out.println("\nPARAMETRI RISTORANTE");
		System.out.print("Data corrente: ");
		stampaData(dataCorrente);
		System.out.println(" " + dataCorrente.getDayOfWeek());
		System.out.println("Numero di posti a sedere del ristorante: " + nPosti);
		System.out.println("Carico di lavoro per persona: " + caricoLavoroPersona);
		System.out.println("Carico lavoro gestibile dal ristorante per un pasto: " + caricoLavoroRistorante);
	}
	
	public int stampaMenuGestore() {
		MyMenu menu = new MyMenu(TITOLO, ELENCO_COMANDI);
		return menu.scegli();
	}
	
	//stampa l'elenco dei piatti del ristorante, che non deve essere vuota
	public void stampaElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		int contatore = 0;
		for(Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaMsg("Nome: " + p.getNomePiatto());
			stampaMsg("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
	}
	
	public void stampaPiattoRicetta(Piatto p, Ricetta r) {
		System.out.println("PIATTO:");
		System.out.println("Nome: " + p.getNomePiatto());
		System.out.println("Carico di lavoro: " + p.getCaricoLavoro());
		System.out.println("Periodi di validita': ");
		for(Periodo d : p.getPeriodiValidita()) {
			LocalDate dInizio = d.getDataInizio();
			LocalDate dFine = d.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
		System.out.println("RICETTA: ");
		System.out.println("Numero porzioni: " + r.getNumeroPorzioni());
		System.out.println("Carico di lavoro per porzione: " + r.getCaricoLavoroPorzione());
		System.out.println("INGREDIENTI:");
		for(Prodotto i : r.getElencoIngredienti()) {
			stampaIngrediente(i);
		}
	}
	
	//forse e' meglio con overwrite di LocalDate?
	public void stampaData(LocalDate data) {
		System.out.print(data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
	}
	
	public void stampaPeriodo(LocalDate dataInizio, LocalDate dataFine) {
		stampaData(dataInizio);
		System.out.print(" --> ");
		stampaData(dataFine);
		System.out.println();
	}
	
	public void stampaIngrediente(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Dose: " + p.getQuantita() + p.getUnitaMisura());	
	}
	
	public void stampaProdotto(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Consumo pro capite: " + p.getQuantita() + p.getUnitaMisura());	
	}
	
	public void stampaInsiemeProdotti(ArrayList<Prodotto> elencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println();
			System.out.println("La lista e' vuota.");
			System.out.println();
		} else {
			for(Prodotto p : elencoProdotti) {
				System.out.println(" ----------- " + contatore + " ----------- ");
				stampaProdotto(p);
				contatore++;
			}
		}
	}
	
	public void stampaElencoPiattiRicette(ArrayList<Piatto> elencoPiatti) {
		int contatore = 0;
		for(Piatto p : elencoPiatti) {
			Ricetta r = p.getRicetta();
			stampaMsg(" ----------- " + contatore + " ----------- ");
			stampaPiattoRicetta(p, r);
			contatore++;
		}
	}
	
	public void stampaMenuTematico(MenuTematico m) {
		System.out.println("Nome menu tematico: " + m.getNome());
		System.out.println("Carico lavoro: " + m.getCaricoLavoro());
		System.out.println("Piatti del menu: ");
		for(Piatto p : m.getElencoPiatti()) {
			System.out.println("Nome: " + p.getNomePiatto());
		}
		System.out.println("Periodi di validita': ");
		for(Periodo periodo : m.getPeriodiValidita()) {
			LocalDate dInizio = periodo.getDataInizio();
			LocalDate dFine = periodo.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
	}
	
	public void stampaElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		if(elencoMenuTematici.isEmpty()) {
			stampaMsg("La lista e' vuota.");
		} else {
			int contatore = 0;
			for(MenuTematico m : elencoMenuTematici) {
				stampaMsg(" ----------- " + contatore + " ----------- ");
				stampaMenuTematico(m);
				contatore++;
			}
		}
	}
}
