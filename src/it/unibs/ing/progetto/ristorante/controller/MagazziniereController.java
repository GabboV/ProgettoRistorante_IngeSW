package it.unibs.ing.progetto.ristorante.controller;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.interfacce.IMagazzino;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;
import it.unibs.ing.progetto.ristorante.view.MagazziniereView;
import it.unibs.ing.progetto.ristorante.view.OutputFormatter;

public class MagazziniereController implements Controller {

	private static final String MAGAZZINO_VUOTO = "\nIl Magazzino is vuoto\n";
	private static final String LOGOUT_END = "Hai effettuato il Logout";
	private static final String NESSUNA_LISTA_DELLA_SPESA = "\nLa lista della spesa is vuota o mai stata creata\n";
	private static final String PROFILO_MAGAZZINIERE = "Profilo: Magazziniere\n";
	private static final String ERRORE = "Something really really bad just happened... riavviare il programma";
	private static final String INSERISCI_QUANTITA_DA_RIDURRE = "Inserisci quantita da ridurre -> ";
	private static final String SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE = "Selezione il prodotto (numero) da eliminare o ridurre -> ";
	private static final String INSERISCI_QUANTITA_DEL_PRODOTTO = "Inserisci quantita del prodotto -> ";
	private static final String INSERISCI_UNITA_DI_MISURA_DEL_PRODOTTO = "Inserisci unita di misura del prodotto -> ";
	private static final String INSERISCI_NOME_DEL_PRODOTTO = "Inserisci nome del prodotto -> ";

	private static final int ZERO = 0;
	private static final int LOGOUT = 0;
	private static final int VISUALIZZA_INVENTARIO = 1;
	private static final int VISUALIZZA_LISTA_SPESA = 2;
	private static final int AGGIUNGI_PRODOTTI = 3;
	private static final int RIMUOVI_PRODOTTI_DETERIORATI = 4;

	private MagazziniereView view;
	private IMagazzino model;

	public MagazziniereController(IMagazzino model) {
		this.model = model;
		this.view = new MagazziniereView();
	}

	public void avviaSessione() {
		view.stampaMsg(PROFILO_MAGAZZINIERE);
		boolean sessionOn = true;
		do {
			this.creaListaSpesa();
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
				case AGGIUNGI_PRODOTTI:
					this.addProdottoRegistroMagazzino();
					break;
				case RIMUOVI_PRODOTTI_DETERIORATI:
					this.rimuoviProdottiInventario();
					break;
				default:
					view.stampaMsg(ERRORE);
					break;
			}
		} while (sessionOn);
		view.stampaMsg(LOGOUT_END);
	}

	/**
	 * Genera la lista della spesa
	 */
	public void creaListaSpesa() {
		model.generaListaSpesa();
	}

	/**
	 * Visualizza la lista della spesa
	 */
	public void visualizzaListaSpesa() {
		String listaFormattata;
		if (model.getListaSpesa().isEmpty()) {
			listaFormattata = NESSUNA_LISTA_DELLA_SPESA;
		} else {
			listaFormattata = BelleStringhe.incornicia(OutputFormatter.formatListaProdotti(model.getListaSpesa()));
		}
		view.stampaMsg(listaFormattata);
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino, dopo aver chiesto le
	 * informazioni al riguardo
	 */
	public void addProdottoRegistroMagazzino() {
		String nome = view.richiestaNome(INSERISCI_NOME_DEL_PRODOTTO);
		UnitaMisura unitaMisura = view.richiestaUnitaMisura(INSERISCI_UNITA_DI_MISURA_DEL_PRODOTTO);
		float quantita = view.richiestaQuantita(INSERISCI_QUANTITA_DEL_PRODOTTO);
		model.addProdottoInventario(nome, quantita, unitaMisura);
	}

	/**
	 * Passa al modello i parametri di un prodotto
	 * @param nome
	 * @param quantita
	 * @param unitaMisura
	 */
	public void addProdottoRegistro(String nome, float quantita, UnitaMisura unitaMisura) {
		model.addProdottoInventario(nome, quantita, unitaMisura);
	}

	/**
	 * Metodo per rimuovere un prodotto dall'inventario
	 */
	public void rimuoviProdottiInventario() {
		if (!model.getRegistroMagazzino().isEmpty()) {
			view.stampaInsiemeProdotti(model.getRegistroMagazzino()); // stampa i prodotti
			int indiceProdottoSelezionato = view.leggiInteroCompreso(
					SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE, ZERO, model.getRegistroMagazzino().size() - 1); // sceglie l'indice
			Prodotto prodotto = model.prodottoScelto(indiceProdottoSelezionato);
			float quantitaDaRidurre = view.richiestaQuantitaCompreso(INSERISCI_QUANTITA_DA_RIDURRE, 0f,
					prodotto.getQuantita()); // Richiede la quantita da rimuovere
			model.rimuoviQuantitaProdottoDaRegistro(model.getRegistroMagazzino().get(indiceProdottoSelezionato),
					quantitaDaRidurre);
			model.generaListaSpesa(); // Si rigenera la lista della spesa 
		} else {
			view.stampaMsg(BelleStringhe.incornicia(MAGAZZINO_VUOTO)); 
		}
	}

	/**
	 * Visualizza l'inventario
	 */
	public void visualizzaInventario() {
		String inventario;
		if (model.getRegistroMagazzino().isEmpty()) {
			inventario = MAGAZZINO_VUOTO;
		} else {
			inventario = OutputFormatter.formatListaProdotti(model.getRegistroMagazzino());
		}
		view.stampaMsg(inventario);
	}

}
