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
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class MainXML {

	public final static String PATH_XML_RISTORANTE = "src/xmlFile/Ristorante.xml";
	private static final String STRINGA_INIZIO_STAMPA = "Inizio generazione del file di output.";

	public static void main(String[] args) {
		// DATI DI PROVA (DA USARE IN XML)
		// PARAMETRI
		Ristorante model = new Ristorante();
		model.setNumeroPostiASedere(50);
		model.setCaricoLavoroPerPersona(20);
		model.setCaricoLavoroRistorante((int) (20 * 50 + 20 * 50 * 0.2));
		model.setDataCorrente(LocalDate.of(2023, 1, 1));
		LocalDate dataprenotazione = LocalDate.of(2023, 5, 1);

		LocalDate inizioValidita = LocalDate.of(2023, 01, 1);
		LocalDate fineValidita = LocalDate.of(2023, 01, 31);
		Periodo periodo = new Periodo(inizioValidita, fineValidita);
		ArrayList<Periodo> elencoPeriodi = new ArrayList<>();
		elencoPeriodi.add(periodo);

		// PIATTO-RICETTA 0
		Prodotto i1piatto0 = new Prodotto("Pasta", 0.3f, UnitaMisura.KG);
		Prodotto i2piatto0 = new Prodotto("Tonno", 0.1f, UnitaMisura.KG);
		Prodotto i3piatto0 = new Prodotto("Sugo", 0.08f, UnitaMisura.KG);
		Prodotto i4piatto0 = new Prodotto("Olio", 0.02f, UnitaMisura.LITRI);
		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
		ingredienti0.add(i1piatto0);
		ingredienti0.add(i2piatto0);
		ingredienti0.add(i3piatto0);
		ingredienti0.add(i4piatto0);
		model.addPiattoRicetta(ingredienti0, 3, 4, "Pasta al tonno", elencoPeriodi);

		// PIATTO-RICETTA 1
		Prodotto i1piatto1 = new Prodotto("Pollo", 1f, UnitaMisura.KG);
		Prodotto i2piatto1 = new Prodotto("Curry", 0.1f, UnitaMisura.KG);
		Prodotto i3piatto1 = new Prodotto("Olio", 0.02f, UnitaMisura.LITRI);
		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
		ingredienti1.add(i1piatto1);
		ingredienti1.add(i2piatto1);
		ingredienti1.add(i3piatto1);
		model.addPiattoRicetta(ingredienti1, 3, 4, "Pollo halal al curry", elencoPeriodi);

		// PIATTO-RICETTA 2
		Prodotto i1piatto2 = new Prodotto("Riso", 0.3f, UnitaMisura.KG);
		Prodotto i2piatto2 = new Prodotto("Zafferano", 0.01f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti2 = new ArrayList<>();
		ingredienti2.add(i1piatto2);
		ingredienti2.add(i2piatto2);
		model.addPiattoRicetta(ingredienti2, 2, 4, "Risotto allo zafferano", elencoPeriodi);

		// PIATTO-RICETTA 3
		Prodotto i1piatto3 = new Prodotto("Alga Nori", 3f, UnitaMisura.UNITA);
		Prodotto i2piatto3 = new Prodotto("Tonno", 0.1f, UnitaMisura.KG);
		Prodotto i3piatto3 = new Prodotto("Salsa", 0.08f, UnitaMisura.LITRI);
		Prodotto i4piatto3 = new Prodotto("Riso", 0.2f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti3 = new ArrayList<>();
		ingredienti3.add(i1piatto3);
		ingredienti3.add(i2piatto3);
		ingredienti3.add(i3piatto3);
		ingredienti3.add(i4piatto3);
		model.addPiattoRicetta(ingredienti3, 3, 7, "Alga Nori", elencoPeriodi);

		// PIATTO-RICETTA 4
		Prodotto i1piatto4 = new Prodotto("Pasta", 4f, UnitaMisura.KG);
		Prodotto i2piatto4 = new Prodotto("Pancetta", 500f, UnitaMisura.KG);
		Prodotto i3piatto4 = new Prodotto("Uova", 8f, UnitaMisura.UNITA);
		Prodotto i4piatto4 = new Prodotto("Sale", 0.02f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti4 = new ArrayList<>();
		ingredienti4.add(i1piatto4);
		ingredienti4.add(i2piatto4);
		ingredienti4.add(i3piatto4);
		ingredienti4.add(i4piatto4);
		model.addPiattoRicetta(ingredienti4, 4, 6, "Pasta alla carbonara", elencoPeriodi);

		// PIATTO-RICETTA 5
		Prodotto i1piatto5 = new Prodotto("Riso", 0.3f, UnitaMisura.KG);
		Prodotto i2piatto5 = new Prodotto("Funghi", 0.05f, UnitaMisura.KG);
		Prodotto i3piatto5 = new Prodotto("Olio", 0.02f, UnitaMisura.LITRI);
		Prodotto i4piatto5 = new Prodotto("Sale", 0.03f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti5 = new ArrayList<>();
		ingredienti5.add(i1piatto5);
		ingredienti5.add(i2piatto5);
		ingredienti5.add(i3piatto5);
		ingredienti5.add(i4piatto5);
		model.addPiattoRicetta(ingredienti5, 4, 4, "Risotto ai funghi", elencoPeriodi);

		// PIATTO-RICETTA 6
		Prodotto i1piatto6 = new Prodotto("Carne di manzo", 0.3f, UnitaMisura.KG);
		Prodotto i2piatto6 = new Prodotto("Piselli", 0.1f, UnitaMisura.KG);
		Prodotto i3piatto6 = new Prodotto("Olio", 0.02f, UnitaMisura.LITRI);
		Prodotto i4piatto6 = new Prodotto("Sale", 0.02f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti6 = new ArrayList<>();
		ingredienti6.add(i1piatto6);
		ingredienti6.add(i2piatto6);
		ingredienti6.add(i3piatto6);
		ingredienti6.add(i4piatto6);
		model.addPiattoRicetta(ingredienti6, 1, 7, "Spezzatino di manzo con piselli", elencoPeriodi);

		// PIATTO-RICETTA 7
		Prodotto i1piatto7 = new Prodotto("Salsicce", 1f, UnitaMisura.KG);
		Prodotto i2piatto7 = new Prodotto("Patate", 0.8f, UnitaMisura.KG);
		Prodotto i3piatto7 = new Prodotto("Salsa", 0.04f, UnitaMisura.LITRI);
		Prodotto i4piatto7 = new Prodotto("Sale", 0.02f, UnitaMisura.KG);
		ArrayList<Prodotto> ingredienti7 = new ArrayList<>();
		ingredienti7.add(i1piatto7);
		ingredienti7.add(i2piatto7);
		ingredienti7.add(i3piatto7);
		ingredienti7.add(i4piatto7);
		model.addPiattoRicetta(ingredienti7, 4, 5, "Salsiccia con patate", elencoPeriodi);

		// BEVANDE
		Prodotto b1 = new Prodotto("Acqua", 1f, UnitaMisura.LITRI);
		Prodotto b2 = new Prodotto("Birra", 0.5f, UnitaMisura.LITRI);
		Prodotto b3 = new Prodotto("Vino", 0.2f, UnitaMisura.LITRI);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b1);
		bevande.add(b2);
		bevande.add(b3);
		model.setInsiemeBevande(bevande);

		// GENERI EXTRA
		Prodotto g1 = new Prodotto("Pane", 0.3f, UnitaMisura.HG);
		Prodotto g2 = new Prodotto("Grissini", 0.1f, UnitaMisura.HG);
		Prodotto g3 = new Prodotto("Bruschette", 0.2f, UnitaMisura.HG);
		ArrayList<Prodotto> generiExtra = new ArrayList<>();
		generiExtra.add(g1);
		generiExtra.add(g2);
		generiExtra.add(g3);
		model.setInsiemeGeneriExtra(generiExtra);

		// MENU TEMATICO 0
		ArrayList<Piatto> piatti0 = new ArrayList<>();
		piatti0.add(model.piattoScelto(0));
		piatti0.add(model.piattoScelto(0));
		piatti0.add(model.piattoScelto(3));
		piatti0.add(model.piattoScelto(3));
		model.addMenuTematico("Festa del pesce", piatti0, 20, elencoPeriodi);

		// MENU TEMATICO 1
		ArrayList<Piatto> piatti1 = new ArrayList<>();
		piatti1.add(model.piattoScelto(0));
		piatti1.add(model.piattoScelto(1));
		piatti1.add(model.piattoScelto(7));
		model.addMenuTematico("Lunedi in Uni", piatti1, 15, elencoPeriodi);

		// MENU TEMATICO 2
		ArrayList<Piatto> piatti2 = new ArrayList<>();
		piatti2.add(model.piattoScelto(4));
		piatti2.add(model.piattoScelto(5));
		piatti2.add(model.piattoScelto(6));
		model.addMenuTematico("Comincia il weekend", piatti2, 17, elencoPeriodi);

		// PRENOTAZIONE1
		HashMap<Piatto, Integer> comanda1 = new HashMap<Piatto, Integer>();
		comanda1.put(model.piattoScelto(0), 3);
		comanda1.put(model.piattoScelto(3), 1);
		comanda1.put(model.piattoScelto(0), 1);
		Prenotazione pre1 = new Prenotazione(1, comanda1, dataprenotazione);

		// PRENOTAZIONE2
		HashMap<Piatto, Integer> comanda2 = new HashMap<Piatto, Integer>();
		comanda2.put(model.piattoScelto(0), 2);
		comanda2.put(model.piattoScelto(3), 2);
		Prenotazione pre2 = new Prenotazione(3, comanda2, dataprenotazione);

		WriterXMLRistorante scrittore = new WriterXMLRistorante();
		System.out.println(STRINGA_INIZIO_STAMPA);
		scrittore.writeXMLRistorante(model, PATH_XML_RISTORANTE);
	}

}
