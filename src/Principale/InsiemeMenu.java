package Principale;

import java.util.ArrayList;

public class InsiemeMenu {
	
	public ArrayList<MenuTematico> listaMenuT;
	
	public ArrayList<MenuCarta> listaMenuC;

	public InsiemeMenu(ArrayList<MenuTematico> listaMenuT, ArrayList<MenuCarta> listaMenuC) {
		super();
		this.listaMenuT = listaMenuT;
		this.listaMenuC = listaMenuC;
	}

	public ArrayList<MenuTematico> getListaMenuT() {
		return listaMenuT;
	}

	public ArrayList<MenuCarta> getListaMenuC() {
		return listaMenuC;
	}
	
	

}
