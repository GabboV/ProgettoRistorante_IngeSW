package it.unibs.ing.progetto.ristorante.interfacce;

import it.unibs.ing.progetto.ristorante.model.*;

import java.time.LocalDate;
import java.util.List;

public interface IGestore {

    public void setDataCorrente(LocalDate dataCorrente);

    public int getCaricoLavoroPerPersona();

    public int getNumeroPostiASedere();

    public int getCaricoLavoroRistorante();

    public List<Piatto> getElencoPiatti();

    public List<MenuTematico> getElencoMenuTematici();

    public List<Prodotto> getInsiemeBevande();

    public List<Prodotto> getInsiemeGeneriExtra();

    public Piatto piattoScelto(int scelta);

    public void addPiattoRicetta(List<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro,
            String nomePiatto, List<Periodo> periodi);

    public void addMenuTematico(String nomeMenuTematico, List<MenuComponent> piatti, int caricoLavoroMenuTematico,
            List<Periodo> periodi);

    public void addBevanda(String nomeBevanda, float consumoProCapiteBevanda);

    public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra);

    public void giornoDopo();

    public void setCaricoLavoroPerPersona(int caricoDilavoroPerPersona);

    public void setNumeroPostiASedere(int numeroPostiASedere);

    public void setCaricoLavoroRistorante(int caricoLavoroRistorante);

    public LocalDate getDataCorrente();
}
