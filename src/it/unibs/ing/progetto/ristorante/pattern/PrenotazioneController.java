package it.unibs.ing.progetto.ristorante.pattern;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import it.unibs.ing.progetto.ristorante.interfacce.IPrenotazioni;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;

public class PrenotazioneController {

    private PrenotazioneView view;
    private IPrenotazioni model;

    public PrenotazioneController(PrenotazioneView view, IPrenotazioni model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Recupera l'elenco delle prenotazioni
     * 
     * @return prenotazioni
     */
    public List<Prenotazione> getElencoPrenotazioni() {
        return model.getElencoPrenotazioni();
    }

    /**
     * Passa i parametri di una nuova prenotazione al modello
     * 
     * @param data
     * @param comanda
     * @param coperti
     */
    public void addPrenotazione(String cliente, LocalDate data, List<MenuComponent> comanda, int coperti) {
        if(confermaOrdine(data, comanda, coperti)){
            model.addPrenotazione(cliente, data, toHashMap(comanda), coperti);
        } else {
            view.stampaMsgPrenotazioneRifiutata();
        }
        
    }

    /**
     * Rimuove una prenotazione dall'elenco indicato dal indice nell'elenco
     * 
     * @param index
     */
    public void removePrenotazione(int index) {
        model.removePrenotazione(index);
    }

    public boolean giornoValido(LocalDate date) {
        boolean almenoUnGiornoFeriale = almenoUnGiornoFeriale(model.getDataCorrente(), date);
        boolean feriale = isGiornoFeriale(date);
        boolean menu = model.ciSonoMenuTematiciValidiInData(date);
        boolean piatti = model.ciSonoPiattiValidiInData(date);
        boolean disponibilita = !model.isRistorantePienoInData(date);
        return (almenoUnGiornoFeriale && feriale && (menu || piatti) && disponibilita);
    }

    /**
     * Controlla se c'e' almeno un giorno feriale di differenza tra due date
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean almenoUnGiornoFeriale(LocalDate date1, LocalDate date2) {
        LocalDate nextWorkingDay = getGiornoFerialeSuccessivo(date1);
        return date2.isAfter(nextWorkingDay) || date2.isEqual(nextWorkingDay);
    }

    /**
     * Calcola il giorno feriale successivo
     * 
     * @param date
     * @return
     */
    private static LocalDate getGiornoFerialeSuccessivo(LocalDate date) {
        do {
            date = date.plusDays(1);
        } while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
        return date;
    }

    /**
     * Restituisce true se la data is giorno feriale
     * 
     * @param data
     * @return
     */
    private boolean isGiornoFeriale(LocalDate data) {
        DayOfWeek dayOfWeek = data.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public List<Piatto> getPiattiInData(LocalDate date) {
        return model.getMenuCartaInData(date);
    }

    public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
        return model.getMenuTematiciInData(date);
    }

    public int getPostiLiberiInData(LocalDate date) {
        return model.getPostiDisponibiliInData(date);
    }

    public boolean accettabilita(LocalDate date, int coperti, HashMap<Piatto, Integer> comanda) {
        return model.verificaAccettabilitaPrenotazione(date, comanda, coperti);
    }

    public boolean confermaOrdine(LocalDate dataPrenotazione, List<MenuComponent> ordine, int numCoperti) {
        return model.confermaOrdine(dataPrenotazione, ordine, numCoperti);
    }

    private HashMap<Piatto, Integer> toHashMap(List<MenuComponent> list) {
        HashMap<Piatto, Integer> comanda = new HashMap<>();
        for (MenuComponent m : list) {
            for (Piatto p : m.getContenuto()) {
                if (comanda.containsKey(p)) {
                    int oldValue = comanda.get(p);
                    comanda.replace(p, oldValue + 1);
                } else {
                    comanda.put(p, 1);
                }
            }
        }
        return comanda;
    }
}
