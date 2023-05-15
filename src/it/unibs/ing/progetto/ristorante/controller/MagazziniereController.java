package it.unibs.ing.progetto.ristorante.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.interfacce.IMagazzino;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.MagazziniereView;

public class MagazziniereController implements Controller {

	/**
	 *
	 */

	private static final String MAGAZZINO_VUOTO = "\nIl Magazzino e' vuoto\n";
	private static final String LOGOUT_END = "Hai effettuato il Logout";
	private static final String NESSUNA_LISTA_DELLA_SPESA = "\nLa lista della spesa e' vuota o mai stata creata\n";
	private static final String PROFILO_MAGAZZINIERE = "Profilo: Magazziniere\n";
	private static final String ERRORE = "Something really really bad just happened... riavviare il programma";
	private static final String INSERISCI_QUANTITA_DA_RIDURRE = "Inserisci quantita' da ridurre -> ";
	private static final String SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE = "Selezione il prodotto (numero) da eliminare o ridurre -> ";
	private static final String INSERISCI_QUANTITA_DEL_PRODOTTO = "Inserisci quantita' del prodotto -> ";
	private static final String INSERISCI_NOME_DEL_PRODOTTO = "Inserisci nome del prodotto -> ";

	private static final int ZERO = 0;
	private static final int LOGOUT = 0;
	private static final int VISUALIZZA_INVENTARIO = 1;
	private static final int VISUALIZZA_LISTA_SPESA = 2;
	private static final int REGISTRA_PRODOTTO = 3;
	private static final int AGGIUNGI_PRODOTTI = 4;
	private static final int RIMUOVI_PRODOTTI = 5;

	private MagazziniereView view;
	private IMagazzino model;

	public MagazziniereController(IMagazzino model) {
		this.model = model;
		this.view = new MagazziniereView();
	}

	// presenta i comandi al magazziniere e esegue quello scelto
	public void avviaSessione() {
		view.stampaMsg(PROFILO_MAGAZZINIERE);
		model.generaListaSpesa();
		boolean sessionOn = true;
		do {
			int input = view.printMenu();
			switch (input) {
			case LOGOUT:
				sessionOn = false;
				break;
			case VISUALIZZA_INVENTARIO:
				this.visualizzaInventario();
				break;
			case VISUALIZZA_LISTA_SPESA:
				this.visualizzaListaSpesa();
				break;
			case REGISTRA_PRODOTTO:
				this.addProdottoRegistroMagazzino();
				break;
			case AGGIUNGI_PRODOTTI:
				this.aggiungiFlussoEntrante();
				break;
			case RIMUOVI_PRODOTTI:
				this.aggiungiFlussoUscente();
				break;
			default:
				view.stampaMsg(ERRORE);
				break;
			}
		} while (sessionOn);
		view.stampaMsg(LOGOUT_END);
	}

	/**
	 * Visualizza l'inventario
	 */
	public void visualizzaInventario() {
		if (model.getRegistroMagazzino().isEmpty()) {
			view.stampaMsg(MAGAZZINO_VUOTO);
		} else {
			view.stampaMagazzino(model.getRegistroMagazzino());
		}

	}

	/**
	 * Visualizza la lista della spesa
	 */
	public void visualizzaListaSpesa() {
		if (model.getListaSpesa().isEmpty()) {
			view.stampaMsg(NESSUNA_LISTA_DELLA_SPESA);
		} else {
			view.stampaListaSpesa(model.getListaSpesa());
		}
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino, dopo aver chiesto le
	 * informazioni al riguardo, Non fa aggiungere prodotti gia esistenti
	 */
	public void addProdottoRegistroMagazzino() {
		String nome = view.richiestaNome(INSERISCI_NOME_DEL_PRODOTTO);
		if (!model.esisteProdottoInMagazzino(nome)) {
			UnitaMisura unitaMisura = view.leggiUnitaMisura();
			float quantita = view.richiestaQuantita(INSERISCI_QUANTITA_DEL_PRODOTTO);
			model.addProdottoInventario(nome, quantita, unitaMisura);
			model.generaListaSpesa();
		} else {
			view.stampaMsg("Prodotto gia' presente, per aggiornarne la quantita scegliere la funzionalita' apposita");
		}
	}

	/**
	 * Metodo per aggiungere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoEntrante() {
		if (!model.getRegistroMagazzino().isEmpty()) {
			ArrayList<Prodotto> magazzino = model.getRegistroMagazzino();
			view.stampaInsiemeProdottiMagazzino(magazzino);
			int scelto = view.leggiInteroCompreso("Indica il prodotto da aggiornare > ", 0, magazzino.size() - 1);
			Prodotto scelta = magazzino.get(scelto);
			float quantita = view.richiestaQuantita("Indica la quantita' da aggiungere > ");
			this.model.addQuantitaProdottoMagazzino(scelta, quantita);
			model.generaListaSpesa();
		} else {
			view.stampaMsg("Magazzino vuoto");
		}
	}

	/**
	 * Metodo per rimuovere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoUscente() {
		if (!model.getRegistroMagazzino().isEmpty()) {
			ArrayList<Prodotto> magazzino = model.getRegistroMagazzino();
			view.stampaInsiemeProdottiMagazzino(magazzino); // stampa i prodotti
			int indiceProdottoSelezionato = view.leggiInteroCompreso(
					SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE, ZERO, magazzino.size() - 1);
			Prodotto prodotto = magazzino.get(indiceProdottoSelezionato);
			float quantitaDaRidurre = view.richiestaQuantitaCompreso(INSERISCI_QUANTITA_DA_RIDURRE, 0f,
					prodotto.getQuantita());
			model.rimuoviQuantitaProdottoMagazzino(model.getRegistroMagazzino().get(indiceProdottoSelezionato),
					quantitaDaRidurre);
			model.generaListaSpesa(); // Si rigenera la lista della spesa
		} else {
			view.stampaMsg(BelleStringhe.incornicia(MAGAZZINO_VUOTO));
		}
	}

}
