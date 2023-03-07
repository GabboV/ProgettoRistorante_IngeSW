package it.unibs.ing.progetto.ristorante.model;

public class Prodotto {

	private String nome;
	private Float quantita;
	private UnitaMisura unitaMisura;
	
	public Prodotto(String nome, Float quantita, UnitaMisura unitaMisura) {
		this.nome = nome;
		this.quantita = quantita;
		this.unitaMisura = unitaMisura;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Float getQuantita() {
		return quantita;
	}
	public void setQuantita(Float quantita) {
		this.quantita = quantita;
	}
	public UnitaMisura getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(UnitaMisura unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	
	
	
}
