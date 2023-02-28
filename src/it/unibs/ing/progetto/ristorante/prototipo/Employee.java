package it.unibs.ing.progetto.ristorante.prototipo;

import it.unibs.ing.progetto.ristorante.Ristorante;

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
