package it.unibs.ing.progetto.ristorante.interfacce;

import it.unibs.ing.progetto.ristorante.model.*;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IGestore {

    public boolean esisteBevanda(String nome);

    public boolean esisteGenereExtra(String nome);

    public void setDataCorrente(LocalDate dataCorrente);

    public int getCaricoLavoroPerPersona();

    public int getNumeroPostiASedere();

    public int getCaricoLavoroRistorante();

    public ArrayList<Piatto> getElencoPiatti();

    public ArrayList<MenuTematico> getElencoMenuTematici();

    public ArrayList<Prodotto> getInsiemeBevande();

    public ArrayList<Prodotto> getInsiemeGeneriExtra();

    public Piatto piattoScelto(int scelta);

    public void addPiattoRicetta(ArrayList<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro,
            String nomePiatto, ArrayList<Periodo> periodi);

    public void addMenuTematico(String nomeMenuTematico, ArrayList<Piatto> piatti, int caricoLavoroMenuTematico,
            ArrayList<Periodo> periodi);

    public void addBevanda(String nomeBevanda, float consumoProCapiteBevanda);

    public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra);

    public void giornoDopo();

    public void setCaricoLavoroPerPersona(int caricoDilavoroPerPersona);

    public void setNumeroPostiASedere(int numeroPostiASedere);

    public void setCaricoLavoroRistorante(int caricoLavoroRistorante);

    public LocalDate getDataCorrente();
}
