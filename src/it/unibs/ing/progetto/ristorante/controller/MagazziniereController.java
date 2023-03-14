 package it.unibs.ing.progetto.ristorante.controller;

import java.time.LocalDate;

import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.MagazziniereView;
import it.unibs.ing.progetto.ristorante.view.OutputFormatter;

public class MagazziniereController extends Controller {

	private static final String MAGAZZINO_VUOTO = "\nIl Magazzino è vuoto\n";
	private static final String LOGOUT_END = "Hai effettuato il Logout";
	private static final String NESSUNA_LISTA_DELLA_SPESA = "\nLa lista della spesa è vuota o non è mai stata creata\n";
	private static final String INSERIRE_LA_DATA_PER_CUI_CREARE_LA_LISTA_DELLA_SPESA = "Inserire la data per cui creare la lista della spesa\n";
	private static final String PROFILO_MAGAZZINIERE = "Profilo: Magazziniere\n";
	private static final String ERRORE = "Something really really bad happened...riavviare il programma";
	private static final String INSERISCI_QUANTITA_DA_RIDURRE = "Inserisci quantita da ridurre -> ";
	private static final String SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE = "Selezione il prodotto (numero) da eliminare o ridurre -> ";
	private static final String INSERISCI_QUANTITA_DEL_PRODOTTO = "Inserisci quantita del prodotto -> ";
	private static final String INSERISCI_UNITA_DI_MISURA_DEL_PRODOTTO = "Inserisci unita di misura del prodotto -> ";
	private static final String INSERISCI_NOME_DEL_PRODOTTO = "Inserisci nome del prodotto -> ";

	private static final int ZERO = 0;
	private static final int LOGOUT = 0;
	private static final int CREA_LISTA_SPESA = 1;
	private static final int VISUALIZZA_INVENTARIO = 2;
	private static final int VISUALIZZA_LISTA_SPESA = 3;
	private static final int AGGIUNGI_PRODOTTI = 4;
	private static final int RIMUOVI_PRODOTTI_DETERIORATI = 5;

	private MagazziniereView view;

	public MagazziniereController(Ristorante model) {
		super(model);
		this.view = new MagazziniereView();
	}

	public void avviaSessione() {
		view.stampaMsg(PROFILO_MAGAZZINIERE);
		boolean sessionOn = true;
		do {
			int input = view.printMenu();
			switch (input) {
			case LOGOUT:
				sessionOn = false;
				break;
			case CREA_LISTA_SPESA:
				this.creaListaSpesa();
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

	public void creaListaSpesa() {
		LocalDate data = view.richiestaData(INSERIRE_LA_DATA_PER_CUI_CREARE_LA_LISTA_DELLA_SPESA);
		this.getModel().generaListaSpesa(data);
		this.visualizzaListaSpesa();
	}

	public void visualizzaListaSpesa() {
		String listaFormattata;
		if (this.getModel().isListaSpesaEmpty()) {
			listaFormattata = NESSUNA_LISTA_DELLA_SPESA;
		} else {
			listaFormattata = OutputFormatter.formatListaProdotti(this.getModel().getListaSpesa());
		}
		view.stampaMsg(listaFormattata);
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino (con feedback dalla view)
	 */
	public void addProdottoRegistroMagazzino() {
		String nome = view.richiestaNome(INSERISCI_NOME_DEL_PRODOTTO);
		String unitaMisura = view.richiestaNome(INSERISCI_UNITA_DI_MISURA_DEL_PRODOTTO);
		float quantita = view.richiestaQuantita(INSERISCI_QUANTITA_DEL_PRODOTTO);

		this.addProdottoRegistro(nome, quantita, unitaMisura);

		String feedback = "Aggiungto\n";
		view.stampaMsg(feedback);
	}

	public void addProdottoRegistro(String nome, float quantita, String unitaMisura) {
		this.getModel().addProdottoInventario(nome, quantita, unitaMisura);
	}

	public void rimuoviProdottiInventario() {
		if (!this.getModel().isRegistroMagazzinoEmpty()) {
			view.stampaInsiemeProdotti(this.getModel().getRegistroMagazzino());
			int indiceProdottoSelezionato = view.leggiInteroCompreso(
					SELEZIONE_IL_PRODOTTO_NUMERO_DA_ELIMINARE_O_RIDURRE, ZERO,
					this.getModel().getRegistroMagazzino().size() - 1);
			float quantitaDaRidurre = view.richiestaQuantita(INSERISCI_QUANTITA_DA_RIDURRE);
			this.getModel().rimuoviQuantitaProdottoDaRegistro(
					this.getModel().getRegistroMagazzino().get(indiceProdottoSelezionato), quantitaDaRidurre);
		} else {
			view.stampaMsg(MAGAZZINO_VUOTO);
		}

	}

	public void visualizzaInventario() {
		String inventario;
		if (this.getModel().isRegistroMagazzinoEmpty()) {
			inventario = MAGAZZINO_VUOTO;
		} else {
			inventario = OutputFormatter.formatListaProdotti(this.getModel().getRegistroMagazzino());
		}
		view.stampaMsg(inventario);
	}

}
