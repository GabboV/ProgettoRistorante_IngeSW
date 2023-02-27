package it.unibs.ing.progetto.ristorante.XML;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainXML {

	public final static String firstPath="src/xmlFile/Prenotazione.xml";
    private static final String STRINGA_INIZIO_STAMPA="Inizio generazione del file di output.";
	
	public static void main(String[] args) {
		
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
        scrittore.writeXMLPrenotazioni("Mario", 4, dataPrenotazione, ordine, firstPath);
		
	}

}
