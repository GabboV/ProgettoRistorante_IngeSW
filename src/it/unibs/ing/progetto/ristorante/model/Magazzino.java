package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Magazzino implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Prodotto> registroMagazzino;
	private ArrayList<Prodotto> listaSpesa;
	public final static double EPSILON = 1E-3;

	public Magazzino(ArrayList<Prodotto> registroMagazzino, ArrayList<Prodotto> listaSpesa) {
		this.registroMagazzino = registroMagazzino;
		this.listaSpesa = listaSpesa;
	}

	public Magazzino() {
		super();
		this.registroMagazzino = new ArrayList<>();
		this.listaSpesa = new ArrayList<>();
	}

	public ArrayList<Prodotto> getRegistroMagazzino() {
		return registroMagazzino;
	}

	public ArrayList<Prodotto> getListaSpesa() {
		return listaSpesa;
	}

	/**
	 * Genera la lista della spesa
	 * 
	 * @param prenotazioni
	 * @param generi
	 * @param bevande
	 * @param prenotati
	 */
	public void generaListaSpesa(ArrayList<Prenotazione> prenotazioni, ArrayList<Prodotto> generi,
			ArrayList<Prodotto> bevande, int prenotati) {
		ArrayList<Prodotto> lista_provvisoria = (ArrayList<Prodotto>) this.generaProdottiUsatiInCucina(prenotazioni,
				generi, bevande, prenotati);
		maggiorazionePercentuale(lista_provvisoria, 10);
		aggiornaListaSpesaConInventario(lista_provvisoria);
	}

	/**
	 * Aggiunge una voce alla lista con un nuovo prodotto
	 * 
	 * @param nome
	 * @param quantita
	 * @param unitaMisura
	 */
	public void addProdottoInventario(String nome, float quantita, UnitaMisura unitaMisura) {
		Prodotto prodotto = new Prodotto(nome, quantita, unitaMisura);
		this.registroMagazzino.add(prodotto);
	}

	/**
	 * True se esiste il prodotto esiste
	 * 
	 * @param nome
	 * @return
	 */
	public boolean esisteProdottoInMagazzino(String nome) {
		return this.registroMagazzino.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));

	}

	/**
	 * Dando in input un prodotto e una quantita, la cerca nel registro e ne aumenta
	 * della quantita indicata
	 * 
	 * @param prodotto
	 * @param quantita
	 */
	public void addQuantitaProdottoMagazzino(Prodotto prodotto, float quantita) {
		String pNome = prodotto.getNome();
		UnitaMisura pMisura = prodotto.getUnitaMisura();
		for (Prodotto p : registroMagazzino) {
			if (p.getNome().equalsIgnoreCase(pNome) && p.getUnitaMisura().equals(pMisura)) {
				p.setQuantita(quantita + p.getQuantita());
			}
		}
	}

	/**
	 * Dando in input un prodotto e una quantita, la cerca nel registro e ne riduce
	 * della quantita indicata, nel caso la quantita finale sia prossima allo 0
	 * allora la rimuove definitivamente
	 * 
	 * @param prodotto
	 * @param quantita
	 */
	public void rimuoviQuantitaProdottoMagazzino(Prodotto prodotto, float quantita) {
		String nomeCercato = prodotto.getNome();
		UnitaMisura misura = prodotto.getUnitaMisura();
		for (Prodotto p : this.registroMagazzino) {
			if (p.getNome().equalsIgnoreCase(nomeCercato) && p.getUnitaMisura().equals(misura)) {
				if (compareFloats(0f, p.getQuantita() - quantita)) {
					rimuoviProdotto(registroMagazzino, prodotto);
				} else {
					p.setQuantita(p.getQuantita() - quantita);
				}
				break;
			}
		}

	}

	/**
	 * Confronta due float, se sono vicini restituisce true
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static boolean compareFloats(float x, float y) {
		if (Math.abs(x - y) <= EPSILON) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Aggiorna la lista della spesa in base alle giacenze in magazzino
	 * 
	 * @param listaIniziale
	 */
	private void aggiornaListaSpesaConInventario(ArrayList<Prodotto> listaIniziale) {
		this.listaSpesa = new ArrayList<>();
		for (Prodotto prodotto : listaIniziale) {
			if (!this.contieneProdottoSufficiente(registroMagazzino, prodotto)) {
				float daAcquistare = prodotto.getQuantita() - this.quantitaInMagazzino(prodotto);
				this.listaSpesa.add(new Prodotto(prodotto.getNome(), daAcquistare, prodotto.getUnitaMisura()));
			}
		}
	}

	/**
	 * Restituisce la quantita di prodotto presente in magazzino
	 * 
	 * @param prodotto
	 * @return
	 */
	private float quantitaInMagazzino(Prodotto prodotto) {
		return registroMagazzino.stream().filter(p -> p.getNome().equalsIgnoreCase(prodotto.getNome())).findFirst()
				.map(Prodotto::getQuantita).orElse(0.0f);
	}

	/**
	 * Restituisce true se la lista contiene il prodotto con una quantit� maggiore
	 * uguale
	 * 
	 * @param prodotti
	 * @param prodotto
	 * @return
	 */
	private boolean contieneProdottoSufficiente(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		String cercato = prodotto.getNome();
		Float quantita = prodotto.getQuantita();
		return prodotti.stream().filter(p -> p.getNome().equalsIgnoreCase(cercato))
				.anyMatch(p -> p.getQuantita() >= quantita);
	}

	/**
	 * Combina tutte le comande -> output = unica comanda
	 * 
	 * @param prenotazioni
	 * @return
	 */
	private static HashMap<Piatto, Integer> combinaAllcomande(ArrayList<Prenotazione> prenotazioni) {
		return prenotazioni.stream().map(Prenotazione::getComanda).flatMap(map -> map.entrySet().stream())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, HashMap::new));
	}

	/**
	 * Crea la lista di prodotti che servono in cucina
	 * 
	 * @return
	 */
	private List<Prodotto> generaProdottiUsatiInCucina(ArrayList<Prenotazione> prenotazioni, ArrayList<Prodotto> generi,
			ArrayList<Prodotto> bevande, int prenotati) {
		HashMap<Piatto, Integer> comanda_unica = combinaAllcomande(prenotazioni);
		ArrayList<Prodotto> list = (ArrayList<Prodotto>) costruisciListaProdottiDaComanda(comanda_unica);
		aggiungiBevandeEGeneriExtra(list, generi, bevande, prenotati);
		return list;
	}

	/**
	 * Aggiorna la lista aggiungendo bevande e generi extra
	 * 
	 * @param list
	 */
	private void aggiungiBevandeEGeneriExtra(ArrayList<Prodotto> list, ArrayList<Prodotto> generi,
			ArrayList<Prodotto> bevande, int prenotati) {
		if (prenotati > 0) {

			aggiornaListaConProdotti(list, (ArrayList<Prodotto>) this.moltiplicaPerPrenotati(generi, prenotati));
			aggiornaListaConProdotti(list, (ArrayList<Prodotto>) this.moltiplicaPerPrenotati(bevande, prenotati));
		}
	}

	private List<Prodotto> moltiplicaPerPrenotati(ArrayList<Prodotto> list, int prenotati) {
		return list.stream().map(p -> new Prodotto(p.getNome(), (p.getQuantita() * prenotati), p.getUnitaMisura()))
				.collect(Collectors.toList());
	}

	/**
	 * Restituisce una lista contenenti tutti i prodotti richiesti per un
	 * ordinazione
	 * 
	 * @param ordine
	 * @return
	 */
	private List<Prodotto> costruisciListaProdottiDaComanda(HashMap<Piatto, Integer> ordine) {
		ArrayList<Prodotto> prodotti = new ArrayList<Prodotto>();
		for (Entry<Piatto, Integer> entry : ordine.entrySet()) {
			Ricetta recipe = entry.getKey().getRicetta();
			int porzioni = entry.getValue();
			aggiornaListaConProdotti(prodotti, (ArrayList<Prodotto>) recipe.getElencoIngredientiPerPorzioni(porzioni));
		}
		return prodotti;
	}

	/**
	 * Aumenta di una percentuale la quantita dei prodotti nella lista
	 * 
	 * @param lista
	 * @param percentuale
	 */
	private static void maggiorazionePercentuale(ArrayList<Prodotto> lista, float percentuale) {
		if (lista == null || percentuale < 0) {
			throw new IllegalArgumentException("Impossibile aggiungere una percentuale");
		}
		float fattore = 1 + (percentuale / 100);
		for (Prodotto prodotto : lista) {
			float quantitaNew = prodotto.getQuantita() * fattore;
			prodotto.setQuantita(quantitaNew);
		}
	}

	/**
	 * Aggiorna una lista con una lista di prodotti
	 * 
	 * @param prodottiLista
	 * @param prodotti
	 */
	private static void aggiornaListaConProdotti(ArrayList<Prodotto> prodottiLista, ArrayList<Prodotto> prodotti) {
		for (Prodotto prodotto : prodotti) {
			aggiornaListaConProdotto(prodottiLista, prodotto);
		}
	}

	/**
	 * Aggiorna con un prodotto la lista
	 * 
	 * @param listaProdotti
	 * @param prodotto
	 */
	private static void aggiornaListaConProdotto(ArrayList<Prodotto> listaProdotti, Prodotto prodotto) {
		if (listaContieneProdotto(listaProdotti, prodotto)) {
			aggiornaQuantitaProdotto(listaProdotti, prodotto);
		} else {
			listaProdotti.add(prodotto);
		}
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

	/**
	 * Aggiorna la quantita di un prodotto all'interno di una lista
	 * 
	 * @param prodotti
	 * @param prodotto
	 * @return
	 */
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
	 * Rimuove un prodotto da una lista di prodotti
	 * 
	 * @param prodotti
	 * @param prodotto
	 */
	private static void rimuoviProdotto(ArrayList<Prodotto> prodotti, Prodotto prodotto) {
		UnitaMisura misura = prodotto.getUnitaMisura();
		String nome = prodotto.getNome();
		prodotti.removeIf(p -> p.getNome().equalsIgnoreCase(nome) && p.getUnitaMisura().equals(misura));
	}
}
