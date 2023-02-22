package it.unibs.ing.progetto.ristorante;

import java.util.Date;

/**
 * 
 * @author Kevin
 *
 */
public class Piatto {

	private String nomePiatto; 
	private Date inizio;
	private Date fine;
	private double caricoLavoro;
	
	

	public Piatto(String nomePiatto, double caricoLavoro) {
		super();
		this.nomePiatto = nomePiatto;
		this.inizio = null;
		this.fine = null;
		this.caricoLavoro = caricoLavoro;
	}

	public Piatto(String nomePiatto, Date inizio, Date fine,  double caricoLavoro) {
		super();
		this.nomePiatto = nomePiatto;
		this.inizio = inizio;
		this.fine = fine;
		this.caricoLavoro = caricoLavoro;
	}

	/**
	 * 
	 * @return
	 */
	public String getNomePiatto() {
		return nomePiatto;
	}

	public Date getInizio() {
		return inizio;
	}

	public Date getFine() {
		return fine;
	}
/*
	public Ricetta getRecipe() {
		return recipe;
	}
*/
	public double getCaricoLavoro() {
		return caricoLavoro;
	}

	@Override
	public String toString() {
		return "Piatto [nomePiatto=" + nomePiatto + ", inizio=" + inizio + ", fine=" + fine + ", caricoLavoro="
				+ caricoLavoro + "]";
	}

	/**
	 * DA IMPLEMENTARE
	 * 
	 * @return
	 */
	public boolean isValido() {
		return false;
	}

}
