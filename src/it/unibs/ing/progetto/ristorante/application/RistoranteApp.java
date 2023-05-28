package it.unibs.ing.progetto.ristorante.application;

import java.io.File;

import it.unibs.ing.progetto.ristorante.UI.controllerMVC.AddettoPrenotazioniController;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.GestoreController;
import it.unibs.ing.progetto.ristorante.UI.controllerMVC.MagazziniereController;
import it.unibs.ing.progetto.ristorante.controllerGRASP.GestioneController;
import it.unibs.ing.progetto.ristorante.controllerGRASP.MagazzinoController;
import it.unibs.ing.progetto.ristorante.controllerGRASP.PrenotazioneController;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class RistoranteApp {

    private static final String RISTORANTE_DAT = "ristorante.dat";

    public void run() {
        File file = new File(RISTORANTE_DAT);
        DataManager manager = new DataManager();
        Ristorante model = manager.menuAvvioDatiRistorante(file);
        Login login = buildLogin(model);
        login.accessoLogin();
        manager.menuGestioneMemoria(model, file);
    }

    private Login buildLogin(Ristorante model) {
        if (model != null) {
            GestioneController gestioneController = new GestioneController(model);
            PrenotazioneController prenotazioneController = new PrenotazioneController(model);
            MagazzinoController magazzinoController = new MagazzinoController(model);

            GestoreController gestoreController = new GestoreController(gestioneController);
            AddettoPrenotazioniController addettoPrenotazioniController = new AddettoPrenotazioniController(prenotazioneController);
            MagazziniereController magazziniereController = new MagazziniereController(magazzinoController);

            return new Login(gestoreController, addettoPrenotazioniController, magazziniereController);
        } else {
            throw new NullPointerException("Model is null, non e' possibile costruire le dipendenze necessarie");
        }
    }

}
