package it.unibs.ing.progetto.ristorante.model;

public class Prodotto {

	String nome;
	Float quantita;
	String unitaMisura;
	
	public Prodotto(String nome, Float quantita, String unitaMisura) {
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
	public String getUnitaMisura() {
		return unitaMisura;
	}
	public void setUnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}
	
	
	
}
