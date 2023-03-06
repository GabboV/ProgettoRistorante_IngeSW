package it.unibs.ing.progetto.ristorante.controller;


import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.MagazziniereView;

public class MagazziniereController{

	private Ristorante model;
	private MagazziniereView view;
	
	public MagazziniereController(Ristorante model) {
		this.model = model;
	}
	
	public void aggiornaRegistroMagazzino() {

	}

	public ArrayList<Prodotto> creaListaSpesa() {
		
		//...
		
		return new ArrayList<Prodotto>();
	}

}
