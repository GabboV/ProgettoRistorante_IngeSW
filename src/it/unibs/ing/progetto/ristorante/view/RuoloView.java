package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class RuoloView {

	private static String[] ELENCO_COMANDI;
	private MyMenu menu;

	public RuoloView() {
		
	}

	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}

	public void stampaMsg(String messaggio) {
		System.out.println(messaggio);
	}

	public boolean richiestaNuovaAggiunta(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}

	public int printMenu() {
		return menu.scegli();
	}

	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}

	public int leggiInteroCompreso(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public int richiestaInteroPositivo(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}

	public int richiestaInteroNonNegativo(String messaggio) {
		return InputDati.leggiInteroNonNegativo(messaggio);
	}

	public int richiestaInteroConMinimoMassimo(String messaggio, int min, int max) {
		return InputDati.leggiIntero(messaggio, min, max);
	}

	public UnitaMisura richiestaUnitaMisura(String messaggio) {
		return InputDati.leggiUnitaMisura(messaggio);
	}

	public Float richiestaFloatPositivo(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}

	public void stampaElencoPiattiRicette(ArrayList<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			Ricetta r = p.getRicetta();
			stampaMsg(" ----------- " + contatore + " ----------- ");
			stampaPiattoRicetta(p, r);
			contatore++;
		}
	}

	public void stampaElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici) {
		if (elencoMenuTematici.isEmpty()) {
			stampaMsg("La lista e' vuota.");
		} else {
			int contatore = 0;
			for (MenuTematico m : elencoMenuTematici) {
				stampaMsg(" ----------- " + contatore + " ----------- ");
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

	public void stampaElencoPiatti(ArrayList<Piatto> elencoPiatti) {
		int contatore = 0;
		for (Piatto p : elencoPiatti) {
			System.out.println(" ----------- " + contatore + " ----------- ");
			stampaMsg("Nome: " + p.getNomePiatto());
			stampaMsg("Carico di lavoro: " + p.getCaricoLavoro());
			contatore++;
		}
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

	public void stampaIngrediente(Prodotto p) {
		System.out.println("Nome: " + p.getNome());
		System.out.println("Dose: " + p.getQuantita() + p.getUnitaMisura());
	}

	public void stampaInsiemeProdotti(ArrayList<Prodotto> elencoProdotti) {
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
