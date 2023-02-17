package Principale;

import java.util.*;

public class RegistroMagazzino {

	private HashMap<Prodotto, Double> inventario;
	private ArrayList<Prodotto> prodottiAcquistati;
	private ArrayList<Prodotto> prodottiInCucina;
	private ArrayList<Prodotto> bevandeInSala;
	private ArrayList<Prodotto> extraInSala;
	private ArrayList<Prodotto> prodottiScartati;
	
	public RegistroMagazzino() {
		super();
		this.inventario = new HashMap<Prodotto, Double>();
		this.prodottiAcquistati = new ArrayList<Prodotto>();
		this.prodottiInCucina = new ArrayList<Prodotto>();
		this.bevandeInSala = new ArrayList<Prodotto>();
		this.extraInSala = new ArrayList<Prodotto>();
		this.prodottiScartati = new ArrayList<Prodotto>();
	}

	public HashMap<Prodotto, Double> getInventario() {
		return inventario;
	}

	public ArrayList<Prodotto> getProdottiAcquistati() {
		return prodottiAcquistati;
	}

	public ArrayList<Prodotto> getProdottiInCucina() {
		return prodottiInCucina;
	}

	public ArrayList<Prodotto> getBevandeInSala() {
		return bevandeInSala;
	}

	public ArrayList<Prodotto> getExtraInSala() {
		return extraInSala;
	}

	public ArrayList<Prodotto> getProdottiScartati() {
		return prodottiScartati;
	}
	
	public void addInventario(Prodotto p, Double amount) {
		this.inventario.put(p, amount);
	}
	
	
	
}
