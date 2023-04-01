package it.unibs.ing.progetto.ristorante.interfacce;

import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public interface IMagazzino {

    public ArrayList<Prodotto> getRegistroMagazzino();

    public ArrayList<Prodotto> getListaSpesa();

    public void generaListaSpesa();

    public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura);

    public void rimuoviQuantitaProdottoMagazzino(Prodotto prodotto, float quantita);

    public boolean esisteProdottoInMagazzino(String nome);
    
    public void addQuantitaProdottoMagazzino(Prodotto prodotto, float quantita);
}
