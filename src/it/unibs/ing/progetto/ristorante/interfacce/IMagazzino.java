package it.unibs.ing.progetto.ristorante.interfacce;

import java.util.List;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public interface IMagazzino {

    public List<Prodotto> getRegistroMagazzino();

    public List<Prodotto> getListaSpesa();

    public void generaListaSpesa();

    public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura);

    public boolean rimuoviQuantitaProdottoMagazzino(Prodotto prodotto, float quantita);

    public boolean esisteProdottoInMagazzino(String nome);
    
    public boolean addQuantitaProdottoMagazzino(Prodotto prodotto, float quantita);
}
