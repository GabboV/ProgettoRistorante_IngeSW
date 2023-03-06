package it.unibs.ing.progetto.ristorante.view;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.controller.AddettoPrenotazioniController;
import it.unibs.ing.progetto.ristorante.controller.GestoreController;
import it.unibs.ing.progetto.ristorante.controller.MagazziniereController;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class ViewGenerale {

	final private static String MSG_BENVENUTO = "Benvenuto nel programma di supporto del tuo ristorante.";
	final private static String TITOLO_SCHERMATA = "PAGINA DI LOGIN";
	final private static String[] ELENCO_RUOLI = {"GESTORE", "ADDETTO PRENOTAZIONI", "MAGAZZINIERE"};
	final private static String MSG_ARRESTO_PROGRAMMA = "Arresto programma...";
	
	public void avvioProgramma() {
		System.out.println(MSG_BENVENUTO);
		//Ristorante model = loginInizializzazione();
		Ristorante model=null;
		loginUtente(model);
	}
	
	public Ristorante loginInizializzazione() {
		System.out.println("Per la fase di inizializzazione del programma è necessario il login del Gestore.");
		Ristorante model = null;
		boolean risposta = InputDati.yesOrNo("Sei il Gestore? ");
		if (risposta == false) {
			System.out.println("Bisogna essere il Gestore per poter inizializzare il programma.");
			System.out.println(MSG_ARRESTO_PROGRAMMA);
			System.exit(0);
		}
		else {
			GestoreController gestore = new GestoreController();
			model = gestore.inizializzaRistorante();
			System.out.println("Hai completato l'inizializzazione del programma.");
			//Finita l'inizializzazione si dovrebbe rimanere nel GestoreController
		}
		return model;
	}
	
	public void loginUtente(Ristorante model) {
		MyMenu menu = new MyMenu(TITOLO_SCHERMATA, ELENCO_RUOLI);
		System.out.println();
		int scelta = menu.scegli();
		if(scelta == 1) {
			GestoreController gestore = new GestoreController(model);
		}
		if(scelta == 2) {
			AddettoPrenotazioniController addettoPrenotazioni = new AddettoPrenotazioniController(model);
		}
		if(scelta == 3) {
			MagazziniereController magazziniere = new MagazziniereController(model);
		}
		if(scelta == 0) {
			System.out.println(MSG_ARRESTO_PROGRAMMA);
			System.exit(0);
		}
	}
}
