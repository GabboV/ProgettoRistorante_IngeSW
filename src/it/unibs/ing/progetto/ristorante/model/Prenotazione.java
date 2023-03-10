package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Prenotazione {

	private String codice_cliente;
	private int numeroCoperti;
	private HashMap<Piatto, Integer> comanda;
	private LocalDate dataPrenotazione;

	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda,
			LocalDate dataPrenotazione) {
		super();
		this.codice_cliente = cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
		this.dataPrenotazione = dataPrenotazione;
	}

	
	
	public void setDataPrenotazione(LocalDate dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}



	public HashMap<Piatto, Integer> getComanda() {
		return comanda;
	}

	public int getNumeroCoperti() {
		return numeroCoperti;
	}

	public LocalDate getDataPrenotazione() {
		return dataPrenotazione;
	}

	public ArrayList<Piatto> getAllPiatti() {
		return new ArrayList<Piatto>(this.comanda.keySet());
	}

	public String getCodiceCliente() {
		return codice_cliente;
	}

	public boolean isValidinData(LocalDate data) {
		if (data.getDayOfMonth() == this.dataPrenotazione.getDayOfMonth()
				&& data.getMonth() == this.dataPrenotazione.getMonth()
				&& data.getYear() == this.dataPrenotazione.getYear()) {
			return true;
		} else {
			return false;
		}
	}

	public float getCaricoLavoroTotalePrenotazione() {
		float caricoTotale = 0;
		ArrayList<Piatto> piattiOrdinati = this.getAllPiatti();
		for (Piatto p : piattiOrdinati) {
			int personeOrdinatoP = this.comanda.get(p);
			float caricoParziale = p.getCaricoLavoro() * personeOrdinatoP;
			caricoTotale += caricoParziale;
		}
		return caricoTotale;
	}

}
