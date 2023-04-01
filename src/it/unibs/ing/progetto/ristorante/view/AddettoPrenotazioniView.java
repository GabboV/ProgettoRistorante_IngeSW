package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;
import it.unibs.fp.mylib.MyMenu;

import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;

public class AddettoPrenotazioniView extends View {
	
	private static final String ADDETTO_PRENOTAZIONI = "Addetto Prenotazioni";

	private static String[] OPZIONI = new String[] { "Aggiungi prenotazione", "Rimuovi prenotazione",
			"Visualizza prenotazioni" };

	private static String[] ORDINA = new String[] { "Ordina menu tematici", "Ordina dal menu alla carta" };

	public AddettoPrenotazioniView() {
		super(ADDETTO_PRENOTAZIONI, OPZIONI);
	}

	public void stampaPrenotazione(Prenotazione prenotazione) {
		StringBuilder s = new StringBuilder();

		String codice_cliente = prenotazione.getCodiceCliente();
		LocalDate data = prenotazione.getDataPrenotazione();
		int day = data.getDayOfMonth(), month = data.getMonthValue(), year = data.getYear();
		HashMap<Piatto, Integer> comanda = prenotazione.getComanda();
		int coperti = prenotazione.getNumeroCoperti();

		String rigaUno = String.format("Codice: %15s\t\tData: %d/%d/%d \n", codice_cliente, day, month, year);
		String rigaTre = "--------------------- Comanda ---------------------\n";
		String rigaDue = String.format("Numero coperti: %d\n", coperti);
		String righeComanda = this.buildComandaString(comanda);
		s.append(rigaUno);
		s.append(rigaDue);
		s.append(rigaTre);
		s.append(righeComanda);

		System.out.println(s.toString());
	}

	public void stampaListaPrenotazioni(ArrayList<Prenotazione> prenotazioni) {
		int contatore = 0;
		System.out.println("\n\n");
		for (Prenotazione p : prenotazioni) {
			System.out.println("------------------------ " + contatore + " ------------------------");
			this.stampaPrenotazione(p);
			System.out.println("\n");
			contatore++;
		}
	}

	public String buildComandaString(HashMap<Piatto, Integer> hash) {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<Piatto, Integer>> iter = hash.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Piatto, Integer> entry = iter.next();
			Piatto p = entry.getKey();
			int i = entry.getValue();
			String voce = String.format("Piatto: %15s   carico: %d   porzioni: %d", p.getNomePiatto(),
					p.getCaricoLavoro(), i);
			builder.append(voce);
			if (iter.hasNext())
				builder.append("\n");
		}
		return BelleStringhe.incornicia(builder.toString());
	}

	public int printSelezioneMenu() {
		MyMenu selezione = new MyMenu("Ordina", ORDINA);
		return selezione.scegli();
	}

	public int richiestaNumeroPorzioni(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}

}
