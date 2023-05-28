package it.unibs.ing.progetto.ristorante.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


/**
 * Classe immutabile (Attributi privati e solo metodi getters)
 * 
 *
 */
public class MenuTematico implements Serializable, MenuComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private List<Piatto> elencoPiatti;
	private int caricoLavoro;
	private List<Periodo> periodiValidita;

	public MenuTematico(String nome, List<Piatto> elencoPiatti, int caricoLavoro,
			List<Periodo> periodiValidita) {
		super();
		if (nome == null || elencoPiatti == null || elencoPiatti.isEmpty() || caricoLavoro <= 0
				|| periodiValidita == null || periodiValidita.isEmpty()) {
			throw new IllegalArgumentException("Problemi nella creazione del menu tematico");
		}
		this.nome = nome;
		this.elencoPiatti = elencoPiatti;
		this.caricoLavoro = caricoLavoro;
		this.periodiValidita = periodiValidita;
	}

	public boolean isDisponibileInData(LocalDate data) {
		// prende ciascun Periodo presente in periodiValidita
		for (Periodo d : periodiValidita) {
			// se la data � compresa tra dataInizio e dataFine oppure coincide con una delle
			// due date ritorna true
			if (d.contieneDataEstremiInclusi(data))
				return true;
		}
		return false;
	}

	public String getNome() {
		return nome;
	}

	public List<Piatto> getElencoPiatti() {
		return elencoPiatti;
	}

	public int getCaricoLavoro() {
		
		return caricoLavoro;
	}

	public List<Periodo> getPeriodiValidita() {
		return periodiValidita;
	}

	@Override
	public List<Piatto> getContenuto() {
		return this.elencoPiatti;
	}

	

}
