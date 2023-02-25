package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.Piatto;
import it.unibs.ing.progetto.ristorante.Menu.MenuTematico;

public class AddettoPrenotazioni {
	
	private ArrayList<Prenotazione> elencoPrenotazioni;


	public AddettoPrenotazioni() {
		super();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
	}
	
	public void addPrenotazione(Prenotazione p) {
		this.elencoPrenotazioni.add(p);
	}
	
	//Riceve una data e rimuove da elencoPrenotazioni tutte le prenotazioni che sono precedenti a tale data
	public void removePrenotazioniScadute() {
		
	}
	
	//serve un metodo per input parametri prenotazione
	public void creaPrenotazione(String nomeCliente, int nCoperti, Date dataPrenotazione, 
								ArrayList<String> ordine) {
		
		//potrei creare all'inizio un MenuSpecialiInData e MenuCartaInData poi controllare in quali metodi servono
		boolean valido = true;
		Prenotazione p = null;
		//messo fuori dall'IF così poi posso creare menuSpecialiOrdinati e piattiSingoliOrdinati corretti
		//	e usarli per i successivi controlli
		//altre soluzioni?
		valido = controlloEsistenzaElemOrdine(ordine, dataPrenotazione);
		if(valido) {
			ArrayList<MenuTematico> menuSpecialiOrdinati = selezionaMenuSpeciali(ordine, dataPrenotazione);
			ArrayList<Piatto> piattiSingoliOrdinati = selezionaPiattiSingoli(ordine, dataPrenotazione);
			valido = controllaPrenotazione(nomeCliente, nCoperti, dataPrenotazione, menuSpecialiOrdinati, piattiSingoliOrdinati);
			HashMap<Piatto, Integer> comanda = generaComanda(menuSpecialiOrdinati, piattiSingoliOrdinati);
			
			p = new Prenotazione(nomeCliente, nCoperti, comanda, dataPrenotazione);
		}
		
		aggiungiPrenotazione(p, valido);
		stampaMsgConfermaPrenotazione(p, valido);
	}
	
	public boolean controlloEsistenzaElemOrdine(ArrayList<String> ordine, Date dataPrenotazione) {
		//genero elencoMenuSpecialiInData e menuCartaInData
		//controllo ogni elem se presente (corrispondenza nome) in uno dei due elenchi
		//	ELSE return false
		return true;
	}
	
	//controllo se il nome nell'ordine corrisponde al nome di un menuTematico valido nella data
	public ArrayList<MenuTematico> selezionaMenuSpeciali(ArrayList<String> ordine, Date dataPrenotazione){
		ArrayList<MenuTematico> elencoMenuSpecialiOrdinati = new ArrayList<MenuTematico>();
		//genero elencoMenuSpecialiInData con metodo da Database
		//se nome nell'ordine corrisponde a uno nel MenuSpecialeInData
		//	THEN creo una nuova istanza e lo aggiungo a elencoMenuSpecialiOrdinati
		return elencoMenuSpecialiOrdinati;
	}
	
	//controllo se il nome nell'ordine corrisponde al nome di un piatto valido nella data
	public ArrayList<Piatto> selezionaPiattiSingoli(ArrayList<String> ordine, Date dataPrenotazione){
		ArrayList<Piatto> elencoPiattiSingoliOrdinati = new ArrayList<Piatto>();
		//genero menuCartaInData con metodo da Database
		//se nome nell'ordine corrisponde a uno nel menuCartaInData
		//	THEN creo una nuova istanza e lo aggiungo a elencoPiattiSingoliOrdinati
		return elencoPiattiSingoliOrdinati;
	}
	
	public boolean controlloPostiLiberiInData(int nCoperti, Date dataPrenotazione) {
		//controllo se il nPostiLiberiInData >= nCoperti THEN return true
		//ELSE return false
		return false;
	}
	
	public boolean controlloNumeroPersonoValido(int nCoperti, ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {
		//conto menuTematiciOrdinati e PiattiSingoliOrdinati
		int counterMenuSpecialiOrdinati = menuSpecialiOrdinati.size();
		int counterPiattiSingoliOrdinati = piattiSingoliOrdinati.size();
				
		//confronto nCoperti e counterMenuSpeciali e counterPiattiSingoli
		//IF (counterPiattiSingoli + counterMenuSpeciali) < numeroCoperti THEN return false
		//IF counterMenuTematici > numeroCoperti THEN return false
		//ELSE return true
		return true;
	}
	
	//ha bisogno di altri parametri come corrispondenza Piatto-Ricetta
	//metodo LUNGO perchè
	//prende ogni piatto e trova il suo carico di lavoro
	//fa la somma tot e controlla se < caricoLavoroRistorante
	public boolean controlloCaricoLavoroPrenotazione(ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {
		//da implementare
		return true;
	}
	
	//meglio boolean per la stampa del msg?
	public boolean controllaPrenotazione(String nomeCliente, int nCoperti, Date dataPrenotazione, 
								ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {
		
		boolean postiLiberiValido = controlloPostiLiberiInData(nCoperti, dataPrenotazione);
		if (postiLiberiValido == false) {
			//stampa msg
			return false;
		}
		
		boolean nPersoneValido = controlloNumeroPersonoValido(nCoperti, menuSpecialiOrdinati, piattiSingoliOrdinati);
		if (nPersoneValido == false) {
			//stampa msg
			return false;
		}
		
		boolean caricoLavoroPrenotazioneValido = controlloCaricoLavoroPrenotazione(menuSpecialiOrdinati, piattiSingoliOrdinati);
		if (caricoLavoroPrenotazioneValido == false) {
			//stampa msg
			return false;
		}
		
		return true;
	}
	
	//forse meglio con metodo che da ArrayList<MenuTematico> e ArrayList<Piatto> ritorna una ArrayList<Piatto> contenente tutti i piatti?
	public HashMap<Piatto, Integer> generaComanda(ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati){
		HashMap<Piatto, Integer> comanda = new HashMap<Piatto, Integer>();
		//aggiungo alla comanda ogni piatto e la quantità
		return comanda;
	}
	
	
	public boolean aggiungiPrenotazione(Prenotazione p, boolean prenotazioneValida) {
		boolean prenotazioneConfermata = false;
		if(prenotazioneValida == true) {
			prenotazioneConfermata = true;
			elencoPrenotazioni.add(p);
		}
		return prenotazioneConfermata;
	}
	
	//stampa msg per dire se la prenotazione è stata aggiunta o rifiutata
	//utile per testing
	public void stampaMsgConfermaPrenotazione(Prenotazione p, boolean prenotazioneConfermata) {
		if(prenotazioneConfermata) {
			//print msg conferma con nome e data
		}
	}
	
	//metodo utile per il magazziniere
	//prende tutte le prenotazioni di una data e ritorna una HashMap<Piatto, Integer> contenente tutti i piatti ordinati
	public HashMap<Piatto, Integer> getComandaInData(Date data) {
		HashMap<Piatto, Integer> comandaInData = new HashMap<Piatto, Integer>();
		return comandaInData;
	}
	
	
	@Override
	public String toString() {
		String s = "";
		for (Prenotazione p : elencoPrenotazioni) {
			s = s.concat(p.toString());
		}
		return "AddettoPrenotazioni\n\n" + s;
	}
}
