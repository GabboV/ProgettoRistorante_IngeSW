package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Prenotazione {

	private String nomeCliente;
	// indica il numero di persone che occupanto un coperto
	private int numeroCoperti;
	// basta avere una HashMap<String, Integer>? no perchè cosi semplifico le
	// operazioni del magazziniere
	private HashMap<Piatto, Integer> comanda;
	private LocalDate dataPrenotazione;

	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda,
			LocalDate dataPrenotazione) {
		super();
		this.nomeCliente = cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
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

	public boolean isValidinData(LocalDate data) {
		if (data.getDayOfMonth() == this.dataPrenotazione.getDayOfMonth()
				&& data.getMonth() == this.dataPrenotazione.getMonth()
				&& data.getYear() == this.dataPrenotazione.getYear()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Piatto> getAllPiatti() {
		return new ArrayList<Piatto>(this.comanda.keySet());
	}

}
