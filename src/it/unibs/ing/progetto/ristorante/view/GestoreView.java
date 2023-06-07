package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.UI.view.View;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;

public class GestoreView extends View {

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

	public GestoreView() {
		super(GESTORE, ELENCO_COMANDI);
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
		System.out.println("Data corrente: " + ottieniStringaData(dataCorrente) + " " + dataCorrente.getDayOfWeek());
		System.out.println("Numero di posti a sedere del ristorante: " + nPosti);
		System.out.println("Carico di lavoro per persona: " + caricoLavoroPersona);
		System.out.println("Carico lavoro gestibile dal ristorante per un pasto: " + caricoLavoroRistorante);
		System.out.println();
	}

	public boolean yesOrNo(String messaggio) {
		return InputDati.yesOrNo(messaggio);
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
		System.out.println("\nELENCO PIATTI-RICETTE");
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			Ricetta r = p.getRicetta();
			stampaMsg(" ----------- " + contatore + " ----------- ");
			stampaPiattoRicetta(p, r);
			contatore++;
		}
		System.out.println();
	}

}

