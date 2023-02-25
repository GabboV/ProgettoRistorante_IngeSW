package it.unibs.ing.progetto.ristorante.Menu;

import java.util.ArrayList;
import java.util.Date;

import it.unibs.ing.progetto.ristorante.Piatto;

public class Menu {

	private ArrayList<Piatto> elencoPiatti;

	public Menu(ArrayList<Piatto> elencoPiatti) {
		super();
		this.elencoPiatti = elencoPiatti;
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public void setElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}

	public void removePiatto(Piatto p) {
		/*
		if (this.presentePiatto(p)) {
			int pos = posPiatto(p);
			elencoPiatti.remove(pos);
			
		}
		*/
		String nomePiatto = p.getNomePiatto();
		if (elencoPiatti.contains(p)) {
			elencoPiatti.remove(p);
			System.out.print("Il piatto " + nomePiatto + " è stato rimosso dal Menu");
		} else {
			System.out.print("Il piatto " + nomePiatto + " non è presente nel Menu");
		}
	}

	/*
	public boolean presentePiatto(Piatto p) {
		String pName = p.getNomePiatto();
		for (int i = 0; i < this.elencoPiatti.size(); i++) {
			if (this.elencoPiatti.get(i).equals(pName))
				return true;
		}
		return false;
	}

	private int posPiatto(Piatto p) {
		int i = 0;
		String pName = p.getNomePiatto();
		for (; i < this.elencoPiatti.size(); i++) {
			if (p.getNomePiatto().equals(pName))
				return i;
		}
		return i;
	}
	*/
	
	
	//MenuCarta contiene elenco dei piatti validi nella data
	//serve classe MenuCarta? No, perchè è piu facile generarlo con un metodo solo quando serve
	//	altrimenti dovrei ogni volta che è richiesta generare una istanza di MenuCarta per quando mi serve,
	//	ma non ha senso conservarla da qualche parte perchè ogni giorno può cambiare
		
	//MenuCarta deve essere generato da un metodo quando richiesto da AddettoPrenotazioni
	//metodo si potrebbe trovare in Menu
	//crea un ArrayList<Piatto> menuCarta
	//prende uno alla volta i piatti dal Menu
	//IF data è valida THEN aggiungi piatto al menuCarta
	//return menuCarta
}
