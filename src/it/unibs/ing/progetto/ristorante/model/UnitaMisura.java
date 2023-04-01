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

	public static String[] getValues() {
		UnitaMisura[] misure = UnitaMisura.values();
		String[] valori = new String[misure.length];
		for(int i = 0; i < misure.length; i++ ) {
			valori[i] = misure[i].name();
		}
		if(valori.length <= 0 ) throw new NullPointerException("Problemi nelle unità di misura");
		return valori;
	}		
}
