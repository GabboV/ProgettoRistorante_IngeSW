package Principale;

import java.util.ArrayList;
import java.util.Date;

public class MenuTematico extends Menu{
	
	private String nome;

	public MenuTematico(ArrayList<Piatto> piatti, Date data, boolean valido, String nome) {
		super(piatti, data, valido);
		this.nome = nome;
		// TODO Auto-generated constructor stub
	}
	
	

}
