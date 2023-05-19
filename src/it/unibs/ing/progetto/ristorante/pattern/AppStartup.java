package it.unibs.ing.progetto.ristorante.pattern;

import java.io.File;

import it.unibs.ing.progetto.ristorante.model.Ristorante;

public class AppStartup {

    private static final String RISTORANTE_DAT = "ristorante.dat";

    public AppStartup() {
        super();
    }

    public void runApp() {
        File file = new File(RISTORANTE_DAT);
        DataManager manager = new DataManager();
        Ristorante model = manager.menuAvvioDatiRistorante(file);
        AccessView accessView = buildAccessView(model);
        accessView.richiestaAccessoLogin();
        manager.menuGestioneMemoria(model, file);
    }

    private AccessView buildAccessView(Ristorante model) {
        if (model != null) {
            GestioneView gestioneView = new GestioneView(null);
            PrenotazioneView prenotazioneView = new PrenotazioneView(null);
            MagazzinoView magazzinoView = new MagazzinoView(null);
            dependencySolverGestione(model, gestioneView);
            dependencySolverPrenotazioni(model, prenotazioneView);
            dependencySolverMagazzino(model, magazzinoView);
            AccessView accessView = new AccessView(gestioneView, prenotazioneView, magazzinoView);
            return accessView;
        } else {
            throw new NullPointerException("Model is null, non e' possibile costruire le dipendenze necessarie");
        }
    }

    private GestioneController dependencySolverGestione(Ristorante model, GestioneView view) {
        GestioneController gestioneController = new GestioneController(model, view);
        view.setController(gestioneController);
        return gestioneController;
    }

    private PrenotazioneController dependencySolverPrenotazioni(Ristorante model, PrenotazioneView view) {
        PrenotazioneController prenotazioniController = new PrenotazioneController(view, model);
        view.setController(prenotazioniController);
        return prenotazioniController;
    }

    private MagazzinoController dependencySolverMagazzino(Ristorante model, MagazzinoView view) {
        MagazzinoController magazzinoController = new MagazzinoController(model, view);
        view.setController(magazzinoController);
        return magazzinoController;
    }

}
