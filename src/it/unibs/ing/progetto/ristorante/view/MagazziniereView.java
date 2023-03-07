package it.unibs.ing.progetto.ristorante.view;

import java.util.ArrayList;

import it.unibs.fp.mylib.MyMenu;

public class MagazziniereView {
	
	private static final String[] OPZIONI = new String[] {
			"Aggiorna registro Magazzino",
			"Crea Lista Spesa",
			"Visualizza lista spesa"
	};
	
	private MyMenu menu = new MyMenu("Magazziniere", OPZIONI);
	
	public int printMenu(){
		return menu.scegli();
	}
	
	public void print(String msg) {
		System.out.println(msg);
	}

}
