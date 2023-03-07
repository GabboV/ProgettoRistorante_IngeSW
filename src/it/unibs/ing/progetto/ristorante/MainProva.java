package it.unibs.ing.progetto.ristorante;
import it.unibs.ing.progetto.ristorante.controller.*;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class MainProva {

	public static void main(String[] args) {

		Ristorante ristorante = new Ristorante();
		GestoreController gestore = new GestoreController(ristorante);
		
		gestore.setPostiASedere(100);
		
		
		
		
		
	}

}
