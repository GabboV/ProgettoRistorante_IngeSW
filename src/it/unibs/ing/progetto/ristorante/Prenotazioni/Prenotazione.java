package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.time.LocalDate;
import java.util.HashMap;
import it.unibs.ing.progetto.ristorante.Piatto;

public class Prenotazione {
	
	private String nomeCliente;
	//indica il numero di persone che occupanto un coperto
	private int numeroCoperti;
	//basta avere una HashMap<String, Integer>? no perchè cosi semplifico le operazioni del magazziniere
	private HashMap<Piatto,Integer> comanda;
	private LocalDate dataPrenotazione;
	
	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda, LocalDate dataPrenotazione) {
		super();
		this.nomeCliente = cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
		this.dataPrenotazione = dataPrenotazione;
	}


	public String getCliente() {
		return nomeCliente;
	}


	public void setCliente(String cliente) {
		this.nomeCliente = cliente;
	}


	public int getNumeroCoperti() {
		return numeroCoperti;
	}


	public void setNumeroCoperti(int numeroCoperti) {
		this.numeroCoperti = numeroCoperti;
	}


	public HashMap<Piatto, Integer> getComanda() {
		return comanda;
	}


	public void setComanda(HashMap<Piatto, Integer> comanda) {
		this.comanda = comanda;
	}

	public LocalDate getDataPrenotazione() {
		return dataPrenotazione;
	}


	public void setDataPrenotazione(LocalDate dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	
}
