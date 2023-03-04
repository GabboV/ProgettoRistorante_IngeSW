package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico {
	
	private String nome;
	private ArrayList<Piatto> elencoPiatti;
	private float caricoLavoro;
	private ArrayList<DatePair> periodiValidita;
	
	
	//cosa succede se non ho un DatePair non inizializzato (se può succedere)
	//esistono menuTematici sempre validi?
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
	
	public MenuTematico(String nome, ArrayList<Piatto> elencoPiatti, float caricoLavoro,
			ArrayList<DatePair> periodiValidita) {
		super();
		this.nome = nome;
		this.elencoPiatti = elencoPiatti;
		this.caricoLavoro = caricoLavoro;
		this.periodiValidita = periodiValidita;
	}
	
	//getters e setters
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}
	public void setElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}
	public float getCaricoLavoro() {
		return caricoLavoro;
	}
	public void setCaricoLavoro(float caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}
	public ArrayList<DatePair> getPeriodiValidita() {
		return periodiValidita;
	}
	public void setPeriodiValidita(ArrayList<DatePair> periodiValidita) {
		this.periodiValidita = periodiValidita;
	}
}
