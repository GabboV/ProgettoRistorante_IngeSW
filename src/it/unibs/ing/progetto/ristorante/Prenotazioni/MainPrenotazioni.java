package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.Gestore;
import it.unibs.ing.progetto.ristorante.Piatto;
import it.unibs.ing.progetto.ristorante.Ristorante;
import it.unibs.ing.progetto.ristorante.Menu.MenuTematico;

public class MainPrenotazioni {

	public static void main(String[] args) {
		Ristorante ristoranteDB = new Ristorante();
		Gestore gestore = new Gestore(ristoranteDB);
		
		AddettoPrenotazioni addettoPrenotazioni = new AddettoPrenotazioni();
		Prenotazione prenotazione = chiediPrenotazione();
		
		System.out.println(addettoPrenotazioni.toString());

	}

	private static Prenotazione chiediPrenotazione(){
		
		String cliente = "Mario";
		int numeroCoperti = 3;
		LocalDate dataPrenotazione = null;
		float caricoLavoroPrenotazione = 100;
		
		HashMap<Piatto, Integer> comanda= new HashMap<Piatto, Integer>();
		//Piatto piatto1 = new Piatto("Pasta tonno e olive", 2);
		//Piatto piatto2 = new Piatto("Seppie in umido", 5);
		//Piatto piatto3 = new Piatto("Carote al vapore", 2);
		//Piatto piatto4 = new Piatto("Finocchi al vapore", 2);
		/*
		aggiungiPiattoInComanda(comanda, piatto1);
		aggiungiPiattoInComanda(comanda, piatto3);
		aggiungiPiattoInComanda(comanda, piatto2);
		aggiungiPiattoInComanda(comanda, piatto4);
		aggiungiPiattoInComanda(comanda, piatto1);
		aggiungiPiattoInComanda(comanda, piatto2);
		 */
		int numeroPersone = calcolaNumeroPersoneComanda(comanda);
		
		Prenotazione prenotazione = new Prenotazione(cliente, numeroCoperti, comanda, dataPrenotazione);
		
		/*
		provato con array di SingoliPiatti e MenuTematici
		
		ArrayList<Piatto> elencoPiattiSingoli = new ArrayList<Piatto>();
		ArrayList<MenuTematico> elencoMenuTematici = new ArrayList<MenuTematico>();
		
		Piatto piatto1 = new Piatto("Pasta tonno e olive", 2);
		elencoPiattiSingoli.add(piatto1);
		Piatto piatto2 = new Piatto("Seppie in umido", 5);
		
		Piatto piatto3 = new Piatto("Carote al vapore", 2);
		Piatto piatto4 = new Piatto("Finocchi al vapore", 2);
		ArrayList<Piatto> piattiMenuTematico = new ArrayList<Piatto>();
		piattiMenuTematico.add(piatto3);
		piattiMenuTematico.add(piatto4);
		
		elencoPiattiSingoli.add(piatto1);
		MenuTematico menuTematico1 = new MenuTematico(piattiMenuTematico, null, false, dataPrenotazione);
		*/
		
		return prenotazione;
	}
	
	//funzione che confronta il nuovo piatto con la HashMap della comanda attuale
	//se il piatto gia esiste nella comanda allora aumenta il value corrispondente di 1
	//se il piatto non esiste nella comanda allora lo aggiunge con value = 1;
	private static HashMap<Piatto, Integer> aggiungiPiattoInComanda(HashMap<Piatto, Integer> comanda, Piatto p){
		if (comanda.containsKey(p)) {
			int numeroOrdini = comanda.get(p);
			comanda.replace(p, numeroOrdini+1);
		} else {
			comanda.put(p, 1);
		}
		return comanda;
	}
	
	//Calcola il numero di piatti ordinati in una comanda
	private static int calcolaNumeroPersoneComanda(HashMap<Piatto, Integer> comanda){
		int somma = 0;
		for (int i : comanda.values()) {
		    somma += i;
		}
		return somma;
	}
	
}
