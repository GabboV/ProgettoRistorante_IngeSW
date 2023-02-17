package Principale;

import java.util.ArrayList;
import java.util.Date;

public class Menu {
	
	private ArrayList<Piatto> piatti;
	
	private Date data;
	
	private boolean valido;

	public Menu(ArrayList<Piatto> piatti, Date data, boolean valido) {
		super();
		this.piatti = piatti;
		this.data = data;
		this.valido = valido;
	}

	public ArrayList<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(ArrayList<Piatto> piatti) {
		this.piatti = piatti;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isValido() {
		return valido;
	}

	public void setValido(boolean valido) {
		this.valido = valido;
	}
	
}
