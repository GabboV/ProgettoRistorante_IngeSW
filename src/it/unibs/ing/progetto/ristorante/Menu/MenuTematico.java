package it.unibs.ing.progetto.ristorante.Menu;

import java.util.ArrayList;
import java.util.Date;

import it.unibs.ing.progetto.ristorante.Piatto;

public class MenuTematico extends Menu{
	
	private String nome;
	private float caricoLavoro;
	//array contentente coppie di date (dataInizio e dataFine)
	private ArrayList<Date> giorniValido;

	public MenuTematico(ArrayList<Piatto> elencoPiatti, String nome, float caricoLavoro, ArrayList<Date> giorniValido) {
		super(elencoPiatti);
		this.nome = nome;
		this.caricoLavoro = caricoLavoro;
		this.giorniValido = giorniValido;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getCaricoLavoro() {
		return caricoLavoro;
	}

	public void setCaricoLavoro(float caricoLavoro) {
		this.caricoLavoro = caricoLavoro;
	}

	public ArrayList<Date> getGiorniValido() {
		return giorniValido;
	}

	public void setGiorniValido(ArrayList<Date> giorniValido) {
		this.giorniValido = giorniValido;
	}
	

	//ho bisogno di un elencoMenuTematici che si può conservare nel DataBase
	//	così il gestore può aggiungerci menu tematici
	
	//ho bisogno di un metodo getElencoMenuTematiciValidiInData chiamato da AddettoPrenotazioni
	//	per controllare se una prenotazione è valida
	//crea un ArrayList<MenuTematico> elencoMenuTematiciValidi
	//prende uno alla volta i menu tematici dall'Array 
	//IF data è valida THEN aggiungi menuTematico al elencoMenuTematiciValidi
	//return elencoMenuTematiciValidi

}
