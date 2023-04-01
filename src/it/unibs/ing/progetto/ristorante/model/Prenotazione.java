package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
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

	public Prenotazione(String codice_cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda,
			LocalDate dataPrenotazione) {
		super();
		if (comanda == null || numeroCoperti < 1 || dataPrenotazione == null || codice_cliente == null) {
			throw new IllegalArgumentException("Input invalidi");
		}
		this.codice_cliente = codice_cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
		this.dataPrenotazione = dataPrenotazione;
	}

	public float getCaricoLavoroTotalePrenotazione() {
		return calcolaCaricoLavoroComanda(this.comanda);
	}

	public static float calcolaCaricoLavoroComanda(HashMap<Piatto, Integer> comanda) {
		float totale = 0.0f;
		for (Entry<Piatto, Integer> entry : comanda.entrySet()) {
			int personeOrdinatoP = entry.getValue();
			Piatto p = entry.getKey();
			float parziale = p.getCaricoLavoro() * personeOrdinatoP;
			totale += parziale;
		}
		return totale;
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

}
