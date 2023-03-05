package it.unibs.ing.progetto.ristorante.view;

import java.time.LocalDate;
import java.util.Date;

import it.unibs.fp.mylib.InputDati;

public class GestoreView {

	
	public void stampaMsgBenvenutoInizializzazione() {
		System.out.println("Benvenuto Gestore.");
		System.out.println("Siccome si tratta del primo avvio del programma e' necessaria l'inizializzazione dei parametri.");
	}
	
	public LocalDate richiestaData(String messaggio) {
		return InputDati.leggiData(messaggio);
	}
	
	//possibile raccogliere alcuni metodi in un metodo solo?
	public int richiestaNumeroPostiRistorante(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public int richiestaCaricoLavoro(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public int richiestaNumeroPorzioni(String messaggio) {
		return InputDati.leggiInteroPositivo(messaggio);
	}
	
	public String richiestaNome(String messaggio) {
		return InputDati.leggiStringaNonVuota(messaggio);
	}
	
	public String richiestaUnitaMisura(String messaggio) {
		//devo imporre scelta tra kg,l,hg (enum)?
		return InputDati.leggiStringaNonVuota(messaggio);
	}
	
	public Float richiestaConsumoProCapite(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}
	
	public Float richiestaQuantita(String messaggio) {
		return InputDati.leggiFloatPositivo(messaggio);
	}
	
	public boolean richiestaNuovaAggiunta(String messaggio) {
		return InputDati.yesOrNo(messaggio);
	}
	
	public void stampaParametriRistorante(LocalDate dataCorrente, int nPosti, int caricoLavoroPersona, int caricoLavoroRistorante) {
		System.out.println();
		System.out.println("Questi sono i valori dei parametri del ristorante: ");
		System.out.println("La data corrente e' " + dataCorrente.getDayOfMonth() + "/" + dataCorrente.getMonthValue() +
				"/" + dataCorrente.getYear() + " ed e' " + dataCorrente.getDayOfWeek());
		System.out.println("Il numero di posti a sedere del ristorante e' " + nPosti);
		System.out.println("Il carico di lavoro per persona e' " + caricoLavoroPersona);
		System.out.println("Il carico lavoro gestibile dal ristorante per un pasto e' " + caricoLavoroRistorante);
		System.out.println();
	}
}
