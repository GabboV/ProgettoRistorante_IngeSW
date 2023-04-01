package it.unibs.ing.progetto.ristorante.view;

import java.util.ArrayList;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.ing.progetto.ristorante.model.Prodotto;

public class MagazziniereView extends View {

	/**
	 *
	 */
	private static final String REGISTRA_UN_NUOVO_PRODOTTO = "Registra un nuovo prodotto";
	private static final String LISTA_VUOTA = "Lista vuota";
	private static final String MAGAZZINO_VUOTO = "Magazzino vuoto";
	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in uscita";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in ingresso";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa della giornata";
	private static final String FORMAT_UNITA_DI_MISURA_S = "Unita di misura: %s\n";
	private static final String FORMAT_QUANTITA_2F = "Quantita: %.2f\n";
	private static final String FORMAT_NOME = "Prodotto: %15s\n";
	private static final String FORMAT_VOCI_LISTA = "-%3d  %20s\t%8.2f  %10s\n";
	private static final String ELENCO = "Indice       Denominazione      Quantita      Misura\n";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { VISUALIZZA_INVENTARIO,
			VISUALIZZA_LISTA_SPESA, REGISTRA_UN_NUOVO_PRODOTTO, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO,
			RIMUOVI_PRODOTTO_NEL_MAGAZZINO };

	public MagazziniereView() {
		super(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
	}

	/**
	 * Formatta una lista di prodotti
	 * 
	 * @param lista
	 * @return
	 */
	public static String formatListaProdotti(ArrayList<Prodotto> lista) {
		StringBuilder builder = new StringBuilder();
		int indice = 1;
		builder.append(ELENCO);
		for (Prodotto voce : lista) {
			String denominazione = voce.getNome();
			float quantita = voce.getQuantita();
			String unitaMisura = voce.getUnitaMisura().getName();
			String voceFormattata = String.format(FORMAT_VOCI_LISTA, indice, denominazione, quantita, unitaMisura);
			builder.append(voceFormattata);
			indice++;
		}
		return builder.toString();
	}

	public static String formatProdotto(Prodotto prodotto) {
		StringBuilder builder = new StringBuilder();
		String formatNome = String.format(FORMAT_NOME, prodotto.getNome());
		String quantita = String.format(FORMAT_QUANTITA_2F, prodotto.getQuantita());
		String unitaDiMisura = String.format(FORMAT_UNITA_DI_MISURA_S, prodotto.getUnitaMisura());
		builder.append(formatNome);
		builder.append(quantita);
		builder.append(unitaDiMisura);
		return builder.toString();
	}

	public void stampaMagazzino(ArrayList<Prodotto> magazzino) {
		if (magazzino != null && !magazzino.isEmpty()) {
			System.out.println(formatListaProdotti(magazzino));
		} else {
			System.out.println(MAGAZZINO_VUOTO);
		}

	}

	public void stampaListaSpesa(ArrayList<Prodotto> listaSpesa) {
		if (listaSpesa != null && !listaSpesa.isEmpty()) {
			System.out.println(BelleStringhe.incornicia(formatListaProdotti(listaSpesa)));
		} else {
			System.out.println(LISTA_VUOTA);
		}
	}

}
