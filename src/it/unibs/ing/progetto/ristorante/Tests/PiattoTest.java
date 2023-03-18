package it.unibs.ing.progetto.ristorante.Tests;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Periodo;

public class PiattoTest {
	
	private Piatto piatto;
	private ArrayList<Periodo> periodiValidita;
	private Periodo periodo1;
	private Periodo periodo2;
	private Ricetta ricetta;

	@Before
	public void setUp() throws Exception {
		ricetta = new Ricetta(null, 4,3);
		periodiValidita = new ArrayList<>();
		periodo1 = new Periodo(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 3, 31));
		periodo2 = new Periodo(LocalDate.of(2022, 10, 1), LocalDate.of(2022, 12, 31));
		periodiValidita.add(periodo1);
		periodiValidita.add(periodo2);
		piatto = new Piatto("Spaghetti aglio, olio e peperoncino", 3, ricetta, periodiValidita);
	}

	@Test
	public void testIsDisponibileInData() {
		// check if a date falls within a valid period
		LocalDate data1 = LocalDate.of(2022, 2, 1);
		assertTrue(piatto.isDisponibileInData(data1));
		
		// check if a date outside any valid period returns false
		LocalDate data2 = LocalDate.of(2022, 4, 1);
		assertFalse(piatto.isDisponibileInData(data2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPiattoIllegalArgumentException() {
		// test if IllegalArgumentException is thrown when creating a Piatto object with null Ricetta or PeriodiValidita
		new Piatto("Spaghetti aglio, olio e peperoncino", 3, null, periodiValidita);
		new Piatto("Spaghetti aglio, olio e peperoncino", 3, ricetta, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCostruttorePeriodiValiditaNull() {
		Ricetta ricetta = new Ricetta(null, 4,5);
		new Piatto("Carbonara", 5, ricetta, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCostruttoreRicettaNull() {
		ArrayList<Periodo> periodiValidita = new ArrayList<Periodo>();
		periodiValidita.add(new Periodo(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 7)));
		new Piatto("Carbonara", 5, null, periodiValidita);
	}

	@Test
	public void testAddPeriodoValidita() {
		Ricetta ricetta = new Ricetta(null, 4,5);
		ArrayList<Periodo> periodiValidita = new ArrayList<Periodo>();
		periodiValidita.add(new Periodo(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 7)));
		Piatto piatto = new Piatto("Carbonara", 5, ricetta, periodiValidita);

		piatto.addPeriodoValidita(new Periodo(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 7)));
		assertEquals(2, piatto.getPeriodiValidita().size());
		assertTrue(piatto.isDisponibileInData(LocalDate.of(2022, 2, 4)));
		assertFalse(piatto.isDisponibileInData(LocalDate.of(2022, 2, 8)));
	}
}
