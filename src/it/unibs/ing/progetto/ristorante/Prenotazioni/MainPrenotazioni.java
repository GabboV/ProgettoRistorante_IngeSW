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
		
		//chiedi codiceCliente
		//chiedi codicePrenotazione
		//chiedi numeroCoperti e controllo posti a sedere rimasti liberi
		//chiedi dataPrenotazione
		//creo ArrayList<Piatto>
		//creo ArrayList<MenuTematico>
		//chiedi piatto/menuTematico
		//controllo esistenza piatto/menuTematico nel menuRistoranteValido per quella data
		//confronto numeroCoperti e counterMenuTematici e counterPiattiSingoli
		//if (counterMenuTematici == numeroCoperti) && (counterPiattiSingoli == 0) then confermo la prenotazione
		//if (counterMenuTematici == (numeroCoperti - 1)) && (counterPiattiSingoli == 0) then posso chiedere solo piatti singoli
		//if (counterMenuTematici == (numeroCoperti - 1)) && (counterPiattiSingoli > 1) then posso chiedere solo piatti singoli o conferma della prenotazione
		//if (counterPiattiSingoli + counterMenuTematici) < numeroCoperti then chiedo piatto/menuTematico
		//else chiedi piatto/menuTematico o conferma della prenotazione
		//costruisci HashMap<Piatto, Integer> da elenco di piattiSingoli e MenuTematici
		//calcola numeroPersone tenendone conto dopo l'aggiunta di ogni piatto o menuTematico
		//calcola caricoLavoroPrenotazione tenendone conto dopo l'aggiunta di ogni piatto o menuTematico
		//if caricoLavoroPrenotazione > caricoLavoroRistorante then rifai prenotazione (o rimuovi l'ultimo piatto, con attenzione alle conseguenze)
		//crea prenotazione e aggiungi all'elencoPrenotazioni
		
		//posso chiedere prenotazione un piatto alla volta 
		//oppure con un eleco di piatti, confermando l'ordine alla fine se rispetta tutti i vincoli
		
		String codiceCliente = "Mario";
		Integer numeroCoperti = 3;
		Cliente cliente = new Cliente(codiceCliente, numeroCoperti);
		String codicePrenotazione = "001";
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
		
		Prenotazione prenotazione = new Prenotazione(cliente, codicePrenotazione, numeroPersone, dataPrenotazione, caricoLavoroPrenotazione, comanda);
		
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
