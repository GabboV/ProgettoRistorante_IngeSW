package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.util.stream.Collectors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.Map.Entry;

public class Ristorante implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate dataCorrente;
	private int caricoLavoroPerPersona;
	private int numeroPostiASedere;
	private int caricoLavoroRistorante;

	private ArrayList<Piatto> elencoPiatti;
	private ArrayList<MenuTematico> elencoMenuTematici;
	private ArrayList<Prodotto> insiemeBevande;
	private ArrayList<Prodotto> insiemeGeneriExtra;

	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;

	private ArrayList<Prenotazione> elencoPrenotazioni;

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

	public void removePrenotazione(Prenotazione prenotazione) {
		String daRimuovere = prenotazione.getCodiceCliente();
		this.elencoPrenotazioni.removeIf(p -> p.getCodiceCliente().equalsIgnoreCase(daRimuovere));
	}

	public boolean ciSonoMenuTematiciValidiInData(LocalDate data) {
		return (this.getMenuTematiciInData(data).size() > 0);
	}

	public boolean ciSonoPiattiValidiInData(LocalDate data) {
		return (this.getMenuCartaInData(data).size() > 0);
	}

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
		return insiemeGeneriExtra.stream().anyMatch(g -> g.getNome().equalsIgnoreCase(nome));
	}

	public boolean esisteBevanda(String nome) {
		return insiemeBevande.stream().anyMatch(b -> b.getNome().equalsIgnoreCase(nome));
	}

	public boolean ciSonoMenuValidiInData(LocalDate date) {
		ArrayList<MenuTematico> menuTematici = (ArrayList<MenuTematico>) this.getMenuTematiciInData(date);
		return !menuTematici.isEmpty();
	}

	public boolean esisteMenuCartaValidoInData(LocalDate date) {
		ArrayList<Piatto> piattiValidi = (ArrayList<Piatto>) this.getMenuCartaInData(date);
		return !piattiValidi.isEmpty();
	}

	public void removePrenotazioniScadute(LocalDate data) {
		elencoPrenotazioni = (ArrayList<Prenotazione>) this.elencoPrenotazioni.stream()
				.filter(p -> !p.getDataPrenotazione().isBefore(data)).collect(Collectors.toList());
	}

	// GETTER AND SETTER
	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public ArrayList<Prenotazione> getElencoPrenotazioni() {
		return elencoPrenotazioni;
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

	public ArrayList<MenuTematico> getElencoMenuTematici() {
		return elencoMenuTematici;
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

	// ritorna un ArrayList<Piatto> contenente solo i piatti singoli memorizzati nel
	// DataBase e validi nella data
	public List<Piatto> getMenuCartaInData(LocalDate date) {
		return elencoPiatti.stream().filter(p -> p.isDisponibileInData(date)).collect(Collectors.toList());
	}

	// ritorna un ArrayList<MenuTematico> contenente solo i menu tematici
	// memorizzati nel DataBase e validi nella data
	public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
		return this.elencoMenuTematici.stream().filter(m -> m.isDisponibileInData(date)).collect(Collectors.toList());
	}

	// ritorna il valore del piatto in posizione data da scelta
	public Piatto piattoScelto(int scelta) {
		if (scelta >= 0 && scelta < elencoPiatti.size()) {
			return elencoPiatti.get(scelta);
		}
		return null;
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
		return prenotazioni.stream().map(Prenotazione::getComanda).flatMap(map -> map.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, HashMap::new));
	}

	public void generaListaSpesa(LocalDate data) {
		this.dataCorrente = data;
		ArrayList<Prenotazione> prenotazioniInData = (ArrayList<Prenotazione>) this.getPrenotazioniInData(data);
		HashMap<Piatto, Integer> comanda_unica = combinaAllcomande(prenotazioniInData);
		int prenotati = this.getNumeroClientiPrenotatiInData(data);
		ArrayList<Prodotto> lista_provvisoria = this.costruisciListaProdottiDaComanda(comanda_unica);
		maggiorazionePercentuale(lista_provvisoria, 10);
		aggiornaListaConProdotti(lista_provvisoria,
				(ArrayList<Prodotto>) this.ricalcolaInBaseNumeroClienti(insiemeGeneriExtra, prenotati));
		aggiornaListaConProdotti(lista_provvisoria,
				(ArrayList<Prodotto>) this.ricalcolaInBaseNumeroClienti(insiemeBevande, prenotati));
		aggiornaListaSpesaConInventario(lista_provvisoria);
	}

	private ArrayList<Prodotto> costruisciListaProdottiDaComanda(HashMap<Piatto, Integer> piattiOrdinati) {
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		for (Entry<Piatto, Integer> entry : piattiOrdinati.entrySet()) {
			Ricetta recipe = entry.getKey().getRicetta();
			int porzioni = entry.getValue();
			aggiornaListaConProdotti(prodotti, (ArrayList<Prodotto>) recipe.getElencoIngredientiPerPorzioni(porzioni));
		}
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

	public boolean verificaAccettabilitaPrenotazione(LocalDate date, HashMap<Piatto, Integer> comanda,
			int numeroCoperti) {
		if (!isGiornoFeriale(date)) {
			return false;
		}
		int postiDisponibili = this.getPostiDisponibiliInData(date);
		if (numeroCoperti > postiDisponibili) {
			return false;
		}
		float caricoLavoroSostenibileRimasto = this.getCaricoLavoroSostenibileRimasto(date);
		float caricoLavoroRichiesto = Prenotazione.calcolaCaricoLavoroComanda(comanda);
		if (caricoLavoroRichiesto > caricoLavoroSostenibileRimasto) {
			return false;
		}
		return true;
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

	private static boolean aggiornaQuantitaProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String nome = prodotto.getNome();
		for (int i = 0; i < prodotti.size(); i++) {
			if (prodotti.get(i).getNome().equalsIgnoreCase(nome)) {
				Prodotto daAggiornare = prodotti.get(i);
				daAggiornare.setQuantita(daAggiornare.getQuantita() + prodotto.getQuantita());
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
		String cercato = prodotto.getNome();
		return prodotti.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(cercato));
	}

	private boolean contieneProdottoSufficiente(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String cercato = prodotto.getNome();
		Float quantita = prodotto.getQuantita();
		return prodotti.stream().filter(p -> p.getNome().equalsIgnoreCase(cercato))
				.anyMatch(p -> p.getQuantita() >= quantita);
	}

	public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura) {
		Prodotto prodotto = new Prodotto(nome, quantita, unitaMisura);
		aggiornaListaConProdotto(this.registroMagazzino, prodotto);
		if (!this.listaSpesa.isEmpty()) {
			this.generaListaSpesa(dataCorrente);
		}
	}

	private int getNumeroClientiPrenotatiInData(LocalDate date) {
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
		return insieme.stream().map(p -> new Prodotto(p.getNome(), p.getQuantita() * numero, p.getUnitaMisura()))
				.collect(Collectors.toList());
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
		prodotti.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
	}

}
