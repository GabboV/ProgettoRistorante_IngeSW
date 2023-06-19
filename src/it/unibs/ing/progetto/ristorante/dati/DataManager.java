package it.unibs.ing.progetto.ristorante.dati;

import java.io.File;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.ServizioFile;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.GestoreController;
import it.unibs.ing.progetto.ristorante.XML.ReaderXMLRistorante;
import it.unibs.ing.progetto.ristorante.controllerGRASP.GestioneController;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class DataManager {

    private static final String DATI_IN_MEMORIA = "Sono presenti dei dati salvati in memoria, vuoi caricarli? ";
    private static final int ELIMINA_DATI = 2;
    private static final int SALVA_MODIFICHE = 1;
    private static final String ERRORE_RECUPERO_DATI = "Errore nel recupero dei dati da memoria";
    private static final String TITOLO_SCELTA_DATI = "Opzioni di Avvio";
    private static final String[] OPZIONI_DATI = { "Avvio da zero (necessita inizializzazione da parte del gestore)",
            "Avvio da dati predefiniti", "Avvio da ultimo salvataggio" };
    private static final int AVVIO_DA_ZERO = 1;
    private static final int AVVIO_DA_PREDEFINITO = 2;
    private static final int AVVIO_DA_ULTIMO_SALVATAGGIO = 3;
    private static final String[] OPZIONI_FILE = { "Salva ultime modifiche", "Cancella dati" };
    private static final String PATH_XML_RISTORANTE = "src/xmlFile/Ristorante.xml";
    final private static String CORNICE = "--------------------------------";

  
    public Ristorante menuAvvioDatiRistorante(File fileDat) {
        System.out.println();
        Ristorante model = new Ristorante();
        boolean altraOpzione = true;
        do {
            int opzione = scegli(TITOLO_SCELTA_DATI, OPZIONI_DATI);
            switch (opzione) {
                case AVVIO_DA_ZERO:
                    boolean risposta = identificazioneGestore();
                    if (risposta) {
                        model = primaConfigurazione();
                        altraOpzione = false;
                    } else {
                        System.out.println("Bisogna essere il Gestore per poter inizializzare il programma.");
                    }
                    break;
                case AVVIO_DA_PREDEFINITO:
                    model = ReaderXMLRistorante.leggiXML(PATH_XML_RISTORANTE);
                    if (model != null) {
                        altraOpzione = false;
                    } else {
                        System.out.println("Ci sono stati problemi nella lettura del file XML");
                    }
                    break;
                case AVVIO_DA_ULTIMO_SALVATAGGIO:
                    model = this.caricaDatiDaMemoria(fileDat);
                    if (model != null) {
                        altraOpzione = false;
                    } else {
                        System.out.println("Caricamento non effettuato");
                    }
                    break;
            }
        } while (altraOpzione);
        return model;
    }

    public void menuGestioneMemoria(Ristorante model, File fileDat) {
        System.out.println();
        MyMenu menu = new MyMenu("Gestione memoria", OPZIONI_FILE);
        boolean appAttiva = true;
        do {
            int scelta = menu.scegli();
            switch (scelta) {
                case 0:
                    appAttiva = false;
                    break;
                case SALVA_MODIFICHE:
                    if (model != null) {
                        BoxMemoria memoria_new = new BoxMemoria(model);
                        ServizioFile.salvaSingoloOggetto(fileDat, memoria_new);
                        System.out.println("\nDati salvati\n");
                    } else {
                        System.out.println("Non ci sono elementi da salvare!!");
                    }
                    break;
                case ELIMINA_DATI:
                    if (fileDat.exists()) {
                        System.out
                                .println(
                                        "ATTENZIONE: eliminando i dati, non si avra piu la possibilita di recuperarli\n");
                        boolean conferma = InputDati.yesOrNo("Vuoi confermare la tua scelta? ");
                        if (conferma) {
                            fileDat.delete();
                            model = null; // Si elimina l'unico riferimento al modello e si lascia il lavoro al Garbage
                            // collector di eliminare i dati
                            System.out.println("File eliminato");
                        } else {
                            System.out.println("Hai annullato l'operazione");
                        }
                    } else {
                        System.out.println("Non esistono file da eliminare, forse li hai gia eliminati");
                    }
                    break;
                default:
                    appAttiva = false;
                    System.out.println(ERRORE_RECUPERO_DATI);
                    break;
            }
        } while (appAttiva);
        System.out.println("Chiusura applicazione...");
    }

    /**
     * Chiede all'utente se si identifica come Gestore, e ritorna la risposta
     * come true o false
     * 
     * @return
     */
    public boolean identificazioneGestore() {
        System.out.println("Per la fase di inizializzazione del programma e' necessario il login del Gestore.");
        boolean risposta = InputDati.yesOrNo("Sei il Gestore? ");
        return risposta;
    }

    // permette al gestore di inizializzare i parametri del ristorante al primo
    // avvio
    public Ristorante primaConfigurazione() {
        Ristorante model = new Ristorante();
        GestioneController controller = new GestioneController(model);
        GestoreController gestore = new GestoreController(controller);

        gestore.inizializzaRistorante();
        System.out.println("E' necessario inserire almeno una ricetta.");
        gestore.aggiungiPiattoRicetta(model.getElencoPiatti());
        System.out.println("Hai completato l'inizializzazione del programma.");
        return model;
    }

    /**
     * Carica i dati dalla memoria
     * 
     * @param file_memoria
     * @return
     */
    public Ristorante caricaDatiDaMemoria(File fileDat) {
        Ristorante model = null;
        if (ServizioFile.fileContieneDati(fileDat)) {
            boolean caricaMemoria = InputDati.yesOrNo(DATI_IN_MEMORIA);
            if (caricaMemoria) {
                try {
                    BoxMemoria memoriaBox = (BoxMemoria) ServizioFile.caricaSingoloOggetto(fileDat);
                    model = memoriaBox.getRistorante();
                } catch (NullPointerException e) {
                    System.out.println("Problemi nel caricamento dei dati, riavviare da zero il programma");
                }
            }
        } else {
            System.out.println("Non ci sono dati salvati");
        }
        return model;
    }

    public int scegli(String titolo, String[] vocString) {
        stampaMenu(titolo, vocString);
        return InputDati.leggiIntero("Digita il numero dell'opzione desiderata > ", 1, vocString.length);
    }

    public void stampaMenu(String titolo, String[] vocString) {
        System.out.println(CORNICE);
        System.out.println(titolo);
        System.out.println(CORNICE);
        for (int i = 0; i < vocString.length; i++) {
            System.out.println((i + 1) + " - " + vocString[i]);
        }
        System.out.println();
    }

}
