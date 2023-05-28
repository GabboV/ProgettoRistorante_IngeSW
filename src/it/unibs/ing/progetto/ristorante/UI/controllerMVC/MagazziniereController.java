package it.unibs.ing.progetto.ristorante.UI.controllerMVC;

import java.util.List;

import it.unibs.ing.progetto.ristorante.UI.view.MagazzinoView;
import it.unibs.ing.progetto.ristorante.controllerGRASP.MagazzinoController;
import it.unibs.ing.progetto.ristorante.interfacce.Controller;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

/**
 * Controller MVC
 */
public class MagazziniereController implements Controller {

	static final int LOGOUT = 0;
	private static final int VISUALIZZA_INVENTARIO = 1;
	private static final int VISUALIZZA_LISTA_SPESA = 2;
	private static final int REGISTRA_PRODOTTO = 3;
	private static final int AGGIUNGI_PRODOTTI = 4;
	private static final int RIMUOVI_PRODOTTI = 5;

	private MagazzinoView window;
	private MagazzinoController controller;

	public MagazziniereController(MagazzinoController controller) {
		this.controller = controller;
		this.window = new MagazzinoView();
	}

	/**
	 * Gestisce il menu visibile dall'utente magazziniere
	 */
	public void avviaSessione() {
		window.printMsgBenvenuto();
		boolean sessionOn = true;
		do {
			int input = window.scegliMenu();
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
					break;
			}
		} while (sessionOn);
		window.printLogout();
	}

	/**
	 * Visualizza l'inventario
	 */
	public void visualizzaInventario() {
		if (controller.retrieveInventario().isEmpty()) {
			window.printMsgInventarioVuoto();
		} else {
			window.printListaProdotto(controller.retrieveInventario());
		}

	}

	/**
	 * Visualizza la lista della spesa
	 */
	public void visualizzaListaSpesa() {
		if (controller.retrieveLista().isEmpty()) {
			window.printMsgSpesaVuota();
		} else {
			window.printListaProdotto(controller.retrieveLista());
		}
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino, dopo aver chiesto le
	 * informazioni al riguardo, Non fa aggiungere prodotti gia esistenti
	 */
	public void addProdottoRegistroMagazzino() {
		String nome = window.richiediNomeProdotto();
		if (!controller.nomeProdottoGiaPresente(nome)) {
			float quantita = window.richiediQuantitaRegistrazione();
			UnitaMisura unitaMisura = window.richiestaUnitaMisura();
			controller.addProdottoRegistroMagazzino(nome, quantita, unitaMisura);
		} else {
			window.printMsgProdottoGiaEsistente();
		}
	}

	/**
	 * Metodo per aggiungere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoEntrante() {
		if (!controller.retrieveInventario().isEmpty()) {
			List<Prodotto> magazzino = controller.retrieveInventario();
			int scelto = window.scegliProdottoDaInventario(magazzino);
			Prodotto scelta = magazzino.get(scelto);
			float quantita = window.richiediQuantita();
			controller.aggiungiFlussoEntrante(scelta, quantita);
		} else {
			window.printMsgInventarioVuoto();
		}
	}

	/**
	 * Metodo per rimuovere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoUscente() {
		if (!controller.retrieveInventario().isEmpty()) {
			List<Prodotto> magazzino = controller.retrieveInventario();
			int scelto = window.scegliProdottoDaInventario(magazzino);
			Prodotto prodotto = magazzino.get(scelto);
			float quantitaDaRidurre = window.richiediQuantitaCompresa(prodotto.getQuantita());
			controller.aggiungiFlussoUscente(prodotto, quantitaDaRidurre);
		} else {
			window.printMsgInventarioVuoto();
		}
	}

}
