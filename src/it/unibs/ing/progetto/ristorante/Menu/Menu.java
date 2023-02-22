package it.unibs.ing.progetto.ristorante.Menu;

import java.util.ArrayList;
import java.util.Date;

import it.unibs.ing.progetto.ristorante.Piatto;

/**
 * 
 * @author Kevin
 *
 */
public class Menu {

	private ArrayList<Piatto> elencoPiatti;
	private Date data;
	private boolean valido;

	public Menu(ArrayList<Piatto> piatti, Date data, boolean valido) {
		super();
		this.elencoPiatti = piatti;
		this.data = data;
		this.valido = valido;
	}

	public ArrayList<Piatto> getPiatti() {
		return elencoPiatti;
	}

	public void setPiatti(ArrayList<Piatto> piatti) {
		this.elencoPiatti = piatti;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}

	public void addPiatto(Piatto p) {
		this.elencoPiatti.add(p);
	}

	public void removePiatto(Piatto p) {
		if (this.presentePiatto(p)) {
			int pos = posPiatto(p);
			elencoPiatti.remove(pos);
		}
	}

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

}
