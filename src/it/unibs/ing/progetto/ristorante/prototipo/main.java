package it.unibs.ing.progetto.ristorante.prototipo;

import it.unibs.ing.progetto.ristorante.DataBase;
import it.unibs.ing.progetto.ristorante.Gestore;

public class main {

	public static void main(String[] args) {

		View view = new View();
		DataBase d = new DataBase();
		Gestore gestore = new Gestore("nothing", d);

		Controller c = new Controller(gestore, view);

		c.avviaController();

	}

}
