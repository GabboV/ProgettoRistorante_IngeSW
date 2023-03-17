package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Prenotazione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codice_cliente;
	private int numeroCoperti;
	private HashMap<Piatto, Integer> comanda;
	private LocalDate dataPrenotazione;

	public Prenotazione(int numeroCoperti, HashMap<Piatto, Integer> comanda, LocalDate dataPrenotazione) {
		super();
		if (comanda == null || numeroCoperti < 1) {
			throw new IllegalArgumentException("Input invalidi");
		}
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

	public String getCodiceCliente() {
		return codice_cliente;
	}

	public boolean isPrenotazioneInData(LocalDate data) {
	    return this.dataPrenotazione.equals(data);
	}

	public float getCaricoLavoroTotalePrenotazione() {
		float caricoTotale = 0;
		Iterator<Entry<Piatto, Integer>> iter = this.comanda.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			int personeOrdinatoP = entry.getValue();
			Piatto p = entry.getKey();
			float caricoParziale = p.getCaricoLavoro() * personeOrdinatoP;
			caricoTotale += caricoParziale;
		}
		return caricoTotale;
	}

}
