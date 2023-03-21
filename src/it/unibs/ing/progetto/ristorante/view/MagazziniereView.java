package it.unibs.ing.progetto.ristorante.view;

public class MagazziniereView extends View {

	private static final String MAGAZZINIERE = "Magazziniere";
	private static final String VISUALIZZA_INVENTARIO = "Visualizza inventario";
	private static final String RIMUOVI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in uscita";
	private static final String AGGIUNGI_PRODOTTO_NEL_MAGAZZINO = "Aggiorna quantita in ingresso";
	private static final String VISUALIZZA_LISTA_SPESA = "Visualizza lista spesa della giornata";
	private static final String ACQUISTA_PRODOTTI = "Controllo prodotti sufficienti per oggi";

	private static final String[] OPZIONI_MAGAZZINIERE = new String[] { ACQUISTA_PRODOTTI, VISUALIZZA_INVENTARIO,
			VISUALIZZA_LISTA_SPESA, AGGIUNGI_PRODOTTO_NEL_MAGAZZINO, RIMUOVI_PRODOTTO_NEL_MAGAZZINO};

	public MagazziniereView() {
		super(MAGAZZINIERE, OPZIONI_MAGAZZINIERE);
	}
	
}
