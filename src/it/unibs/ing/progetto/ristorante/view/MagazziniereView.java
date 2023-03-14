package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.Prodotto;

public class MagazziniereView {

	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Rimuovi prodotto nel magazzino";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiungi prodotto nel magazzino";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa";
	private static final String CREA_LISTA_SPESA = "Crea Lista Spesa";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { CREA_LISTA_SPESA, VISUALIZZA_INVENTARIO,
			VISUALIZZA_LISTA_SPESA, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO, RIMUOVI_PRODOTTO_NEL_MAGAZZINO };

	private MyMenu menu;

	public MagazziniereView() {
		super();
		this.menu = new MyMenu(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
	}

	public int printMenu() {
		return menu.scegli();
	}

	public void stampaMsg(String msg) {
		System.out.println(msg);
	}

	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}

	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
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
	
	public void stampaProdotto(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Consumo pro capite: " + p.getQuantita() + p.getUnitaMisura());	
	}

}
