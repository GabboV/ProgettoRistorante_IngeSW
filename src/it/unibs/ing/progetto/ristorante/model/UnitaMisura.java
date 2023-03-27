package it.unibs.ing.progetto.ristorante.model;
/*
 * 
 * Java enums are automatically Serializable, 
 * there is no need to explicitly add the "implements Serializable" clause. fonte: Google
 */
public enum UnitaMisura {

	KG("kg"), HG("hg"), L("l"), GRAMMI("g"), UNITA("Unita");

	private final String unitaMisura;

	private UnitaMisura(String unitaMisura) {
		this.unitaMisura = unitaMisura;
	}

	public String getName() {
		return unitaMisura;
	}
	
	
}
