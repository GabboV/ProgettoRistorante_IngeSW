package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Piatto {

	private String nomePiatto; 
	private int caricoLavoro;
	//array contentente coppie di date (dataInizio e dataFine)
	private ArrayList<DatePair> periodiValidita;
	
	
	public Piatto(String nomePiatto,  int caricoLavoro) {
		super();
		this.nomePiatto = nomePiatto;
		this.periodiValidita = new ArrayList<DatePair>();
		this.caricoLavoro = caricoLavoro;
	}

	//cosa succede se non ho un DatePair non inizializzato (se può succedere)
	//esistono piatti sempre validi?
	//devo imporre che date sono in ordine prima di usare questo metodo
	//posso avere un periodiValidita vuoto?
	public boolean isValidoInData(LocalDate data) {
		//prende ciascun DatePair presente in periodiValidita
		for(DatePair d : periodiValidita) {
			//se la data è compresa tra dataInizio e dataFine oppure coincide con una delle due date ritorna true
			if(d.contieneDataEstremiInclusi(data)) return true;
		}
		return false;
	}
	
	public void addDatePair(DatePair periodoValidita) {
		periodiValidita.add(periodoValidita);
	}
	
	
	//getters e setters
	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	public int getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(int caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}

	public ArrayList<DatePair> getPeriodiValidita() {
		return periodiValidita;
	}

	public void setPeriodiValidita(ArrayList<DatePair> periodiValidita) {
		this.periodiValidita = periodiValidita;
	}
}
