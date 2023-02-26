package it.unibs.ing.progetto.ristorante.prototipo;

import it.unibs.ing.progetto.ristorante.Gestore;
import it.unibs.ing.progetto.ristorante.Ingrediente;
import it.unibs.ing.progetto.ristorante.Misura;
import it.unibs.ing.progetto.ristorante.ProductSheet;

import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

public class Controller {

	private Gestore model;
	private View view;

	public Controller(Gestore model, View view) {
		super();
		this.model = model;
		this.view = view;
	}

	public void avviaController() {
		boolean running = true;
		MyMenu menu = new MyMenu("Gestore", Gestore.getOpzioni());
		int scelta;
		do {
			scelta = menu.scegli();
			switch (scelta) {
			case 0:
				running = false;
				break;
			case 1:
				ArrayList<ProductSheet> ingredienti = this.chiediIngredienti();
				double workLoad = InputDati.leggiDoubleConMinimo("Inserisci work load -> ", 0.0);
				int porzioni = InputDati.leggiInteroConMinimo("Inserisci porzioni -> ", 1);
				String nome = InputDati.leggiStringaNonVuota("Nome del piatto -> ");
				model.creaRicetta(ingredienti, workLoad, porzioni, nome);
				break;
			case 2:
				int numero = InputDati.leggiInteroConMinimo("Inserisci numero posti ristorante -> ", 1);
				model.setPostiASedere(numero);
				break;
			case 3:
				double workLoadRistorante = InputDati.leggiDoubleConMinimo("Inserisci carico ristorante ->", 0.0);
				model.inizializzaWorkload(workLoadRistorante);
				break;
			case 4:
				view.printData(this.model.visualizzaRicette());
				break;
			default:
				view.printData("Errore");
			}
		} while (running);
	}

	private ArrayList<ProductSheet> chiediIngredienti() {
		boolean on = false;
		ArrayList<ProductSheet> ingredienti = new ArrayList<>();
		do {
			String nomeIngrediente = InputDati.leggiStringaNonVuota("Inserisci nome dell'ingrediente --> ");
			double dose = InputDati.leggiDoubleConMinimo("Inserisci dose (dose must be > 0.0) --> ", 0.0);
			ingredienti.add(new ProductSheet(new Ingrediente(nomeIngrediente, Misura.DEFAULT), dose));
			on = InputDati.yesOrNo("Vuoi aggiungere un altro ingrediente? ");
		} while (on);
		return ingredienti;
	}

}
