package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico {
	
	private String nome;
	private ArrayList<Piatto> elencoPiatti;
	private float caricoLavoro;
	private ArrayList<Periodo> periodiValidita;
	
	
	//cosa succede se non ho un Periodo non inizializzato (se può succedere)
	//esistono menuTematici sempre validi?
	//devo imporre che date sono in ordine prima di usare questo metodo
	//posso avere un periodiValidita vuoto?
	public boolean isValidoInData(LocalDate data) {
		//prende ciascun Periodo presente in periodiValidita
		for(Periodo d : periodiValidita) {
			//se la data è compresa tra dataInizio e dataFine oppure coincide con una delle due date ritorna true
			if(d.contieneDataEstremiInclusi(data)) return true;
		}
		return false;
	}
	
	public MenuTematico(String nome, ArrayList<Piatto> elencoPiatti, float caricoLavoro,
			ArrayList<Periodo> periodiValidita) {
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
	public ArrayList<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}
	public void setPeriodiValidita(ArrayList<Periodo> periodiValidita) {
		this.periodiValidita = periodiValidita;
	}
}
