package it.unibs.ing.progetto.ristorante.Menu;

import java.util.ArrayList;

public class MenuSpeciali {

	private ArrayList<MenuTematico> elencoMenuTematici;

	public MenuSpeciali(ArrayList<MenuTematico> elencoMenuTematici) {
		super();
		this.elencoMenuTematici = elencoMenuTematici;
	}

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return elencoMenuTematici;
	}

	public void setElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		this.elencoMenuTematici = elencoMenuTematici;
	}
	
	public void removeMenuTematico(MenuTematico p) {
		String nomeMenuTematico = p.getNome();
		if (elencoMenuTematici.contains(p)) {
			elencoMenuTematici.remove(p);
			System.out.print("Il menu tematico " + nomeMenuTematico + " � stato rimosso dai Menu Speciali");
		} else {
			System.out.print("Il menu tematico " + nomeMenuTematico + " non � presente tra i Menu Speciali");
		}
	}
	
	//nel DataBase si pu� conservare un istanza di MenuSpeciali
		
	//ho bisogno di un metodo getElencoMenuSpecialiValidiInData chiamato da AddettoPrenotazioni
	//	per controllare se una prenotazione � valida
	//crea un ArrayList<MenuTematico> elencoMenuSpecialiValidi
	//prende uno alla volta i menu tematici dall'Array 
	//IF data � valida THEN aggiungi menuTematico al elencoMenuSpecialiValidi
	//return elencoMenuSpecialiValidi
	
}
