package it.unibs.ing.progetto.ristorante.XML;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class MainXML {

	public final static String PATH_XML_PRENOTAZIONI="src/xmlFile/Prenotazione.xml";
	public final static String PATH_XML_RICETTE="src/xmlFile/Ricette.xml";
    private static final String STRINGA_INIZIO_STAMPA="Inizio generazione del file di output.";
    private static final String STRINGA_INIZIO_LETTURA ="Inizio lettura del file.";
    
	public static void main(String[] args) {
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
	
		// PIATTO-RICETTA 0
		Prodotto i1 = new Prodotto("Pasta", 300.0f, "grammi");
		Prodotto i2 = new Prodotto("Tonno", 100.0f, "grammi");
		Prodotto i3 = new Prodotto("Sugo", 80.0f, "grammi");
		Prodotto i4 = new Prodotto("Olio", 20.0f, "ml");
		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
		ingredienti0.add(i1);
		ingredienti0.add(i2);
		ingredienti0.add(i3);
		ingredienti0.add(i4);
		model.addPiattoRicetta(ingredienti0, 3, 4, "Pasta al tonno", elencoPeriodi);
	
		// PIATTO-RICETTA 1
		Prodotto i5 = new Prodotto("Pollo", (float) 1000.0, "grammi");
		Prodotto i6 = new Prodotto("Curry", (float) 100.0, "grammi");
		Prodotto i7 = new Prodotto("Olio", (float) 20.0, "ml");
		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
		ingredienti1.add(i5);
		ingredienti1.add(i6);
		ingredienti1.add(i7);
		model.addPiattoRicetta(ingredienti1, 3, 4, "Pollo halal", elencoPeriodi);
	
		// PIATTO-RICETTA 2
		Prodotto i8 = new Prodotto("Riso basmati", (float) 500.0, "grammi");
		Prodotto i9 = new Prodotto("Zafferano", (float) 10.0, "grammi");
		ArrayList<Prodotto> ingredienti2 = new ArrayList<>();
		ingredienti2.add(i8);
		ingredienti2.add(i9);
		model.addPiattoRicetta(ingredienti2, 1, 9, "Risotto allo zafferano", elencoPeriodi);
	
		// PIATTO-RICETTA 4
		Prodotto i10 = new Prodotto("Alga Nori", (float) 3, "unita");
		Prodotto i12 = new Prodotto("Tonno", (float) 100.0, "grammi");
		Prodotto i13 = new Prodotto("Salsa", (float) 80.0, "ml");
		Prodotto i14 = new Prodotto("Riso", (float) 200.0, "grammi");
		ArrayList<Prodotto> ingredienti3 = new ArrayList<>();
		ingredienti3.add(i10);
		ingredienti3.add(i12);
		ingredienti3.add(i13);
		ingredienti3.add(i14);
		model.addPiattoRicetta(ingredienti3, 3, 7, "Alga Nori", elencoPeriodi);
	
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
		piatti1.add(model.piattoScelto(0));
		piatti1.add(model.piattoScelto(0));
		piatti1.add(model.piattoScelto(3));
		piatti1.add(model.piattoScelto(3));
		model.addMenuTematico("Festa del pesce", piatti1, 20, elencoPeriodi);;
		
		// PRENOTAZIONI
		HashMap<Piatto,Integer> comanda1 = new HashMap<Piatto,Integer>();
		comanda1.put(model.piattoScelto(0), 3);
		comanda1.put(model.piattoScelto(3), 1);
		comanda1.put(model.piattoScelto(0), 1);
		Prenotazione pre1 = new Prenotazione("Prima", 1, comanda1, dataprenotazione);
		
		HashMap<Piatto,Integer> comanda2 = new HashMap<Piatto,Integer>();
		comanda2.put(model.piattoScelto(0), 2);
		comanda2.put(model.piattoScelto(3), 2);
		Prenotazione pre2 = new Prenotazione("Seconda", 3, comanda2, dataprenotazione);
		
		WriterXMLRistorante scrittore = new WriterXMLRistorante();
		System.out.println(STRINGA_INIZIO_STAMPA);
		scrittore.writeXMLRistorante(model, PATH_XML_RICETTE);
	}

}
