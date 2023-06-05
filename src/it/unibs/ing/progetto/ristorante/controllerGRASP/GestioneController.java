package it.unibs.ing.progetto.ristorante.controllerGRASP;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unibs.ing.progetto.ristorante.interfacce.IGestore;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;

public class GestioneController {

    private IGestore model;

    public GestioneController(IGestore model) {
        this.model = model;
    }

    /**
     * Configurazione dei parametri
     * 
     * @param caricoLavoroPersona
     * @param nPosti
     * @param dataCorrente
     */
    public void setConfigurazioneRistorante(int caricoLavoroPersona, int nPosti, LocalDate dataCorrente) {
        int caricoLavoroRistorante = caricoLavoroPersona * nPosti;
        caricoLavoroRistorante += Math.floor(caricoLavoroRistorante / 100.0 * 20);
        model.setDataCorrente(dataCorrente);
        model.setNumeroPostiASedere(nPosti);
        model.setCaricoLavoroPerPersona(caricoLavoroPersona);
        model.setCaricoLavoroRistorante(caricoLavoroRistorante);
    }

    public Piatto piattoScelto(int index) {
        return model.piattoScelto(index);
    }

    /**
     * 
     * @return insieme bevande
     */
    public List<Prodotto> getInsiemeBevande() {
        return model.getInsiemeBevande();
    }

    /**
     * 
     * @return insieme generi extra
     */
    public List<Prodotto> getInsiemeGeneriExtra() {
        return model.getInsiemeGeneriExtra();
    }

    /**
     * Aggiunge un piatto al model
     * 
     * @param nomePiatto
     * @param porzioni
     * @param caricoLavoro
     * @param elencoIngredienti
     * @param periodi
     */
    public void aggiungiPiattoRicetta(String nomePiatto, int porzioni, int caricoLavoro,
            ArrayList<Prodotto> elencoIngredienti, ArrayList<Periodo> periodi) {
        model.addPiattoRicetta(elencoIngredienti, porzioni, caricoLavoro, nomePiatto, periodi);
    }

    /**
     * Recupera l'elenco dei piatti
     * 
     * @return
     */
    public List<Piatto> getElencoPiatti() {
        return model.getElencoPiatti();
    }

    /**
     * 
     * @return data corrente del sistema
     */
    public LocalDate getDataCorrente() {
        return model.getDataCorrente();
    }

    /**
     * Recupera l'elenco dei menu tematici
     * 
     * @return
     */
    public List<MenuTematico> getElencoMenuTematici() {
        return model.getElencoMenuTematici();
    }

    /**
     * Return carico lavoro per persona
     * 
     * @return
     */
    public int getCaricoLavoroPerPersona() {
        return model.getCaricoLavoroPerPersona();
    }

    /**
     * Aggiunge una bevanda
     * 
     * @param nomeBevanda
     * @param consumoProCapiteBevanda
     */
    public boolean aggiungiBevanda(String nomeBevanda, float consumoProCapiteBevanda) {
        if (model.esisteBevanda(nomeBevanda)) {
            model.addBevanda(nomeBevanda, consumoProCapiteBevanda);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Aggiunta genereExtra
     * 
     * @param nomeGenereExtra
     * @param consumoProCapiteGenereExtra
     */
    public boolean aggiungiGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra) {
        if (!model.esisteGenereExtra(nomeGenereExtra)) {
            model.addGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Aggiunge un menu tematico
     * 
     * @param nomeMenuTematico
     * @param elencoPiatti
     * @param periodi
     */
    public void aggiungiMenuTematico(String nomeMenuTematico, List<MenuComponent> elencoPiatti,
            List<Periodo> periodi) {
        int caricoLavoroMenuTematico = caricoLavoroPiatti(elencoPiatti);
        model.addMenuTematico(nomeMenuTematico, elencoPiatti, caricoLavoroMenuTematico, periodi);
    }

    /**
     * Calcolo del carico di lavoro
     * 
     * @param elencoPiatti
     * @return
     */
    private int caricoLavoroPiatti(List<MenuComponent> elencoPiatti) {
        int caricoLavoroMenuTematico = 0;
        for (MenuComponent p : elencoPiatti) {
            caricoLavoroMenuTematico += p.getCaricoLavoro();
        }
        return caricoLavoroMenuTematico;
    }

    /**
     * Return true se il nome è già presente nell'insieme bevande
     * 
     * @param nome
     * @return
     */
    public boolean esisteBevanda(String nome) {
        return model.esisteBevanda(nome);
    }
    
 // dato un piatto, controlla se l'aggiunta di tale piatto al menu tematico
 	// supererebbe il carico lavoro massimo di un menu tematico
 	// se non lo supera ritorna true
 	public boolean controllaCaricoLavoroPiattoPerMenuTematico(Piatto piatto, int caricoLavoroMenuTematico,
 			int caricoLavoroMax) {
 		boolean piattoValido = false;
 		if (caricoLavoroMenuTematico + piatto.getCaricoLavoro() <= caricoLavoroMax) {
 			return true;
 		} 
 		return piattoValido;
 	}

    /**
     * Return true se il nome è gia presente nell'insieme genere extra
     * 
     * @param nome
     * @return
     */
    public boolean esisteGenereExtra(String nome) {
        return model.esisteGenereExtra(nome);
    }

    /**
     * Aggiorna giorno corrente
     */
    public void giornoDopo() {
        model.giornoDopo();
    }

    public int getNumeroPostiASedere() {
        return model.getNumeroPostiASedere();
    }

    public int getCaricoLavoro() {
        return model.getCaricoLavoroPerPersona();
    }

    public int getCaricoLavoroRistorante() {
        return model.getCaricoLavoroRistorante();
    }

    public boolean piattoEsistente(String nome){
        List<Piatto> piatti = model.getElencoPiatti();
        for (Piatto p : piatti) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return true;
            }
        }
        return false;
    }

}
