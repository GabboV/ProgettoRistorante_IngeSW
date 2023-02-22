package it.unibs.ing.progetto.ristorante.Menu;

import java.util.ArrayList;
import java.util.Date;

import it.unibs.ing.progetto.ristorante.Piatto;

public class MenuTematico extends Menu{
	
	private String nome;

	public MenuTematico(ArrayList<Piatto> piatti, Date data, boolean valido, String nome) {
		super(piatti, data, valido);
		this.setNome(nome);
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
