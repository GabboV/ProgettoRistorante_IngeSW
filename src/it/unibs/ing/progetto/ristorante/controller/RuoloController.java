package it.unibs.ing.progetto.ristorante.controller;

import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.view.RuoloView;

public abstract class RuoloController {
	
	private Ristorante model;
	private RuoloView view;
	
	public RuoloController(Ristorante model) {
		super();
		this.model = model;
		this.view = new RuoloView();
	}
	
	public abstract void avviaSessione();

	public void setModel(Ristorante model) {
		this.model = model;
	}

	public void setView(RuoloView view) {
		this.view = view;
	}

	public Ristorante getModel() {
		return model;
	}

	public RuoloView getView() {
		return view;
	}
	
	


}
