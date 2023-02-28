package it.unibs.ing.progetto.ristorante.prototipo;

import it.unibs.ing.progetto.ristorante.Ristorante;
import it.unibs.ing.progetto.ristorante.Gestore;

public class main {

	public static void main(String[] args) {

		View view = new View();
		Ristorante d = new Ristorante();
		Gestore gestore = new Gestore("nothing", d);

		Controller c = new Controller(gestore, view);

		c.avviaController();

	}

}
