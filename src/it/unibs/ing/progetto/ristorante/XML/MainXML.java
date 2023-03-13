package it.unibs.ing.progetto.ristorante.XML;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class MainXML {

	public final static String PATH_XML_PRENOTAZIONI="src/xmlFile/Prenotazione.xml";
	public final static String PATH_XML_RICETTE="src/xmlFile/Ricette.xml";
    private static final String STRINGA_INIZIO_STAMPA="Inizio generazione del file di output.";
    private static final String STRINGA_INIZIO_LETTURA ="Inizio lettura del file.";
    
	public static void main(String[] args) {
		/*
		LocalDate dataPrenotazione = LocalDate.of(2023, 2, 26);
		String piatto1 = "Pasta tonno e olive";
		String piatto2 = "Seppie in umido";
		String piatto3 = "Carote al vapore";
		String piatto4 = "Finocchi al vapore";
		ArrayList<String> ordine = new ArrayList<String> ();
		ordine.add(piatto1);
		ordine.add(piatto2);
		ordine.add(piatto3);
		ordine.add(piatto4);

		WriterXMLPrenotazioni scrittore = new WriterXMLPrenotazioni();
        System.out.println(STRINGA_INIZIO_STAMPA);
        scrittore.writeXMLPrenotazioni("Mario", 4, dataPrenotazione, ordine, PATH_XML_PRENOTAZIONI);
        */
		
		//DATI DI PROVA (DA USARE IN XML) (DA TOGLIERE)
		//PARAMETRI
		Ristorante model = new Ristorante();
		model.setNumeroPostiASedere(50);
		model.setCaricoLavoroPerPersona(20);
		model.setCaricoLavoroRistorante((int) (20 * 50 + 20 * 50 * 0.2));
		model.setDataCorrente(LocalDate.of(2023, 3, 8));
		LocalDate dataprenotazione = LocalDate.of(2023, 1, 14);
	
		LocalDate _10gennaio2023 = LocalDate.of(2023, 01, 10);
		LocalDate _17gennaio2023 = LocalDate.of(2023, 01, 17);
		Periodo periodo = new Periodo(_10gennaio2023, _17gennaio2023);
		ArrayList<Periodo> elencoPeriodi = new ArrayList<>();
		elencoPeriodi.add(periodo);
	
		// PIATTO-RICETTA 1
		Piatto p1 = new Piatto("Pasta al tonno", 3, elencoPeriodi);
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
		model.addPiattoRicetta(ingredienti1, 3, 4, "Pasta al tonno", elencoPeriodi);
	
		// PIATTO-RICETTA 2
		Piatto p2 = new Piatto("Pollo halal", 4, elencoPeriodi);
		Prodotto i5 = new Prodotto("Pollo", (float) 1000.0, "grammi");
		Prodotto i6 = new Prodotto("Curry", (float) 100.0, "grammi");
		Prodotto i7 = new Prodotto("Olio", (float) 20.0, "ml");
		ArrayList<Prodotto> ingredienti2 = new ArrayList<>();
		ingredienti2.add(i5);
		ingredienti2.add(i6);
		ingredienti2.add(i7);
		Ricetta r2 = new Ricetta(3, p2.getCaricoLavoro());
		r2.setElencoIngredienti(ingredienti2);
		model.addPiattoRicetta(ingredienti2, 3, 4, "Pollo halal", elencoPeriodi);
	
		// PIATTO-RICETTA 3
		Piatto p3 = new Piatto("Risotto allo zafferano", 9, elencoPeriodi);
		Prodotto i8 = new Prodotto("Riso basmati", (float) 500.0, "grammi");
		Prodotto i9 = new Prodotto("Zafferano", (float) 10.0, "grammi");
		ArrayList<Prodotto> ingredienti3 = new ArrayList<>();
		ingredienti3.add(i8);
		ingredienti3.add(i9);
		Ricetta r3 = new Ricetta(1, p3.getCaricoLavoro());
		r3.setElencoIngredienti(ingredienti3);
		model.addPiattoRicetta(ingredienti3, 1, 9, "Risotto allo zafferano", elencoPeriodi);
	
		// PIATTO-RICETTA 4
		Piatto p4 = new Piatto("Onigiri", 7, elencoPeriodi);
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
		model.addPiattoRicetta(ingredienti4, 3, 7, "Alga Nori", elencoPeriodi);
	
		// BEVANDE
		Prodotto b1 = new Prodotto("Acqua", (float) 1, "l");
		Prodotto b2 = new Prodotto("Birra", (float) 0.5, "l");
		Prodotto b3 = new Prodotto("Vino", (float) 0.2, "l");
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b1);
		bevande.add(b2);
		bevande.add(b3);
		model.setInsiemeBevande(bevande);
	
		// GENERI EXTRA
		Prodotto g1 = new Prodotto("Pane", (float) 0.3, "hg");
		Prodotto g2 = new Prodotto("Grissini", (float) 0.1, "hg");
		Prodotto g3 = new Prodotto("Bruschette", (float) 0.2, "hg");
		ArrayList<Prodotto> generiExtra = new ArrayList<>();
		generiExtra.add(g1);
		generiExtra.add(g2);
		generiExtra.add(g3);
		model.setInsiemeGeneriExtra(generiExtra);
		
		//MENU TEMATICI
		ArrayList<Piatto> piatti1 = new ArrayList<>();
		piatti1.add(p1);
		piatti1.add(p1);
		piatti1.add(p4);
		piatti1.add(p4);
		model.addMenuTematico("Festa del pesce", piatti1, 20, elencoPeriodi);;
		
		WriterXMLRistorante scrittore = new WriterXMLRistorante();
		System.out.println(STRINGA_INIZIO_STAMPA);
		scrittore.writeXMLRistorante(model, PATH_XML_RICETTE);
	}

}
