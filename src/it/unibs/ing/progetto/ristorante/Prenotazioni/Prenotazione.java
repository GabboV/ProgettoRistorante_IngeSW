package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.HashMap;
import it.unibs.ing.progetto.ristorante.Piatto;
import java.util.Date;

public class Prenotazione {
	
	private String nomeCliente;
	//indica il numero di persone che occupanto un coperto
	private int numeroCoperti;
	//basta avere una HashMap<String, Integer>? no perchè cosi semplifico le operazioni del magazziniere
	private HashMap<Piatto,Integer> comanda;
	private Date dataPrenotazione;
	
	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda, Date dataPrenotazione) {
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

	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}


	public void setDataPrenotazione(Date dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}
	
}
