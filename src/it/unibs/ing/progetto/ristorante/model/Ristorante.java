package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.util.stream.Collectors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Ristorante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate dataCorrente;
	private int caricoLavoroPerPersona;
	private int numeroPostiASedere;
	private int caricoLavoroRistorante;

	/*
	 * Ipotesi nuova classe Gestione
	 */
	private ArrayList<Piatto> elencoPiatti;
	private ArrayList<MenuTematico> elencoMenuTematici;
	private ArrayList<Prodotto> insiemeBevande;
	private ArrayList<Prodotto> insiemeGeneriExtra;

	/*
	 * Ipotesi classe Inventario
	 * 
	 */
	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;

	/*
	 * Classe 'AgendaRistorante' contiene elencoPrenotazione
	 */
	private ArrayList<Prenotazione> elencoPrenotazioni;

	/*
	 * Costruttore
	 */
	public Ristorante() {
		this.dataCorrente = null;
		this.caricoLavoroPerPersona = 1;
		this.numeroPostiASedere = 1;
		this.caricoLavoroRistorante = 1;
		this.elencoPiatti = new ArrayList<>();
		this.elencoMenuTematici = new ArrayList<>();
		this.insiemeBevande = new ArrayList<>();
		this.insiemeGeneriExtra = new ArrayList<>();
		this.registroMagazzino = new ArrayList<>();
		this.listaSpesa = new ArrayList<>();
		this.elencoPrenotazioni = new ArrayList<>();
	}

	public boolean isRistorantePienoInData(LocalDate data) {
		return (this.getPostiDisponibiliInData(data) == 0);
	}

	public int getPostiDisponibiliInData(LocalDate data) {
		int numeroClienti = this.getNumeroClientiPrenotatiInData(data);
		return this.numeroPostiASedere - numeroClienti;
	}

	public void addPrenotazione(LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.elencoPrenotazioni.add(new Prenotazione(coperti, comanda, data));
	}

	public boolean removePrenotazione(Prenotazione prenotazione) {
		String daRimuovere = prenotazione.getCodiceCliente();
		for (int i = 0; i < this.elencoPrenotazioni.size(); i++) {
			Prenotazione corrente = elencoPrenotazioni.get(i);
			String cliente = corrente.getCodiceCliente();
			if (daRimuovere.equalsIgnoreCase(cliente)) {
				this.elencoPrenotazioni.remove(i);
				return true;
			}
		}
		return false;
	}

	public ArrayList<Piatto> getMenuCartaValidiInData(LocalDate data) {
		ArrayList<Piatto> menuCarta = new ArrayList<Piatto>();
		for (Piatto p : this.elencoPiatti) {
			if (p.isDisponibileInData(data)) {
				menuCarta.add(p);
			}
		}
		return menuCarta;
	}

	public ArrayList<MenuTematico> getMenuTematiciValidiInData(LocalDate data) {
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for (MenuTematico m : this.elencoMenuTematici) {
			if (m.isDisponibileInData(data)) {
				menuTematiciValidi.add(m);
			}
		}
		return menuTematiciValidi;
	}

	public boolean ciSonoMenuTematiciValidiInData(LocalDate data) {
		return (this.getMenuCartaValidiInData(data).size() > 0);
	}

	public boolean ciSonoPiattiValidiInData(LocalDate data) {
		return (this.getMenuCartaInData(data).size() > 0);
	}

	/*
	 * Carico di lavoro della totalita delle prenotazioni -> somma del valore del
	 * carico di lavoro di tutti i menu tematici, estesa a tutte le persone che
	 * hanno ordinato ciascun menu, e di tutti i piatti prenotati, estesa a tutte le
	 * persone che hanno ordinato ciascun piatto
	 */

	private float getCaricoLavoroDaSostenereInData(LocalDate data) {
		float caricoDaSostenere = 0;
		for (Prenotazione p : this.elencoPrenotazioni) {
			caricoDaSostenere += p.getCaricoLavoroTotalePrenotazione();
		}
		return caricoDaSostenere;
	}

	public float getCaricoLavoroSostenibileRimasto(LocalDate data) {
		return this.caricoLavoroRistorante - this.getCaricoLavoroDaSostenereInData(data);
	}

	public boolean esisteGenereExtra(String nome) {
		for (Prodotto p : this.insiemeGeneriExtra) {
			if (p.getNome().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}

	public boolean esisteBevanda(String nome) {
		for (Prodotto p : this.insiemeBevande) {
			if (p.getNome().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}

	public boolean ciSonoMenuValidiInData(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = this.getMenuTematiciValidiInData(date);
		if (menuTematici.isEmpty()) {
			return false;
		} else
			return true;

	}

	public boolean esisteMenuCartaValidoInData(LocalDate date) {
		ArrayList<Piatto> piattiValidi = this.getMenuCartaValidiInData(date);
		if (piattiValidi.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void removePrenotazioniScadute(LocalDate data) {
		ArrayList<Prenotazione> elencoPrenotazioni = getElencoPrenotazioni();
		for (int i = elencoPrenotazioni.size() - 1; i >= 0; i--) {
			Prenotazione prenotazione = elencoPrenotazioni.get(i);
			if (prenotazione.getDataPrenotazione().isBefore(data)) {
				elencoPrenotazioni.remove(i);
			}
		}
	}

	// GETTER AND SETTER
	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public int getCaricoLavoroPerPersona() {
		return caricoLavoroPerPersona;
	}

	public void setCaricoLavoroPerPersona(int caricoDilavoroPerPersona) {
		this.caricoLavoroPerPersona = caricoDilavoroPerPersona;
	}

	public int getNumeroPostiASedere() {
		return numeroPostiASedere;
	}

	public void setNumeroPostiASedere(int numeroPostiASedere) {
		this.numeroPostiASedere = numeroPostiASedere;
	}

	public int getCaricoLavoroRistorante() {
		return caricoLavoroRistorante;
	}

	public void setCaricoLavoroRistorante(int caricoLavoroRistorante) {
		this.caricoLavoroRistorante = caricoLavoroRistorante;
	}

	public ArrayList<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public void setElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		this.elencoPiatti = elencoPiatti;
	}

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return elencoMenuTematici;
	}

	public void setElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		this.elencoMenuTematici = elencoMenuTematici;
	}

	public ArrayList<Prodotto> getInsiemeBevande() {
		return insiemeBevande;
	}

	public void setInsiemeBevande(ArrayList<Prodotto> insiemeBevande) {
		this.insiemeBevande = insiemeBevande;
	}

	public ArrayList<Prodotto> getInsiemeGeneriExtra() {
		return insiemeGeneriExtra;
	}

	public void setInsiemeGeneriExtra(ArrayList<Prodotto> insiemeGeneriExtra) {
		this.insiemeGeneriExtra = insiemeGeneriExtra;
	}

	public ArrayList<Prodotto> getRegistroMagazzino() {
		return registroMagazzino;
	}

	public ArrayList<Prodotto> getListaSpesa() {
		return listaSpesa;
	}

	public void setListaSpesa(ArrayList<Prodotto> listaSpesa) {
		this.listaSpesa = listaSpesa;
	}

	public ArrayList<Prenotazione> getElencoPrenotazioni() {
		return elencoPrenotazioni;
	}

	public void setElencoPrenotazioni(ArrayList<Prenotazione> elencoPrenotazioni) {
		this.elencoPrenotazioni = elencoPrenotazioni;
	}

	// ritorna un ArrayList<Piatto> contenente solo i piatti singoli memorizzati nel
	// DataBase e validi nella data
	public List<Piatto> getMenuCartaInData(LocalDate date) {
		ArrayList<Piatto> menuCartaValido = new ArrayList<Piatto>();
		for (Piatto p : elencoPiatti) {
			if (p.isDisponibileInData(date))
				menuCartaValido.add(p);
		}
		return menuCartaValido;
	}

	// ritorna un ArrayList<MenuTematico> contenente solo i menu tematici
	// memorizzati nel DataBase e validi nella data
	public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for (MenuTematico m : menuTematiciValidi) {
			if (m.isDisponibileInData(date))
				menuTematiciValidi.add(m);
		}
		return menuTematiciValidi;
	}

	// ritorna il valore del piatto in posizione data da scelta
	public Piatto piattoScelto(int scelta) {
		Piatto p = null;
		for (int i = 0; i < elencoPiatti.size(); i++) {
			if (scelta == i) {
				p = elencoPiatti.get(i);
				break;
			}
		}
		return p;
	}

	// aggiunge un piatto e la rispettiva ricetta a elencoPiatti
	public void addPiattoRicetta(ArrayList<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro,
			String nomePiatto, ArrayList<Periodo> periodi) {
		Ricetta ricetta = new Ricetta(elencoIngredienti, porzioni, caricoLavoro);
		Piatto piatto = new Piatto(nomePiatto, caricoLavoro, ricetta, periodi);
		elencoPiatti.add(piatto);
	}

	// aggiunge un piatto a quelli esistenti
	public void addPiatto(Piatto p) {
		elencoPiatti.add(p);
	}

	// aggiunge un nuovo menu tematico
	public void addMenuTematico(String nomeMenuTematico, ArrayList<Piatto> piatti, int caricoLavoroMenuTematico,
			ArrayList<Periodo> periodi) {
		MenuTematico menuTematico = new MenuTematico(nomeMenuTematico, piatti, caricoLavoroMenuTematico, periodi);
		elencoMenuTematici.add(menuTematico);
	}

	// aggiunge una nuova benvanda a insiemeBevande
	public void addBevanda(String nomeBevanda, float consumoProCapiteBevanda) {
		Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, UnitaMisura.LITRI);
		insiemeBevande.add(bevanda);
	}

	// aggiunge un nuovo genere extra a insiemeGeneriExtra
	public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra) {
		Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, UnitaMisura.HG);
		insiemeGeneriExtra.add(genereExtra);
	}

	public void generaListaSpesa(LocalDate data) {
		this.dataCorrente = data;
		ArrayList<Prenotazione> prenotazioniInData = (ArrayList<Prenotazione>) this.getPrenotazioniInData(data);
		HashMap<Piatto, Integer> comanda_unica = combinaAllcomande(prenotazioniInData);
		int prenotati = this.getNumeroClientiPrenotatiInData(data);
		ArrayList<Prodotto> lista_provvisoria = this.costruisciListaProdottiDaComanda(comanda_unica, prenotati);
		maggiorazionePercentuale(lista_provvisoria, 10);
		aggiornaListaSpesaConInventario(lista_provvisoria);
	}

	public void aggiornaListaSpesaConInventario(ArrayList<Prodotto> listaIniziale) {
		this.listaSpesa = new ArrayList<>();
		for (Prodotto prodotto : listaIniziale) {
			if (!this.contieneProdottoSufficiente(registroMagazzino, prodotto)) {
				float daAcquistare = prodotto.getQuantita() - this.quantitaInMagazzino(prodotto);
				prodotto.setQuantita(daAcquistare);
				this.listaSpesa.add(prodotto);
			}
		}
	}

	private static void maggiorazionePercentuale(ArrayList<Prodotto> lista, float percentuale) {
		if (lista == null || percentuale < 0) {
			throw new IllegalArgumentException("Input invalidi");
		}
		float fattore = 1 + (percentuale / 100);
		for (Prodotto prodotto : lista) {
			float quantitaNew = prodotto.getQuantita() * fattore;
			prodotto.setQuantita(quantitaNew);
		}

	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private List<Prenotazione> getPrenotazioniInData(LocalDate data) {
		return elencoPrenotazioni.stream().filter(p -> p.isPrenotazioneInData(data)).collect(Collectors.toList());
	}

	private float quantitaInMagazzino(Prodotto prodotto) {
		return registroMagazzino.stream().filter(p -> p.getNome().equalsIgnoreCase(prodotto.getNome())).findFirst()
				.map(Prodotto::getQuantita).orElse(0.0f);
	}

	/**
	 * Definitivo, combina tutte le comande -> output = unica comanda
	 * 
	 * @param prenotazioni
	 * @return
	 */
	private static HashMap<Piatto, Integer> combinaAllcomande(ArrayList<Prenotazione> prenotazioni) {
		// prendo tutte le comande
		// -> creo entry per ogni voce
		// creo una nuova hashHamp
		// inserisco chiave valore, se la chiave esiste gia -> sommo i valori delle due
		// chiavi
		return prenotazioni.stream().map(Prenotazione::getComanda).flatMap(map -> map.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, HashMap::new));
	}

	private ArrayList<Prodotto> costruisciListaProdottiDaComanda(HashMap<Piatto, Integer> piattiOrdinati,
			int prenotati) {
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		Iterator<Entry<Piatto, Integer>> iter = piattiOrdinati.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			Ricetta recipe = entry.getKey().getRicetta();
			int porzioni = entry.getValue();
			ArrayList<Prodotto> ingredientiInRicetta = (ArrayList<Prodotto>) recipe.getElencoIngredientiPerPorzioni(porzioni);
			aggiornaListaConProdotti(prodotti, ingredientiInRicetta);
		}
		aggiornaListaConProdotti(prodotti,
				(ArrayList<Prodotto>) this.ricalcolaInBaseNumeroClienti(insiemeBevande, prenotati));
		aggiornaListaConProdotti(prodotti,
				(ArrayList<Prodotto>) this.ricalcolaInBaseNumeroClienti(insiemeGeneriExtra, prenotati));
		return prodotti;
	}

	public static void unisciListaProdotti(ArrayList<Prodotto> mainList, ArrayList<Prodotto> prodotti) {
		for (Prodotto p : prodotti) {
			aggiornaListaConProdotto(mainList, p);
		}
	}

	private boolean isGiornoFeriale(LocalDate data) {
		DayOfWeek dayOfWeek = data.getDayOfWeek();
		return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
	}

	public boolean verificaAccettabilitaPrenotazione(LocalDate date, HashMap<Piatto, Integer> piatti,
			int numeroCoperti) {
		if (!isGiornoFeriale(date)) {
			return false;
		}

		int postiDisponibili = this.getPostiDisponibiliInData(date);
		if (numeroCoperti > postiDisponibili) {
			return false;
		}

		float caricoLavoroSostenibileRimasto = this.getCaricoLavoroSostenibileRimasto(date);
		float caricoLavoroRichiesto = this.calcolaCaricoLavoro(piatti);
		if (caricoLavoroRichiesto > caricoLavoroSostenibileRimasto) {
			return false;
		}

		return true;
	}

	/**
	 * Calcola il carico di lavoro di una comanda
	 * 
	 * @param piatti
	 * @return
	 */
	public float calcolaCaricoLavoro(HashMap<Piatto, Integer> piatti) {
		float carico = 0;
		Iterator<Entry<Piatto, Integer>> iter = piatti.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			int carico_lavoro = entry.getKey().getCaricoLavoro();
			int porzioni = entry.getValue();
			carico += carico_lavoro * porzioni;
		}
		return carico;
	}

	private void aggiornaListaConProdotti(ArrayList<Prodotto> prodottiLista, ArrayList<Prodotto> ingredienti) {
		for (Prodotto prodotto : ingredienti) {
			aggiornaListaConProdotto(prodottiLista, prodotto);
		}
	}

	private static void aggiornaListaConProdotto(ArrayList<Prodotto> listaProdotti, Prodotto prodotto) {
		if (listaContieneProdotto(listaProdotti, prodotto)) {
			aggiornaQuantitaProdotto(listaProdotti, prodotto);
		} else {
			listaProdotti.add(prodotto);
		}
	}

	private static boolean aggiornaQuantitaProdotto(ArrayList<Prodotto> prodotti, Prodotto i) {
		for (Prodotto product : prodotti) {
			if (product.getNome().equalsIgnoreCase(i.getNome())) {
				product.setQuantita(product.getQuantita() + i.getQuantita());
				return true;
			}
		}
		return false;
	}

	/**
	 * Restituisce true se il prodotto esiste in una lista
	 * 
	 * @param prodotti
	 * @param prodotto
	 * @return
	 */
	private static boolean listaContieneProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		// uso lo stream per iterare in modo piu efficiente
		String cercato = prodotto.getNome();
		return prodotti.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(cercato));
	}

	/**
	 * Se il prodotto è presente nella lista e la quantita è maggiore allora
	 * restituisce true
	 * 
	 * @param prodotti
	 * @param prodotto
	 * @return
	 */
	private boolean contieneProdottoSufficiente(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String cercato = prodotto.getNome();
		Float quantita = prodotto.getQuantita();
		// Verifico se esiste il prodotto
		// Se esiste verifico se la quantita presente è sufficiente
		return prodotti.stream().filter(p -> p.getNome().equalsIgnoreCase(cercato))
				.anyMatch(p -> p.getQuantita() >= quantita);
	}

	/**
	 * Aggiunge un prodotto all'inventario
	 * 
	 * @param nome
	 * @param quantita
	 * @param unitaMisura
	 */
	public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura) {
		Prodotto prodotto = new Prodotto(nome, quantita, unitaMisura);
		aggiornaListaConProdotto(this.registroMagazzino, prodotto);
		if (!this.listaSpesa.isEmpty()) {
			this.generaListaSpesa(dataCorrente);
		}
	}

	private int getNumeroClientiPrenotatiInData(LocalDate date) {
		// prendo tutte le prenotazioni in data
		// per ogni prenotazione recupero il numero di coperti
		// li sommo e ritorno il risultato
		return this.elencoPrenotazioni.stream().filter(p -> p.isPrenotazioneInData(date))
				.mapToInt(Prenotazione::getNumeroCoperti).sum();
	}

	/**
	 * Restituisce una lista con quantita ricalcolate (moltiplicazione) in base al
	 * numero
	 * 
	 * @param insieme
	 * @param numero
	 * @return
	 */
	private List<Prodotto> ricalcolaInBaseNumeroClienti(ArrayList<Prodotto> insieme, int numero) {
		ArrayList<Prodotto> insiemeNew = new ArrayList<>();
		for (Prodotto p : insieme) {
			Prodotto nuovo = new Prodotto(p.getNome(), p.getQuantita() * numero, p.getUnitaMisura());
			insiemeNew.add(nuovo);
		}
		return insiemeNew;
	}

	/**
	 * Dando in input un prodotto e una quantita, la cerca nel registro e ne riduce
	 * della quantita indicata
	 * 
	 * @param prodotto
	 * @param quantita
	 */
	public void rimuoviQuantitaProdottoDaRegistro(Prodotto prodotto, float quantita) {
		String nomeCercato = prodotto.getNome();
		for (Prodotto p : this.registroMagazzino) {
			if (p.getNome().equalsIgnoreCase(nomeCercato)) {
				if (p.getQuantita() > quantita) {
					p.setQuantita(p.getQuantita() - quantita);
				} else {
					rimuoviProdotto(registroMagazzino, prodotto);
				}
				break;
			}
		}
		if (!this.listaSpesa.isEmpty()) {
			this.generaListaSpesa(dataCorrente);
		}
	}

	/**
	 * Rimuove un prodotto da una lista di prodotto
	 * 
	 * @param prodotti
	 * @param prodotto
	 */
	private static void rimuoviProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String nome = prodotto.getNome();
		for (int i = 0; i < prodotti.size(); i++) {
			if (nome.equalsIgnoreCase(prodotti.get(i).getNome())) {
				prodotti.remove(i);
				break;
			}
		}
	}

	public boolean isListaSpesaEmpty() {
		return this.listaSpesa.isEmpty();
	}

	public boolean isRegistroMagazzinoEmpty() {
		return this.registroMagazzino.isEmpty();
	}

}
