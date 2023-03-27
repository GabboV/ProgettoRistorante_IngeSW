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
		if (nome == null || quantita < 0 || unitaMisura == null) {
            throw new IllegalArgumentException("Input non valido");
        }
		this.nome = nome;
		this.quantita = quantita;
		this.unitaMis = unitaMisura;
	}

	public String getNome() {
		return nome;
	}

	public Float getQuantita() {
		return quantita;
	}

	public UnitaMisura getUnitaMisura() {
		return unitaMis;
	}
	
	public void setQuantita(Float quantita) {
		this.quantita = quantita;
	}
	
}
