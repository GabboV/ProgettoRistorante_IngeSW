package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;

public class Prodotto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private Float quantita;
	private UnitaMisura unitaMis;
	
	
	public Prodotto(String nome, Float quantita, UnitaMisura unitaMisura) {
		if (quantita < 0) {
            throw new IllegalArgumentException("La quantit� non pu� essere negativa");
        }
		this.nome = nome;
		this.quantita = quantita;
		this.unitaMis = unitaMisura;
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
	public UnitaMisura getUnitaMisura() {
		return unitaMis;
	}
	
}
