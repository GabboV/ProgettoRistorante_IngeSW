package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Prodotto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Float quantita;
	private String unitaMisura;
	
	public Prodotto(String nome, Float quantita, String unitaMisura) {
		this.nome = nome;
		this.quantita = quantita;
		this.unitaMisura = unitaMisura;
	}
	
	public Prodotto(String nome, Float quantita) {
		this.nome = nome;
		this.quantita = quantita;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Float getQuantita() {
		return quantita;
	}
	public void setQuantita(Float quantita) {
		this.quantita = quantita;
	}
	public String getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	
	public boolean esisteIn(ArrayList<Prodotto> ingredienti) {
		boolean esiste = false;
		for(Prodotto i : ingredienti) {
			if(i.getNome().equalsIgnoreCase(nome)) {
				esiste = true;
				break;
			}
		}
		return esiste;
	}
	
}
