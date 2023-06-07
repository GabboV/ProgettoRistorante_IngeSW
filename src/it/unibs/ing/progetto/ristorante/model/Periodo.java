package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Periodo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LocalDate dataInizio;
	private final LocalDate dataFine;

	public Periodo(LocalDate first, LocalDate second) {
		this.dataInizio = first;
		this.dataFine = second;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	/**
	 * controlla se la data e' compresa tra dataInizio e dataFine estremi inclusi
	 */
	public boolean contieneDataEstremiInclusi(LocalDate data) {
		if (((data.isAfter(this.getDataInizio()) && data.isBefore(this.getDataFine())))
				|| data.isEqual(this.getDataInizio()) || data.isEqual(this.getDataFine())) {
			return true;
		}
		return false;
	}

	// ritorna true se la dataInizio coincide con la dataFine o se e' successiva, e se la data fine e' successiva alla data corrente
	public boolean isValido(LocalDate dataCorrente) {
		boolean valido = false;
		if ((dataInizio.isBefore(dataFine) || dataInizio.isEqual(dataFine)) && dataFine.isAfter(dataCorrente)) {
			valido = true;
		}
		return valido;
	}
}
