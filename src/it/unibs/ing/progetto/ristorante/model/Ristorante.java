package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Ristorante implements Serializable{

	/**
	 * 
	 */
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
		this.elencoPiatti = new ArrayList<Piatto>();
		this.elencoMenuTematici = new ArrayList<MenuTematico>();
		this.insiemeBevande = new ArrayList<Prodotto>();
		this.insiemeGeneriExtra = new ArrayList<Prodotto>();
		this.registroMagazzino = new ArrayList<Prodotto>();
		this.listaSpesa = new ArrayList<Prodotto>();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
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

	public boolean nonCiSonoPrenotazioni() {
		return (this.elencoPrenotazioni.isEmpty());
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
			if (p.isValidoInData(data)) {
				menuCarta.add(p);
			}
		}
		return menuCarta;
	}

	public ArrayList<MenuTematico> getMenuTematiciValidiInData(LocalDate data) {
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for (MenuTematico m : this.elencoMenuTematici) {
			if (m.isValidoInData(data)) {
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
		for(Prodotto p : this.insiemeGeneriExtra) {
			if(p.getNome().equalsIgnoreCase(nome)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean esisteBevanda(String nome) {
		for(Prodotto p : this.insiemeBevande) {
			if(p.getNome().equalsIgnoreCase(nome)) {
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
		// Con che base si stabilisce se una prenotazione sia scaduta o meno (?)
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

	public void setRegistroMagazzino(ArrayList<Prodotto> registroMagazzino) {
		this.registroMagazzino = registroMagazzino;
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
			if (p.isValidoInData(date))
				menuCartaValido.add(p);
		}
		return menuCartaValido;
	}

	// ritorna un ArrayList<MenuTematico> contenente solo i menu tematici
	// memorizzati nel DataBase e validi nella data
	public List<MenuTematico> getMenuTematiciInData(LocalDate date) {
		ArrayList<MenuTematico> menuTematiciValidi = new ArrayList<MenuTematico>();
		for (MenuTematico m : menuTematiciValidi) {
			if (m.isValidoInData(date))
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
		Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, "l");
		insiemeBevande.add(bevanda);
	}

	// aggiunge un nuovo genere extra a insiemeGeneriExtra
	public void addGenereExtra(String nomeGenereExtra, float consumoProCapiteGenereExtra) {
		Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, "hg");
		insiemeGeneriExtra.add(genereExtra);
	}

	public void generaListaSpesa(LocalDate data) {
		this.dataCorrente = data;
		ArrayList<Prenotazione> prenotazioniInData = this.creaElencoPrenotazioniInData(data);
		HashMap<Piatto, Integer> comanda_unica = combinaAllcomande(prenotazioniInData);
		int prenotati = this.getNumeroClientiPrenotatiInData(data);
		ArrayList<Prodotto> lista_provvisoria = this.costruisciListaProdottiDaComanda(comanda_unica, prenotati);
		maggiorazionePercentuale(lista_provvisoria, 10);
		aggiornaListaSpesa(lista_provvisoria);
	}

	public void aggiornaListaSpesa(ArrayList<Prodotto> listaIniziale) {
		this.listaSpesa = new ArrayList<Prodotto>();
		for (Prodotto prodotto : listaIniziale) {
			if (!this.contieneProdottoSufficiente(registroMagazzino, prodotto)) {
				float daAcquistare = (float) (prodotto.getQuantita() - this.quantitaInMagazzino(prodotto));
				prodotto.setQuantita(daAcquistare);
				this.listaSpesa.add(prodotto);

			}

		}
	}

	private static void maggiorazionePercentuale(ArrayList<Prodotto> lista, float percentuale) {
		float fattore = 1 + (percentuale / 100);
		for (Prodotto prodotto : lista) {
			float quantitaNew = prodotto.getQuantita() * fattore;
			prodotto.setQuantita(quantitaNew);
		}

	}

	/**
	 * Definitivo
	 * 
	 * @param data
	 * @return
	 */
	private ArrayList<Prenotazione> creaElencoPrenotazioniInData(LocalDate data) {
		ArrayList<Prenotazione> prenotazioniInData = new ArrayList<Prenotazione>();
		for (Prenotazione p : this.elencoPrenotazioni) {
			if (p.isPrenotazioneInData(data)) {
				prenotazioniInData.add(p);
			}
		}
		return prenotazioniInData;
	}

	private float quantitaInMagazzino(Prodotto prodotto) {
		for (Prodotto p : registroMagazzino) {
			if (p.getNome().equalsIgnoreCase(prodotto.getNome())) {
				return p.getQuantita();
			}
		}
		return 0;
	}

	/**
	 * Definitivo, combina tutte le comande -> output = unica comanda
	 * 
	 * @param prenotazioni
	 * @return
	 */
	private static HashMap<Piatto, Integer> combinaAllcomande(ArrayList<Prenotazione> prenotazioni) {
		HashMap<Piatto, Integer> elenco = new HashMap<>();
		for (Prenotazione pre : prenotazioni) {
			HashMap<Piatto, Integer> comanda = pre.getComanda();
			Iterator<Entry<Piatto, Integer>> iter = comanda.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Piatto, Integer> entry = iter.next();
				Piatto piatto = entry.getKey();
				if (elenco.containsKey(piatto)) {
					int old_value = elenco.get(piatto);
					int incremento = entry.getValue();
					int new_value = old_value + incremento;
					elenco.replace(piatto, new_value);
				} else {
					elenco.put(piatto, entry.getValue());
				}

			}
		}
		return elenco;
	}

	private ArrayList<Prodotto> costruisciListaProdottiDaComanda(HashMap<Piatto, Integer> piattiOrdinati,
			int prenotati) {
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		Iterator<Entry<Piatto, Integer>> iter = piattiOrdinati.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			Ricetta recipe = entry.getKey().getRicetta();
			int porzioni = entry.getValue();
			ArrayList<Prodotto> ingredientiInRicetta = recipe.getElencoIngredientiPerPorzioni(porzioni);
			aggiornaListaConProdotti(prodotti, ingredientiInRicetta);
		}
		aggiornaListaConProdotti(prodotti, this.ricalcolaInBaseNumeroClienti(insiemeBevande, prenotati));
		aggiornaListaConProdotti(prodotti, this.ricalcolaInBaseNumeroClienti(insiemeGeneriExtra, prenotati));
		return prodotti;
	}

	public static void unisciListaProdotti(ArrayList<Prodotto> mainList, ArrayList<Prodotto> prodotti) {
		for (Prodotto p : prodotti) {
			aggiornaListaConProdotto(mainList, p);
		}
	}

	public boolean checkPrenotazione(LocalDate date, HashMap<Piatto, Integer> piatti, int numeroCoperti) {
		if (numeroCoperti > this.getPostiDisponibiliInData(date))
			return false;
		float caricoRimasto = this.getCaricoLavoroSostenibileRimasto(date);
		float carico = this.calcolaCaricoLavoro(piatti);
		if (carico > caricoRimasto)
			return false;
		return true;
	}

	public float calcolaCaricoLavoro(HashMap<Piatto, Integer> piatti) {
		float carico = 0;
		List<Piatto> piattiInComanda = new ArrayList<>(piatti.keySet());
		for (Piatto p : piattiInComanda) {
			carico += p.getCaricoLavoro() * piatti.get(p);
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

	private static boolean listaContieneProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String nomeCercato = prodotto.getNome();
		for (Prodotto p : prodotti) {
			String prodottoCorrente = p.getNome();
			if (prodottoCorrente.equalsIgnoreCase(nomeCercato)) {
				return true;
			}
		}
		return false;
	}

	private boolean contieneProdottoSufficiente(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String nomeCercato = prodotto.getNome();
		for (Prodotto p : prodotti) {
			String prodottoCorrente = p.getNome();
			if (prodottoCorrente.equalsIgnoreCase(nomeCercato) && p.getQuantita() >= prodotto.getQuantita()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void addProdottoInventario(String nome, float quantita, String unitaMisura) {
		Prodotto prodotto = new Prodotto(nome, quantita, unitaMisura);
		aggiornaListaConProdotto(this.registroMagazzino, prodotto);
		if (!this.listaSpesa.isEmpty()) {
			this.aggiornaListaSpesa(this.listaSpesa);
		}
	}

	private int getNumeroClientiPrenotatiInData(LocalDate date) {
		int count = 0;
		for (Prenotazione p : this.elencoPrenotazioni) {
			if (p.isPrenotazioneInData(date)) {
				count += p.getNumeroCoperti();
			}
		}
		return count;
	}

	private ArrayList<Prodotto> ricalcolaInBaseNumeroClienti(ArrayList<Prodotto> insieme, int numero) {
		ArrayList<Prodotto> insiemeNew = new ArrayList<>();
		for (Prodotto p : insieme) {
			Prodotto nuovo = new Prodotto(p.getNome(), p.getQuantita() * numero, p.getUnitaMisura());
			insiemeNew.add(nuovo);
		}
		return insiemeNew;
	}

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
