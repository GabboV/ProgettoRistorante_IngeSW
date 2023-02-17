package Principale;

import java.util.ArrayList;

public class Ricetta {
	
	private ArrayList<Ingrediente> ingredienti;

	public Ricetta(ArrayList<Ingrediente> ingredienti) {
		super();
		this.ingredienti = ingredienti;
	}

	public ArrayList<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(ArrayList<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	} 
	

}
