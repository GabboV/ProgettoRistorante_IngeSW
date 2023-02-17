package Principale;

import java.util.Date;

public class Piatto {
	
	private String nomePiatto;
	private Date inizio;
	private Date fine;
	private Ricetta recipe;
	private double caricoLavoro;
	
	public Piatto(String nomePiatto, Date inizio, Date fine, Ricetta recipe, double caricoLavoro) {
		super();
		this.nomePiatto = nomePiatto;
		this.inizio = inizio;
		this.fine = fine;
		this.recipe = recipe;
		this.caricoLavoro = caricoLavoro;
	}
	
	public String getNomePiatto() {
		return nomePiatto;
	}

	public void setNomePiatto(String nomePiatto) {
		this.nomePiatto = nomePiatto;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public Ricetta getRecipe() {
		return recipe;
	}

	public void setRecipe(Ricetta recipe) {
		this.recipe = recipe;
	}

	public double getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(double caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}
	
	/**
	 *DA IMPLEMENTARE
	 * @return
	 */
	public boolean isValido() {
		return false;
	}

}
