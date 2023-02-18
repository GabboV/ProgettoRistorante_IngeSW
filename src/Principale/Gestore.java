package Principale;

import java.util.HashMap;

public class Gestore {

	private int caricoDilavoroPerPersona;
	private int numeroPostiASedere;

	private HashMap<Bevanda, Integer> consumoProcapite;
	private HashMap<GenereExtra, Integer> consumoProcapiteExtra;

	private double caricoLavoroRistorante;

	public Gestore(int caricoDilavoroPerPersona, int numeroPostiASedere, double caricoLavoroRistorante) {
		super();
		this.caricoDilavoroPerPersona = caricoDilavoroPerPersona;
		this.numeroPostiASedere = numeroPostiASedere;
		this.consumoProcapite = new HashMap<Bevanda, Integer>();
		this.consumoProcapiteExtra = new HashMap<GenereExtra, Integer>();
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public int getCaricoDilavoroPerPersona() {
		return caricoDilavoroPerPersona;
	}

	public void setCaricoDilavoroPerPersona(int caricoDilavoroPerPersona) {
		this.caricoDilavoroPerPersona = caricoDilavoroPerPersona;
	}

	public int getNumeroPostiASedere() {
		return numeroPostiASedere;
	}

	public void setNumeroPostiASedere(int numeroPostiASedere) {
		this.numeroPostiASedere = numeroPostiASedere;
	}

	public double getCaricoLavoroRistorante() {
		return caricoLavoroRistorante;
	}

	public void setCaricoLavoroRistorante(double caricoLavoroRistorante) {
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public HashMap<Bevanda, Integer> getConsumoProcapite() {
		return consumoProcapite;
	}

	public HashMap<GenereExtra, Integer> getConsumoProcapiteExtra() {
		return consumoProcapiteExtra;
	}
	

}
