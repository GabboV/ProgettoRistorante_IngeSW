package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Prenotazione {
	
	private String nomeCliente;
	//indica il numero di persone che occupanto un coperto
	private int numeroCoperti;
	//basta avere una HashMap<String, Integer>? no perch� cosi semplifico le operazioni del magazziniere
	private HashMap<Piatto,Integer> comanda;
	private LocalDate dataPrenotazione;
	
	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda, LocalDate dataPrenotazione) {
		super();
		this.nomeCliente = cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
		this.dataPrenotazione = dataPrenotazione;
	}


	public HashMap<Piatto, Integer> getComanda() {
		return comanda;
	}


	public LocalDate getDataPrenotazione() {
		return dataPrenotazione;
	}
	
	public boolean isValidinData(LocalDate data) {
		//
		return false;
	}
	
	public List<Piatto> getAllPiatti(){
		return (List<Piatto>) this.comanda.keySet();
	}



}
