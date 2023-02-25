package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.Piatto;
import it.unibs.ing.progetto.ristorante.Menu.MenuTematico;

public class AddettoPrenotazioni {
	
	private ArrayList<Prenotazione> elencoPrenotazioni;


	public AddettoPrenotazioni() {
		super();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
	}
	
	//Riceve una data e rimuove da elencoPrenotazioni tutte le prenotazioni che sono precedenti a tale data
	//	(da usare alla fine di getElencoPrenotazioniValidoInData oppure in altri casi?)
	public void removePrenotazioniScadute() {
		
	}
	
	//serve un metodo per input parametri prenotazione
	public void creaPrenotazione(String nomeCliente, int nCoperti, LocalDate dataPrenotazione, ArrayList<String> ordine) {
		
		//devo creare all'inizio un MenuSpecialiInData e MenuCartaInData poi controllare in quali metodi servono
		
		boolean valido = true;
		Prenotazione p = null;
		//messo fuori dall'IF così poi posso creare menuSpecialiOrdinati e piattiSingoliOrdinati corretti e usarli per i successivi controlli
		//controllo se tutti i nomi nell'ordine sono un menuTematicoValido o un piattoValido
		valido = controlloEsistenzaElemOrdine(ordine, dataPrenotazione);
		if(valido) {
			//creo un ArrayList<MenuTematico> contenente tutti i menu tematici presenti nell'ordine
			ArrayList<MenuTematico> menuSpecialiOrdinati = selezionaMenuSpeciali(ordine, dataPrenotazione);
			//creo un ArrayList<Piatto> contenente tutti i piatti singoli presenti nell'ordine
			ArrayList<Piatto> piattiSingoliOrdinati = selezionaPiattiSingoli(ordine, dataPrenotazione);
			//eseguo dei controlli sui parametri della prenotazione
			valido = controllaPrenotazione(nomeCliente, nCoperti, dataPrenotazione, menuSpecialiOrdinati, piattiSingoliOrdinati);
			//creo una HashMap<Piatto, Integer> in cui metto ogni piatto (anche appartenente a un MenuTematico) presente nell'ordine e il corrispondente value quantità
			HashMap<Piatto, Integer> comanda = generaComanda(menuSpecialiOrdinati, piattiSingoliOrdinati);
			
			//creo l'istanza prenotazione
			p = new Prenotazione(nomeCliente, nCoperti, comanda, dataPrenotazione);
		}
		
		//se la prenotazione ha superato i controlli, la aggiunge a elencoPrenotazioni
		boolean prenotazioneConfermata = aggiungiPrenotazione(p, valido);
		//stampa il msg finale di conferma prenotazione o rifiuto
		stampaMsgConfermaPrenotazione(p, prenotazioneConfermata);
		
		//devo aggiornare nPostiLiberiInData e caricoLavoroRistoranteInData (con metodo di gestore?)	!!!
	}
	
	public boolean controlloEsistenzaElemOrdine(ArrayList<String> ordine, LocalDate dataPrenotazione) {
		//genero elencoMenuSpecialiInData e menuCartaInData
		//controllo ogni elem se presente (corrispondenza nome) in uno dei due elenchi
		//	ELSE return false
		return true;
	}
	
	//controllo se il nome nell'ordine corrisponde al nome di un menuTematico valido nella data
	public ArrayList<MenuTematico> selezionaMenuSpeciali(ArrayList<String> ordine, LocalDate dataPrenotazione){
		ArrayList<MenuTematico> elencoMenuSpecialiOrdinati = new ArrayList<MenuTematico>();
		//genero elencoMenuSpecialiInData con metodo da Database
		//se nome nell'ordine corrisponde a uno nel MenuSpecialeInData
		//	THEN creo una nuova istanza e lo aggiungo a elencoMenuSpecialiOrdinati
		return elencoMenuSpecialiOrdinati;
	}
	
	//controllo se il nome nell'ordine corrisponde al nome di un piatto valido nella data
	public ArrayList<Piatto> selezionaPiattiSingoli(ArrayList<String> ordine, LocalDate dataPrenotazione){
		ArrayList<Piatto> elencoPiattiSingoliOrdinati = new ArrayList<Piatto>();
		//genero menuCartaInData con metodo da Database
		//se nome nell'ordine corrisponde a uno nel menuCartaInData
		//	THEN creo una nuova istanza e lo aggiungo a elencoPiattiSingoliOrdinati
		return elencoPiattiSingoliOrdinati;
	}
	
	//controlla se il numeroCoperti della prenotazione è < del numero di posti liberi in una certa data
	public boolean controlloPostiLiberiInData(int nCoperti, LocalDate dataPrenotazione) {
		//controllo se il nPostiLiberiInData >= nCoperti THEN return true
		//ELSE return false
		return false;
	}
	
	//controlla se la somma di counterMenuSpecialiOrdinati e counterPiattiSingoliOrdinati è rispetta i vincoli:
	//counterPiattiSingoliOrdinati + counterMenuSpecialiOrdinati >= numeroCoperti
	//counterMenuSpecialiOrdinati <= numeroCoperti
	public boolean controlloNumeroPersoneValido(int nCoperti, ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {
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
	//prende ogni piatto o menuTematico, trova il suo carico di lavoro e fa la sommatoria e controlla se è < caricoLavoroRistorante per una data
	public boolean controlloCaricoLavoroPrenotazione(ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati, LocalDate dataPrenotazione) {
		//da implementare
		return true;
	}
	
	//meglio boolean per la stampa del msg?
	public boolean controllaPrenotazione(String nomeCliente, int nCoperti, LocalDate dataPrenotazione, 
								ArrayList<MenuTematico> menuSpecialiOrdinati, ArrayList<Piatto> piattiSingoliOrdinati) {
		
		boolean postiLiberiValido = controlloPostiLiberiInData(nCoperti, dataPrenotazione);
		if (postiLiberiValido == false) {
			//stampa msg
			return false;
		}
		
		boolean nPersoneValido = controlloNumeroPersoneValido(nCoperti, menuSpecialiOrdinati, piattiSingoliOrdinati);
		if (nPersoneValido == false) {
			//stampa msg
			return false;
		}
		
		boolean caricoLavoroPrenotazioneValido = controlloCaricoLavoroPrenotazione(menuSpecialiOrdinati, piattiSingoliOrdinati, dataPrenotazione);
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
	public HashMap<Piatto, Integer> getComandaInData(LocalDate data) {
		HashMap<Piatto, Integer> comandaInData = new HashMap<Piatto, Integer>();
		//da implementare
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
	
	
	//Se chiedo prenotazioni un piatto alla volta ci sono troppe complicazioni nell'aggiornare valori e controllare validita (troppe alternative)
	//Per esempio se caricoLavoroRistorante quasi raggiunto, devo sperare che cliente chiede piatto "leggero" e continuare a richiedere, o dargli delle
	//	opzioni, o eliminare la prenotazione?
	//Devo raccogliere prenotazioni come elecoPiattiSingoliOrdinati e elencoMenuSpecialiOrdinati
}
