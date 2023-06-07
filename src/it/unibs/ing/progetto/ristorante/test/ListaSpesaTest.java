package it.unibs.ing.progetto.ristorante.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import it.unibs.ing.progetto.ristorante.model.Magazzino;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class ListaSpesaTest {
	private Ristorante model;
	private LocalDate dataprenotazione;
	private LocalDate inizioValidita;
	private LocalDate fineValidita;
	private Periodo periodo;
	private ArrayList<Periodo> elencoPeriodi;
	float delta = 0.0001f;
	int caricoLavoroFisso = 1;
	int porzioneBevandaFissa = 1;
	int porzioneGenereFissa = 1;
    
	@Before
    public void inizializzaRistoranteTest() {
    	model = new Ristorante();
    	model.setDataCorrente(LocalDate.of(2023, 1, 1));
        dataprenotazione = LocalDate.of(2023, 1, 1);
        inizioValidita = LocalDate.of(2023, 1, 1);
        fineValidita = LocalDate.of(2023, 1, 31);
        periodo = new Periodo(inizioValidita, fineValidita);
        elencoPeriodi = new ArrayList<>();
        elencoPeriodi.add(periodo);
    }
    
	// calcola la quantita' che dovrebbe essere presente nella lista della spesa
	// considera anche il numeroOrdini di un certo piatto contenente l'ingrediente
	// considera anche il numero di porzioni del piatto
    private float maggiorazionePercentuale(float quantita, int numeroOrdini, int porzioni) {
        return (float) (quantita * (1 + Magazzino.PERCENTUALE_MAGGIORAZIONE_PRODOTTI/100.0)  * numeroOrdini / porzioni);
    }
    
    @Test
    public void testGeneraListaSpesaNoPrenotazioniNoBevandeNoGeneri() {
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertTrue(listaSpesa.isEmpty());
    }
    
    @Test
    public void testGeneraListaSpesaNoPrenotazioniSiBevandeNoGeneri() {
		// BEVANDE
		Prodotto b0 = new Prodotto("Bevanda0", 1f, UnitaMisura.L);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b0);
		model.setInsiemeBevande(bevande);
    	
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertTrue(listaSpesa.isEmpty());
    }
    
    @Test
    public void testGeneraListaSpesa1PiattoNoBevandeNoGeneri() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioni = 1;
 		model.addPiattoRicetta(ingredienti0, porzioni, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		comanda0.put(model.piattoScelto(0), 1);
		int numClienti = 1;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, numClienti);
 		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(1, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValue = maggiorazionePercentuale(i0.getQuantita(), numClienti, porzioni);
        Assertions.assertEquals(expectedValue, listaSpesa.get(0).getQuantita(), delta);

    }
    
    @Test
    public void testGeneraListaSpesa1PiattoSiBevandeNoGeneri() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioni = 1;
 		model.addPiattoRicetta(ingredienti0, porzioni, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		comanda0.put(model.piattoScelto(0), 1);
		int numClienti = 1;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, numClienti);
 		
		// BEVANDE
		Prodotto b0 = new Prodotto("Bevanda0", 1f, UnitaMisura.L);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b0);
		model.setInsiemeBevande(bevande);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(2, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numClienti, porzioni);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);

        Assertions.assertEquals("Bevanda0", listaSpesa.get(1).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(1).getUnitaMisura());
        float expectedValueBevanda0 = maggiorazionePercentuale(b0.getQuantita(), numClienti, porzioni);
        Assertions.assertEquals(expectedValueBevanda0, listaSpesa.get(1).getQuantita(), delta);

    }
    
    @Test
    public void testGeneraListaSpesa1PiattoSiBevandeSiGeneri() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioniPiatto0 = 1;
 		model.addPiattoRicetta(ingredienti0, porzioniPiatto0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		comanda0.put(model.piattoScelto(0), 1);
		int numClienti = 1;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, numClienti);
 		
		// BEVANDE
		Prodotto b0 = new Prodotto("Bevanda0", 1f, UnitaMisura.L);
		Prodotto b1 = new Prodotto("Bevanda1", 1f, UnitaMisura.L);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b0);
		bevande.add(b1);
		model.setInsiemeBevande(bevande);
		
		// GENERI EXTRA
		Prodotto g0 = new Prodotto("Genere0", 2f, UnitaMisura.HG);
		ArrayList<Prodotto> generiExtra = new ArrayList<>();
		generiExtra.add(g0);
		model.setInsiemeGeneriExtra(generiExtra);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(4, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numClienti, porzioniPiatto0);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);

        Assertions.assertEquals("Bevanda0", listaSpesa.get(1).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(1).getUnitaMisura());
        float expectedValueBevanda0 = maggiorazionePercentuale(b0.getQuantita(), numClienti, 1);
        Assertions.assertEquals(expectedValueBevanda0, listaSpesa.get(1).getQuantita(), delta);

        Assertions.assertEquals("Genere0", listaSpesa.get(3).getNome());
        Assertions.assertEquals(UnitaMisura.HG, listaSpesa.get(3).getUnitaMisura());
        float expectedValueGenere0 = maggiorazionePercentuale(g0.getQuantita(), numClienti, 1);
        Assertions.assertEquals(expectedValueGenere0, listaSpesa.get(3).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesa2PiattiUguali() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		model.addPiattoRicetta(ingredienti0, 1, 1, "Piatto0", elencoPeriodi);
    	
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		comanda0.put(model.piattoScelto(0), 2);
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, 1);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(1, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        int porzioni = 1;
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), 2, porzioni);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesa2PiattiDiversiSenzaIngredientiInComune() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		model.addPiattoRicetta(ingredienti0, 1, 1, "Piatto0", elencoPeriodi);
 		
    	// PIATTO-RICETTA 1
 		Prodotto i1 = new Prodotto("Prodotto1", 2f, UnitaMisura.L);
 		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
 		ingredienti1.add(i1);
 		model.addPiattoRicetta(ingredienti1, 1, 1, "Piatto1", elencoPeriodi);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		comanda0.put(model.piattoScelto(0), 1);
		comanda0.put(model.piattoScelto(1), 1);
		int numClienti = 1;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, numClienti);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(2, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        int porzioni = 1;
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numClienti, porzioni);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);

        Assertions.assertEquals("Prodotto1", listaSpesa.get(1).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(1).getUnitaMisura());
        float expectedValueProdotto1 = maggiorazionePercentuale(i1.getQuantita(), numClienti, porzioni);
        Assertions.assertEquals(expectedValueProdotto1, listaSpesa.get(1).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesa2PiattiDiversiConIngredientiInComune() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioniPiatto0 = 1;
 		model.addPiattoRicetta(ingredienti0, porzioniPiatto0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
 		
    	// PIATTO-RICETTA 1	
 		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
 		ingredienti1.add(i0);
 		int porzioniPiatto1 = 1;
 		model.addPiattoRicetta(ingredienti1, porzioniPiatto1, caricoLavoroFisso, "Piatto1", elencoPeriodi);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		int numOrdiniPiatto0 = 1;
		int numOrdiniPiatto1 = 1;
		comanda0.put(model.piattoScelto(0), numOrdiniPiatto0);
		comanda0.put(model.piattoScelto(1), numOrdiniPiatto1);
		int numClienti = 1;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, numClienti);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(1, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numOrdiniPiatto0, porzioniPiatto0)
        		+ maggiorazionePercentuale(i0.getQuantita(), numOrdiniPiatto1, porzioniPiatto1);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);
    }
    
    //per correggere servirebbe differenziare due prodotti non solo da nome ma anche da unitaMisura
    @Test
    public void testGeneraListaSpesaDiversaUnitaMisura() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioniPiatto0 = 1;
 		model.addPiattoRicetta(ingredienti0, porzioniPiatto0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// PIATTO-RICETTA 1
 		Prodotto i1 = new Prodotto("Prodotto0", 1f, UnitaMisura.L);
 		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
 		ingredienti1.add(i1);
 		int porzioniPiatto1 = 1;
 		model.addPiattoRicetta(ingredienti1, porzioniPiatto1, caricoLavoroFisso, "Piatto1", elencoPeriodi);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		int numOrdiniPiatto0 = 1;
		comanda0.put(model.piattoScelto(0), numOrdiniPiatto0);
		int numOrdiniPiatto1 = 1;
		comanda0.put(model.piattoScelto(1), numOrdiniPiatto1);
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, 1);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(1, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(0).getUnitaMisura());
        //Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numOrdiniPiatto0, porzioniPiatto0)
        		+ maggiorazionePercentuale(i0.getQuantita(), numOrdiniPiatto1, porzioniPiatto1);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesaControlloPorzioniNonUnitarie() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioni0 = 3;
 		model.addPiattoRicetta(ingredienti0, porzioni0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// PIATTO-RICETTA 1
 		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
 		ingredienti1.add(i0);
 		int porzioni1 = 2;
 		model.addPiattoRicetta(ingredienti1, porzioni1, caricoLavoroFisso, "Piatto1", elencoPeriodi);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		int numeroOrdini0 = 1;
		int numeroOrdini1 = 1;
		comanda0.put(model.piattoScelto(0), numeroOrdini0);
		comanda0.put(model.piattoScelto(1), numeroOrdini1);
		model.addPrenotazione("Cliente1", dataprenotazione, comanda0, 1);
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(1, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numeroOrdini0, porzioni0) 
                + maggiorazionePercentuale(i0.getQuantita(), numeroOrdini1, porzioni1);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesaCalcoloBevandeConPiuPrenotazioni() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioni0 = 3;
 		model.addPiattoRicetta(ingredienti0, porzioni0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
    	
 		// BEVANDE
		Prodotto b0 = new Prodotto("Bevanda0", 1f, UnitaMisura.L);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b0);
		model.setInsiemeBevande(bevande);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		int numeroOrdini0 = 1;
		comanda0.put(model.piattoScelto(0), numeroOrdini0);
		int numClienti0 = 1;
		model.addPrenotazione("Cliente0", dataprenotazione, comanda0, numClienti0);
		
		// PRENOTAZIONE 1
		HashMap<Piatto, Integer> comanda1 = new HashMap<Piatto, Integer>();
		numeroOrdini0 = 1;
		comanda1.put(model.piattoScelto(0), numeroOrdini0);
		int numClienti1 = 3;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda1, numClienti1);
		
		int totClienti = numClienti0 + numClienti1;
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(2, listaSpesa.size());

        Assertions.assertEquals("Bevanda0", listaSpesa.get(1).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(1).getUnitaMisura());
        float expectedValueBevanda0 = maggiorazionePercentuale(b0.getQuantita(), totClienti, porzioneBevandaFissa);
        Assertions.assertEquals(expectedValueBevanda0, listaSpesa.get(1).getQuantita(), delta);
    }
    
    @Test
    public void testGeneraListaSpesaComplesso() {
    	// PIATTO-RICETTA 0
 		Prodotto i0 = new Prodotto("Prodotto0", 1f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti0 = new ArrayList<>();
 		ingredienti0.add(i0);
 		int porzioniPiatto0 = 2;
 		model.addPiattoRicetta(ingredienti0, porzioniPiatto0, caricoLavoroFisso, "Piatto0", elencoPeriodi);
 		
    	// PIATTO-RICETTA 1	
 		Prodotto i1 = new Prodotto("Prodotto1", 1f, UnitaMisura.L);
 		Prodotto i2 = new Prodotto("Prodotto0", 2f, UnitaMisura.KG);
 		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
 		ingredienti1.add(i1);
 		ingredienti1.add(i2);
 		int porzioniPiatto1 = 3;
 		model.addPiattoRicetta(ingredienti1, porzioniPiatto1, caricoLavoroFisso, "Piatto1", elencoPeriodi);
 		
 		// BEVANDE
		Prodotto b0 = new Prodotto("Bevanda0", 1f, UnitaMisura.L);
		ArrayList<Prodotto> bevande = new ArrayList<>();
		bevande.add(b0);
		model.setInsiemeBevande(bevande);
		
		// GENERI EXTRA
		Prodotto g0 = new Prodotto("Genere0", 2f, UnitaMisura.HG);
		ArrayList<Prodotto> generiExtra = new ArrayList<>();
		generiExtra.add(g0);
		model.setInsiemeGeneriExtra(generiExtra);
 		
 		// PRENOTAZIONE 0
		HashMap<Piatto, Integer> comanda0 = new HashMap<Piatto, Integer>();
		int numOrdiniComanda0Piatto0 = 1;
		int numOrdiniComanda0Piatto1 = 2;
		comanda0.put(model.piattoScelto(0), numOrdiniComanda0Piatto0);
		comanda0.put(model.piattoScelto(1), numOrdiniComanda0Piatto1);
		int numClienti0 = 2;
		model.addPrenotazione("Cliente0", dataprenotazione, comanda0, numClienti0);
		
		// PRENOTAZIONE 1
		HashMap<Piatto, Integer> comanda1 = new HashMap<Piatto, Integer>();
		int numOrdiniComanda1Piatto1 = 3;
		comanda1.put(model.piattoScelto(1), numOrdiniComanda1Piatto1);
		int numClienti1 = 3;
		model.addPrenotazione("Cliente1", dataprenotazione, comanda1, numClienti1);

		int totClienti = numClienti0 + numClienti1;
		
        // Esecuzione del metodo da testare
        model.generaListaSpesa();
        ArrayList<Prodotto> listaSpesa = model.getListaSpesa();
        
        // Assert di testing
        Assertions.assertFalse(listaSpesa.isEmpty());
        Assertions.assertEquals(4, listaSpesa.size());

        Assertions.assertEquals("Prodotto0", listaSpesa.get(0).getNome());
        Assertions.assertEquals(UnitaMisura.KG, listaSpesa.get(0).getUnitaMisura());
        // "Prodotto0" = 1.1 + 2.2 * 5 = 12.1
        float expectedValueProdotto0 = maggiorazionePercentuale(i0.getQuantita(), numOrdiniComanda0Piatto0, porzioniPiatto0)
            + maggiorazionePercentuale(i2.getQuantita(), numOrdiniComanda0Piatto1, porzioniPiatto1)
            + maggiorazionePercentuale(i2.getQuantita(), numOrdiniComanda1Piatto1, porzioniPiatto1);
        Assertions.assertEquals(expectedValueProdotto0, listaSpesa.get(0).getQuantita(), delta);

        Assertions.assertEquals("Prodotto1", listaSpesa.get(1).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(1).getUnitaMisura());
        // "Prodotto1" = 1.1 * 3 + 1.1 * 2 = 5.5
        float expectedValueProdotto1 = maggiorazionePercentuale(i1.getQuantita(), numOrdiniComanda0Piatto1, porzioniPiatto1)
            + maggiorazionePercentuale(i1.getQuantita(), numOrdiniComanda1Piatto1, porzioniPiatto1);
        Assertions.assertEquals(expectedValueProdotto1, listaSpesa.get(1).getQuantita(), delta);

        Assertions.assertEquals("Bevanda0", listaSpesa.get(2).getNome());
        Assertions.assertEquals(UnitaMisura.L, listaSpesa.get(2).getUnitaMisura());
        float expectedValueBevanda0 = maggiorazionePercentuale(b0.getQuantita(), totClienti, porzioneBevandaFissa);
        Assertions.assertEquals(expectedValueBevanda0, listaSpesa.get(2).getQuantita(), delta);

        Assertions.assertEquals("Genere0", listaSpesa.get(3).getNome());
        Assertions.assertEquals(UnitaMisura.HG, listaSpesa.get(3).getUnitaMisura());
        float expectedValueGenere0 = maggiorazionePercentuale(g0.getQuantita(), totClienti, porzioneGenereFissa);
        Assertions.assertEquals(expectedValueGenere0, listaSpesa.get(3).getQuantita(), delta);
    }
}
