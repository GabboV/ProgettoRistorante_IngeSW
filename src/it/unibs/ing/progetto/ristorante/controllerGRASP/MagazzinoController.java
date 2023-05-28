package it.unibs.ing.progetto.ristorante.controllerGRASP;

import java.util.List;

import it.unibs.ing.progetto.ristorante.interfacce.IMagazzino;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

/**
 * Grasp Controller Magazzino
 * @author Kevin
 *
 */
public class MagazzinoController {

	private IMagazzino model;
	
	public MagazzinoController(IMagazzino model) {
		this.model = model;
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino, dopo aver chiesto le
	 * informazioni al riguardo, Non fa aggiungere prodotti gia esistenti
	 * @throws Exception 
	 */
	public boolean addProdottoRegistroMagazzino(String nome, float quantita, UnitaMisura misura) {
		if(!model.esisteProdottoInMagazzino(nome)) {
			model.addProdottoInventario(nome, quantita, misura);
			model.generaListaSpesa();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo per aggiungere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoEntrante(Prodotto prodotto, float entrata) {
		model.addQuantitaProdottoMagazzino(prodotto, entrata);
		model.generaListaSpesa();
	}

	/**
	 * Metodo per rimuovere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoUscente(Prodotto prodotto, float uscita) {
		model.rimuoviQuantitaProdottoMagazzino(prodotto, uscita);
		model.generaListaSpesa();
	}
	
	/**
	 * Passa il registro del magazzino
	 * @return registro magazzino
	 */
	public List<Prodotto> retrieveInventario(){
		return model.getRegistroMagazzino();
	}
	
	/**
	 * Passa la lista della spesa
	 * @return lista della spesa
	 */
	public List<Prodotto> retrieveLista(){
		return model.getListaSpesa();
	}

	/**
	 * Return true se nome esite, false altrimenti
	 * @param nome
	 * @return boolean
	 */
	public boolean nomeProdottoGiaPresente(String nome) {
		return model.esisteProdottoInMagazzino(nome);
	}

	

}
