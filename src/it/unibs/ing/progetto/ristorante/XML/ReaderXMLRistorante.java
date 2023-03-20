package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prenotazione;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;
import it.unibs.ing.progetto.ristorante.model.UnitaMisura;

import java.beans.DefaultPersistenceDelegate;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReaderXMLRistorante {
    private static final String ERRORE_READER = "Errore nell'inizializzazione del reader: ";
    private static final String STRINGA_INIZIO_LETTURA = "Inzio lettura file: ";
    private static final String STRINGA_FINE_LETTURA = "Fine lettura file: ";
    private static final String ELENCO_INGREDIENTI = "ElencoIngredienti";
	private static final String ELENCO_PERIODI = "ElencoPeriodi";
	private static final String MENU_TEMATICO = "MenuTematico";
	private static final String ELENCO_MENU_TEMATICI = "ElencoMenuTematici";
	private static final String PIATTO = "Piatto";
	private static final String ELENCO_PIATTI = "ElencoPiatti";
	private static final String DATA_CORRENTE = "DataCorrente";
	private static final String CARICO_LAVORO_RISTORANTE = "caricoLavoroRistorante";
	private static final String CARICO_LAVORO_PER_PERSONA = "caricoLavoroPerPersona";
	private static final String NUMERO_POSTI = "numeroPosti";
	private static final String PARAMETRI_RISTORANTE = "ParametriRistorante";
    private static final String ELENCO_RICETTE = "ElencoRicette";
    private static final String RICETTA = "Ricetta";
    private static final String NOME_PIATTO = "nomePiatto";
    private static final String CARICO_LAVORO = "caricoLavoro";
    private static final String INGREDIENTE = "Ingrediente";
    private static final String NOME = "nome";
    private static final String DOSE = "dose";
    private static final String UNITA_MISURA = "unitaMisura";
    private static final String PORZIONI = "porzioni";
    private static final String DATA_INIZIO = "DataInizio";
    private static final String DATA_FINE = "DataFine";
    private static final String GIORNO = "giorno";
    private static final String MESE = "mese";
    private static final String ANNO = "anno";

    //fatto male
    //da dividere in piu metodi se possibile
    //manca lettura insiemeBevande e insiemeGeneriExtra
    public static Ristorante leggiXML (String filename) {
    	LocalDate dataCorrente = null;
    	int caricoLavoroPerPersona = 0;
    	int numeroPostiASedere = 0;
    	int caricoLavoroRistorante = 0;
    	ArrayList<Piatto> elencoPiatti = new ArrayList<>();
    	ArrayList<MenuTematico> elencoMenuTematici = new ArrayList<>();
    	ArrayList<Prodotto> insiemeBevande = new ArrayList<>();
    	ArrayList<Prodotto> insiemeGeneriExtra = new ArrayList<>();
    	ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

    	//Questo frammento di codice serve a creare ed istanziare la variabile xmlr di tipo XMLStreamReader,
    	//che sarà utilizzata per leggere il file XML
    	XMLInputFactory xmlif;
    	XMLStreamReader xmlr;
    	try {
    		xmlif = XMLInputFactory.newInstance();
    		xmlr = xmlif.createXMLStreamReader(filename, new FileInputStream(filename));
    		System.out.println(STRINGA_INIZIO_LETTURA+filename);
    		//Legge il File xml fino a quando ci sono eventi di parsing disponibili
    		while (xmlr.hasNext()) {
    			xmlr.next();
    			if (xmlr.isStartElement()) {
    				switch (xmlr.getLocalName()) {
    				case "ParametriRistorante":
    					numeroPostiASedere = Integer.parseInt(xmlr.getAttributeValue(null, "numeroPosti"));
    					caricoLavoroPerPersona = Integer.parseInt(xmlr.getAttributeValue(null, "caricoLavoroPerPersona"));
    					caricoLavoroRistorante = Integer.parseInt(xmlr.getAttributeValue(null, "caricoLavoroRistorante"));
    					break;
    				case "DataCorrente":
    					int giorno = Integer.parseInt(xmlr.getAttributeValue(null, "giorno"));
    					int mese = Integer.parseInt(xmlr.getAttributeValue(null, "mese"));
    					int anno = Integer.parseInt(xmlr.getAttributeValue(null, "anno"));
    					dataCorrente = LocalDate.of(anno, mese, giorno);
    					break;
    				case "Ricetta":
    					String nomePiatto = xmlr.getAttributeValue(null, "nomePiatto");
    					int porzioni = Integer.parseInt(xmlr.getAttributeValue(null, "porzioni"));
    					int caricoLavoro = Integer.parseInt(xmlr.getAttributeValue(null, "caricoLavoro"));
    					ArrayList<Prodotto> elencoIngredienti = new ArrayList<>();
    					ArrayList<Periodo> elencoPeriodi = new ArrayList<>();
    					while (xmlr.hasNext()) {
    						xmlr.next();
    						if (xmlr.isStartElement()) {
    							switch (xmlr.getLocalName()) {
    							case "Ingrediente":
    								String nomeIngrediente = xmlr.getAttributeValue(null, "nome");
    								float dose = Float.parseFloat(xmlr.getAttributeValue(null, "dose"));
    								UnitaMisura unitaMisura = UnitaMisura.valueOf(xmlr.getAttributeValue(null, "unitaMisura")) ;
    								Prodotto ingrediente = new Prodotto(nomeIngrediente, dose, unitaMisura );
    								elencoIngredienti.add(ingrediente);
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
    								elencoPeriodi.add(periodo);
    								break;
    							}
    						}
    						if (xmlr.isEndElement() && xmlr.getLocalName().equals("Ricetta")) {
    							Ricetta ricetta = new Ricetta(elencoIngredienti, porzioni, caricoLavoro);
    							Piatto piatto = new Piatto(nomePiatto, caricoLavoro, ricetta, elencoPeriodi);
    							elencoPiatti.add(piatto);
    							break;
    						}
    					}
    					break;
    					
    				case "MenuTematico":
    					String nomeMenu = xmlr.getAttributeValue(null, "nome");
						int caricoLavoroMenuTematico = Integer.parseInt(xmlr.getAttributeValue(null, "caricoLavoro"));
    					ArrayList<Piatto> elencoPiattiMenuTematico = new ArrayList<>();
    					ArrayList<Periodo> elencoPeriodiMenuTematico = new ArrayList<>();
    					while (xmlr.hasNext()) {
    						xmlr.next();
    						if (xmlr.isStartElement()) {
    							switch (xmlr.getLocalName()) {
    							case "Piatto":
    								String nomePiattoMenuTematico = xmlr.getAttributeValue(null, "nome");
    								//metodo che data una stringa, ritorna il piatto da elenco piatti che ha come nome quella stringa
    								Piatto piattoMenuTematico = prendiPiattoConNome(nomePiattoMenuTematico, elencoPiatti);
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
        						MenuTematico menuTematico = new MenuTematico(nomeMenu, elencoPiattiMenuTematico, caricoLavoroMenuTematico, elencoPeriodiMenuTematico);
        						elencoMenuTematici.add(menuTematico);
        						break;
        					}
    					}
    					break;
    					
    				case "Bevanda":
    					String nomeBevanda = xmlr.getAttributeValue(null, "nome");
    					float consumoProCapiteBevanda = Float.parseFloat(xmlr.getAttributeValue(null, "consumoProCapite"));
    					UnitaMisura unitaMisuraBevanda = UnitaMisura.valueOf(xmlr.getAttributeValue(null, "unitaMisura")) ;
    					Prodotto bevanda = new Prodotto(nomeBevanda, consumoProCapiteBevanda, unitaMisuraBevanda);
    					insiemeBevande.add(bevanda);
    					break;
    					
    				case "GenereExtra":
    					String nomeGenereExtra = xmlr.getAttributeValue(null, "nome");
    					float consumoProCapiteGenereExtra = Float.parseFloat(xmlr.getAttributeValue(null, "consumoProCapite"));
    					UnitaMisura unitaMisuraGenereExtra = UnitaMisura.valueOf(xmlr.getAttributeValue(null, "unitaMisura")) ;
    					Prodotto genereExtra = new Prodotto(nomeGenereExtra, consumoProCapiteGenereExtra, unitaMisuraGenereExtra);
    					insiemeGeneriExtra.add(genereExtra);
    					break;
    					
    				case "Prenotazione":
						String codiceCliente = xmlr.getAttributeValue(null, "codiceCliente");
						LocalDate dataPrenotazione = null;
						int numeroCoperti = Integer.parseInt( xmlr.getAttributeValue(null, "numeroCoperti"));
    					HashMap<Piatto, Integer> piattiComanda = new HashMap<>();
    					while (xmlr.hasNext()) {
    						xmlr.next();
    						if (xmlr.isStartElement()) {
    							switch (xmlr.getLocalName()) {
    							case "DataPrenotazione":
    		    					int giornoPrenotazione = Integer.parseInt(xmlr.getAttributeValue(null, "giorno"));
    		    					int mesePrenotazione = Integer.parseInt(xmlr.getAttributeValue(null, "mese"));
    		    					int annoPrenotazione = Integer.parseInt(xmlr.getAttributeValue(null, "anno"));
    		    					dataPrenotazione = LocalDate.of(annoPrenotazione, mesePrenotazione, giornoPrenotazione);
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
        						Prenotazione prenotazione = new Prenotazione(numeroCoperti, piattiComanda, dataPrenotazione);
        						elencoPrenotazioni.add(prenotazione);
        						break;
        					}
    					}
    					break;
    				}
    				//Passa all’evento successivo
    				xmlr.next();
    			}
    		}
    		System.out.println(STRINGA_FINE_LETTURA+filename);
    	} catch (Exception e) {
    		System.out.println(ERRORE_READER);
    		System.out.println(e.getMessage());
    	}
    	Ristorante model = new Ristorante();
    	model.setDataCorrente(dataCorrente);
    	model.setCaricoLavoroPerPersona(caricoLavoroPerPersona);
    	model.setCaricoLavoroRistorante(caricoLavoroRistorante);
    	model.setNumeroPostiASedere(numeroPostiASedere);
    	model.setElencoPiatti(elencoPiatti);
    	model.setInsiemeBevande(insiemeBevande);
    	model.setInsiemeGeneriExtra(insiemeGeneriExtra);
    	model.setElencoMenuTematici(elencoMenuTematici);
    	model.setElencoPrenotazioni(elencoPrenotazioni);
		return model;
    }
    
    
    public static Piatto prendiPiattoConNome(String nomeScelto, ArrayList<Piatto> elencoPiatti) {
		Piatto piattoScelto = null;
		for (Piatto p : elencoPiatti) {
			if (nomeScelto.equalsIgnoreCase(p.getNomePiatto())) {
				piattoScelto = p;
				break;
			}
		}
		return piattoScelto;
	}
    
    /*
  	//Se trova un evento di tipo START.ELEMENT controlla il nome del tag dell'elemento corrente
    if (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
        String nomeTag = xmlr.getLocalName();
        //Se il tag è "city" allora crea un nuovo oggetto Nodo e lo aggiunge all'elencoNodi
        if (nomeTag.equals(CITY)) {
            idNodoPartenza = Integer.parseInt(xmlr.getAttributeValue(0));
            String name = xmlr.getAttributeValue(1);
            int x = Integer.parseInt(xmlr.getAttributeValue(2));
            int y = Integer.parseInt(xmlr.getAttributeValue(3));
            int h = Integer.parseInt(xmlr.getAttributeValue(4));
            elencoNodi.add(new Nodo(name, idNodoPartenza, x, y, h));
            //Crea un arrayList in cui verranno inseriti gli id delle città a cui si collega
            elencoIdNodiArrivo = new ArrayList<>();
        }
        //Se il tag è "link" allora inserisci l'id trovato in elencoNodiArrivo
        else if (nomeTag.equals(LINK)){
            //Continua finchè ci sono eventi di tipo START_ELEMENT (che hanno tag "link")
            while (xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
                int idNodoArrivo = Integer.parseInt(xmlr.getAttributeValue(0));
                elencoIdNodiArrivo.add(idNodoArrivo);
                xmlr.next();
                xmlr.next();
            }
        }
    }
    //Quando trova un evento di tipo END_ELEMENT
    if (xmlr.getEventType() == XMLStreamConstants.END_ELEMENT){
        String nomeTag = xmlr.getLocalName();
        //Se il tag è "city" imposta la mappaArchi
        if (nomeTag.equals(CITY)){
            mappaArchi.put(idNodoPartenza, elencoIdNodiArrivo);
        }
    }*/
	
}