package it.unibs.ing.progetto.ristorante.model;

public enum UnitaMisura {

	KG("kg"), HG("hg"), LITRI("l"), GRAMMI("g");

	String nome;
	UnitaMisura(String string) {
		this.nome = string;
	}
	
	public String getName() {
        return this.nome;
    }
	
}
