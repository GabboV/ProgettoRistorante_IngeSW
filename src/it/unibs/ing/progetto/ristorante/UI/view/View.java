package it.unibs.ing.progetto.ristorante.UI.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class View {

	private String[] ELENCO_COMANDI;
	private String ruolo_nome;
	private MyMenu menu;

	public View(String ruolo_nome, String[] elenco_comandi) {
		super();
		ELENCO_COMANDI = elenco_comandi;
		this.ruolo_nome = ruolo_nome;
		this.setMenu();
	}

	private void setMenu() {
		menu = new MyMenu(ruolo_nome, ELENCO_COMANDI);
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}

	public void stampaMsg(String messaggio) {
		System.out.println(messaggio);
	}

	public boolean yesOrNo(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}

	public int printMenu() {
		return menu.scegli();
	}

	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public Float richiestaQuantitaCompreso(String messaggio, float min, float max) {
		return InputDati.leggiFloatCompreso(messaggio, min, max);
	}

	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}

	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public int richiestaInteroConMinimoMassimo(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public UnitaMisura leggiUnitaMisura() {
		return InputDati.richiestaUnitaMisura();
	}

	public void stampaElencoMenuTematici(List<MenuTematico> elencoMenuTematici) {
		if (elencoMenuTematici.isEmpty()) {
			stampaMsg("La lista e' vuota.");
		} else {
			System.out.println("\nELENCO MENU TEMATICI");
			int contatore = 0;
			for (MenuTematico m : elencoMenuTematici) {
				stampaMsg(" ----------- " + contatore + " ----------- ");
				stampaMenuTematico(m);
				contatore++;
			}
			System.out.println();
		}
	}

	public void stampaMenuTematico(MenuTematico m) {
		System.out.println("Nome menu tematico: " + m.getNome());
		System.out.println("Carico lavoro: " + m.getCaricoLavoro());
		System.out.println("Piatti del menu: ");
		for (MenuComponent p : m.getElencoPiatti()) {
			System.out.println("Nome: " + p.getNome());
		}
		System.out.println("Periodi di validita': ");
		for (Periodo periodo : m.getPeriodiValidita()) {
			LocalDate dInizio = periodo.getDataInizio();
			LocalDate dFine = periodo.getDataFine();
			stampaPeriodo(dInizio, dFine);
		}
	}

	public void stampaElencoPiatti(List<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaMsg("Nome: " + p.getNome());
			stampaMsg("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
	}

	public void stampaInsiemeProdotti(List<Prodotto> elencoProdotti, String nomeElencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println("\nLa lista e' vuota.\n");
		} else {
			System.out.println(nomeElencoProdotti);
			for (Prodotto p : elencoProdotti) {
				System.out.println(" ----------- " + contatore + " ----------- ");
				stampaProdotto(p);
				contatore++;
			}
			System.out.println();
		}
	}

	public void stampaInsiemeProdottiMagazzino(List<Prodotto> elencoProdotti) {
		int contatore = 0;
		if (elencoProdotti.isEmpty()) {
			System.out.println();
			System.out.println("La lista e' vuota.");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("+----------------+ Aggiungi flusso +----------------+");
			System.out.println();
			for (Prodotto p : elencoProdotti) {
				System.out.println("------------------------- " + contatore + " -------------------------");
				stampaProdottoMagazzino(p);
				System.out.println();
				contatore++;
			}
			System.out.println();
		}
	}

	public void stampaProdotto(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Consumo pro capite: " + p.getQuantita() + p.getUnitaMisura());
	}

	public void stampaProdottoMagazzino(Prodotto p) {
		System.out.println(
				String.format("Nome: %10s\tGiacienza: %5.2f\t%5s", p.getNome(), p.getQuantita(), p.getUnitaMisura()));
	}

	public String ottieniStringaData(LocalDate data) {
		return data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear();
	}
	
	public void stampaData(LocalDate data) {
		System.out.print(data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
	}

	public void stampaPeriodo(LocalDate dataInizio, LocalDate dataFine) {
		stampaData(dataInizio);
		System.out.print(" --> ");
		stampaData(dataFine);
		System.out.println();
	}

}

