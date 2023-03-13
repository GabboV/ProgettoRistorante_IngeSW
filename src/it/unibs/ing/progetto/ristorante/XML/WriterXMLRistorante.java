package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.unibs.ing.progetto.ristorante.model.MenuTematico;
import it.unibs.ing.progetto.ristorante.model.Periodo;
import it.unibs.ing.progetto.ristorante.model.Piatto;
import it.unibs.ing.progetto.ristorante.model.Prodotto;
import it.unibs.ing.progetto.ristorante.model.Ricetta;
import it.unibs.ing.progetto.ristorante.model.Ristorante;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class WriterXMLRistorante {
    private static final String DATA_CORRENTE = "DataCorrente";

	private static final String CARICO_LAVORO_RISTORANTE = "caricoLavoroRistorante";

	private static final String CARICO_LAVORO_PER_PERSONA = "caricoLavoroPerPersona";

	private static final String NUMERO_POSTI = "numeroPosti";

	private static final String PARAMETRI_RISTORANTE = "ParametriRistorante";

	private static final String MSG_ERROR_WRITER = "Errore nell'inizializzazione del writer:";
    
    private static final String ELENCO_RICETTE = "ElencoRicette";
    private static final String RICETTA = "Ricetta";
    private static final String NOME_PIATTO = "nomePiatto";
    private static final String CARICO_LAVORO = "caricoLavoro";
    private static final String INGREDIENTE = "Ingrediente";
    private static final String NOME_INGREDIENTE = "nomeIngrediente";
    private static final String DOSE = "dose";
    private static final String UNITA_MISURA = "unitaMisura";
    private static final String PORZIONI = "porzioni";
    private static final String DATA_INIZIO = "DataInizio";
    private static final String DATA_FINE = "DataFine";
    private static final String GIORNO = "giorno";
    private static final String MESE = "mese";
    private static final String ANNO = "anno";
    private static final String STRINGA_FINE = "Il file e' stato generato con successo.";
    

    /**
     * Costruttore del WriterXML
     */
    public WriterXMLRistorante(){}

    
    public void writeXMLRistorante(Ristorante model, String filePath) {
        //Questo frammento di codice serve a creare ed istanziare la variabile xmlw di tipo XMLStreamWriter, che
        //sara utilizzata per scrivere il file XML. Viene inoltre inizializzato il documento XML.
        XMLOutputFactory xmlof;
        XMLStreamWriter xmlw;
        try {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(filePath), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");
        } catch (Exception e) {
            System.out.println(MSG_ERROR_WRITER);
            System.out.println(e.getMessage());
            return;
        }
        
        try {
            xmlw.writeCharacters("\n");
            stampaParametri(model, xmlw);
            stampaElencoRicette(model.getCorrispondenzePiattoRicetta(), xmlw);
        } catch (Exception e) { // se trova un errore viene eseguita questa parte
            System.out.println(e.getMessage());
            return;
        }
        try {
            xmlw.writeCharacters("\n");
            xmlw.writeEndDocument();
            xmlw.flush();
            xmlw.close();
            System.out.println(STRINGA_FINE);
        } catch (Exception e) { // se trova un errore viene eseguita questa parte
            System.out.println(e.getMessage());
        }
    }

    
    private void stampaParametri(Ristorante model, XMLStreamWriter xmlw) throws XMLStreamException{
    	xmlw.writeStartElement(PARAMETRI_RISTORANTE);
    	xmlw.writeAttribute(NUMERO_POSTI, Integer.toString(model.getNumeroPostiASedere()));
        xmlw.writeAttribute(CARICO_LAVORO_PER_PERSONA, Integer.toString(model.getCaricoLavoroPerPersona()));
        xmlw.writeAttribute(CARICO_LAVORO_RISTORANTE, Integer.toString(model.getCaricoLavoroRistorante()));	
        xmlw.writeCharacters("\n"+"\t");
        LocalDate dataCorrente = model.getDataCorrente();
        xmlw.writeStartElement(DATA_CORRENTE);
        xmlw.writeAttribute(GIORNO, Integer.toString(dataCorrente.getDayOfMonth()));
        xmlw.writeAttribute(MESE, Integer.toString(dataCorrente.getMonthValue()));
        xmlw.writeAttribute(ANNO, Integer.toString(dataCorrente.getYear()));
        xmlw.writeEndElement();
        xmlw.writeCharacters("\n"+"\t");
    }
    
    private void stampaElencoRicette(HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta, XMLStreamWriter xmlw) throws XMLStreamException{
    	xmlw.writeStartElement(ELENCO_RICETTE);
        xmlw.writeCharacters("\n"+"\t");
        for (Entry<Piatto, Ricetta> set : corrispondenzePiattoRicetta.entrySet()) {
        	Piatto p = set.getKey();
        	Ricetta r = set.getValue();
        	stampaRicetta(p, r, xmlw);
        }
        xmlw.writeEndElement();
    }
    
    private void stampaRicetta(Piatto p, Ricetta r, XMLStreamWriter xmlw) throws XMLStreamException {
    	xmlw.writeCharacters("\t");
        xmlw.writeStartElement(RICETTA);
        xmlw.writeAttribute(NOME_PIATTO, p.getNomePiatto());
        xmlw.writeAttribute(PORZIONI, Float.toString(r.getNumeroPorzioni()));
        xmlw.writeAttribute(CARICO_LAVORO, Float.toString(p.getCaricoLavoro()));	
        xmlw.writeCharacters("\n");
        stampaElencoIngredienti(r.getElencoIngredienti(), xmlw);
        stampaElencoPeriodiValidita(p.getPeriodiValidita(), xmlw);
        xmlw.writeCharacters("\t"+"\t");
        xmlw.writeEndElement();
        xmlw.writeCharacters("\n"+"\t");
    }
    
    private void stampaElencoIngredienti(ArrayList<Prodotto> elencoIngredienti, XMLStreamWriter xmlw) throws XMLStreamException{
    	for (Prodotto i : elencoIngredienti) {
            xmlw.writeCharacters("\t"+"\t"+"\t");
            xmlw.writeStartElement(INGREDIENTE);
            xmlw.writeAttribute(NOME_INGREDIENTE, i.getNome());
            xmlw.writeAttribute(DOSE, Float.toString(i.getQuantita()));
            xmlw.writeAttribute(UNITA_MISURA, i.getUnitaMisura());
            xmlw.writeEndElement();
            xmlw.writeCharacters("\n");
    	}
    }
    
    public void stampaElencoPeriodiValidita(ArrayList<Periodo> elencoPeriodiValidi, XMLStreamWriter xmlw) throws XMLStreamException{
    	for (Periodo p : elencoPeriodiValidi) {
    		LocalDate dataInizio = p.getDataInizio();
    		LocalDate dataFine = p.getDataFine();
            xmlw.writeCharacters("\t"+"\t"+"\t");
            xmlw.writeStartElement(DATA_INIZIO);
            xmlw.writeAttribute(GIORNO, Integer.toString(dataInizio.getDayOfMonth()));
            xmlw.writeAttribute(MESE, Integer.toString(dataInizio.getMonthValue()));
            xmlw.writeAttribute(ANNO, Integer.toString(dataInizio.getYear()));
            xmlw.writeEndElement();
            xmlw.writeCharacters("\n");
            xmlw.writeCharacters("\t"+"\t"+"\t");
            xmlw.writeStartElement(DATA_FINE);
            xmlw.writeAttribute(GIORNO, Integer.toString(dataFine.getDayOfMonth()));
            xmlw.writeAttribute(MESE, Integer.toString(dataFine.getMonthValue()));
            xmlw.writeAttribute(ANNO, Integer.toString(dataFine.getYear()));
            xmlw.writeEndElement();
            xmlw.writeCharacters("\n");
    	}
	}
    
    public void stampaElencoMenuTematici(ArrayList<MenuTematico> elencoMenuTematici, XMLStreamWriter xmlw) throws XMLStreamException{
    	
    }
}