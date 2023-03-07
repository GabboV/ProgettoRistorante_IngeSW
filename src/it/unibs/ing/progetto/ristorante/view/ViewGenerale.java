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
	final private static int GESTORE = 1;
	private static final int ADDETTO_PRENOTAZIONI = 2;
	private static final int MAGAZZINIERE = 3;
	private static final int ESCI = 0;

	
	public void avvioProgramma() {
		System.out.println(MSG_BENVENUTO);
		Ristorante model = loginInizializzazione();
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
			//Finita l'inizializzazione si dovrebbe rimanere nel GestoreController
			
		}
		return model;
	}
	
	public void loginUtente(Ristorante model) {
		MyMenu menu = new MyMenu(TITOLO_SCHERMATA, ELENCO_RUOLI);
		System.out.println();
		int scelta = menu.scegli();
		
		switch(scelta) {
		case GESTORE:
			GestoreController gestore = new GestoreController(model);
			gestore.apriMenuGestore();
			break;
		case ADDETTO_PRENOTAZIONI:
			AddettoPrenotazioniController addettoPrenotazioni = new AddettoPrenotazioniController(model);
			break;
		case MAGAZZINIERE:
			MagazziniereController magazziniere = new MagazziniereController(model);
			magazziniere.magazziniereHandler();
			
			break;
		case ESCI:
			System.out.println(MSG_ARRESTO_PROGRAMMA);
			System.exit(0);
			break;
		}
	}
}
