package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.DataBase;
import it.unibs.ing.progetto.ristorante.Gestore;
import it.unibs.ing.progetto.ristorante.Piatto;
import it.unibs.ing.progetto.ristorante.Menu.MenuTematico;

public class MainPrenotazioni {

	public static void main(String[] args) {
		DataBase ristoranteDB = new DataBase();
		Gestore gestore = new Gestore("administrator", ristoranteDB);
		
		gestore.setPostiASedere(50);
		gestore.inizializzaWorkload(300);
		
		AddettoPrenotazioni addettoPrenotazioni = new AddettoPrenotazioni();
		Prenotazione prenotazione = chiediPrenotazione();
		addettoPrenotazioni.addPrenotazione(prenotazione);
		
		System.out.println(addettoPrenotazioni.toString());

	}

	private static Prenotazione chiediPrenotazione(){
		
		//ricevi Prenotazione
		//salva nomeCliente
		//salva numeroCoperti
		//salva dataPrenotazione
		//controllo posti a sedere rimasti liberi nella dataPrenotazione
		//creo menuAllaCarta e elencoMenuTematiciValidi della data (serve metodo dal DataBase)
		
		//conto MenuTematici e PiattiSingoli
		//confronto numeroCoperti e counterMenuTematici e counterPiattiSingoli
		//IF (counterPiattiSingoli + counterMenuTematici) < numeroCoperti THEN prenotazione NON VALIDA (+messaggioMotivo)
		//IF counterMenuTematici > numeroCoperti THEN prenotazione NON VALIDA (+messaggioMotivo)
		
		//crea HashMap<Piatto, Integer> vuota
		//prendi un piatto/menuTematico alla volta
		//IF nome del piatto/menuTematico non esiste nel menuRistoranteValido THEN prenotazione NON VALIDA (+messaggioMotivo)
		//aggiorno valore caricoLavoroPrenotazione (devo stare attento a questione di ricetta-porzione-caricoLavoroPerPorzione?) !!!
		//IF caricoLavoroPrenotazione > caricoLavoroRistorante THEN prenotazione NON VALIDA (+messaggioMotivo)
		//creo Piatto o elenco di piatti di un menu tematico e aggiungo in HashMap<Piatto, Integer>
		
		//creo prenotazione e aggiungo all'elencoPrenotazioni
		//aggiorno valore postiLiberiRistorante e caricoLavoroRistorante di una certa data
		
		
		//serve metodo in AddettoPrenotazioni di getElencoPrenotazioniValideInData utile al Magazziniere per calcolare la ListaDellaSpesa 
		//	(bastano le HashMap<Piatto, Integer> oppure una sola HashMap<Piatto, Integer> che le comprende tutte?)
		//serve metodo in AddettoPrenotazioni di removePrenotazioniScadute (da usare alla fine di getElencoPrenotazioniValidoInData oppure in altri casi?)
		//serve metodo per calcolare il caricoLavoro tenendo conto di piatti gia ordinati? !!!

		
		//Se chiedo prenotazioni un piatto alla volta ci sono troppe complicazioni nell'aggiornare valori e controllare validita (troppe alternative)
		//Per esempio se caricoLavoroRistorante quasi raggiunto, devo sperare che cliente chiede piatto "leggero" e continuare a richiedere, o dargli delle
		//	opzioni, o eliminare la prenotazione?
		//Devo raccogliere prenotazioni come elecoPiattiSingoliPrenotati e elencoMenuTematiciPrenotati
		
		String cliente = "Mario";
		int numeroCoperti = 3;
		String dataPrenotazione = "20/03/2023";
		float caricoLavoroPrenotazione = 100;
		
		HashMap<Piatto, Integer> comanda= new HashMap<Piatto, Integer>();
		Piatto piatto1 = new Piatto("Pasta tonno e olive", 2);
		Piatto piatto2 = new Piatto("Seppie in umido", 5);
		Piatto piatto3 = new Piatto("Carote al vapore", 2);
		Piatto piatto4 = new Piatto("Finocchi al vapore", 2);
		
		aggiungiPiattoInComanda(comanda, piatto1);
		aggiungiPiattoInComanda(comanda, piatto3);
		aggiungiPiattoInComanda(comanda, piatto2);
		aggiungiPiattoInComanda(comanda, piatto4);
		aggiungiPiattoInComanda(comanda, piatto1);
		aggiungiPiattoInComanda(comanda, piatto2);

		int numeroPersone = calcolaNumeroPersoneComanda(comanda);
		
		Prenotazione prenotazione = new Prenotazione(cliente, numeroCoperti, comanda, numeroPersone, dataPrenotazione, caricoLavoroPrenotazione);
		
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
