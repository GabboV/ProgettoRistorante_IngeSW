package it.unibs.ing.progetto.ristorante.controller;

import it.unibs.ing.progetto.ristorante.model.Ristorante;

public abstract class Controller {

	private Ristorante model;

	public Controller(Ristorante model) {
		super();
		this.model = model;
	}

	public Ristorante getModel() {
		return model;
	}
	
	public abstract void avviaSessione();

}
