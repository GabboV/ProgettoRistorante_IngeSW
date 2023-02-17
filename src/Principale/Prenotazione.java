package Principale;

import java.util.HashMap;

import javax.xml.crypto.Data;

public class Prenotazione {
	
	private Cliente prenotante;
	private String codicePrenotazione;
	private  HashMap<MenuTematico,Integer> comandaTematico;
	private  HashMap<MenuCarta,Integer> comandaCarta;
	private int numeroPersone;
	private Data dataPrenotazione;
	
	public Prenotazione(Cliente prenotante, String codicePrenotazione, HashMap<MenuTematico, Integer> comandaTematico,
			HashMap<MenuCarta, Integer> comandaCarta, int numeroPersone, Data dataPrenotazione) {
		super();
		this.prenotante = prenotante;
		this.codicePrenotazione = codicePrenotazione;
		this.comandaTematico = comandaTematico;
		this.comandaCarta = comandaCarta;
		this.numeroPersone = numeroPersone;
		this.dataPrenotazione = dataPrenotazione;
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

	public Data getDataPrenotazione() {
		return dataPrenotazione;
	}
	
	
}
