package it.unibs.ing.progetto.ristorante.pattern;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Iterator;
import java.util.Map.Entry;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;

public class PrenotazioneView {

    /**
     *
     */
    private static final String ADDETTO_PRENOTAZIONI = "Addetto prenotazioni menu";
    private static final String VISUALIZZA_PRENOTAZIONI = "Visualizza prenotazioni";
    private static final String RIMUOVI_PRENOTAZIONE = "Rimuovi prenotazione";
    private static final String AGGIUNGI_PRENOTAZIONE = "Aggiungi prenotazione";
    /*
     * 
     */
    private static final int VISUALIZZA_PRENOTAZIONI_MENU = 3;
    private static final int RIMUOVI_PRENOTAZIONE_MENU = 2;
    private static final int AGGIUNGI_PRENOTAZIONE_MENU = 1;
    private static final int LOGOUT = 0;

    private static String[] OPZIONI = new String[] { AGGIUNGI_PRENOTAZIONE, RIMUOVI_PRENOTAZIONE,
            VISUALIZZA_PRENOTAZIONI };

    private PrenotazioneController controller;

    public PrenotazioneView(PrenotazioneController controller) {
        this.controller = controller;
    }

    public void gestioneMenu() {
        MyMenu menu = new MyMenu(ADDETTO_PRENOTAZIONI, OPZIONI);
        boolean sessioneON = true;
        do {
            int scelta = menu.scegli();
            switch (scelta) {
                case LOGOUT:
                    sessioneON = false;
                    break;
                case AGGIUNGI_PRENOTAZIONE_MENU:
                    this.inserisciPrenotazione();
                    break;
                case RIMUOVI_PRENOTAZIONE_MENU:
                    this.removePrenotazione();
                    break;
                case VISUALIZZA_PRENOTAZIONI_MENU:
                    this.visualizzaPrenotazioni();
                    break;
                default:
                    break;
            }
        } while (sessioneON);
    }

    /**
     * Richiede gli input per inserire una prenotazione
     */
    public void inserisciPrenotazione() {
        System.out.println("Inserimento prenotazione");
        String cliente = InputDati.leggiStringaNonVuota("Inserire il nome del cliente > ");
        LocalDate dataPrenotazione = InputDati.leggiData("Inserisci la data della prenotazione");
        if (controller.giornoValido(dataPrenotazione)) {
            stampaRiepilogo(dataPrenotazione);
            boolean conferma = InputDati.yesOrNo("Proseguire con la prenotazione?");
            if (conferma) {
                int coperti = InputDati.leggiIntero("Inserisci il numero di posti da riservare > ", 1,
                        controller.getPostiLiberiInData(dataPrenotazione));
                List<Piatto> piattiOrdinabili = controller.getPiattiInData(dataPrenotazione);
                List<MenuTematico> menuOrdinabili = controller.getMenuTematiciInData(dataPrenotazione);
                if (!(piattiOrdinabili.isEmpty() && menuOrdinabili.isEmpty())) {
                    List<MenuComponent> ordine = enterOrdine(coperti, piattiOrdinabili, menuOrdinabili);
                    if (controller.confermaOrdine(dataPrenotazione, ordine, coperti)) {
                        controller.addPrenotazione(cliente, dataPrenotazione, ordine, coperti);
                        System.out.println("Prenotazione effettuata");
                    } else {
                        System.out.println("Non e' stato possibile registrare la prenotazione");
                    }
                } else {
                    System.out.println("Non ci sono elementi ordinabili");
                }
            } else {
                System.out.println("Operazione conclusa");
            }
        } else {
            System.out.println("La data inserita non e' valida secondo i criteri di accettazione, probabilmente hai scelto un giorno non feriale");
        }
    }

    public List<MenuComponent> enterOrdine(int coperti, List<Piatto> carta, List<MenuTematico> menu) {
        ArrayList<MenuComponent> ordine = new ArrayList<>();
        for (int i = 0; i < coperti; i++) {
            System.out.println("Ordinazione cliente: " + i);
            boolean finito = false;
            do {
                int scelta = sceltaOrdine();
                switch (scelta) {
                    case 1:
                        if (!carta.isEmpty()) {
                            ordine.addAll(enterSingoloOrdineCarta(carta));
                            finito = true;
                        } else {
                            System.out.println("Non ci sono piatti nel menu alla carta");
                        }
                        break;
                    case 2:
                        if (!menu.isEmpty()) {
                            ordine.add(enterSingoloOrdineMenuTematico(menu));
                            finito = true;
                        } else {
                            System.out.println("Non ci sono menu tematici da scegliere");
                        }
                        break;
                }

            } while (!finito);
        }

        return ordine;
    }

    private List<Piatto> enterSingoloOrdineCarta(List<Piatto> carta) {
        ArrayList<Piatto> ordine = new ArrayList<>();
        stampaMenuCarta(carta);
        boolean ancora = false;
        do {
            int scelto = InputDati.leggiIntero("Scegli un piatto (indice) > ", 0, carta.size() - 1);
            ordine.add(carta.get(scelto));
            ancora = InputDati.yesOrNo("Aggiungere altro? ");
        } while (ancora);
        return ordine;
    }

    private MenuTematico enterSingoloOrdineMenuTematico(List<MenuTematico> menu) {
        stampaMenuTematici(menu);
        int scelta = InputDati.leggiIntero("Scegli il menu tematico (indice) > ", 0, menu.size() - 1);
        return menu.get(scelta);
    }

    public void stampaMenuTematici(List<MenuTematico> menu) {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(i + " - " + menu.get(i).getNome());
        }
    }

    public void stampaMenuCarta(List<Piatto> carta) {
        for (int i = 0; i < carta.size(); i++) {
            System.out.println(i + " - " + carta.get(i).getNomePiatto());
        }
    }

    private int sceltaOrdine() {
        System.out.println("Scegli un opzione:  ");
        System.out.println("1 - Menu alla Carta ");
        System.out.println("2 - Menu tematici   ");
        int scelta = InputDati.leggiIntero("Digita il menu da cui ordinare > ", 1, 2);
        return scelta;
    }

    /**
     * Stampa il riepilogo di una giornata
     * 
     * @param date
     */
    public void stampaRiepilogo(LocalDate date) {
        int postiLiberi = controller.getPostiLiberiInData(date);
        int numeroPiatti = controller.getPiattiInData(date).size();
        int numeroMenu = controller.getMenuTematiciInData(date).size();
        System.out.println("Riepilogo giorno:  " + date);
        System.out.println("Posti liberi:      " + postiLiberi);
        System.out.println("Piatti ordinabili: " + numeroPiatti);
        System.out.println("Menu ordinabili:   " + numeroMenu);
    }

    /**
     * Inserimento input per rimuovere una prenotazione
     */
    public void removePrenotazione() {
        List<Prenotazione> prenotazioni = controller.getElencoPrenotazioni();
        if (!prenotazioni.isEmpty()) {
            int index = InputDati.leggiIntero("Indica la prenotazione da rimuovere > ", 0, prenotazioni.size() - 1);
            controller.removePrenotazione(index);
        } else {
            System.out.println("Non sono presenti prenotazioni");
        }
    }

    /**
     * Gestisce la stampa delle prenotazioni
     */
    public void visualizzaPrenotazioni() {
        List<Prenotazione> prenotazioni = controller.getElencoPrenotazioni();
        if (!prenotazioni.isEmpty()) {
            printPrenotazioni(prenotazioni);
        } else {
            System.out.println("Non sono presenti prenotazioni");
        }
    }

    /**
     * Stampa prenotazioni
     * 
     * @param prenotazioni
     */
    public void printPrenotazioni(List<Prenotazione> prenotazioni) {
        int contatore = 0;
        if (!prenotazioni.isEmpty()) {
            System.out.println();
            for (Prenotazione p : prenotazioni) {
                System.out.println("+-------------------------+ " + contatore + " +-------------------------+");
                this.stampaPrenotazione(p);
                contatore++;
            }
        } else {
            System.out.println("Non ci sono prenotazioni");
        }

    }

    /**
     * Stampa prenotazione
     * 
     * @param prenotazione
     */
    public void stampaPrenotazione(Prenotazione prenotazione) {
        String codice_cliente = prenotazione.getCodiceCliente();
        LocalDate data = prenotazione.getDataPrenotazione();
        int day = data.getDayOfMonth(), month = data.getMonthValue(), year = data.getYear();
        int coperti = prenotazione.getNumeroCoperti();
        HashMap<Piatto, Integer> comanda = prenotazione.getComanda();
  //      System.out.println("+-------------------------------------------------------+");
        System.out.printf("| Data: %d-%d-%d    Cliente: %-9s    Coperti: %-4d |\n", day, month, year, codice_cliente, coperti);
        System.out.println("+-------------------------------------------------------+");
        System.out.println(buildComandaString(comanda));
        System.out.println("+-------------------------------------------------------+");
        System.out.println();
    }

    /**
     * Restituisce una comanda formattata
     * 
     * @param hash
     * @return
     */
    public String buildComandaString(HashMap<Piatto, Integer> hash) {
        StringBuilder builder = new StringBuilder();
        Iterator<Entry<Piatto, Integer>> iter = hash.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<Piatto, Integer> entry = iter.next();
            Piatto p = entry.getKey();
            int i = entry.getValue();
            String voce = String.format("| Piatto: %-17s   carico: %d   porzioni: %3d |", p.getNomePiatto(),
                    p.getCaricoLavoro(), i);
            builder.append(voce);
            if (iter.hasNext())
                builder.append("\n");
        }
        return builder.toString();
    }

    public void setController(PrenotazioneController controller) {
        this.controller = controller;
    }

    public void stampaMsgPrenotazioneRifiutata() {
        System.out.println("Prenotazione non effettuabile");
    }

}
