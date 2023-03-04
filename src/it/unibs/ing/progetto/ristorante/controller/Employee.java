package it.unibs.ing.progetto.ristorante.controller;

import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class Employee {
	
	private Ristorante ristorante;

	public Ristorante getRistorante() {
		return ristorante;
	}

	public Employee(Ristorante ristorante) {
		super();
		this.ristorante = ristorante;
	}

}
