package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import it.unibs.ing.progetto.ristorante.model.Agenda;
import it.unibs.ing.progetto.ristorante.model.Gestione;
import it.unibs.ing.progetto.ristorante.model.Magazzino;
import it.unibs.ing.progetto.ristorante.model.MenuComponent;
import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class ReaderXMLRistorante {
	private static final String ERRORE_READER = "Errore nell'inizializzazione del reader: ";
	private static final String STRINGA_INIZIO_LETTURA = "Inzio lettura file: ";
	private static final String STRINGA_FINE_LETTURA = "Fine lettura file: ";
	private static final String CARICO_LAVORO_RISTORANTE = "caricoLavoroRistorante";
	private static final String CARICO_LAVORO_PER_PERSONA = "caricoLavoroPerPersona";
	private static final String NUMERO_POSTI = "numeroPosti";
	private static final String RICETTA = "Ricetta";
	private static final String NOME_PIATTO = "nomePiatto";
	private static final String CARICO_LAVORO = "caricoLavoro";
	private static final String INGREDIENTE = "Ingrediente";
	private static final String NOME = "nome";
	private static final String DOSE = "dose";
	private static final String UNITA_MISURA = "unitaMisura";
	private static final String PORZIONI = "porzioni";
	private static final String GIORNO = "giorno";
	private static final String MESE = "mese";
	private static final String ANNO = "anno";

	public static Ristorante leggiXML(String filename) {
		LocalDate dataCorrente = null;
		int caricoLavoroPerPersona = 0;
		int numeroPostiASedere = 0;
		int caricoLavoroRistorante = 0;
		ArrayList<Piatto> elencoPiatti = new ArrayList<>();
		ArrayList<MenuTematico> elencoMenuTematici = new ArrayList<>();
		ArrayList<Prodotto> insiemeBevande = new ArrayList<>();
		ArrayList<Prodotto> insiemeGeneriExtra = new ArrayList<>();
		ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

		// Questo frammento di codice serve a creare ed istanziare la variabile xmlr di
		// tipo XMLStreamReader,
		// che sar� utilizzata per leggere il file XML
		XMLInputFactory xmlif;
		XMLStreamReader xmlr;
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
			System.out.println(STRINGA_INIZIO_LETTURA + filename);
			// Legge il File xml fino a quando ci sono eventi di parsing disponibili
			while (xmlr.hasNext()) {
				xmlr.next();
				if (xmlr.isStartElement()) {
					switch (xmlr.getLocalName()) {
						case "ParametriRistorante":
							numeroPostiASedere = Integer.parseInt(xmlr.getAttributeValue(null, NUMERO_POSTI));
							caricoLavoroPerPersona = Integer
									.parseInt(xmlr.getAttributeValue(null, CARICO_LAVORO_PER_PERSONA));
							caricoLavoroRistorante = Integer
									.parseInt(xmlr.getAttributeValue(null, CARICO_LAVORO_RISTORANTE));
							break;
						case "DataCorrente":
							int giorno = Integer.parseInt(xmlr.getAttributeValue(null, GIORNO));
							int mese = Integer.parseInt(xmlr.getAttributeValue(null, MESE));
							int anno = Integer.parseInt(xmlr.getAttributeValue(null, ANNO));
							dataCorrente = LocalDate.of(anno, mese, giorno);
							break;
						case "Ricetta":
							String nomePiatto = xmlr.getAttributeValue(null, NOME_PIATTO);
							int porzioni = Integer.parseInt(xmlr.getAttributeValue(null, PORZIONI));
							int caricoLavoro = Integer.parseInt(xmlr.getAttributeValue(null, CARICO_LAVORO));
							ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
							ArrayList<Periodo> elencoPeriodi = new ArrayList<>();
							while (xmlr.hasNext()) {
								xmlr.next();
								if (xmlr.isStartElement()) {
									switch (xmlr.getLocalName()) {
										case INGREDIENTE:
											String nomeIngrediente = xmlr.getAttributeValue(null, NOME);
											float dose = Float.parseFloat(xmlr.getAttributeValue(null, DOSE));
											UnitaMisura unitaMisura = UnitaMisura
													.valueOf(xmlr.getAttributeValue(null, UNITA_MISURA).toUpperCase());
											Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura);
											elencoIngredienti.add(ingrediente);
											break;
										case "DataInizio":
											int giornoInizio = Integer.parseInt(xmlr.getAttributeValue(null, GIORNO));
											int meseInizio = Integer.parseInt(xmlr.getAttributeValue(null, MESE));
											int annoInizio = Integer.parseInt(xmlr.getAttributeValue(null, ANNO));
											LocalDate dataInizio = LocalDate.of(annoInizio, meseInizio, giornoInizio);
											xmlr.next();
											xmlr.next();
											xmlr.next();
											int giornoFine = Integer.parseInt(xmlr.getAttributeValue(null, GIORNO));
											int meseFine = Integer.parseInt(xmlr.getAttributeValue(null, MESE));
											int annoFine = Integer.parseInt(xmlr.getAttributeValue(null, ANNO));
											LocalDate dataFine = LocalDate.of(annoFine, meseFine, giornoFine);
											Periodo periodo = new Periodo(dataInizio, dataFine);
											elencoPeriodi.add(periodo);
											break;
									}
								}
								if (xmlr.isEndElement() && xmlr.getLocalName().equals(RICETTA)) {
									Ricetta ricetta = new Ricetta(elencoIngredienti, porzioni, caricoLavoro);
									Piatto piatto = new Piatto(nomePiatto, caricoLavoro, ricetta, elencoPeriodi);
									elencoPiatti.add(piatto);
									break;
								}
							}
							break;

						case "MenuTematico":
							String nomeMenu = xmlr.getAttributeValue(null, "nome");
							int caricoLavoroMenuTematico = Integer
									.parseInt(xmlr.getAttributeValue(null, "caricoLavoro"));
							ArrayList<MenuComponent> elencoPiattiMenuTematico = new ArrayList<>();
							ArrayList<Periodo> elencoPeriodiMenuTematico = new ArrayList<>();
							while (xmlr.hasNext()) {
								xmlr.next();
								if (xmlr.isStartElement()) {
									switch (xmlr.getLocalName()) {
										case "Piatto":
											String nomePiattoMenuTematico = xmlr.getAttributeValue(null, "nome");
											// metodo che data una stringa, ritorna il piatto da elenco piatti che ha
											// come nome quella stringa
											Piatto piattoMenuTematico = prendiPiattoConNome(nomePiattoMenuTematico,
													elencoPiatti);
											elencoPiattiMenuTematico.add(piattoMenuTematico);

											break;
										case "DataInizio":
											int giornoInizio = Integer.parseInt(xmlr.getAttributeValue(null, "giorno"));
											int meseInizio = Integer.parseInt(xmlr.getAttributeValue(null, "mese"));
											int annoInizio = Integer.parseInt(xmlr.getAttributeValue(null, "anno"));
											LocalDate dataInizio = LocalDate.of(annoInizio, meseInizio, giornoInizio);
											xmlr.next();
											xmlr.next();
											xmlr.next();
											int giornoFine = Integer.parseInt(xmlr.getAttributeValue(null, "giorno"));
											int meseFine = Integer.parseInt(xmlr.getAttributeValue(null, "mese"));
											int annoFine = Integer.parseInt(xmlr.getAttributeValue(null, "anno"));
											LocalDate dataFine = LocalDate.of(annoFine, meseFine, giornoFine);
											Periodo periodo = new Periodo(dataInizio, dataFine);
											elencoPeriodiMenuTematico.add(periodo);
											break;
									}
								}
								if (xmlr.isEndElement() && xmlr.getLocalName().equals("MenuTematico")) {
									MenuTematico menuTematico = new MenuTematico(nomeMenu, elencoPiattiMenuTematico,
											caricoLavoroMenuTematico, elencoPeriodiMenuTematico);
									elencoMenuTematici.add(menuTematico);
									break;
								}
							}
							break;

						case "Bevanda":
							String nomeBevanda = xmlr.getAttributeValue(null, "nome");
							float consumoProCapiteBevanda = Float
									.parseFloat(xmlr.getAttributeValue(null, "consumoProCapite"));
							UnitaMisura unitaMisuraBevanda = UnitaMisura
									.valueOf(xmlr.getAttributeValue(null, "unitaMisura").toUpperCase());
							Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, unitaMisuraBevanda);
							insiemeBevande.add(bevanda);
							break;

						case "GenereExtra":
							String nomeGenereExtra = xmlr.getAttributeValue(null, "nome");
							float consumoProCapiteGenereExtra = Float
									.parseFloat(xmlr.getAttributeValue(null, "consumoProCapite"));
							UnitaMisura unitaMisuraGenereExtra = UnitaMisura
									.valueOf(xmlr.getAttributeValue(null, "unitaMisura").toUpperCase());
							Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra,
									unitaMisuraGenereExtra);
							insiemeGeneriExtra.add(genereExtra);
							break;

						case "Prenotazione":
							LocalDate dataPrenotazione = null;
							int numeroCoperti = Integer.parseInt(xmlr.getAttributeValue(null, "numeroCoperti"));
							String cliente = xmlr.getAttributeValue(null, "cliente");
							HashMap<Piatto, Integer> piattiComanda = new HashMap<>();
							while (xmlr.hasNext()) {
								xmlr.next();
								if (xmlr.isStartElement()) {
									switch (xmlr.getLocalName()) {

										case "DataPrenotazione":
											int giornoPrenotazione = Integer
													.parseInt(xmlr.getAttributeValue(null, "giorno"));
											int mesePrenotazione = Integer
													.parseInt(xmlr.getAttributeValue(null, "mese"));
											int annoPrenotazione = Integer
													.parseInt(xmlr.getAttributeValue(null, "anno"));
											dataPrenotazione = LocalDate.of(annoPrenotazione, mesePrenotazione,
													giornoPrenotazione);
											break;
										case "Piatto":
											String nomePiattoComanda = xmlr.getAttributeValue(null, "nome");
											int quantita = Integer.parseInt(xmlr.getAttributeValue(null, "quantita"));
											Piatto piattoComanda = prendiPiattoConNome(nomePiattoComanda, elencoPiatti);
											piattiComanda.put(piattoComanda, quantita);
											break;
									}
								}
								if (xmlr.isEndElement() && xmlr.getLocalName().equals("Prenotazione")) {
									Prenotazione prenotazione = new Prenotazione(cliente, numeroCoperti, piattiComanda,
											dataPrenotazione);
									elencoPrenotazioni.add(prenotazione);
									break;
								}
							}
							break;
					}
					xmlr.next();
				}
			}
			System.out.println(STRINGA_FINE_LETTURA + filename);
		} catch (Exception e) {
			System.out.println(ERRORE_READER);
			System.out.println(e.getMessage());
		}

		Gestione gestione = new Gestione(caricoLavoroPerPersona, numeroPostiASedere, caricoLavoroRistorante,
				elencoPiatti, elencoMenuTematici, insiemeBevande, insiemeGeneriExtra);
		Agenda agenda = new Agenda(elencoPrenotazioni);
		Ristorante model = new Ristorante(gestione, agenda, new Magazzino());
		model.setDataCorrente(dataCorrente);

		return model;
	}

	public static Piatto prendiPiattoConNome(String nomeScelto, ArrayList<Piatto> elencoPiatti) {
		Piatto piattoScelto = null;
		for (Piatto p : elencoPiatti) {
			if (nomeScelto.equalsIgnoreCase(p.getNome())) {
				piattoScelto = p;
				break;
			}
		}
		return piattoScelto;
	}
}