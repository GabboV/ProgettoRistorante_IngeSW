package it.unibs.ing.progetto.ristorante.pattern;

import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class MagazzinoView {

	private static final int CINQUE_MENU = 5;
	private static final int QUATTRO_MENU = 4;
	private static final int TRE_MENU = 3;
	private static final int DUE_MENU = 2;
	private static final int UNO_MENU = 1;
	private static final int ZERO_MENU = 0;
	private static final String SCELTA_PRODOTTO = "Scegliere il prodotto >";
	private static final String LISTA_VUOTA = "Lista vuota!";
	private static final String RIGA_SOTTO = "+-------------------------------------------------------------+";
	private static final String RIGA_TABELLA = "|   %3d   |   %14s   |  %8.2f  |      %5s      |\n";
	private static final String SEPARAZIONE = "|---------|--------------------|------------|-----------------|";
	private static final String CAMPI_TABELLA = "|  Index  |        Nome        |  Quantita  | Unita di misura |";
	private static final String RIGA_SOPRA = "+---------+--------------------+------------+-----------------+";
	private static final float ZERO_FLOAT = 0.f;
	private static final int ZERO_INT = 0;
	private static final String PRODOTTO_ESISTENTE = "Il prodotto gia registrato";
	private static final String RICHIESTA_QUANTITA = "Quantita > ";
	private static final String RICHIESTA_NOME = "Nome prodotto >";
	private static final String LISTA_SPESA_VUOTA = "Lista della spesa vuota";
	private static final String INVENTARIO_VUOTO = "Inventario vuoto";
	private static final String REGISTRA_UN_NUOVO_PRODOTTO = "Registra un nuovo prodotto";
	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in uscita";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in ingresso";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa della giornata";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { VISUALIZZA_INVENTARIO, VISUALIZZA_LISTA_SPESA,
			REGISTRA_UN_NUOVO_PRODOTTO, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO, RIMUOVI_PRODOTTO_NEL_MAGAZZINO };

	private MagazzinoController controller;

	public MagazzinoView(MagazzinoController controller) {
		super();
		this.controller = controller;
	}

	/**
	 * Stampa il menu e richiede l'input (azione) che l'utente vuole eseguire
	 */
	public void gestioneMenu() {
		MyMenu menu = new MyMenu(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
		boolean sessionOn = true;
		do {
			int input = menu.scegli();
			switch (input) {
			case ZERO_MENU:
				sessionOn = false;
				break;
			case UNO_MENU:
				this.printInventario();
				break;
			case DUE_MENU:
				this.printListaSpesa();
				break;
			case TRE_MENU:
				this.registrazioneProdotto();
				break;
			case QUATTRO_MENU:
				this.enterFlussoEntrante();
				break;
			case CINQUE_MENU:
				this.enterFlussoUscente();
				break;
			default:
				//
				break;
			}
		} while (sessionOn);
	}

	/**
	 * Stampa a console l'inventario
	 */
	public void printInventario() {
		List<Prodotto> inventario = controller.retrieveInventario();
		if (inventario.isEmpty()) {
			System.out.println(INVENTARIO_VUOTO);
		} else {
			this.printListaProdotto(inventario);
		}
	}

	/**
	 * Stampa a console la lista della spesa
	 */
	public void printListaSpesa() {
		List<Prodotto> list = controller.retrieveLista();
		if (list.isEmpty()) {
			System.out.println(LISTA_SPESA_VUOTA);
		} else {
			this.printListaProdotto(list);
		}

	}

	/**
	 * Richiede i dati necessari per registrare un prodotto
	 */
	public void registrazioneProdotto() {
		String nome = InputDati.leggiStringaNonVuota(RICHIESTA_NOME);
		if (!controller.nomeProdottoGiaPresente(nome)) {
			float quantita = InputDati.leggiFloatConMinimo(RICHIESTA_QUANTITA, 0.f);
			UnitaMisura unitaMisura = InputDati.richiestaUnitaMisura();
			controller.addProdottoRegistroMagazzino(nome, quantita, unitaMisura);
		} else {
			System.out.println(PRODOTTO_ESISTENTE);
		}
	}

	/**
	 * Richiede i dati necessari per registrare un flusso entrante
	 */
	public void enterFlussoEntrante() {
		List<Prodotto> inventario = controller.retrieveInventario();
		this.printListaProdotto(inventario);
		int index = InputDati.leggiIntero(SCELTA_PRODOTTO, ZERO_INT, inventario.size() - 1);
		float quantita = InputDati.leggiFloatConMinimo(RICHIESTA_QUANTITA, 0.f);
		controller.aggiungiFlussoEntrante(inventario.get(index), quantita);
	}

	/**
	 * Richiede i dati necessari per registrare un flusso uscente
	 */
	public void enterFlussoUscente() {
		List<Prodotto> inventario = controller.retrieveInventario();
		this.printListaProdotto(inventario);
		int index = InputDati.leggiIntero(SCELTA_PRODOTTO, ZERO_INT, inventario.size() - 1);
		float quantita = InputDati.leggiFloatCompreso(RICHIESTA_QUANTITA, ZERO_FLOAT, inventario.get(index).getQuantita());
		controller.aggiungiFlussoUscente(inventario.get(index), quantita);
	}

	/**
	 * Stampa a console la lista dei prodotti passata
	 * @param list
	 */
	public void printListaProdotto(List<Prodotto> list) {
		if (!list.isEmpty()) {
			int index = ZERO_INT;
			System.out.println(RIGA_SOPRA);
			System.out.println(CAMPI_TABELLA);
			System.out.println(SEPARAZIONE);
			for (Prodotto p : list) {
				System.out.printf(RIGA_TABELLA, index, p.getNome(),
						p.getQuantita(), p.getUnitaMisura());
				index++;
			}
			System.out.println(RIGA_SOTTO);
		} else {
			System.out.println(LISTA_VUOTA);
		}
	}
	
	/**
	 * Avvisa tramite console che il prodotto esiste gia
	 */
	public void printMsgProdottoGiaEsistente() {
		System.out.println(PRODOTTO_ESISTENTE);
	}

	public void setController(MagazzinoController controller) {
		this.controller = controller;
	}

}
