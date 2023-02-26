package it.unibs.ing.progetto.ristoranteCLI;

import java.util.ArrayList;

import Principale.Prenotazione;
import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.DataBase;
import it.unibs.ing.progetto.ristorante.Gestore;
import it.unibs.ing.progetto.ristorante.Ingrediente;
import it.unibs.ing.progetto.ristorante.Misura;
import it.unibs.ing.progetto.ristorante.ProductSheet;

public class CLI_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DataBase ristoranteDB = new DataBase();
		Gestore gestore = new Gestore("administrator", ristoranteDB);

		/*
		 * 
		 */
		gestore.setPostiASedere(InputDati.leggiIntero("Posti a sedere--> ", 1, 100));
		gestore.inizializzaWorkload(InputDati.leggiDoubleConMinimo("Inserisci carico di lavoro per persona -->", 0.0));

		/*
		 * 
		 */
		int porzioni = InputDati.leggiInteroConMinimo("inserisci numero di porzioni --> ", 1);
		double workLoad = InputDati.leggiDoubleConMinimo("Inserisci il workLoad --> ", 0.0);
		ArrayList<ProductSheet> ingredienti = new ArrayList<ProductSheet>();
		ingredienti = chiediIngredienti();
		
		//gestore.creaRicetta(ingredienti, workLoad, porzioni,"CicogniEFagiane");
		
		System.out.println(gestore.visualizzaDB());
		/*
		 * 
		 */

	}

	private static ArrayList<ProductSheet> chiediIngredienti() {
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
	
	private static ArrayList<Prenotazione> chiediPrenotazione(){
		ArrayList<Prenotazione> prenotazione = new ArrayList<>();
		return prenotazione;
	}

}
