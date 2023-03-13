package it.unibs.ing.progetto.ristorante.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ristorante {

	private static final double FATTORE_10_PER_CENTO = 1.10;
	private static final int PERCENTUALE = 10;
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

	/*
	 * 
	 * 
	 * 
	 * 
	 * Work in progress
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public boolean isRistorantePienoInData(LocalDate data) {
		return (this.getPostiDisponibiliInData(data) == 0);
	}

	public int getPostiDisponibiliInData(LocalDate data) {
		int numeroClienti = this.getNumeroClientiPrenotatiInData(data);
		return this.numeroPostiASedere - numeroClienti;
	}

	public void addPrenotazione(LocalDate data, HashMap<Piatto, Integer> comanda, int coperti) {
		this.elencoPrenotazioni.add(new Prenotazione(null, coperti, comanda, data));
	}

	public boolean nonCiSonoPrenotazioni() {
		return (this.elencoPrenotazioni.size() == 0);
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

	public boolean isPrenotazioneFattibileInData(Prenotazione prenotazione, LocalDate data) {

		float caricoDaSostenereInData = this.getCaricoLavoroDaSostenereInData(data);
		float caricoDaSostenereAggiuntivo = prenotazione.getCaricoLavoroTotalePrenotazione();
		float nuovoCarico = caricoDaSostenereInData + caricoDaSostenereAggiuntivo;

		int postiLiberi = this.getPostiDisponibiliInData(data);
		int nuoviPrenotati = prenotazione.getNumeroCoperti();

		/*
		 * Fattibile se e solo se il carico di lavoro nuovo non supera quello settato e
		 * se i postiLiberi sono sufficienti
		 */
		return (nuovoCarico <= this.caricoLavoroRistorante && postiLiberi >= nuoviPrenotati);

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

	public void removePrenotazioniScadute(LocalDate data) {
		// Con che base si stabilisce se una prenotazione sia scaduta o meno (?)
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

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

	// METODI DA CONTROLLARE SE ANCORA VALIDI O SERVONO

	// MenuCarta contiene elenco dei piatti validi nella data
	// serve classe MenuCarta? No, perche' e' piu facile generarlo con un metodo
	// solo quando serve
	// altrimenti dovrei ogni volta che e' richiesta generare una istanza di
	// MenuCarta per quando mi serve,
	// ma non ha senso conservarla da qualche parte perche ogni giorno puo
	// cambiare

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

	//ritorna il valore del piatto in posizione data da scelta
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
	
	//aggiunge un piatto e la rispettiva ricetta a elencoPiatti
	public void addPiattoRicetta(ArrayList<Prodotto> elencoIngredienti, int porzioni, int caricoLavoro, String nomePiatto, ArrayList<Periodo> periodi) {
		Ricetta ricetta = new Ricetta(elencoIngredienti, porzioni, caricoLavoro);
		Piatto piatto = new Piatto(nomePiatto, caricoLavoro, ricetta, periodi);
		elencoPiatti.add(piatto);
	}

	//aggiunge un piatto a quelli esistenti
	public void addPiatto(Piatto p) {
		elencoPiatti.add(p);
	}

	//aggiunge un nuovo menu tematico
	public void addMenuTematico(String nomeMenuTematico, ArrayList<Piatto> piatti, int caricoLavoroMenuTematico, ArrayList<Periodo> periodi) {
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

	/*
	 * ATTENZIONE equals() non implementato nella classe Prodotto, quindi il metodo
	 * non funziona per ora
	 */
	public void creaListaSpesa(LocalDate data) {

		// Prendo tutte le prenotazioni valide per la data

		ArrayList<Prenotazione> prenotazioniInData = this.creaElencoPrenotazioniInData(data);

		// Unisco tutte le comande -> Ogni piatto prenotato avra una voce con la sua
		// molteplicita
		// Questo si rende necessario siccome in diverse prenotazioni ci possono essere
		// piatti
		// uguali

		HashMap<Piatto, Integer> totaleComande = unisciComande(prenotazioniInData);

		// Costruisco la lista INIZIALE di tutti gli ingredienti necessari

		ArrayList<Prodotto> listaIniziale = this.costruisciListaSpesa(totaleComande, data);

		// Confronto con registroMagazzino per togliere dalla lista precedente
		// ingredienti gia presenti e in quantita sufficiente

		this.listaSpesa = new ArrayList<Prodotto>();

		for (Prodotto voce : listaIniziale) {
			if (!this.contieneProdottoSufficiente(registroMagazzino, voce)) {

				float daAcquistare = (float) (voce.getQuantita() * FATTORE_10_PER_CENTO
						- this.quantitaInMagazzino(voce));

				Prodotto voceDaInserire = new Prodotto(voce.getNome(), daAcquistare, voce.getUnitaMisura());
				this.listaSpesa.add(voceDaInserire);
			}
		}

	}

	// crea elenco prenotazioni valide in data
	private ArrayList<Prenotazione> creaElencoPrenotazioniInData(LocalDate data) {
		ArrayList<Prenotazione> prenotazioniInData = new ArrayList<Prenotazione>();
		for (Prenotazione p : this.elencoPrenotazioni) {
			if (p.isValidinData(data)) {
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

	private static HashMap<Piatto, Integer> unisciComande(ArrayList<Prenotazione> prenotazioni) {

		HashMap<Piatto, Integer> elenco = new HashMap<>();

		for (Prenotazione pre : prenotazioni) {
			HashMap<Piatto, Integer> comanda = pre.getComanda();
			List<Piatto> piattiInComanda = new ArrayList<Piatto>(comanda.keySet());

			for (Piatto p : piattiInComanda) {
				if (elenco.containsKey(p)) {
					int ValoreOld = elenco.get(p);
					int daAggiungere = comanda.get(p);
					int nuovoValore = ValoreOld + daAggiungere;
					elenco.replace(p, nuovoValore);
				} else {
					elenco.put(p, comanda.get(p));
				}
			}
		}

		return elenco;
	}

	private ArrayList<Prodotto> costruisciListaSpesa(HashMap<Piatto, Integer> piattiOrdinati, LocalDate date) {

		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		ArrayList<Piatto> piatti = new ArrayList<Piatto>(piattiOrdinati.keySet());
		int numeroClientiIndata = this.getNumeroClientiPrenotatiInData(date);

		prodotti.addAll(this.ricalcolaInBaseNumeroClienti(insiemeBevande, numeroClientiIndata));
		prodotti.addAll(this.ricalcolaInBaseNumeroClienti(insiemeGeneriExtra, numeroClientiIndata));

		for (Piatto piatto : piatti) {

			Ricetta ricetta = piatto.getRicetta(); // Recupero la ricetta dalle corrispondenze
			ArrayList<Prodotto> ingredienti = ricetta.getElencoIngredientiPerPorzioni(piattiOrdinati.get(piatto));// Recupero
																													// la
																													// lista
																													// degli
																													// ingredienti
			aggiungiVoceUnivoco(prodotti, ingredienti);
		}
//		maggiorazionePercentuale(prodotti, PERCENTUALE);
		return prodotti;

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
			return false;
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

	private void aggiungiVoceUnivoco(ArrayList<Prodotto> prodottiLista, ArrayList<Prodotto> ingredienti) {
		for (Prodotto prodotto : ingredienti) {
			aggiornaListaConProdotto(prodottiLista, prodotto);
		}
	}

	private void aggiornaListaConProdotto(ArrayList<Prodotto> listaProdotti, Prodotto prodotto) {
		if (this.listaContieneProdotto(listaProdotti, prodotto)) {
			this.aggiornaQuantitaProdotto(listaProdotti, prodotto);
		} else {
			listaProdotti.add(prodotto);
		}
	}

	private boolean aggiornaQuantitaProdotto(ArrayList<Prodotto> prodotti, Prodotto i) {
		for (Prodotto product : prodotti) {
			if (product.getNome().equalsIgnoreCase(i.getNome())) {
				product.setQuantita(product.getQuantita() + i.getQuantita());
				return true;
			}
		}
		return false;
	}

	private boolean listaContieneProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
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
			if (prodottoCorrente.equalsIgnoreCase(nomeCercato) && prodotto.getQuantita() >= p.getQuantita()) {
				return true;
			}
		}
		return false;
	}

	public void addProdottoInventario(Prodotto prodotto) {
		this.aggiornaListaConProdotto(this.registroMagazzino, prodotto);
	}

	private int getNumeroClientiPrenotatiInData(LocalDate date) {
		int count = 0;
		for (Prenotazione p : this.elencoPrenotazioni) {
			if (p.isValidinData(date)) {
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

	public void rimuoviProdottoQuantitaInRegistro(Prodotto prodotto, float quantita) {
		String nomeCercato = prodotto.getNome();
		for (Prodotto p : this.registroMagazzino) {
			if (p.getNome().equalsIgnoreCase(nomeCercato)) {
				if (p.getQuantita() > quantita) {
					p.setQuantita(p.getQuantita() - quantita);
				} else {
					rmvProdotto(registroMagazzino, prodotto);
				}
				break;
			}
		}
	}

	private static void rmvProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		int indice = 0;
		String nome = prodotto.getNome();
		for (int i = 0; i < prodotti.size(); i++) {
			if (nome.equalsIgnoreCase(prodotti.get(i).getNome())) {
				indice = i;
				prodotti.remove(indice);
				break;
			}
		}
	}

	public boolean isListaSpesaEmpty() {
		if (this.listaSpesa.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isRegistroMagazzinoEmpty() {
		if (this.registroMagazzino.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
