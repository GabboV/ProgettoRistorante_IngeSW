package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.HashMap;
import it.unibs.ing.progetto.ristorante.Piatto;
import java.util.Date;

public class Prenotazione {
	
	private String nomeCliente;
	//indica il numero di persone che il cliente rappresenta
	private int numeroCoperti;
	//basta avere una HashMap<String, Integer>?
	private HashMap<Piatto,Integer> comanda;
	//indica il numero di piatti o menuTematici che le persone hanno ordinato
	private int numeroPersone;
	//da cambiare in classe Date
	private String dataPrenotazione;
	private float caricoLavoroPrenotazione;
	
	public Prenotazione(String cliente, int numeroCoperti, HashMap<Piatto, Integer> comanda, int numeroPersone,
			String dataPrenotazione, float caricoLavoroPrenotazione) {
		super();
		this.nomeCliente = cliente;
		this.numeroCoperti = numeroCoperti;
		this.comanda = comanda;
		this.numeroPersone = numeroPersone;
		this.dataPrenotazione = dataPrenotazione;
		this.caricoLavoroPrenotazione = caricoLavoroPrenotazione;
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


	public int getNumeroPersone() {
		return numeroPersone;
	}


	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}


	public String getDataPrenotazione() {
		return dataPrenotazione;
	}


	public void setDataPrenotazione(String dataPrenotazione) {
		this.dataPrenotazione = dataPrenotazione;
	}


	public float getCaricoLavoroPrenotazione() {
		return caricoLavoroPrenotazione;
	}


	public void setCaricoLavoroPrenotazione(float caricoLavoroPrenotazione) {
		this.caricoLavoroPrenotazione = caricoLavoroPrenotazione;
	}
	
	
	@Override
	public String toString() {
		return "Prenotazione\n" + nomeCliente + ",\ncomanda=" + comanda + ",\nnumeroPersone=" + numeroPersone + ",\ndataPrenotazione=" 
				+ dataPrenotazione + ",\ncaricoLavoroPrenotazione=" + caricoLavoroPrenotazione + "\n\n";
	}
	
}
