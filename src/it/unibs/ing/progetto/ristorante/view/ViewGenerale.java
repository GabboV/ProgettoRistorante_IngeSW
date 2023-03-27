package it.unibs.ing.progetto.ristorante.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.fp.mylib.ServizioFile;
import it.unibs.ing.progetto.ristorante.XML.ReaderXMLRistorante;
import it.unibs.ing.progetto.ristorante.controller.AddettoPrenotazioniController;
import it.unibs.ing.progetto.ristorante.controller.GestoreController;
import it.unibs.ing.progetto.ristorante.controller.MagazziniereController;
import it.unibs.ing.progetto.ristorante.dati.BoxMemoria;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class ViewGenerale {

	private static final String MODEL_IS_NULL = "Impossibile salvare i parametri del ristorante";
	private static final String NO_DATI_IN_MEMORIA = "A quanto risulta non esistono elementi in memoria";
	private static final String DATI_IN_MEMORIA = "Sono presenti dei dati salvati in memoria, vuoi caricarli? ";
	private static final int ELIMINA_DATI = 2;
	private static final int SALVA_MODIFICHE = 1;
	private static final String ERRORE_RECUPERO_DATI = "Errore nel recupero dei dati da memoria";
	final private static String MSG_BENVENUTO = "Benvenuto nel programma di supporto del tuo ristorante.";
	final private static String TITOLO_SCHERMATA_LOGIN = "PAGINA DI LOGIN";
	final private static String TITOLO_SCELTA_DATI = "OPZIONI DI AVVIO";
	final private static String[] ELENCO_RUOLI = { "GESTORE", "ADDETTO PRENOTAZIONI", "MAGAZZINIERE" };
	final private static int GESTORE = 1;
	private static final int ADDETTO_PRENOTAZIONI = 2;
	private static final int MAGAZZINIERE = 3;
	private static final int ESCI = 0;
	final private static String[] OPZIONI_DATI = { "Avvio da zero (necessita inizializzazione da parte del gestore)", 
			"Avvio da dati predefiniti", "Avvio da ultimo salvataggio",};
	final private static int AVVIO_DA_ZERO = 1;
	private static final int AVVIO_DA_PREDEFINITO = 2;
	private static final int AVVIO_DA_ULTIMO_SALVATAGGIO = 3;
	final private static String[] OPZIONI_FILE = { "Salva ultime modifiche", "Cancella dati" };
	final private static String MSG_ARRESTO_PROGRAMMA = "Arresto programma...";
	public final static String PATH_XML_RISTORANTE = "src/xmlFile/Ristorante.xml";

	public void avvioProgramma() {

		System.out.println(MSG_BENVENUTO);
		File file_memoria = new File("ristorante.dat");
		Ristorante model = avvioConSceltaInizializzazione(file_memoria);

		this.salvataggioDati(model, file_memoria);
		System.out.println();
		System.out.println(MSG_ARRESTO_PROGRAMMA);
	}

	public int sceltaDatiAvvio() {
		MyMenu menu = new MyMenu(TITOLO_SCELTA_DATI, OPZIONI_DATI);
		int scelta = menu.scegli();
		return scelta;
	}
	
	//viene chiesto all'utente con quali dati si vuole utilizzare il programma
	//se si sceglie AVVIO_DA_ZERO il gestore deve inizializzare i parametri del ristorante a mano
	//se si sceglie AVVIO DA PREDEFINITO si utilizzano come parametri del ristorante quelli del file Ristorante.xml
	//se si sceglie AVVIO_DA_ULTIMO_SALVATAGGIO si utilizzano i parametri che salvati nel BoxMemoria
	public Ristorante avvioConSceltaInizializzazione(File file_memoria) {
		System.out.println();
		Ristorante model = null;
		boolean altraOpzione = true;
		do {
			int scelta = sceltaDatiAvvio();
			switch (scelta) {
			case AVVIO_DA_ZERO:
				boolean risposta = identificazioneGestore();
				if(risposta) {
					model = loginInizializzazione();
					loginUtente(model);
				}
				break;
			case AVVIO_DA_PREDEFINITO:
				model = ReaderXMLRistorante.leggiXML(PATH_XML_RISTORANTE);
				loginUtente(model);
				altraOpzione = false;
				break;
			case AVVIO_DA_ULTIMO_SALVATAGGIO:
				model = recuperaDatiDaMemoria(file_memoria);
				if (model != null) {
					loginUtente(model);
				} else {
					System.out.println(MODEL_IS_NULL);
				}
				altraOpzione = false;
				break;
			case ESCI:
				altraOpzione = false;
				break;
			}
		} while (altraOpzione);
		return model;
	}
	
	//chiede all'utente se si identifica come Gestore, e ritorna la risposta come true o false
	public boolean identificazioneGestore() {
		System.out.println("Per la fase di inizializzazione del programma e' necessario il login del Gestore.");
		boolean risposta = InputDati.yesOrNo("Sei il Gestore? ");
		if (risposta == false) {
			System.out.println("Bisogna essere il Gestore per poter inizializzare il programma.");
		}
		return risposta;
	}
	
	// permette al gestore di inizializzare i parametri del ristorante al primo avvio
	public Ristorante loginInizializzazione() {
		Ristorante model = null;
		GestoreController gestore = new GestoreController(model);
		model = gestore.inizializzaRistorante();
		return model;
	}

	public Ristorante caricaDatiDaMemoria() {
		Ristorante model = null;
		File file_memoria = new File("ristorante.dat");
		if (file_memoria.exists()) {
			boolean caricaMemoria = InputDati.yesOrNo(DATI_IN_MEMORIA);
			if (caricaMemoria) {
				try {
					BoxMemoria memoriaBox = (BoxMemoria) ServizioFile.caricaSingoloOggetto(file_memoria);
					model = memoriaBox.getRistorante();
				} catch (NullPointerException e) {
					System.out.println();
				}
			}
		}
		return model;
	}

	public void loginUtente(Ristorante model) {
		MyMenu menu = new MyMenu(TITOLO_SCHERMATA_LOGIN, ELENCO_RUOLI);
		System.out.println();
		boolean appAttiva = true;
		do {
			int scelta = menu.scegli();
			switch (scelta) {
				case GESTORE:
					GestoreController gestore = new GestoreController(model);
					gestore.avviaSessione();
					break;
				case ADDETTO_PRENOTAZIONI:
					AddettoPrenotazioniController addettoPrenotazioni = new AddettoPrenotazioniController(model);
					addettoPrenotazioni.avviaSessione();
					break;
				case MAGAZZINIERE:
					MagazziniereController magazziniere = new MagazziniereController(model);
					magazziniere.avviaSessione();
					break;
				case ESCI:
					appAttiva = false;
					break;
			}
		} while (appAttiva);
	}

	//recuper i dati dal file_memoria e ritorna il model inizializzato con tali dati
	public Ristorante recuperaDatiDaMemoria(File file_memoria) {
		Ristorante model = null;
		if (file_memoria.exists()) {
			try {
				BoxMemoria memoriaBox = (BoxMemoria) ServizioFile.caricaSingoloOggetto(file_memoria);
				model = memoriaBox.getRistorante();
				System.out.println("I dati dal file di memoria sono stati caricati nel programma.");
			} catch (NullPointerException e) {
				System.out.println(ERRORE_RECUPERO_DATI);
			}
		} else {
			System.out.println(NO_DATI_IN_MEMORIA);
		}
		return model;
	}
	
	//opzioni per il salvataggio dei parametri del ristorante in un file di memoria
	public void salvataggioDati(Ristorante model, File memoria_file) {
		MyMenu menu = new MyMenu("Gestione memoria", OPZIONI_FILE);
		boolean appAttiva = true;
		do {
			int scelta = menu.scegli();
			switch (scelta) {
			case ESCI:
				appAttiva = false;
				break;
			case SALVA_MODIFICHE:
				if (model != null) {
					BoxMemoria memoria_new = new BoxMemoria(model);
					ServizioFile.salvaSingoloOggetto(memoria_file, memoria_new);
					System.out.println("Dati salvati");
				} else System.out.println("Non ci sono elementi da salvare");
				break;
			case ELIMINA_DATI:
				if (memoria_file.exists()) {
					System.out
					.println(
							"ATTENZIONE: eliminando i dati, non si avra piu la possibilita di recuperarli\n");
					boolean conferma = InputDati.yesOrNo("Vuoi confermare la tua scelta? ");
					if (conferma) {
						memoria_file.delete();
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
	}
}
