package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;

public class Periodo {
	
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
    
    public boolean contieneDataEstremiInclusi(LocalDate data) {
    	if((data.isAfter(this.getDataInizio()) && data.isBefore(this.getDataFine())) || data.isEqual(this.getDataInizio()) || data.isEqual(this.getDataFine())) 
			return true;
		return false;
    }
}
