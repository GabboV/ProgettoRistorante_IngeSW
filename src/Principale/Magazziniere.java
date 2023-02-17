package Principale;

import java.util.*;

public class Magazziniere {

	private ListaSpesa listaSpesa;
	private RegistroMagazzino registroMagazzino;

	public Magazziniere() {
		super();
		this.listaSpesa = new ListaSpesa();
		this.registroMagazzino = new RegistroMagazzino();

	}

	public ListaSpesa getListaSpesa() {
		return listaSpesa;
	}

	public RegistroMagazzino getRegistroMagazzino() {
		return registroMagazzino;
	}

	/**
	 * Da implementare
	 */
	public void aggiornaRegistroMagazzino() {

	}
	
	
	/**
	 * Da implementare
	 * @param listaPrenotazione
	 * @return
	 */
	public ListaSpesa creaListaSpesa(ArrayList<Prenotazione> listaPrenotazione) {
		/*
		 * 
		 * It Does Something undefined
		 * 
		 */
		return new ListaSpesa();

	}

}
