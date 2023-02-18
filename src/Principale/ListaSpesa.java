package Principale;

import java.util.HashMap;

public class ListaSpesa {

	private HashMap<Prodotto, Double> vociLista;

	public ListaSpesa() {
		super();
		this.vociLista = new HashMap<Prodotto, Double>();
	}

	public void addMerce(Prodotto p, Double d) {
		vociLista.put(p, d);
	}

	public HashMap<Prodotto, Double> getVociLista() {
		return vociLista;
	}

	public void setVociLista(HashMap<Prodotto, Double> vociLista) {
		this.vociLista = vociLista;
	}

}
