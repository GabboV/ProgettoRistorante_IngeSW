package it.unibs.ing.progetto.ristorante.dati;

import java.io.Serializable;

import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class BoxMemoria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ristorante ristorante;

	public BoxMemoria(Ristorante ristorante) {
		super();
		this.ristorante = ristorante;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Ristorante getRistorante() {
		return ristorante;
	}
	

}
