package it.unibs.ing.progetto.ristorante;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.progetto.ristorante.controller.*;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

public class MainProva {

	public static void main(String[] args) {

		Ristorante ristorante = new Ristorante();
		LocalDate _10gennaio2023 = LocalDate.of(2023, 01, 10);
		LocalDate _17gennaio2023 = LocalDate.of(2023, 01, 17);
		Periodo periodo = new Periodo(_10gennaio2023, _17gennaio2023);
		LocalDate dataprenotazione = LocalDate.of(2023, 1, 14);

		Piatto p1 = new Piatto("Pasta al tonno", 3);
		p1.addPeriodoValidita(periodo);

		Piatto p2 = new Piatto("Pollo halal", 4);
		p2.addPeriodoValidita(periodo);

		Piatto p3 = new Piatto("Risotto allo zafferano", 9);
		p3.addPeriodoValidita(periodo);
		Piatto p4 = new Piatto("Onigiri", 7);
		p4.addPeriodoValidita(periodo);

		Prodotto i1 = new Prodotto("Pasta", (float) 300.0, "grammi");
		Prodotto i2 = new Prodotto("Tonno", (float) 100.0, "grammi");
		Prodotto i3 = new Prodotto("Sugo", (float) 80.0, "grammi");
		Prodotto i4 = new Prodotto("Olio", (float) 20.0, "ml");

		ArrayList<Prodotto> ingredienti1 = new ArrayList<>();
		ingredienti1.add(i1);
		ingredienti1.add(i2);
		ingredienti1.add(i3);
		ingredienti1.add(i4);
		Ricetta r1 = new Ricetta(3, 4);
		r1.setElencoIngredienti(ingredienti1);

		Prodotto i5 = new Prodotto("Pollo", (float) 1000.0, "grammi");
		Prodotto i6 = new Prodotto("Curry", (float) 100.0, "grammi");
		Prodotto i7 = new Prodotto("Olio", (float) 20.0, "ml");

		ArrayList<Prodotto> ingredienti2 = new ArrayList<>();
		ingredienti2.add(i5);
		ingredienti2.add(i6);
		ingredienti2.add(i7);
		Ricetta r2 = new Ricetta(4, 3);
		r2.setElencoIngredienti(ingredienti2);

		Prodotto i8 = new Prodotto("Riso basmati", (float) 500.0, "grammi");
		Prodotto i9 = new Prodotto("Zafferano", (float) 10.0, "grammi");

		ArrayList<Prodotto> ingredienti3 = new ArrayList<>();
		ingredienti3.add(i8);
		ingredienti3.add(i9);
		Ricetta r3 = new Ricetta(5, 1);
		r3.setElencoIngredienti(ingredienti3);

		Prodotto i10 = new Prodotto("Alga Nori", (float) 3, "unita");
		Prodotto i12 = new Prodotto("Tonno", (float) 100.0, "grammi");
		Prodotto i13 = new Prodotto("Salsa", (float) 80.0, "ml");
		Prodotto i14 = new Prodotto("Riso", (float) 200.0, "grammi");

		ArrayList<Prodotto> ingredienti4 = new ArrayList<>();
		ingredienti4.add(i10);
		ingredienti4.add(i12);
		ingredienti4.add(i13);
		ingredienti4.add(i14);
		Ricetta r4 = new Ricetta(3, 1);
		r4.setElencoIngredienti(ingredienti4);

		HashMap<Piatto, Ricetta> corrispondenzeProva = new HashMap<>();
		corrispondenzeProva.put(p1, r1);
		corrispondenzeProva.put(p2, r2);
		corrispondenzeProva.put(p3, r3);
		corrispondenzeProva.put(p4, r4);
		ristorante.setCorrispondenzePiattoRicetta(corrispondenzeProva);

		ArrayList<Piatto> elencoPiattiProva = new ArrayList<>();
		elencoPiattiProva.add(p1);
		elencoPiattiProva.add(p2);
		elencoPiattiProva.add(p3);
		elencoPiattiProva.add(p4);

		ArrayList<Ricetta> ricetteProva = new ArrayList<>();
		ricetteProva.add(r1);
		ricetteProva.add(r2);
		ricetteProva.add(r3);
		ricetteProva.add(r4);
		
		ristorante.setElencoPiatti(elencoPiattiProva);
		ristorante.setElencoRicette(ricetteProva);

		HashMap<Piatto,Integer> comanda1 = new HashMap<Piatto,Integer>();
		comanda1.put(p1, 3);
		comanda1.put(p4, 7);
		comanda1.put(p2, 6);
		comanda1.put(p3, 2);
	
		Prenotazione pre1 = new Prenotazione("Prima",4,comanda1, dataprenotazione);
		
		HashMap<Piatto,Integer> comanda2 = new HashMap<Piatto,Integer>();
		comanda2.put(p1, 1);
		comanda2.put(p4, 5);
		comanda2.put(p2, 4);
		comanda2.put(p3, 3);
	
		Prenotazione pre2 = new Prenotazione("Prima",3,comanda2, dataprenotazione);
		
		ristorante.addPrenotazione(pre1);
		ristorante.addPrenotazione(pre2);
		
		MagazziniereController m = new MagazziniereController(ristorante);
		m.avviaSessione();

	}

}
