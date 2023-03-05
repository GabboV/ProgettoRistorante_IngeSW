package it.unibs.ing.progetto.ristorante;

import java.time.LocalDate;

import it.unibs.fp.mylib.InputDati;
import it.unibs.ing.progetto.ristorante.controller.GestoreController;

public class MainRistorante {

	public static void main(String[] args) {
		
		System.out.println("Benvenuto nel programma di supporto del tuo ristorante.");
		System.out.println("Per la fase di inizializzazione del programma è necessario il login del Gestore.");
		boolean risposta = InputDati.yesOrNo("Sei il Gestore? ");
		if (risposta == false) {
			System.out.println("Bisogna essere il Gestore per poter inizializzare il programma.");
			System.out.println("Arresto programma...");
			System.exit(0);
		}
		else {
			GestoreController gestore = new GestoreController();
			gestore.inizializzaRistorante();
		}
	}

	
	
}
