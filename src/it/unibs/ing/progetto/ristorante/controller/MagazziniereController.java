package it.unibs.ing.progetto.ristorante.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.MagazziniereView;

public class MagazziniereController {

	private static final int VISUALIZZA_LISTA_SPESA = 3;
	private static final String LOGOUT_END = "Hai effettuato il Logout";
	private static final int AGGIORNA_REGISTRO_MAGAZZINO = 2;
	private static final int CREA_LISTA_SPESA = 1;
	private static final int LOGOUT = 0;
	private Ristorante model;
	private MagazziniereView view;

	public MagazziniereController(Ristorante model) {
		super();
		this.model = model;
	}

	public void aggiornaRegistroMagazzino() {
		/*
		 * Da implementare
		 */
	}

	public void creaListaSpesa(LocalDate date) {
		model.creaListaSpesa(date);
	}

	public void magazziniereHandler(LocalDate date) {

		boolean on = true;
		do {
			int input = this.view.printMenu();
			switch (input) {
			case LOGOUT:
				on = false;
				break;
			case CREA_LISTA_SPESA:
				this.creaListaSpesa(date);
				break;
			case AGGIORNA_REGISTRO_MAGAZZINO:
				this.aggiornaRegistroMagazzino();
				break;
			case VISUALIZZA_LISTA_SPESA:
				break;
			}
		} while (on);

		view.print(LOGOUT_END);

	}

}
