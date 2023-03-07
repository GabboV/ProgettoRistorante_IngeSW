package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;

import it.unibs.ing.progetto.ristorante.model.*;

public class OutputHandler {

	private static final String PERIODI_VALIDI = "Periodi validi:\n";
	private static final String FORMAT_PERIODO = "- [%2d/%2d/%4d] - [%2d/%2d/%4d]\n";
	private static final String QUANTITA_PIATTO = "Quantita: %d\n";
	private static final String DENOMINAZIONE_PIATTO = "Denominazione: %10s\n";
	private static final String FORMAT_VOCI_LISTA = "-%d  %20s  %3.2f  %5s\n";
	private static final String LISTA_DELLA_SPESA = "Lista della Spesa\n";

	/**
	 * Formatta una lista di prodotti
	 * @param lista
	 * @return
	 */
	public static String formatListaProdotti(ArrayList<Prodotto> lista) {
		StringBuilder builder = new StringBuilder();
		int indice = 1;
		builder.append(LISTA_DELLA_SPESA);
		for (Prodotto voce : lista) {
			String denominazione = voce.getNome();
			float quantita = voce.getQuantita();
			UnitaMisura unitaMisura = voce.getUnitaMisura();
			String voceFormattata = String.format(FORMAT_VOCI_LISTA, indice, denominazione, quantita,
					unitaMisura);
			builder.append(voceFormattata);
			indice++;
		}
		return builder.toString();
	}

	/**
	 * Formatta una string da un piatto
	 * @param piatto
	 * @return
	 */
	public static String formatPiatto(Piatto piatto) {
		StringBuilder builder = new StringBuilder();
		String formatNome = String.format(DENOMINAZIONE_PIATTO, piatto.getNomePiatto());
		String formatCaricoLavoro = String.format(QUANTITA_PIATTO, piatto.getCaricoLavoro());
		builder.append(formatNome);
		builder.append(formatCaricoLavoro);
		builder.append(PERIODI_VALIDI);
		for (Periodo periodo : piatto.getPeriodiValidita()) {
			LocalDate inizio = periodo.getDataInizio();
			LocalDate fine = periodo.getDataFine();
			String formatPeriodo = String.format(FORMAT_PERIODO, inizio.getDayOfMonth(),
					inizio.getMonth(), inizio.getYear(), fine.getDayOfMonth(), fine.getMonth(), fine.getYear());
			builder.append(formatPeriodo);
		}
		return builder.toString();
	}
	

}
