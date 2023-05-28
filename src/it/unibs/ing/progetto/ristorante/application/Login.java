package it.unibs.ing.progetto.ristorante.application;

import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.AddettoPrenotazioniController;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.GestoreController;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.MagazziniereController;

public class Login {

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

    private GestoreController gestoreController;
    private AddettoPrenotazioniController addettoPrenotazioniController;
    private MagazziniereController magazziniereController;

    public Login(GestoreController gestoreController, AddettoPrenotazioniController addettoPrenotazioniController,
            MagazziniereController magazziniereController) {
        this.gestoreController = gestoreController;
        this.addettoPrenotazioniController = addettoPrenotazioniController;
        this.magazziniereController = magazziniereController;
    }

    public void accessoLogin() {
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
                gestoreController.avviaSessione();
                    break;
                case LOGIN_PRENOTAZIONE:
                addettoPrenotazioniController.avviaSessione();
                    break;
                case LOGIN_MAGAZZINO:
                magazziniereController.avviaSessione();
                    break;
            }
        } while (attiva);
        System.out.println(HAI_EFFETTUATO_IL_LOGOUT);
    }

}
