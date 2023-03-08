package it.unibs.ing.progetto.ristorante.model;

public enum UnitaMisura {

	KG("kg"), HG("hg"), LITRI("l"), GRAMMI("g"), UNITA("Unita");

	private final String unitaMisura;

	private UnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getName() {
		return unitaMisura;
	}
	
}
