package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.unibs.ing.progetto.ristorante.Ingrediente;
import it.unibs.ing.progetto.ristorante.Piatto;
import it.unibs.ing.progetto.ristorante.ProductSheet;
import it.unibs.ing.progetto.ristorante.Ricetta;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class WriterXMLRicette {
    private static final String MSG_ERROR_WRITER = "Errore nell'inizializzazione del writer:";
    private static final String OUTPUT = "ElencoRicette";
    private static final String RICETTA = "Ricetta";
    private static final String NOME_PIATTO = "nomePiatto";
    private static final String CARICO_LAVORO_PIATTO = "caricoLavoroPiatto";
    private static final String INGREDIENTE = "Ingrediente";
    private static final String NOME_INGREDIENTE = "nomeIngrediente";
    private static final String DOSE = "dose";
    private static final String MISURA = "misura";
    private static final String PORZIONI = "porzioni";
    private static final String CARICO_LAVORO_PORZIONE = "caricoLavoroPorzione";
    private static final String STRINGA_FINE = "Il file e' stato generato con successo.";

    /**
     * Costruttore del WriterXML
     */
    public WriterXMLRicette(){}

    
    public void writeXMLRicette(HashMap<Piatto, Ricetta> corrispondenzePiattoRicetta, String filePath) {
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
            xmlw.writeStartElement(OUTPUT);
            xmlw.writeCharacters("\n"+"\t");
            for (Entry<Piatto, Ricetta> set : corrispondenzePiattoRicetta.entrySet()) {
            	Piatto p = set.getKey();
            	Ricetta r = set.getValue();
            	stampaRicetta(p, r, xmlw);
           }
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

    private void stampaRicetta(Piatto p, Ricetta r, XMLStreamWriter xmlw) throws XMLStreamException {

        xmlw.writeStartElement(RICETTA);
        xmlw.writeAttribute(NOME_PIATTO, p.getNomePiatto());
        xmlw.writeAttribute(CARICO_LAVORO_PIATTO, Double.toString(p.getCaricoLavoro()));
        xmlw.writeCharacters("\n");
        for (ProductSheet i : r.getIngredienti()) {
            xmlw.writeCharacters("\t"+"\t");
            xmlw.writeStartElement(INGREDIENTE);
            xmlw.writeAttribute(NOME_INGREDIENTE, i.getIngrendient().getNome());
            xmlw.writeAttribute(DOSE, Double.toString(i.getAmount()));
            xmlw.writeAttribute(PORZIONI, i.getIngrendient().getNome());
            xmlw.writeAttribute(CARICO_LAVORO_PORZIONE, i.getIngrendient().getNome());
            xmlw.writeEndElement();
            xmlw.writeCharacters("\n");
        xmlw.writeCharacters("\t");
        xmlw.writeEndElement();
    }
    }
}