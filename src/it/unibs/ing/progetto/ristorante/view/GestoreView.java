package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class GestoreView {

	final static private String TITOLO = "COMANDI GESTORE";
	final static private String[] ELENCO_COMANDI = {"Aggiungi ricetta", "Aggiungi menu tematico", "Aggiungi bevanda",
			"Aggiungi genere extra", "Visualizza parametri", "Visualizza ricette",
			"Visualizza menu tematici", "Visualizza bevande", "Visualizza generi extra"};
	
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
	
	//possibile raccogliere alcuni metodi in un metodo solo?
	public int richiestaNumeroPostiRistorante(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public int richiestaCaricoLavoro(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public int richiestaNumeroPorzioni(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}
	
	public UnitaMisura richiestaUnitaMisura(String messaggio) {
		return InputDati.leggiUnitaMisura(messaggio);
	}
	
	public Float richiestaConsumoProCapite(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}
	
	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}
	
	public boolean richiestaNuovaAggiunta(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}
	
	public void stampaParametriRistorante(LocalDate dataCorrente, int nPosti, int caricoLavoroPersona, int caricoLavoroRistorante) {
		System.out.println();
		System.out.println("Questi sono i valori dei parametri del ristorante: ");
		System.out.print("La data corrente e' ");
		stampaData(dataCorrente);
		System.out.println(" ed e' " + dataCorrente.getDayOfWeek());
		System.out.println("Il numero di posti a sedere del ristorante e' " + nPosti);
		System.out.println("Il carico di lavoro per persona e' " + caricoLavoroPersona);
		System.out.println("Il carico lavoro gestibile dal ristorante per un pasto e' " + caricoLavoroRistorante);
		System.out.println();
	}
	
	public int stampaMenuGestore() {
		MyMenu menu = new MyMenu(TITOLO, ELENCO_COMANDI);
		return menu.scegli();
	}
	
	public void stampaPiattoRicetta(Piatto p, Ricetta r) {
		System.out.println("Nome del piatto: " + p.getNomePiatto());
		System.out.println("Numero porzioni: " + r.getNumeroPorzioni());
		System.out.println("Carico di lavoro per porzione: " + p.getCaricoLavoro());
		System.out.println("RICETTA: ");
		for(Prodotto i : r.getElencoIngredienti()) {
			stampaProdotto(i);
		}
		System.out.println("Periodi di validita': ");
		for(Periodo d : p.getPeriodiValidita()) {
			LocalDate dInizio = d.getDataInizio();
			LocalDate dFine = d.getDataFine();
			stampaPeriodo(dInizio, dFine);
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
}
