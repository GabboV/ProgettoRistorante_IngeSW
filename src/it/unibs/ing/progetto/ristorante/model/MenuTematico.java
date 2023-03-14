package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class MenuTematico implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private ArrayList<Piatto> elencoPiatti;
	private int caricoLavoro;
	private ArrayList<Periodo> periodiValidita;
	
	public MenuTematico(String nome, ArrayList<Piatto> elencoPiatti, int caricoLavoro,
			ArrayList<Periodo> periodiValidita) {
		super();
		this.nome = nome;
		this.elencoPiatti = elencoPiatti;
		this.caricoLavoro = caricoLavoro;
		this.periodiValidita = periodiValidita;
	}
	
	public boolean isValidoInData(LocalDate data) {
		//prende ciascun Periodo presente in periodiValidita
		for(Periodo d : periodiValidita) {
			//se la data è compresa tra dataInizio e dataFine oppure coincide con una delle due date ritorna true
			if(d.contieneDataEstremiInclusi(data)) return true;
		}
		return false;
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
	public int getCaricoLavoro() {
		return caricoLavoro;
	}
	public void setCaricoLavoro(int caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}
	public ArrayList<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}
	public void setPeriodiValidita(ArrayList<Periodo> periodiValidita) {
		this.periodiValidita = periodiValidita;
	}
}
