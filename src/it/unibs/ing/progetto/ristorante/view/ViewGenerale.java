package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.controller.AddettoPrenotazioniController;
import it.unibs.ing.progetto.ristorante.controller.GestoreController;
import it.unibs.ing.progetto.ristorante.controller.MagazziniereController;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class ViewGenerale {

	final private static String MSG_BENVENUTO = "Benvenuto nel programma di supporto del tuo ristorante.";
	final private static String TITOLO_SCHERMATA = "PAGINA DI LOGIN";
	final private static String[] ELENCO_RUOLI = {"GESTORE", "ADDETTO PRENOTAZIONI (non ancora implementato)", "MAGAZZINIERE"};
	final private static String MSG_ARRESTO_PROGRAMMA = "Arresto programma...";
	final private static int GESTORE = 1;
	private static final int ADDETTO_PRENOTAZIONI = 2;
	private static final int MAGAZZINIERE = 3;
	private static final int ESCI = 0;

	
	public void avvioProgramma() {
		System.out.println(MSG_BENVENUTO);
		//Ristorante model = loginInizializzazione();
		
		
		//DATI DI PROVA (DA USARE IN XML) (DA TOGLIERE)
		//PARAMETRI
		Ristorante model = new Ristorante();
		model.setNumeroPostiASedere(50);
		model.setCaricoLavoroPerPersona(20);
		model.setCaricoLavoroRistorante((int) (20*50 + 20*50*0.2));
		model.setDataCorrente(LocalDate.of(2023, 3, 8));
		
		LocalDate _10gennaio2023 = LocalDate.of(2023, 01, 10);
		LocalDate _17gennaio2023 = LocalDate.of(2023, 01, 17);
		Periodo periodo = new Periodo(_10gennaio2023, _17gennaio2023);

		//PIATTO-RICETTA 1
		Piatto p1 = new Piatto("Pasta al tonno", 3);
		p1.addDatePair(periodo);
		Prodotto i1 = new Prodotto("Pasta", 300.0f, "grammi");
		Prodotto i2 = new Prodotto("Tonno", 100.0f, "grammi");
		Prodotto i3 = new Prodotto("Sugo", 80.0f, "grammi");
		Prodotto i4 = new Prodotto("Olio", 20.0f, "ml");
		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
		ingredienti1.add(i1);
		ingredienti1.add(i2);
		ingredienti1.add(i3);
		ingredienti1.add(i4);
		Ricetta r1 = new Ricetta(4, p1.getCaricoLavoro());
		r1.setElencoIngredienti(ingredienti1);
		model.addPiattoRicetta(p1, r1);
		
		//PIATTO-RICETTA 2
		Piatto p2 = new Piatto("Pollo halal", 4);
		p2.addDatePair(periodo);
		Prodotto i5 = new Prodotto("Pollo", (float) 1000.0, "grammi");
		Prodotto i6 = new Prodotto("Curry", (float) 100.0, "grammi");
		Prodotto i7 = new Prodotto("Olio", (float) 20.0, "ml");
		ArrayList<Prodotto> ingredienti2 = new ArrayList<>();
		ingredienti2.add(i5);
		ingredienti2.add(i6);
		ingredienti2.add(i7);
		Ricetta r2 = new Ricetta(3, p2.getCaricoLavoro());
		r2.setElencoIngredienti(ingredienti2);
		model.addPiattoRicetta(p2, r2);
		
		//PIATTO-RICETTA 3
		Piatto p3 = new Piatto("Risotto allo zafferano", 9);
		p3.addDatePair(periodo);
		Prodotto i8 = new Prodotto("Riso basmati", (float) 500.0, "grammi");
		Prodotto i9 = new Prodotto("Zafferano", (float) 10.0, "grammi");
		ArrayList<Prodotto> ingredienti3 = new ArrayList<>();
		ingredienti3.add(i8);
		ingredienti3.add(i9);
		Ricetta r3 = new Ricetta(1, p3.getCaricoLavoro());
		r3.setElencoIngredienti(ingredienti3);
		model.addPiattoRicetta(p3, r3);
		
		//PIATTO-RICETTA 4
		Piatto p4 = new Piatto("Onigiri", 7);
		p4.addDatePair(periodo);
		Prodotto i10 = new Prodotto("Alga Nori", (float) 3, "unita");
		Prodotto i12 = new Prodotto("Tonno", (float) 100.0, "grammi");
		Prodotto i13 = new Prodotto("Salsa", (float) 80.0, "ml");
		Prodotto i14 = new Prodotto("Riso", (float) 200.0, "grammi");
		ArrayList<Prodotto> ingredienti4 = new ArrayList<>();
		ingredienti4.add(i10);
		ingredienti4.add(i12);
		ingredienti4.add(i13);
		ingredienti4.add(i14);
		Ricetta r4 = new Ricetta(3, p4.getCaricoLavoro());
		r4.setElencoIngredienti(ingredienti4);
		model.addPiattoRicetta(p4, r4);
		
		//BEVANDE
		Prodotto b1 = new Prodotto("Acqua", (float) 1, "l");
		Prodotto b2 = new Prodotto("Birra", (float) 0.5, "l");
		Prodotto b3 = new Prodotto("Vino", (float) 0.2, "l");
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b1);
		bevande.add(b2);
		bevande.add(b3);
		model.setInsiemeBevande(bevande);
		
		//GENERI EXTRA
		Prodotto g1 = new Prodotto("Pane", (float) 0.3, "hg");
		Prodotto g2 = new Prodotto("Grissini", (float) 0.1, "hg");
		Prodotto g3 = new Prodotto("Bruschette", (float) 0.2, "hg");
		ArrayList<Prodotto> generiExtra = new ArrayList<>();
		generiExtra.add(g1);
		generiExtra.add(g2);
		generiExtra.add(g3);
		model.setInsiemeGeneriExtra(generiExtra);
		
		
		
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
			loginUtente(model);
			break;
		case ADDETTO_PRENOTAZIONI:
			AddettoPrenotazioniController addettoPrenotazioni = new AddettoPrenotazioniController(model);
			
			loginUtente(model);
			break;
		case MAGAZZINIERE:
			MagazziniereController magazziniere = new MagazziniereController(model);
			magazziniere.magazziniereHandler();
			loginUtente(model);
			break;
		case ESCI:
			System.out.println(MSG_ARRESTO_PROGRAMMA);
			System.exit(0);
			break;
		}
	}
}
