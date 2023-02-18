package Principale;

import java.util.HashMap;

import java.util.Date;

public class Prenotazione {
	
	private Cliente prenotante;
	private String codicePrenotazione;
	private  HashMap<MenuTematico,Integer> comandaTematico;
	private  HashMap<MenuCarta,Integer> comandaCarta;
	private int numeroPersone;
	private Date dataPrenotazione;
	
	public Prenotazione(Cliente prenotante, String codicePrenotazione, int numeroPersone) {
		super();
		this.prenotante = prenotante;
		this.codicePrenotazione = codicePrenotazione;
		this.comandaTematico = new HashMap<MenuTematico,Integer>();
		this.comandaCarta = new HashMap<MenuCarta,Integer>();
		this.numeroPersone = numeroPersone;
		this.dataPrenotazione = new Date();
	}

	public String getCodicePrenotazione() {
		return codicePrenotazione;
	}

	public void setCodicePrenotazione(String codicePrenotazione) {
		this.codicePrenotazione = codicePrenotazione;
	}

	public HashMap<MenuTematico, Integer> getComandaTematico() {
		return comandaTematico;
	}

	public void setComandaTematico(HashMap<MenuTematico, Integer> comandaTematico) {
		this.comandaTematico = comandaTematico;
	}

	public HashMap<MenuCarta, Integer> getComandaCarta() {
		return comandaCarta;
	}

	public void setComandaCarta(HashMap<MenuCarta, Integer> comandaCarta) {
		this.comandaCarta = comandaCarta;
	}

	public int getNumeroPersone() {
		return numeroPersone;
	}

	public void setNumeroPersone(int numeroPersone) {
		this.numeroPersone = numeroPersone;
	}

	public Cliente getPrenotante() {
		return prenotante;
	}

	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}
	
	
}
