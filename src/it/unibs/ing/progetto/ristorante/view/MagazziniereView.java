package it.unibs.ing.progetto.ristorante.view;

public class MagazziniereView extends View {

	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Rimuovi prodotto nel magazzino";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiungi prodotto nel magazzino";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa";
	private static final String CREA_LISTA_SPESA = "Crea Lista Spesa";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { CREA_LISTA_SPESA, VISUALIZZA_INVENTARIO,
			VISUALIZZA_LISTA_SPESA, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO, RIMUOVI_PRODOTTO_NEL_MAGAZZINO };

	public MagazziniereView() {
		super(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
	}

}
