package it.unibs.ing.progetto.ristorante.UI.view;

import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class MagazzinoView {

	/**
	 *
	 */
	private static final String PRODOTTO_AGGIUNTO = "Il prodotto e' stato aggiunto";

	private static final String LISTA_VUOTA = "Lista vuota!";
	private static final String RIGA_SOTTO = "+-------------------------------------------------------------+";
	private static final String RIGA_TABELLA = "|   %3d   |   %14s   |  %8.2f  |      %5s      |\n";
	private static final String SEPARAZIONE = "|---------|--------------------|------------|-----------------|";
	private static final String CAMPI_TABELLA = "|  Index  |        Nome        |  Quantita  | Unita di misura |";
	private static final String RIGA_SOPRA = "+---------+--------------------+------------+-----------------+";
	private static final int ZERO_INT = 0;
	private static final String PRODOTTO_ESISTENTE = "Il prodotto gia registrato";
	private static final String INVENTARIO_VUOTO = "Inventario vuoto";
	private static final String REGISTRA_UN_NUOVO_PRODOTTO = "Registra un nuovo prodotto";
	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in uscita";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in ingresso";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa della giornata";
	private static final String LOGOUT_END = "Hai effettuato il Logout";
	private static final String PROFILO_MAGAZZINIERE = "Profilo: Magazziniere\n";
	private static final String INSERISCI_QUANTITA_DEL_PRODOTTO = "Inserisci quantita del prodotto -> ";
	private static final String INSERISCI_NOME_DEL_PRODOTTO = "Inserisci nome del prodotto -> ";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { VISUALIZZA_INVENTARIO, VISUALIZZA_LISTA_SPESA,
			REGISTRA_UN_NUOVO_PRODOTTO, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO, RIMUOVI_PRODOTTO_NEL_MAGAZZINO };

	/**
	 * 
	 * @return numero dell'azione
	 */
	public int scegliMenu() {
		MyMenu menu = new MyMenu(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
		return menu.scegli();
	}

	/**
	 * Stampa a console la lista dei prodotti passata
	 * 
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

	public void printMsgProdottoAggiunto() {
		System.out.println(PRODOTTO_AGGIUNTO);
	}

	public void printMsgOperazioneEffettuata() {
		System.out.println("Operazione effettuata");
	}

	public void printMsgInventarioVuoto() {
		System.out.println(INVENTARIO_VUOTO);
	}

	/**
	 * Avvisa tramite console che il prodotto esiste gia
	 */
	public void printMsgProdottoGiaEsistente() {
		System.out.println(PRODOTTO_ESISTENTE);
	}

	public void printMsgSpesaVuota() {
		System.out.println(LISTA_VUOTA);
	}

	public int scegliProdottoDaInventario(List<Prodotto> prodotti) {
		this.printListaProdotto(prodotti);
		return InputDati.leggiIntero("Indica il prodotto da aggiornare > ", 0, prodotti.size() - 1);
	}

	public float richiediQuantita() {
		return InputDati.leggiFloatConMinimo("Indica la quantita da aggiungere > ", 0);
	}

	public float richiediQuantitaCompresa(float max) {
		return InputDati.leggiFloatCompreso("Inserisci quantita da ridurre -> ", 0f, max);
	}

	public float richiediQuantitaRegistrazione() {
		return InputDati.leggiFloatConMinimo(INSERISCI_QUANTITA_DEL_PRODOTTO, 0f);
	}

	public void printLogout() {
		System.out.println(LOGOUT_END);
	}

	public void printMsgBenvenuto() {
		System.out.println(PROFILO_MAGAZZINIERE);
	}

	public String richiediNomeProdotto() {
		return InputDati.leggiStringaNonVuota(INSERISCI_NOME_DEL_PRODOTTO);
	}

	public UnitaMisura richiestaUnitaMisura() {
		return InputDati.richiestaUnitaMisura();
	}

}
