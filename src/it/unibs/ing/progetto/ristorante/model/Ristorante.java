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

	// Contiene tutte le ricette memorizzate nel database
	private ArrayList<Ricetta> elencoRicette;
	// Contiene tutti i piatti memorizzati nel database
	private ArrayList<Piatto> elencoPiatti;
	// Contiene tutti i menu tematici memorizzati nel database
	private ArrayList<MenuTematico> elencoMenuTematici;
	private ArrayList<Prodotto> insiemeBevande;
	private ArrayList<Prodotto> insiemeGeneriExtra;
	// perch� serve una HashMap?
	private HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta;
	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;
	private ArrayList<Prenotazione> elencoPrenotazioni;

	public Ristorante() {
		this.dataCorrente = null;
		this.caricoLavoroPerPersona = 1;
		this.numeroPostiASedere = 1;
		this.caricoLavoroRistorante = 1;
		this.elencoRicette = new ArrayList<Ricetta>();
		this.elencoPiatti = new ArrayList<Piatto>();
		this.elencoMenuTematici = new ArrayList<MenuTematico>();
		this.insiemeBevande = new ArrayList<Prodotto>();
		this.insiemeGeneriExtra = new ArrayList<Prodotto>();
		this.corrispondenzePiattoRicetta = new HashMap<Piatto, Ricetta>();
		this.registroMagazzino = new ArrayList<Prodotto>();
		this.listaSpesa = new ArrayList<Prodotto>();
		this.elencoPrenotazioni = new ArrayList<Prenotazione>();
	}

	// GETTER AND SETTER
	public LocalDate getDataCorrente() {
		return dataCorrente;
	}

	public void setDataCorrente(LocalDate dataCorrente) {
		this.dataCorrente = dataCorrente;
	}

	public void addPrenotazione(Prenotazione prenotazione) {
		this.elencoPrenotazioni.add(prenotazione);
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

	public ArrayList<Ricetta> getElencoRicette() {
		return elencoRicette;
	}

	public void setElencoRicette(ArrayList<Ricetta> elencoRicette) {
		this.elencoRicette = elencoRicette;
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

	public HashMap<Piatto, Ricetta> getCorrispondenzePiattoRicetta() {
		return corrispondenzePiattoRicetta;
	}

	public void setCorrispondenzePiattoRicetta(HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta) {
		this.corrispondenzePiattoRicetta = corrispondenzePiattoRicetta;
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

	public void addPiattoRicetta(Piatto piatto, Ricetta ricetta) {
		elencoPiatti.add(piatto);
		elencoRicette.add(ricetta);
		corrispondenzePiattoRicetta.put(piatto, ricetta);
	}

	// Aggiunge una nuova ricetta a quelle esistenti
	public void addRicetta(Ricetta r) {
		elencoRicette.add(r);
	}

	// Aggiunge un piatto a quelli esistenti
	public void addPiatto(Piatto p) {
		elencoPiatti.add(p);
	}

	// Aggiunge una nuova corrispondenza piatto-ricetta
	public void addCorrispondenza(Piatto p, Ricetta r) {
		corrispondenzePiattoRicetta.put(p, r);
	}

	// Controlla se in insiemeBevande esiste gia' una bevanda con il nome uguale a
	// qullo che si vuole aggiungere
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a insiemeBevande e stampa msg a video
	public void addBevanda(Prodotto bevanda) {
		boolean esiste = false;
		for (Prodotto b : insiemeBevande) {
			if (b.getNome().equalsIgnoreCase(bevanda.getNome())) {
				System.out.println("La bevanda gia' esiste.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			insiemeBevande.add(bevanda);
			System.out.println("E' stata aggiunta una bevanda.");
			System.out.println("Nome: " + bevanda.getNome());
			System.out.println("ConsumoProCapite: " + bevanda.getQuantita() + bevanda.getUnitaMisura());
		}
	}

	// Controlla se in insiemeGeneriExtra esiste gia' un genereExtra con il nome
	// uguale a qullo che si vuole aggiungere
	// Se gia' esiste, stampa a video un msg
	// Se non esiste la aggiunge a inisemeGeneriExtra e stampa a video un msg
	public void addGenereExtra(Prodotto genereExtra) {
		boolean esiste = false;
		for (Prodotto g : insiemeBevande) {
			if (g.getNome().equalsIgnoreCase(genereExtra.getNome())) {
				System.out.println("Il genere extra gia' esiste.");
				esiste = true;
				break;
			}
		}
		if (!esiste) {
			insiemeGeneriExtra.add(genereExtra);
			System.out.println("E' stata aggiunta un genere extra.");
			System.out.println("Nome: " + genereExtra.getNome());
			System.out.println("ConsumoProCapite: " + genereExtra.getQuantita() + genereExtra.getUnitaMisura());
		}
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

		ArrayList<Prodotto> listaIniziale = this.costruisciListaSpesa(totaleComande,data);

		// Confronto con registroMagazzino per togliere dalla lista precedente
		// ingredienti gia presenti e in quantita sufficiente

		this.listaSpesa = new ArrayList<Prodotto>();

		for (Prodotto voce : listaIniziale) {
			if (!this.contieneProdottoSufficiente(registroMagazzino, voce)) {

				float daAcquistare = (float) (voce.getQuantita()*FATTORE_10_PER_CENTO - this.quantitaInMagazzino(voce));

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
		List<Piatto> piatti = new ArrayList<Piatto>(piattiOrdinati.keySet());
		int numeroClientiIndata= this.getNumeroClientiPrenotatiInData(date);
		
		prodotti.addAll(this.ricalcolaInBaseNumeroClienti(insiemeBevande, numeroClientiIndata));
		prodotti.addAll(this.ricalcolaInBaseNumeroClienti(insiemeGeneriExtra, numeroClientiIndata));
		for (Piatto piatto : piatti) {

			Ricetta ricetta = corrispondenzePiattoRicetta.get(piatto); // Recupero la ricetta dalle corrispondenze
			ArrayList<Prodotto> ingredienti = ricetta.getElencoIngredientiPerPorzioni(piattiOrdinati.get(piatto));
			// Recupero la lista degli ingredienti
			aggiungiVoceUnivoco(prodotti, ingredienti);
		}
//		maggiorazionePercentuale(prodotti, PERCENTUALE);
		return prodotti;

	}

	private void aggiungiVoceUnivoco(ArrayList<Prodotto> prodotti, ArrayList<Prodotto> ingredienti) {
		for (Prodotto i : ingredienti) {
			if (this.contieneProdotto(prodotti, i)) {
				replaceProdotto(prodotti, i);
			} else {
				prodotti.add(i);
			}
		}
	}

	private void maggiorazionePercentuale(ArrayList<Prodotto> prodotti, float percentuale) {
		double fattorePercentuale = 1 + (percentuale / 100);
		for (Prodotto voceLista : prodotti) {
			float quantitaOld = voceLista.getQuantita();
			voceLista.setQuantita((float) (quantitaOld * fattorePercentuale));
		}
	}

	private void aggiornaListaConProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		if (this.contieneProdotto(prodotti, prodotto)) {
			this.replaceProdotto(prodotti, prodotto);
		} else {
			prodotti.add(prodotto);
		}
	}

	private void replaceProdotto(ArrayList<Prodotto> prodotti, Prodotto i) {
		for (Prodotto product : prodotti) {
			if (product.getNome().equalsIgnoreCase(i.getNome())) {
				product.setQuantita(product.getQuantita() + i.getQuantita());
				break;
			}
		}
	}

	private boolean contieneProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
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
		for(Prenotazione p: this.elencoPrenotazioni) {
			if(p.isValidinData(date)) {
				count+= p.getNumeroCoperti();
			}
		}
		return count;
	}
	
	private ArrayList<Prodotto> ricalcolaInBaseNumeroClienti(ArrayList<Prodotto> insieme ,int numero){
		ArrayList<Prodotto> insiemeNew = new ArrayList<>();
		for(Prodotto p: insieme) {
			Prodotto nuovo = new Prodotto(p.getNome(),p.getQuantita()*numero,p.getUnitaMisura());
			insiemeNew.add(nuovo);
		}
		return insiemeNew;
	}
	
	public void rimuoviProdottoQuantita(Prodotto prodotto, float quantita) {
		String nomeCercato = prodotto.getNome();
		for(Prodotto p: this.registroMagazzino) {
			if(p.getNome().equalsIgnoreCase(nomeCercato)) {
				if(p.getQuantita() > quantita) {
					p.setQuantita(p.getQuantita()-quantita);
				} else {
					rmvProdotto(registroMagazzino, prodotto);
				}
				break;
			}
		}
	}
	
	private static void rmvProdotto(ArrayList<Prodotto> prodotti,Prodotto prodotto) {
		int indice = 0;
		String nome = prodotto.getNome();
		for (int i = 0 ; i < prodotti.size(); i++) {
			if(nome.equalsIgnoreCase(prodotti.get(i).getNome())) {
				indice = i;
				prodotti.remove(indice);
				break;
			}
		}
		
	}

}
