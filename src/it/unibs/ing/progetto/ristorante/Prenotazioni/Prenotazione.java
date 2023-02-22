package it.unibs.ing.progetto.ristorante.Prenotazioni;

import java.util.HashMap;
import it.unibs.ing.progetto.ristorante.Piatto;
import java.util.Date;

public class Prenotazione {
	
	private Cliente prenotante;
	private String codicePrenotazione;
	//basta avere una HashMap<String, Integer>?
	private HashMap<Piatto,Integer> comanda;
	private int numeroPersone;
	//da cambiare in classe Date
	private String dataPrenotazione;
	private float caricoLavoroPrenotazione;
	
	public Prenotazione(Cliente prenotante, String codicePrenotazione, int numeroPersone, String dataPrenotazione, float caricoLavoroPrenotazione, HashMap<Piatto,Integer> comanda) {
		super();
		this.prenotante = prenotante;
		this.codicePrenotazione = codicePrenotazione;
		this.comanda = comanda;
		this.numeroPersone = numeroPersone;
		this.caricoLavoroPrenotazione = caricoLavoroPrenotazione;
		this.dataPrenotazione = dataPrenotazione;
	}


	public String getCodicePrenotazione() {
		return codicePrenotazione;
	}

	public void setCodicePrenotazione(String codicePrenotazione) {
		this.codicePrenotazione = codicePrenotazione;
	}

	public HashMap<Piatto, Integer> getComandaTematico() {
		return comanda;
	}

	public void setComandaTematico(HashMap<Piatto, Integer> comandaTematico) {
		this.comanda = comandaTematico;
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

	public String getDataPrenotazione() {
		return dataPrenotazione;
	}

	public float getCaricoLavoroPrenotazione() {
		return caricoLavoroPrenotazione;
	}
	
	@Override
	public String toString() {
		return "Prenotazione\n" + prenotante + ",\ncodicePrenotazione=" + codicePrenotazione + ",\ncomanda="
				+ comanda + ",\nnumeroPersone=" + numeroPersone + ",\ndataPrenotazione=" + dataPrenotazione
				+ ",\ncaricoLavoroPrenotazione=" + caricoLavoroPrenotazione + "\n\n";
	}
	
}
