package it.unibs.ing.progetto.ristorante.pattern;

import java.util.List;

import it.unibs.ing.progetto.ristorante.interfacce.IMagazzino;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class MagazzinoController {

	private IMagazzino model;
	private MagazzinoView view;
	
	public MagazzinoController(IMagazzino model, MagazzinoView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Aggiunge un prodotto al registro del magazzino, dopo aver chiesto le
	 * informazioni al riguardo, Non fa aggiungere prodotti gia esistenti
	 * @throws Exception 
	 */
	public void addProdottoRegistroMagazzino(String nome, float quantita, UnitaMisura misura) {
		if(!model.esisteProdottoInMagazzino(nome)) {
			model.addProdottoInventario(nome, quantita, misura);
		} else {
			view.printMsgProdottoGiaEsistente();
		}
	}

	/**
	 * Metodo per aggiungere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoEntrante(Prodotto prodotto, float entrata) {
		this.model.addQuantitaProdottoMagazzino(prodotto, entrata);
	}

	/**
	 * Metodo per rimuovere quantita ad un prodotto nell'inventario
	 */
	public void aggiungiFlussoUscente(Prodotto prodotto, float uscita) {
		model.rimuoviQuantitaProdottoMagazzino(prodotto, uscita);
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
