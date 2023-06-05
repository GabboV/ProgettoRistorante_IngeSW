package it.unibs.ing.progetto.ristorante.UI.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class GestioneView {

	private static final String GESTORE = "Gestore";
	private static final String VISUALIZZA_GENERI_EXTRA = "Visualizza generi extra";
	private static final String VISUALIZZA_BEVANDE = "Visualizza bevande";
	private static final String VISUALIZZA_MENU_TEMATICI = "Visualizza menu tematici";
	private static final String VISUALIZZA_RICETTE = "Visualizza ricette";
	private static final String VISUALIZZA_PARAMETRI = "Visualizza parametri";
	private static final String AGGIUNGI_GENERE_EXTRA = "Aggiungi genere extra";
	private static final String AGGIUNGI_BEVANDA = "Aggiungi bevanda";
	private static final String AGGIUNGI_MENU_TEMATICO = "Aggiungi menu tematico";
	private static final String AGGIUNGI_RICETTA = "Aggiungi ricetta";

	final static private String[] ELENCO_COMANDI = { AGGIUNGI_RICETTA, AGGIUNGI_MENU_TEMATICO, AGGIUNGI_BEVANDA,
			AGGIUNGI_GENERE_EXTRA, VISUALIZZA_PARAMETRI, VISUALIZZA_RICETTE, VISUALIZZA_MENU_TEMATICI,
			VISUALIZZA_BEVANDE, VISUALIZZA_GENERI_EXTRA, "Giorno dopo" };

	public int scegliMenu() {
		MyMenu menu = new MyMenu(GESTORE, ELENCO_COMANDI);
		return menu.scegli();
	}

	public void stampaMsgBenvenutoInizializzazione() {
		System.out.println("Benvenuto Gestore.");
		System.out.println(
				"Siccome si tratta del primo avvio del programma e' necessaria l'inizializzazione dei parametri.");
	}

	public int richiestaInteroPositivo(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}

	public int richiestaInteroNonNegativo(String messaggio) {
		return InputDati.leggiInteroNonNegativo(messaggio);
	}

	public Float richiestaFloatPositivo(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public void stampaParametriRistorante(LocalDate dataCorrente, int nPosti, int caricoLavoroPersona,
			int caricoLavoroRistorante) {
		System.out.println("\nPARAMETRI RISTORANTE");
		System.out.print("Data corrente: ");
		stampaData(dataCorrente);
		System.out.println(" " + dataCorrente.getDayOfWeek());
		System.out.println("Numero di posti a sedere del ristorante: " + nPosti);
		System.out.println("Carico di lavoro per persona: " + caricoLavoroPersona);
		System.out.println("Carico lavoro gestibile dal ristorante per un pasto: " + caricoLavoroRistorante);
	}

	public void stampaPiattoRicetta(Piatto p, Ricetta r) {
		System.out.println("PIATTO:");
		System.out.println("Nome: " + p.getNome());
		System.out.println("Carico di lavoro: " + p.getCaricoLavoro());
		System.out.println("Periodi di validita': ");
		for (Periodo d : p.getPeriodiValidita()) {
			LocalDate dInizio = d.getDataInizio();
			LocalDate dFine = d.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
		System.out.println("RICETTA: ");
		System.out.println("Numero porzioni: " + r.getNumeroPorzioni());
		System.out.println("Carico di lavoro per porzione: " + r.getCaricoLavoroPorzione());
		System.out.println("INGREDIENTI:");
		for (Prodotto i : r.getElencoIngredienti()) {
			stampaIngrediente(i);
		}
	}

	public void stampaIngrediente(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Dose: " + p.getQuantita() + "  " + p.getUnitaMisura().getName());
	}

	public void stampaElencoPiattiRicette(List<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			Ricetta r = p.getRicetta();
			stampaMsg(" ----------- " + contatore + " ----------- ");
			stampaPiattoRicetta(p, r);
			contatore++;
		}
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}

	public void stampaMsg(String messaggio) {
		System.out.println(messaggio);
	}

	public boolean yesOrNo(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}

	
	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public Float richiestaQuantitaCompreso(String messaggio, float min, float max) {
		return InputDati.leggiFloatCompreso(messaggio, min, max);
	}

	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}

	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public int richiestaInteroConMinimoMassimo(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public UnitaMisura leggiUnitaMisura() {
		return InputDati.richiestaUnitaMisura();
	}

	public void stampaElencoMenuTematici(List<MenuTematico> elencoMenuTematici) {
		if (elencoMenuTematici.isEmpty()) {
			stampaMsg("La lista e' vuota.");
		} else {
			int contatore = 0;
			for (MenuTematico m : elencoMenuTematici) {
				stampaMsg(" ----------- " + contatore + " ----------- ");
				stampaMenuTematico(m);
				contatore++;
			}
		}
	}

	public void stampaMenuTematico(MenuTematico m) {
		System.out.println("Nome menu tematico: " + m.getNome());
		System.out.println("Carico lavoro: " + m.getCaricoLavoro());
		System.out.println("Piatti del menu: ");
		for (MenuComponent p : m.getElencoPiatti()) {
			System.out.println("Nome: " + p.getNome());
		}
		System.out.println("Periodi di validita': ");
		for (Periodo periodo : m.getPeriodiValidita()) {
			LocalDate dInizio = periodo.getDataInizio();
			LocalDate dFine = periodo.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
	}

	public void stampaElencoPiatti(List<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaMsg("Nome: " + p.getNome());
			stampaMsg("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
	}

	public void stampaInsiemeProdotti(List<Prodotto> elencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println();
			System.out.println("La lista e' vuota.");
			System.out.println();
		} else {
			for (Prodotto p : elencoProdotti) {
				System.out.println(" ----------- " + contatore + " ----------- ");
				stampaProdotto(p);
				contatore++;
			}
		}
	}

	public void stampaInsiemeProdottiMagazzino(ArrayList<Prodotto> elencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println();
			System.out.println("La lista e' vuota.");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("+----------------+ Aggiungi flusso +----------------+");
			System.out.println();
			for (Prodotto p : elencoProdotti) {
				System.out.println("------------------------- " + contatore + " -------------------------");
				stampaProdottoMagazzino(p);
				System.out.println();
				contatore++;
			}
			System.out.println();
		}
	}

	public void stampaProdotto(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Consumo pro capite: " + p.getQuantita() + p.getUnitaMisura());
	}

	public void stampaProdottoMagazzino(Prodotto p) {
		System.out.println(
				String.format("Nome: %10s\tGiacienza: %5.2f\t%5s", p.getNome(), p.getQuantita(), p.getUnitaMisura()));
	}

	public void stampaData(LocalDate data) {
		System.out.print(data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
	}

	public void stampaPeriodo(LocalDate dataInizio, LocalDate dataFine) {
		stampaData(dataInizio);
		System.out.print(" --> ");
		stampaData(dataFine);
		System.out.println();
	}

}
