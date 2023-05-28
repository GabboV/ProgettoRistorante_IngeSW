package it.unibs.ing.progetto.ristorante.interfacce;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.unibs.ing.progetto.ristorante.model.*;

public interface IPrenotazioni {

    public boolean isRistorantePienoInData(LocalDate data);

    public int getPostiDisponibiliInData(LocalDate data);

    public void addPrenotazione(String cliente, LocalDate data, HashMap<Piatto, Integer> comanda, int coperti);

    public boolean ciSonoMenuTematiciValidiInData(LocalDate data);

    public boolean ciSonoPiattiValidiInData(LocalDate data);

    public void removePrenotazioniScadute();

    public ArrayList<Prenotazione> getElencoPrenotazioni();

    public List<Piatto> getMenuCartaInData(LocalDate date);

    public List<MenuTematico> getMenuTematiciInData(LocalDate date);

    public LocalDate getDataCorrente();

    public boolean verificaAccettabilitaPrenotazione(LocalDate dataPrenotazione, HashMap<Piatto, Integer> comanda,
            int numCoperti);
            
    public void removePrenotazione(int indice);

    public boolean confermaOrdine(LocalDate dataPrenotazione, List<MenuComponent> comanda,
    int numCoperti);
}
