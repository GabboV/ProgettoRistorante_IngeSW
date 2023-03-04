package it.unibs.ing.progetto.ristorante.XML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import it.unibs.ing.progetto.ristorante.model.Piatto;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WriterXMLPrenotazioni {
    private static final String MSG_ERROR_WRITER = "Errore nell'inizializzazione del writer:";
    private static final String OUTPUT = "ElencoPrenotazioni";
    private static final String PRENOTAZIONE = "Prenotazione";
    private static final String NOME_CLIENTE = "nomeCliente";
    private static final String N_COPERTI = "nCoperti";
    private static final String DATA_PRENOTAZIONE = "dataPrenotazione";
    private static final String ELEM_ORDINE = "ElemOrdine";
    private static final String NOME = "nome";
    private static final String STRINGA_FINE = "Il file e' stato generato con successo.";

    /**
     * Costruttore del WriterXML
     */
    public WriterXMLPrenotazioni(){}

    
    public void writeXMLPrenotazioni(String nomeCliente, int nCoperti, LocalDate dataPrenotazione, ArrayList<String> ordine, String filePath) {
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
            stampaPrenotazione(nomeCliente, nCoperti, dataPrenotazione, ordine, xmlw);
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

    private void stampaPrenotazione(String nomeCliente, int nCoperti, LocalDate dataPrenotazione,
    			ArrayList<String> ordine, XMLStreamWriter xmlw) throws XMLStreamException {

        xmlw.writeStartElement(PRENOTAZIONE);
        xmlw.writeAttribute(NOME_CLIENTE, nomeCliente);
        xmlw.writeAttribute(N_COPERTI, Integer.toString(nCoperti));
        xmlw.writeAttribute(DATA_PRENOTAZIONE, dataPrenotazione.toString());
        xmlw.writeCharacters("\n");
        for (String s : ordine) {
            xmlw.writeCharacters("\t"+"\t");
            xmlw.writeStartElement(ELEM_ORDINE);
            xmlw.writeAttribute(NOME, s);
            xmlw.writeEndElement();
            xmlw.writeCharacters("\n");
        }
        xmlw.writeCharacters("\t");
        xmlw.writeEndElement();
    }
}