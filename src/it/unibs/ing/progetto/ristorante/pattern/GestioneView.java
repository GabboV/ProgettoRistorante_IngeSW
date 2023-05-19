package it.unibs.ing.progetto.ristorante.pattern;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class GestioneView {

	private static final String VISUALIZZA_GENERI_EXTRA = "Visualizza generi extra";
	private static final String VISUALIZZA_BEVANDE = "Visualizza bevande";
	private static final String VISUALIZZA_MENU_TEMATICI = "Visualizza menu tematici";
	private static final String VISUALIZZA_RICETTE = "Visualizza ricette";
	private static final String VISUALIZZA_PARAMETRI = "Visualizza parametri";
	private static final String AGGIUNGI_GENERE_EXTRA = "Aggiungi genere extra";
	private static final String AGGIUNGI_BEVANDA = "Aggiungi bevanda";
	private static final String AGGIUNGI_MENU_TEMATICO = "Aggiungi menu tematico";
	private static final String AGGIUNGI_RICETTA = "Aggiungi ricetta";
	final static private String[] ELENCO_COMANDI = { AGGIUNGI_RICETTA, AGGIUNGI_MENU_TEMATICO, AGGIUNGI_BEVANDA,
			AGGIUNGI_GENERE_EXTRA, VISUALIZZA_PARAMETRI, VISUALIZZA_RICETTE, VISUALIZZA_MENU_TEMATICI,
			VISUALIZZA_BEVANDE, VISUALIZZA_GENERI_EXTRA, "Giorno dopo" };

	private GestioneController controller;

	public GestioneView(GestioneController controller) {
		super();
		this.controller = controller;
	}

	public void gestioneMenu() {
		boolean sessioneOn = true;
		MyMenu menu = new MyMenu("Gestore menu", ELENCO_COMANDI);
		do {
			int scelta = menu.scegli();
			switch (scelta) {
				case 1:
					enterPiattoRicetta();
					break;
				case 2:
					enterMenuTematico();
					break;
				case 3:
					enterBevanda();
					break;
				case 4:
					enterGenereExtra();
					break;
				case 5:
					stampaParametriRistorante(controller.getDataCorrente(), controller.getNumeroPostiASedere(), controller.getCaricoLavoroPerPersona(), controller.getCaricoLavoroRistorante());
					break;
				case 6:
					stampaElencoPiattiRicette(controller.getElencoPiatti());
					break;
				case 7:
					stampaElencoMenuTematici(controller.getElencoMenuTematici());
					break;
				case 8:
					stampaInsiemeProdotti(controller.getInsiemeBevande());
					break;
				case 9:
					stampaInsiemeProdotti(controller.getInsiemeGeneriExtra());
					break;
				case 10:
					controller.giornoDopo();
					System.out.println("Il giorno corrente is: " + controller.getDataCorrente().toString());
					break;
				case 0:
					sessioneOn = false;
					System.out.println("Fine sessione Gestore...");
					break;
			}
		} while (sessioneOn);

	}

	private void enterGenereExtra() {
		String nomeGenereExtra = richiestaNomeGenereExtraValido();
		float consumoProCapiteGenereExtra = InputDati
				.leggiFloatPositivo("Inserisci il consumo pro capite di " + nomeGenereExtra + " (hg) : ");
		controller.aggiungiGenereExtra(nomeGenereExtra, consumoProCapiteGenereExtra);
	}

	// richiede un nome di genere extra. Se gia' esiste in insimeGeneriExtra del
	// model, chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeGenereExtraValido() {
		String nomeProdotto;
		boolean esiste = false;
		do {
			nomeProdotto = InputDati.leggiStringaNonVuota("Inserisci il nome di un prodotto: ");
			esiste = controller.esisteGenereExtra(nomeProdotto);
		} while (esiste);
		return nomeProdotto;
	}

	private void enterBevanda() {
		String nomeBevanda = richiestaNomeBevandaValido();
		Float consumoProCapiteBevanda = InputDati
				.leggiFloatPositivo("Inserisci il consumo pro capite di " + nomeBevanda + " (litri) : ");
		controller.aggiungiBevanda(nomeBevanda, consumoProCapiteBevanda);
	}

	// richiede un nome di bevanda. Se gia' esiste in insiemeBavande del model,
	// chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeBevandaValido() {
		String nomeProdotto;
		boolean esiste = false;
		do {
			nomeProdotto = InputDati.leggiStringaNonVuota("Inserisci il nome di un prodotto: ");
			esiste = controller.esisteBevanda(nomeProdotto);
		} while (esiste);
		return nomeProdotto;
	}

	public void enterConfigurazione() {
		this.stampaMsgBenvenutoInizializzazione();
		LocalDate dataCorrente = InputDati.leggiData("Inserisci la data corrente");
		int nPosti = InputDati.leggiInteroPositivo("Inserisci il numero di posti del ristorante: ");
		int caricoLavoroPersona = InputDati.leggiInteroPositivo("Inserisci il carico di lavoro per persona: ");
		controller.setConfigurazioneRistorante(caricoLavoroPersona, nPosti, dataCorrente);
	}

	public void enterPiattoRicetta() {
		String nomePiatto = richiestaNomePiattoValido(controller.getElencoPiatti());
		int porzioni = InputDati.leggiInteroPositivo("Inserisci il numero di porzioni del piatto: ");
		int caricoLavoro = InputDati.leggiIntero("Inserisci il carico di lavoro per porzione: ", 0,
				controller.getCaricoLavoroPerPersona());
		System.out.println("\nOra bisogna inserire l'elenco di ingredienti della ricetta e le rispettive dosi.");
		ArrayList<Prodotto> elencoIngredienti = richiestaElencoIngredienti();

		System.out.println("E' necessario indicare il periodo di validita' del piatto.");
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();

		controller.aggiungiPiattoRicetta(nomePiatto, porzioni, caricoLavoro, elencoIngredienti, periodi);
		System.out.println("Un nuovo elemento e' stato aggiunto al Menu");

	}

	private void enterMenuTematico() {
		String nomeMenuTematico = richiestaNomeMenuTematicoValido(controller.getElencoMenuTematici());
		ArrayList<Piatto> elencoPiatti = richiediElencoPiattiDelMenuTematico();
		System.out.println("Necessario almeno un peiodo di validita'.");
		ArrayList<Periodo> periodi = richiestaElencoPeriodiValidi();
		controller.aggiungiMenuTematico(nomeMenuTematico, elencoPiatti, periodi);
	}

	/**
	 * Crea una lista di piatti richiesti all'utente, controllando che non superino
	 * il carico lavoro massimo del menu tematico
	 * 
	 * @return
	 */
	private ArrayList<Piatto> richiediElencoPiattiDelMenuTematico() {
		ArrayList<Piatto> elencoPiatti = new ArrayList<>();
		int caricoLavoroMenuTematico = 0;
		int caricoLavoroMax = (int) Math.floor(controller.getCaricoLavoroPerPersona() * 4.0 / 3);
		boolean altroPiatto = false;
		do {
			Piatto piatto = richiestaPiattoDaElenco(caricoLavoroMenuTematico, caricoLavoroMax);
			boolean piattoValido = controllaCaricoLavoroPiattoPerMenuTematico(piatto, caricoLavoroMenuTematico,
					caricoLavoroMax);
			if (piattoValido) {
				elencoPiatti.add(piatto);
				caricoLavoroMenuTematico += piatto.getCaricoLavoro();
			} else {
				System.out.println("Non e' possibile aggiungere il piatto al menu, si supera il carico massimo");
			}
			altroPiatto = controllaAggiuntaNuovoPiattoAlMenuTematico(caricoLavoroMenuTematico, caricoLavoroMax);
		} while (altroPiatto);
		return elencoPiatti;
	}

	// presenta l'elenco contenente tutti i piatti del iGestore e chiede
	// all'utente quale vuole aggiungere al menu tematico
	private Piatto richiestaPiattoDaElenco(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		System.out.println("Elenco piatti");
		stampaElencoPiatti(controller.getElencoPiatti());
		System.out.println("\nCarico di lavoro ancora disponibile per il menu tematico --> "
				+ (caricoLavoroMax - caricoLavoroMenuTematico));
		int scelta = InputDati.leggiIntero(
				"Inserisci il numero del piatto che vuoi aggiungere al menu tematico: ", 0, controller.getElencoPiatti().size() - 1);
		Piatto piatto = controller.piattoScelto(scelta);
		return piatto;
	}

	// dato un piatto, controlla se l'aggiunta di tale piatto al menu tematico
	// supererebbe il carico lavoro massimo di un menu tematico
	// se non lo supera ritorna true
	private boolean controllaCaricoLavoroPiattoPerMenuTematico(Piatto piatto, int caricoLavoroMenuTematico,
			int caricoLavoroMax) {
		boolean piattoValido = false;
		if (caricoLavoroMenuTematico + piatto.getCaricoLavoro() <= caricoLavoroMax) {
			piattoValido = true;
		} else {
			System.out.println(
					"Il carico di lavoro del menu tematico supera il limite massimo. Il piatto non e' stato aggiunto.");
			piattoValido = false;
		}
		return piattoValido;
	}

	public void stampaElencoPiatti(List<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			System.out.println("Nome: " + p.getNomePiatto());
			System.out.println("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
	}

	// controlla se � possibile aggiungere ancora un piatto al menu tematico
	// questo � possibile se il carico lavoro del menu tematico e' minore del
	// carico
	// lavoro massimo
	private boolean controllaAggiuntaNuovoPiattoAlMenuTematico(int caricoLavoroMenuTematico, int caricoLavoroMax) {
		boolean altroPiatto = true;
		if (caricoLavoroMenuTematico < caricoLavoroMax) {
			altroPiatto = InputDati.yesOrNo("Aggiunta nuovo piatto");
		} else {
			System.out.println("Hai raggiunto il limite di carico di lavoro del menu.");
			altroPiatto = false;
		}
		return altroPiatto;
	}

	// chiede un nome di menu tematico. Se gia' esiste in elencoMenuTematici, lo
	// richiede. Ritorna il nome valido
	private String richiestaNomeMenuTematicoValido(List<MenuTematico> elencoMenuTematici) {
		String nomeMenuTematico;
		boolean nomeValido = true;
		do {
			nomeValido = true;
			nomeMenuTematico = InputDati.leggiStringaNonVuota("Inserisci il nome del menu tematico: ");
			for (MenuTematico m : elencoMenuTematici) {
				if (m.getNome().equalsIgnoreCase(nomeMenuTematico)) {
					System.out.println("Il nome del menu tematico e' gia' stato utilizzato.");
					nomeValido = false;
					break;
				}
			}
		} while (!nomeValido);
		return nomeMenuTematico;
	}

	private ArrayList<Periodo> richiestaElencoPeriodiValidi() {
		ArrayList<Periodo> periodi = new ArrayList<>();
		boolean altroPeriodo;
		do {
			aggiungiPeriodoValido(periodi);
			altroPeriodo = InputDati.yesOrNo("Vuoi aggiungere un altro periodo di validita'? ");
		} while (altroPeriodo);
		return periodi;
	}

	private void aggiungiPeriodoValido(ArrayList<Periodo> periodi) {
		boolean valido = false;
		do {
			LocalDate dataInizio = InputDati.leggiData("Inserire data di inizio validita'. ");
			LocalDate dataFine = InputDati.leggiData("Inserire data di fine validita': ");
			Periodo periodoValidita = new Periodo(dataInizio, dataFine);
			valido = periodoValidita.isValido(controller.getDataCorrente());
			if (valido) {
				periodi.add(periodoValidita);
			} else {
				System.out.println("Il periodo inserito deve essere valido in futuro.");
			}
		} while (!valido);
	}

	/**
	 * richiede un nome di piatto. Se gia' esiste in elencoPiatti, lo richiede.
	 * Ritorna il nome valido
	 * 
	 * @param elencoPiatti
	 * @return
	 */
	private String richiestaNomePiattoValido(List<Piatto> elencoPiatti) {
		String nomePiatto;
		boolean nomeValido = true;
		do {
			nomeValido = true;
			nomePiatto = InputDati.leggiStringaNonVuota("Inserisci il nome di un piatto: ");
			for (Piatto p : elencoPiatti) {
				if (p.getNomePiatto().equalsIgnoreCase(nomePiatto)) {
					System.out.println("Il piatto già esiste");
					nomeValido = false;
					break;
				}
			}
		} while (!nomeValido);
		return nomePiatto;
	}

	private ArrayList<Prodotto> richiestaElencoIngredienti() {
		ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
		boolean altroIngrediente;
		do {
			String nomeIngrediente = richiestaNomeIngredienteValido(elencoIngredienti);
			float dose = InputDati.leggiFloatPositivo("Inserisci dose: ");
			UnitaMisura unitaMisura = InputDati.richiestaUnitaMisura();

			if (nomeIngrediente != null && dose > 0f && unitaMisura != null) {
				Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
				elencoIngredienti.add(ingrediente);
				System.out.println("Un ingrediente e' stato inserito");
			} else {
				System.out.println("Elemento non valido");
			}

			altroIngrediente = InputDati.yesOrNo("Vuoi aggiungere un altro ingrediente? ");
		} while (altroIngrediente);
		return elencoIngredienti;
	}

	// richiede un nome di ingrediente. Se gia' esiste in elencoIngredienti della
	// ricetta in questione, chiede un altro nome
	// Ritorna il nome valido
	private String richiestaNomeIngredienteValido(ArrayList<Prodotto> elencoIngredienti) {
		String nomeProdotto;
		boolean nomeValido;
		do {
			nomeValido = true;
			nomeProdotto = InputDati.leggiStringaNonVuota("Inserisci il nome di un prodotto: ");
			for (Prodotto p : elencoIngredienti) {
				if (p.getNome().equalsIgnoreCase(nomeProdotto)) {
					nomeValido = false;
					System.out.println("Prodotto gia presente riprovare \n");
					break;
				}
			}
		} while (!nomeValido);
		return nomeProdotto;
	}

	public void stampaParametriRistorante(LocalDate dataCorrente, int nPosti, int caricoLavoroPersona,
			int caricoLavoroRistorante) {
		System.out.println("\nPARAMETRI RISTORANTE");
		System.out.print("Data corrente: ");
		stampaData(dataCorrente);
		System.out.println(" " + dataCorrente.getDayOfWeek());
		System.out.println("Numero di posti a sedere del ristorante: " + nPosti);
		System.out.println("Carico di lavoro per persona: " + caricoLavoroPersona);
		System.out.println("Carico lavoro gestibile dal ristorante per un pasto: " + caricoLavoroRistorante);
	}

	public void stampaData(LocalDate data) {
		System.out.print(data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
	}

	public void stampaPiattoRicetta(Piatto p, Ricetta r) {
		System.out.println("PIATTO:");
		System.out.println("Nome: " + p.getNomePiatto());
		System.out.println("Carico di lavoro: " + p.getCaricoLavoro());
		System.out.println("Periodi di validita': ");
		for (Periodo d : p.getPeriodiValidita()) {
			LocalDate dInizio = d.getDataInizio();
			LocalDate dFine = d.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
		System.out.println("RICETTA: ");
		System.out.println("Numero porzioni: " + r.getNumeroPorzioni());
		System.out.println("Carico di lavoro per porzione: " + r.getCaricoLavoroPorzione());
		System.out.println("INGREDIENTI:");
		for (Prodotto i : r.getElencoIngredienti()) {
			stampaIngrediente(i);
		}
	}

	public void stampaPeriodo(LocalDate dataInizio, LocalDate dataFine) {
		stampaData(dataInizio);
		System.out.print(" --> ");
		stampaData(dataFine);
		System.out.println();
	}

	public void stampaIngrediente(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Dose: " + p.getQuantita() + "  " + p.getUnitaMisura().getName());
	}

	public void stampaElencoPiattiRicette(List<Piatto> elencoPiatti) {
		int contatore = 0;
		System.out.println("Elenco piatti-ricette");
		for (Piatto p : elencoPiatti) {
			Ricetta r = p.getRicetta();
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaPiattoRicetta(p, r);
			contatore++;
		}
	}

	public void stampaMsgBenvenutoInizializzazione() {
		System.out.println("Benvenuto Gestore.");
		System.out.println(
				"Siccome si tratta del primo avvio del programma e' necessaria l'inizializzazione dei parametri.");
	}

	public void stampaMsgElementoEsistente(){
		System.out.println("Elemento gia' presente, non e' possibile inserirlo di nuovo");
	}

	public void stampaElencoMenuTematici(List<MenuTematico> elencoMenuTematici) {
		if (elencoMenuTematici.isEmpty()) {
			System.out.println("La lista e' vuota");
		} else {
			int contatore = 0;
			System.out.println("Elenco menu tematici");
			for (MenuTematico m : elencoMenuTematici) {
				System.out.println(" ----------- " + contatore + " ----------- ");
				stampaMenuTematico(m);
				contatore++;
			}
		}
	}

	public void stampaMenuTematico(MenuTematico m) {
		System.out.println("Nome menu tematico: " + m.getNome());
		System.out.println("Carico lavoro: " + m.getCaricoLavoro());
		System.out.println("Piatti del menu: ");
		for (Piatto p : m.getElencoPiatti()) {
			System.out.println("Nome: " + p.getNomePiatto());
		}
		System.out.println("Periodi di validita': ");
		for (Periodo periodo : m.getPeriodiValidita()) {
			LocalDate dInizio = periodo.getDataInizio();
			LocalDate dFine = periodo.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
	}

	public void stampaInsiemeProdotti(List<Prodotto> elencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println();
			System.out.println("La lista e' vuota.");
			System.out.println();
		} else {
			for (Prodotto p : elencoProdotti) {
				System.out.println(" ----------- " + contatore + " ----------- ");
				stampaProdotto(p);
				contatore++;
			}
		}
	}

	public void stampaProdotto(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Consumo pro capite: " + p.getQuantita() + p.getUnitaMisura());
	}

	public void setController(GestioneController controller) {
		this.controller = controller;
	}
}
