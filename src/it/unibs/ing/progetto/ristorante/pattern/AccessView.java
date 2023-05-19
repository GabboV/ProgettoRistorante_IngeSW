package it.unibs.ing.progetto.ristorante.pattern;

import it.unibs.fp.mylib.MyMenu;

public class AccessView {

    /**
     *
     */
    private static final int LOGIN_MAGAZZINO = 3;
    private static final int LOGIN_PRENOTAZIONE = 2;
    private static final int LOGIN_GESTIONE = 1;
    private static final int LOGOUT = 0;
    /**
     *
     */
    private static final String LOGIN = "Login";
    private static final String MSG_BENVENUTO = "Benvenuto nel programma di supporto del tuo ristorante.";
    private static final String HAI_EFFETTUATO_IL_LOGOUT = "Hai effettuato il Logout";
    private static final String[] ELENCO_RUOLI = { "Gestore", "Addetto Prenotazioni", "Magazziniere" };

    private GestioneView gestioneView;
    private PrenotazioneView prenotazioneView;
    private MagazzinoView magazzinoView;

    public AccessView(GestioneView gestioneView, PrenotazioneView prenotazioneView, MagazzinoView magazzinoView) {
        this.gestioneView = gestioneView;
        this.prenotazioneView = prenotazioneView;
        this.magazzinoView = magazzinoView;
    }

    public void richiestaAccessoLogin() {
        System.out.println(MSG_BENVENUTO);
        MyMenu menuAccesso = new MyMenu(LOGIN, ELENCO_RUOLI);
        boolean attiva = true;
        do {
            int ruolo = menuAccesso.scegli();
            switch (ruolo) {
                case LOGOUT:
                    attiva = false;
                    break;
                case LOGIN_GESTIONE:
                    gestioneView.gestioneMenu();
                    break;
                case LOGIN_PRENOTAZIONE:
                    prenotazioneView.gestioneMenu();
                    break;
                case LOGIN_MAGAZZINO:
                    magazzinoView.gestioneMenu();
                    break;
            }
        } while (attiva);
        System.out.println(HAI_EFFETTUATO_IL_LOGOUT);
    }

}
